package model.strategies;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.nio.file.*;

import model.TexDoc;

public class StableStrategy implements Strategy
{
	private TexDoc t;
	private String starting_address;
	private List<TexDoc> list = new ArrayList<TexDoc>();
	public StableStrategy(TexDoc t)
	{
		Path path = Paths.get("src");
		Path real = null;
		try {
			real = path.toRealPath(LinkOption.NOFOLLOW_LINKS);
		} catch (IOException e) {
			e.printStackTrace();
		}
		starting_address = real.toString() + "/save_stable_documents/";
		this.t = t.deepClone();
		list.add(t);
	}
	
	public void getFileFromDisk(String name)
	{
		TexDoc e;
		try {
			FileInputStream fileIn = new FileInputStream(starting_address + name);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			e = (TexDoc) in.readObject();
			e.print();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
			return;
		}
	}

	@Override
	public TexDoc getVersion() {
		return t;
	}

	@Override
	public void putVersion(TexDoc version)
	{
		File folder = new File(starting_address);
		int num_of_files = folder.listFiles().length;
		String filename = "version" + String.valueOf(num_of_files + 1) + ".tex";
		File file = new File(starting_address + "/" + filename);	
		try 
		{
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(version);
	        out.close();
	        fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		list.add(version);
	}
	
	@Override
	public void setEntireHistory(List<TexDoc> list) {
		this.list = list;
	}

	@Override
	public List<TexDoc> getEntireHistory() {
		return list;
	}

	@Override
	public void removeVersion() {
		list.remove(list.size() - 1);
	}

	@Override
	public String name() {
		return "stable";
	}

	@Override
	public void printHistory() {
		for(TexDoc c:list){
			c.print();
		}
	}
}