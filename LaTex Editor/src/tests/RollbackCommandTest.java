package tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import view.*;
import model.*;
import control.commands.*;

public class RollbackCommandTest 
{
	private GUI window = new GUI();
	private TexDoc dummy = new TexDoc("","","","","","");
	private String original = "BOB THE BUILDER";
	private String change = "PAPYRUS";
	private VersionStrategyManager vm = new VersionStrategyManager(dummy);
	
	@Test
	public void test() 
	{
		dummy.setContents(original);
		window.getTextArea().setText(change);
		Commit c = new Commit(window,vm);
		c.execute();		
		RollbackCommand r = new RollbackCommand(window,vm);
		r.execute();
		assertEquals("Rollback failed.",original,window.getTextArea().getText());
	}
}