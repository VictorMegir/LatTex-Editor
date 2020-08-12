package tests;

import static org.junit.Assert.*;
import view.GUI;
import model.TexDoc;
import model.VersionStrategyManager;

import org.junit.Test;

import control.commands.Commit;

public class VersionStrategyManagerTest 
{
	private GUI window = new GUI();
	String original = "DORA THE EXPLORER";
	String edited = "SCOOBY DOO";
	TexDoc rat = new TexDoc("Victor","today","Mine(TM)","v.2.4",original,"article");
	VersionStrategyManager manager = new VersionStrategyManager(rat);
	
	@Test
	public void testStable() {
		manager.setStartingStrat("stable");
		Commit c = new Commit(window,manager);
		window.getTextArea().setText(edited);
		c.execute();
		String test = manager.getStartingStrat().getVersion().getContents();
		assertEquals("Stable strategy failed.",original,test);
	}
	
	@Test
	public void testVolatile() {
		manager.setStartingStrat("volatile");
		Commit c = new Commit(window,manager);
		window.getTextArea().setText(edited);
		c.execute();
		String test = manager.getStartingStrat().getVersion().getContents();
		assertEquals("Volatile strategy failed.",original,test);
	}
}
