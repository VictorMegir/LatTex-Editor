package model.strategies;

import java.util.*;

import model.TexDoc;

public interface Strategy 
{
	public TexDoc getVersion();
	public void putVersion(TexDoc version);
	public void setEntireHistory(List<TexDoc> list);
	public List<TexDoc> getEntireHistory();
	public void removeVersion();
	public String name();
	public void printHistory();
}
