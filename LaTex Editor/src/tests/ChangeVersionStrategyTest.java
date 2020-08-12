package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import control.commands.*;
import model.*;
import model.strategies.*;

public class ChangeVersionStrategyTest 
{
	private TexDoc dummy = new TexDoc("","","","","book","");
	private VersionStrategyManager vm = new VersionStrategyManager(dummy);
	
	@Test
	public void test1() 
	{
		vm.setStartingStrat("volatile");
		StableStrategy s = new StableStrategy(dummy);
		s.setEntireHistory(vm.getStartingStrat().getEntireHistory());
		ChangeVersionStrategyCommand c1 = new  ChangeVersionStrategyCommand(vm);
		c1.execute();
		assertEquals("Change Version Strategy test failed.",s.name(),vm.getStartingStrat().name());
		assertEquals("Change Version Strategy test failed.",s.getEntireHistory(),vm.getStartingStrat().getEntireHistory());
	}
	@Test
	public void test2() {
		vm.setStartingStrat("stable");
		VolatileStrategy v = new VolatileStrategy(dummy);
		v.setEntireHistory(vm.getStartingStrat().getEntireHistory());
		ChangeVersionStrategyCommand c2 = new  ChangeVersionStrategyCommand(vm);
		c2.execute();
		assertEquals("Change Version Strategy test failed.",v.name(),vm.getStartingStrat().name());
		assertEquals("Change Version Strategy test failed.",v.getEntireHistory(),vm.getStartingStrat().getEntireHistory());
	}
}