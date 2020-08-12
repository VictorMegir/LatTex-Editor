package control.commands;

import javax.swing.JTextArea;
import view.*;
import model.*;

public class Create implements TexCommand
{
	private TexDocManager manager;
	private String template,author = "",date = "",copyrights = "",versionID = ""; 
	private TexDoc created;
	private JTextArea text;
	private VersionStrategyManager vm;
	
	public Create(GUI window,String template,VersionStrategyManager vm)
	{
		this.vm = vm;
		manager = new TexDocManager();
		this.template = template;
		text = window.getTextArea();
		created = new TexDoc("","","","","","");
	}
	
	@Override
	public void execute() 
	{
		created = manager.getTexDoc(template);
		created.fixValues(author, date, copyrights, versionID);
		text.setText(created.getContents());
		vm.setCurrentTexDoc(created);
	}

	@Override
	public boolean check(String template) {
		return true;
	}
	
	public TexDoc getCreatedDoc(){
		return created;
	}
}