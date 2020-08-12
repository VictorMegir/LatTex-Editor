package model.strategies;

import java.util.ArrayList;
import java.util.List;

import model.TexDoc;

public class VolatileStrategy implements Strategy
{
	private List<TexDoc> save_volatile_documents = new ArrayList<TexDoc>();
	TexDoc createdDoc;
	public VolatileStrategy(TexDoc createdDoc)
	{
		this.createdDoc = createdDoc.deepClone();
		save_volatile_documents.add(createdDoc);	
	}

	@Override
	public TexDoc getVersion() {
		return createdDoc;
	}

	@Override
	public void putVersion(TexDoc version) {
		save_volatile_documents.add(version);
	}

	@Override
	public void setEntireHistory(List<TexDoc> list) {
		save_volatile_documents = list;
	}

	@Override
	public List<TexDoc> getEntireHistory() {
		return save_volatile_documents;
	}

	@Override
	public void removeVersion() {
		save_volatile_documents.remove(save_volatile_documents.size() - 1);
	}
	@Override
	public String name() {
		return "volatile";
	}

	@Override
	public void printHistory() {
		for(TexDoc c:save_volatile_documents){
			c.print();
		}
		
	}
}
