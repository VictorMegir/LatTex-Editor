package control.commands;

import model.VersionStrategyManager;

public class EnableVersionTrackingCommand implements TexCommand
{
	private VersionStrategyManager vm;
	public EnableVersionTrackingCommand(VersionStrategyManager vm) {
		this.vm = vm;
	}
	
	@Override
	public void execute() {
		vm.enable();
	}
	
	@Override
	public boolean check(String template) {
		return true;
	}
}
