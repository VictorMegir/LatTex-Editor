package control.commands;

import view.GUI;
import model.TexDoc;
import model.VersionStrategyManager;
import model.strategies.Strategy;

public class Commit implements TexCommand
{
	private VersionStrategyManager versionmanager;
	private GUI window;
	
	public Commit(GUI window,VersionStrategyManager versionmanager)
	{
		this.versionmanager = versionmanager;
		this.window = window;
	}
	
	@Override
	public void execute() 
	{
		if(!versionmanager.isEnabled())
			return;
		String selectedVersion = window.getVersionBox().getSelectedItem().toString();
		versionmanager.setStartingStrat(selectedVersion);
		Strategy s = versionmanager.getStartingStrat();
		
		TexDoc version = versionmanager.getCurrentTexDoc().deepClone();
		version.setContents(window.getTextArea().getText());
		s.putVersion(version);
		versionmanager.setStrategy(s);
	}

	@Override
	public boolean check(String template) {
		return true;
	}
}