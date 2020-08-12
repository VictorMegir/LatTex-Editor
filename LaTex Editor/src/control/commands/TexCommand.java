package control.commands;

public interface TexCommand 
{
	void execute();
	boolean check(String template);
}