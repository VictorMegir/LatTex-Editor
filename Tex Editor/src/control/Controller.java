package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import control.commands.*;
import model.*;
import view.*;

public class Controller 
{
	private VersionStrategyManager versionmanager;
	private GUI window;
	private HashMap<String, TexCommand> commands = new HashMap<String, TexCommand>();
	
	public Controller(GUI window)
	{
		this.window = window;
		Path path = Paths.get("src");
		Path real = null;
		try {
			real = path.toRealPath(LinkOption.NOFOLLOW_LINKS);
		} catch (IOException e) {
			e.printStackTrace();
		}
		File folder = new File(real + "/commands_contents");
		LatexInit(folder);
		
		TexDoc dummy = new TexDoc("","","","","","");
		versionmanager = new VersionStrategyManager(dummy);
		versionmanager.setStartingStrat(window.getVersionBox().getSelectedItem().toString());
		versionmanager.setCurrentTexDoc(dummy);
		
		File stableVersionsFolder = new File(real + "/save_stable_documents");		
		if(stableVersionsFolder.listFiles().length > 0)
		{
			sorted(stableVersionsFolder);
			DisableInit();
			RollbackInit();
			window.getRollbackButton().setEnabled(true);
			enact("enable");
		}
		LoadInit();
	}
	
	public void sorted(File stableVersionsFolder)
	{
		File[] listOfStableFiles = stableVersionsFolder.listFiles();
		List<TexDoc> loadedHistory = new ArrayList<TexDoc>();  
		Arrays.sort(listOfStableFiles, new Comparator<File>()
		{
	        public int compare(File f1, File f2) 
	        {
	        	int x = Integer.parseInt(f1.getName().split("version")[1].split(".tex")[0]);
	        	long y = (long) Integer.parseInt(f2.getName().split("version")[1].split(".tex")[0]);
	        	return Long.valueOf(x).compareTo(y);
	        } 
		});
		for (int i = 0; i < listOfStableFiles.length; i++)
		{
			if (listOfStableFiles[i].isFile()) 
			{
				String name = listOfStableFiles[i].getName();
				TexDoc e;
				try {
					FileInputStream fileIn = new FileInputStream(stableVersionsFolder + "/" + name );
					ObjectInputStream in = new ObjectInputStream(fileIn);
					e =  (TexDoc) in.readObject();
					loadedHistory.add(e);
					in.close();
					fileIn.close();
				} catch (IOException ex) {
					ex.printStackTrace();
					return;
				} catch (ClassNotFoundException exc) {
					exc.printStackTrace();
				}
			}
		}
		versionmanager.getStartingStrat().setEntireHistory(loadedHistory);
		window.getTextArea().setText(loadedHistory.get(loadedHistory.size()-1).getContents());
	}
	
	public void LatexInit(File folder)
	{
		File[] listOfFiles = folder.listFiles();
		
		String[] Values = new String[listOfFiles.length];
		String[] Keys = new String[listOfFiles.length];
		for (int i = 0; i < listOfFiles.length; i++)
		{
			if (listOfFiles[i].isFile()) 
			{
				String name = listOfFiles[i].getName();
				Keys[i] = name;
				StringBuilder contentBuilder = new StringBuilder();
				try (Stream<String> stream = Files.lines( Paths.get(listOfFiles[i].getPath()), StandardCharsets.UTF_8)){
			        stream.forEach(s -> contentBuilder.append(s).append("\n"));
			    }
			    catch (IOException e){
			        e.printStackTrace();
			    }
				Values[i] = contentBuilder.toString();				
				LatexCommand c = new LatexCommand(Keys[i],window.getTextArea());
				
				c.setContents(Values[i]);
				commands.put(Keys[i],c);
			}
		}
	}
	
	public void DisableInit(){
		commands.put("disable", new DisableVersionTrackingCommand(versionmanager));
		commands.put("enable", new EnableVersionTrackingCommand(versionmanager));
	}
		
	public HashMap<String,TexCommand> getCommands(){
		return commands;
	}
	
	public void CreateInit(String template)
	{
		Create c = new Create(window,template,versionmanager);
		commands.put("create",c);
	}
	
	public void ChangeVersionInit(){
		commands.put("changeVersion",new ChangeVersionStrategyCommand(versionmanager));
	}
	
	public void CommitInit(){
		commands.put("commit",new Commit(window,versionmanager));
	}
	
	public void RollbackInit(){
		commands.put("rollback",new RollbackCommand(window,versionmanager));
	}
	
	public void SaveInit() {
		commands.put("save",new SaveCommand(window,versionmanager));
	}
	
	public void LoadInit() {
		commands.put("load",new LoadCommand(window,versionmanager));
	}
	
	public void enact(String commandName){
		commands.get(commandName).execute();
	}
	
	public boolean canUse(String template,String commandName){
		//System.out.println(commandName);
		return commands.get(commandName).check(template);
	}
}