package de.featjar.feature.model;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import de.featjar.base.FeatJAR;

public abstract class AbstractShellCommand implements IShellCommand {
	
	private final String name;
	
	private static final Scanner in = new Scanner(System.in);
	
	public AbstractShellCommand(String name) {
		this.name = name;
//		commands.put(this.getCommandName(), this);
	}
	
	//TODO use lambdas etc to add better custom prompt with inheritance
	public static String readCommand(String prompt) {
		FeatJAR.log().message(prompt); // TODO eventuell stream aus FEATJAR.log nehemen
		return in.nextLine().trim();
	}

//	@Override
//	public Map<String, IShellCommand> getCommands() {
//		// TODO Auto-generated method stub
//		return commands;
//	}


	public String getCommandName() {
		return name;
	}
	
//	public Boolean findCommand(String cmd) {
//		return commands.get(cmd) != null ? true : false;
//	}

//	public void execute(Path path) {
//		// TODO Auto-generated method stub
//		
//	}

}
