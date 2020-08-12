package control.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.swing.JFileChooser;

import model.*;
import view.*;

public class LoadCommand implements TexCommand 
{
	private GUI window;
	private VersionStrategyManager vm;
	private JFileChooser fileChooser;
	
	public LoadCommand(GUI window ,VersionStrategyManager vm) 
	{
		this.vm = vm;
		this.window = window;
		fileChooser = window.getFileChooser();
	}
	@Override
	public void execute() 
	{
		String address = "";
		String data = "";
		File folder;
		try {
			folder = fileChooser.getSelectedFile();
			address = folder.getAbsolutePath();
		}catch(NullPointerException ex) {
			address = "Test.tex";
		}
		
	    try 
	    {
	    	try {
	    		data = new String ( Files.readAllBytes( Paths.get(address) ));
	    	}catch(NoSuchFileException exc) {
	    		window.warning("File does not exist.");
	    	}
	        
	        vm.getCurrentTexDoc().setContents(data);
	        window.getTextArea().setText(data);
	        
	        List<TexDoc> loadedHistory = new ArrayList<TexDoc>();     
	        File versionFolder = new File(address.split(".tex")[0] + "_history");
			File[] listOfFiles = versionFolder.listFiles();
			try {
				sorted(listOfFiles);
			}
			catch(NullPointerException exc) {
				window.warning("Only TEX files are supported.");
				window.getTextArea().setText("");
			}
			for (int i = 0; i < listOfFiles.length; i++)
			{
				if (listOfFiles[i].isFile()) 
				{
					String name = listOfFiles[i].getName();
					TexDoc e;
					try {
						FileInputStream fileIn = new FileInputStream(versionFolder + "/" + name );
						ObjectInputStream in = new ObjectInputStream(fileIn);
						e = (TexDoc) in.readObject();
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
			vm.getStartingStrat().setEntireHistory(loadedHistory);
	    }
	    catch (IOException e){
	    	e.printStackTrace();
	    }
	}

	public void sorted(File[] listOfStableFiles)
	{
		Arrays.sort(listOfStableFiles, new Comparator<File>()
		{
	        public int compare(File f1, File f2) 
	        {
	        	int x = Integer.parseInt(f1.getName().split("version")[1].split(".tex")[0]);
	        	long y = (long) Integer.parseInt(f2.getName().split("version")[1].split(".tex")[0]);
	        	return Long.valueOf(x).compareTo(y);
	        } 
		});
	}
	
	@Override
	public boolean check(String template) {
		return true;
	}
}