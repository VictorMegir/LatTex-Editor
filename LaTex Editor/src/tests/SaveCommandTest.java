package tests;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;

import control.commands.*;
import model.*;
import view.*;

public class SaveCommandTest 
{
	private GUI window = new GUI();
	private VersionStrategyManager vm = new VersionStrategyManager(new TexDoc("","","","","",""));
	
	@Test
	public void test1() 
	{
		SaveCommand s = new SaveCommand(window, vm);
		window.getTextArea().setText("ORA ORA ORA");	
		s.execute();
		String content = "";
	    try {
	        content = new String ( Files.readAllBytes( Paths.get("Test.tex") ) );
	    }
	    catch (IOException e){
	    	content = "MUDA MUDA MUDA";
	    }
	    assertEquals("Save failed.",vm.getCurrentTexDoc().getContents(),content);
	}
}