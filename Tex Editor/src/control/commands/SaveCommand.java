package control.commands;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import model.*;
import view.GUI;

public class SaveCommand implements TexCommand
{
	private VersionStrategyManager vm;
	private JFileChooser fileChooser;
	private File save;
	private GUI window;
	
	public SaveCommand(GUI window ,VersionStrategyManager vm) 
	{
		this.vm = vm;
		this.window= window;
		fileChooser = window.getFileChooser();
	}
	
	@Override
	public void execute() 
	{
		File historyFolder;
		List<TexDoc> history;
		try 
		{
			File folder = fileChooser.getSelectedFile();
			save = new File(folder.getAbsolutePath() +".tex");
			historyFolder = new File(folder.getAbsolutePath() + "_history");
			historyFolder.mkdir();
			history = vm.getStartingStrat().getEntireHistory();
		}catch(NullPointerException ex) {
			save = new File("Test.tex");
			historyFolder = new File("Test_history");
			historyFolder.mkdir();
			try{
				history = vm.getStartingStrat().getEntireHistory();
			}catch(NullPointerException exc){
				history = new ArrayList<TexDoc>();
			}
			history.add(new TexDoc("","","","","",""));
		}
		
		Integer cv = 0;
		for(TexDoc c:history)
		{
			String filename = "version" + cv.toString() + ".tex";
			cv ++;
			File version = new File(historyFolder + "/" + filename);
			try 
			{
				FileOutputStream fileOut = new FileOutputStream(version);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				System.out.println(c.getContents());
				out.writeObject(c);
		        out.close();
		        fileOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		String data = window.getTextArea().getText();
		TexDoc saveVersion = vm.getCurrentTexDoc().deepClone();
		saveVersion.setContents(data);
		vm.setCurrentTexDoc(saveVersion);
        try 
        {
            FileOutputStream out = new FileOutputStream(save);
            out.write(vm.getCurrentTexDoc().getContents().getBytes());
            out.close();
        }catch(IOException ex) {
        	ex.printStackTrace();;
        }
	}

	@Override
	public boolean check(String template) {
		return true;
	}
}