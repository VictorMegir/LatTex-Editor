package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import model.*;
import control.commands.Create;
import view.GUI;

public class TexDocManagerTest 
{
	private GUI window = new GUI();
	private VersionStrategyManager vm = new VersionStrategyManager(new TexDoc("","","","","",""));
	
	@Test
	public void testarticle() 
	{
		Create c = new Create(window,"article",vm);
		c.execute();
		assertEquals("Article creation failed.",window.getTextArea().getText(),c.getCreatedDoc().getContents());
	}
	@Test
	public void testletter() 
	{
		Create c = new Create(window,"letter",vm);
		c.execute();
		assertEquals("Article creation failed.",window.getTextArea().getText(),c.getCreatedDoc().getContents());
	}

	@Test
	public void testbook() 
	{
		Create c = new Create(window,"book",vm);
		c.execute();
		assertEquals("Article creation failed.",window.getTextArea().getText(),c.getCreatedDoc().getContents());
	}

	@Test
	public void testreport() 
	{
		Create c = new Create(window,"report",vm);
		c.execute();
		assertEquals("Article creation failed.",window.getTextArea().getText(),c.getCreatedDoc().getContents());
	}

	@Test
	public void testnotemp() 
	{
		Create c = new Create(window,"no template",vm);
		c.execute();
		assertEquals("Article creation failed.",window.getTextArea().getText(),c.getCreatedDoc().getContents());
	}
}
