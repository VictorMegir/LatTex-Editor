package model;

import java.nio.file.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.stream.Stream;

public class TexDocManager
{
	private HashMap <String, TexDoc> map = new HashMap <String, TexDoc>();
	
	public TexDocManager()
	{
		Path path = Paths.get("src");
		Path real = null;
		try {
			real = path.toRealPath(LinkOption.NOFOLLOW_LINKS);
		} catch (IOException e) {
			e.printStackTrace();
		}
		File folder = new File(real + "/templates");
		File[] listOfFiles = folder.listFiles();
		dynamicallyLoadTexDoc(listOfFiles);
	}
	
	public void dynamicallyLoadTexDoc(File[] listOfFiles)
	{
		String[] Values = new String[listOfFiles.length];
		String[] Keys = new String[listOfFiles.length];
		for (int i = 0; i < listOfFiles.length; i++) 
		{
			if (listOfFiles[i].isFile()) 
			{
				String name[] = listOfFiles[i].getName().split("-");
				Keys[i] = name[0];
				
				StringBuilder contentBuilder = new StringBuilder();
				try (Stream<String> stream = Files.lines( Paths.get(listOfFiles[i].getPath()), StandardCharsets.UTF_8)){
			        stream.forEach(s -> contentBuilder.append(s).append("\n"));
			    }
			    catch (IOException e){
			        e.printStackTrace();
			    }
				Values[i] = contentBuilder.toString();
				TexDoc fig  = new TexDoc("","","","",Values[i],Keys[i]);
				map.put(Keys[i],fig);
			}
		}
		map.put("no template",new TexDoc("","","","","","no template"));
	}
	
	public TexDoc getTexDoc(String Template)
	{
		TexDoc proto = map.get(Template);
		return proto.deepClone();
	}
}