package control.commands;

import model.VersionStrategyManager;

public class DisableVersionTrackingCommand implements TexCommand
{
	private VersionStrategyManager vm;
	public DisableVersionTrackingCommand(VersionStrategyManager vm) {
		this.vm = vm;
	}
	
	@Override
	public void execute() {
		vm.disbale();
	}
	
	@Override
	public boolean check(String template) {
		return true;
	}
}
