package tests;

import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.Test;

import control.commands.*;
import model.*;
import view.*;

public class LoadCommandTest 
{
	private String test = "RERO RERO RERO";
	private String test1 = "xazamara";
	private GUI window = new GUI();
	private VersionStrategyManager vm = new VersionStrategyManager(new TexDoc("","","","","",""));
	
	@Test
	public void test() 
	{
		SaveCommand s = new SaveCommand(window,vm);
		LoadCommand l = new LoadCommand(window,vm);
		Commit c = new Commit(window, vm);
		RollbackCommand r = new RollbackCommand(window, vm);
		window.getTextArea().setText(test1);
		c.execute();
		window.getTextArea().setText(test);
		c.execute();
		vm.getStartingStrat().printHistory();
		s.execute();
		l.execute();
		assertEquals("Load failed.",test,vm.getCurrentTexDoc().getContents());
		
		List<TexDoc> g = vm.getStartingStrat().getEntireHistory();
		System.out.println(g.size());
		System.out.println(g);
		TexDoc res = g.get(g.size()-2);
		r.execute();
		assertEquals("Rollback after load failed.",window.getTextArea().getText(),res.getContents());
	}
}
