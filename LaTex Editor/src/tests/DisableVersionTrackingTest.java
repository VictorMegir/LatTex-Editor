package tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import model.*;
import control.commands.*;
import view.GUI;

public class DisableVersionTrackingTest 
{
	private GUI window = new GUI();
	TexDoc dummy = new TexDoc("","","","","","");
	private VersionStrategyManager vm = new VersionStrategyManager(dummy);
	
	@Test
	public void test1() 
	{
		vm.setStartingStrat("volatile");
		DisableVersionTrackingCommand d1 = new DisableVersionTrackingCommand(vm);
		List<TexDoc> exp1 = new ArrayList<TexDoc>();
		exp1 = vm.getStartingStrat().getEntireHistory();
		Commit c1 = new Commit(window,vm);
		d1.execute();
		window.getTextArea().setText("POWER RANGERS");
		c1.execute();
		assertEquals("Disable Version Tracking test failed.",exp1,vm.getStartingStrat().getEntireHistory());
	}
	
	@Test
	public void test2() 
	{
		vm.setStartingStrat("stable");
		DisableVersionTrackingCommand d2 = new DisableVersionTrackingCommand(vm);
		List<TexDoc> exp2 = new ArrayList<TexDoc>();
		exp2 = vm.getStartingStrat().getEntireHistory();
		Commit c2 = new Commit(window,vm);
		d2.execute();
		window.getTextArea().setText("STRUMF");
		c2.execute();
		assertEquals("Disable Version Tracking test failed.",exp2,vm.getStartingStrat().getEntireHistory());
	}

	@Test
	public void test3() 
	{
		vm.setStartingStrat("volatile");
		DisableVersionTrackingCommand d3 = new DisableVersionTrackingCommand(vm);
		List<TexDoc> exp3 = new ArrayList<TexDoc>();
		exp3 = vm.getStartingStrat().getEntireHistory();
		EnableVersionTrackingCommand e3 = new EnableVersionTrackingCommand(vm);
		Commit c3 = new Commit(window,vm);
		d3.execute();
		e3.execute();
		window.getTextArea().setText("PIKATSU");
		c3.execute();
		assertEquals("Enable Version Tracking test failed.",exp3,vm.getStartingStrat().getEntireHistory());
	}
	
	@Test
	public void test4() 
	{
		String test = "OPTIMUS PRIME";
		vm.setStartingStrat("stable");
		List<TexDoc> exp4 = new ArrayList<TexDoc>();
		
		DisableVersionTrackingCommand d4 = new DisableVersionTrackingCommand(vm);		
		EnableVersionTrackingCommand e4 = new EnableVersionTrackingCommand(vm);
		Commit c4 = new Commit(window,vm);
		d4.execute();
		e4.execute();
		window.getTextArea().setText(test);
		c4.execute();
		
		exp4 = vm.getStartingStrat().getEntireHistory();
		exp4.get(exp4.size()-1).setContents(test);
		assertEquals("Enable Version Tracking test failed.",exp4,vm.getStartingStrat().getEntireHistory());
	}
}
