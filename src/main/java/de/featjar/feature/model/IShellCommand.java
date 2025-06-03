package de.featjar.feature.model;

public interface IShellCommand { 
	
	void execute(ShellContext ctx, String[] args);	
}
