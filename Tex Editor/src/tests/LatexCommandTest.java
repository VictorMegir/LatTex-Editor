package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import view.GUI;
import control.Controller;
import control.commands.LatexCommand;

public class LatexCommandTest 
{
	GUI window = new GUI();
	Controller cont = new Controller(window);
	@Test
	public void testChapter() {
		window.getTextArea().setText("");
		String test = ((LatexCommand)cont.getCommands().get("chapter")).getContents();
		cont.enact("chapter");
		assertEquals("Chapter command failed.","\n" + test,window.getTextArea().getText());
	}
	@Test
	public void testSection() {
		window.getTextArea().setText("");
		String test = ((LatexCommand)cont.getCommands().get("section")).getContents();
		cont.enact("section");
		assertEquals("Section command failed.","\n" + test,window.getTextArea().getText());
	}
	@Test
	public void testSubsection() {
		window.getTextArea().setText("");
		String test = ((LatexCommand)cont.getCommands().get("subsection")).getContents();
		cont.enact("subsection");
		assertEquals("Subsection command failed.","\n" + test,window.getTextArea().getText());
	}
	@Test
	public void testSubsubsection() {
		window.getTextArea().setText("");
		String test = ((LatexCommand)cont.getCommands().get("subsubsection")).getContents();
		cont.enact("subsubsection");
		assertEquals("Subsubsection command failed.","\n" + test,window.getTextArea().getText());
	}
	@Test
	public void testEnumList() {
		window.getTextArea().setText("");
		String test = ((LatexCommand)cont.getCommands().get("enum list")).getContents();
		cont.enact("enum list");
		assertEquals("Enum list command failed.","\n" + test,window.getTextArea().getText());
	}
	@Test
	public void testItemList() {
		window.getTextArea().setText("");
		String test = ((LatexCommand)cont.getCommands().get("item list")).getContents();
		cont.enact("item list");
		assertEquals("Item list command failed.","\n" + test,window.getTextArea().getText());
	}
	@Test
	public void testTable() {
		window.getTextArea().setText("");
		String test = ((LatexCommand)cont.getCommands().get("table")).getContents();
		cont.enact("table");
		assertEquals("Table command failed.","\n" + test,window.getTextArea().getText());
	}
	@Test
	public void testFigure() {
		window.getTextArea().setText("");
		String test = ((LatexCommand)cont.getCommands().get("figure")).getContents();
		cont.enact("figure");
		assertEquals("Figure command failed.","\n" + test,window.getTextArea().getText());
	}
}
