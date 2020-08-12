package model;

import java.io.Serializable;

public class TexDoc implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private String author;
	private String date;
	private String copyright;
	private String versionID;
	private String template;
	private String contents;
	
	public TexDoc(String author,String date,String copyright,String versionID,String contents,String template) 
	{
		this.author = author;
		this.date = date;
		this.versionID = versionID;
		this.copyright = copyright;
		this.template = template;
		this.contents = contents;
	}
	
	public void setContents(String newContents) {
		this.contents = newContents;
	}
	
	public String getContents() {
		return this.contents;
	}
	
	
	public void setVersionID(String newVersionID) {
		versionID = newVersionID;
	}
	
	public void setCopyright(String newCopyright) {
		this.copyright = newCopyright;
	}
	
	public void setDate(String newDate) {
		this.date = newDate;
	}
	
	
	public void setAuthor(String newAuthor) {
		this.author = newAuthor;
	}
	
	public TexDoc deepClone() {
		TexDoc clone = new TexDoc(new String(this.author),new String(this.date),new String(this.copyright),new String(this.versionID),new String(this.contents),new String(this.template));
		return clone;
	}
	
	public void fixValues(String author,String date,String copyright,String versionID) 
	{
		this.setAuthor(author);
		this.setDate(date);
		this.setCopyright(copyright);
		this.setVersionID(versionID);
	}
	
	public void print() 
	{
		System.out.println(this.author);
		System.out.println(this.date);
		System.out.println(this.versionID);
		System.out.println(this.copyright);
		System.out.println(this.contents);
		System.out.println(this.template);
	}
}