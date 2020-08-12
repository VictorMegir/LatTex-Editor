package tests;

import view.GUI;
import static org.junit.Assert.*;

import org.junit.Test;

public class EditDocTest 
{
	private GUI window = new GUI();
	@Test
	public void testEditContents() 
	{
		String edited = "SCOOBY DOO";
		String contents = window.getTextArea().getText();
		window.getTextArea().setText(contents + edited);
		assertEquals("Edit test failed.",contents + edited,window.getTextArea().getText());
	}
}
