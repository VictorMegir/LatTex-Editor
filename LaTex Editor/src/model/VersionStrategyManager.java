package model;

import java.util.HashMap;
import java.util.List;

import model.strategies.*;

public class VersionStrategyManager 
{
	private boolean enabled;
	private Strategy startingstrat;
	private TexDoc createdDoc,previous;
	private HashMap<String,Strategy> stratmap = new HashMap<String,Strategy>();
	
	public VersionStrategyManager(TexDoc createdDoc)
	{
		enabled = true;
		this.createdDoc = createdDoc;
		previous = createdDoc;
		stratmap.put("stable", new StableStrategy(createdDoc));
		stratmap.put("volatile", new VolatileStrategy(createdDoc));
	}
	
	public void setStartingStrat(String stratname){
		startingstrat = stratmap.get(stratname);
	}
	
	public Strategy getStartingStrat() {
		return startingstrat;
	}
	
	public boolean isEnabled(){
		return enabled;
	}
	
	public void enable(){
		enabled = true;
	}
	public void disbale(){
		enabled = false;
	}
	
	public void setStrategy(Strategy strat){
		startingstrat = strat;
	}

	public void setCurrentTexDoc(TexDoc current){
		createdDoc = current;
	}

	public TexDoc getCurrentTexDoc(){
		return createdDoc;
	}
	
	public boolean getPreviousTexDoc()
	{
		List<TexDoc> his = startingstrat.getEntireHistory();
		int tail = his.size()-2;
		try{
			previous = his.get(tail);
			his.remove(previous);
			return true;
		}
		catch(java.lang.ArrayIndexOutOfBoundsException e){
			return false;
		}
	}
	
	public void rollbackToPreviousTexDoc(){
		createdDoc = previous;
	}
}