package de.featjar.feature.model;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractShellCommand implements IShellCommand {
	
	private final String name;
	
	public AbstractShellCommand(String name) {
		this.name = name;
//		commands.put(this.getCommandName(), this);
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
