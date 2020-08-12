package control.commands;

import model.*;
import model.strategies.*;
import java.util.List;

public class ChangeVersionStrategyCommand implements TexCommand
{
	private Strategy newstrat;
	private Strategy oldstrat;
	private TexDoc doc;
	private List<TexDoc> history;
	private VersionStrategyManager vm;
	
	public ChangeVersionStrategyCommand(VersionStrategyManager vm)
	{
		this.vm = vm;
		oldstrat = vm.getStartingStrat();
		history = oldstrat.getEntireHistory();
	}
	
	@Override
	public void execute() 
	{
		doc = vm.getCurrentTexDoc();
		if(oldstrat.name().equals("stable")){
			newstrat = new VolatileStrategy(doc);
		}else{
			newstrat = new StableStrategy(doc);
		}
		newstrat.setEntireHistory(history);
		oldstrat = newstrat;
		vm.setStrategy(oldstrat);
	}

	@Override
	public boolean check(String StrategyName) 
	{
		if(StrategyName.equals(oldstrat.name())){
			return false;
		}
		return true;
	}
}