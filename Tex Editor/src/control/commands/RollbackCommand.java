package control.commands;

import model.*;
import view.*;

public class RollbackCommand implements TexCommand
{
	private VersionStrategyManager vm;
	private GUI window;
	
	public RollbackCommand(GUI window,VersionStrategyManager vm)
	{
		this.vm = vm;
		this.window = window;
	}
	
	@Override
	public void execute() 
	{
		boolean check = vm.getPreviousTexDoc();
		if(!check) {
			window.warning("No previous version exists.");
			return;
		}
		vm.rollbackToPreviousTexDoc();
		window.getTextArea().setText(vm.getCurrentTexDoc().getContents());
	}

	@Override
	public boolean check(String template) {
		return vm.isEnabled();
	}
}