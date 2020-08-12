package control.commands;

import javax.swing.JTextArea;

public class LatexCommand implements TexCommand 
{
	private String commandContents,commandName;
	private JTextArea text;
	
	public LatexCommand(String commandName,JTextArea text){
		this.text = text;
		this.commandName = commandName;
	}
	
	@Override
	public void execute() 
	{
		int cursor_pos = text.getCaretPosition();
		String written = text.getText();
		String hate = written.substring(0, cursor_pos) + "\n" + commandContents +  written.substring(cursor_pos);
		text.setText(hate);
	}

	public void setContents(String newContents){
		commandContents = newContents;
	}
	
	public String getContents(){
		return commandContents;
	}
	
	@Override
	public boolean check(String template) 
	{
		if(template.equals("article") && commandName.equals("chapter")){
			return false;
		}
		if(template.equals("letter") && (commandName.equals("chapter") || commandName.equals("section"))){
			return false;
		}
		return true;
	}
}