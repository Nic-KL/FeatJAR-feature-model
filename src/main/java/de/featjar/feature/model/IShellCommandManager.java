package de.featjar.feature.model;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// TODO pot. convert to singleton

public class IShellCommandManager{
	private static final Map<String, IShellCommand> COMMADNS = new HashMap<>();
	
	static {
		register(new LoadShellCommand());
		register(new ExitShellCommand());
	}
	
	private static void register(IShellCommand cmd) {
		COMMADNS.put(((AbstractShellCommand) cmd).getCommandName(), cmd);
	}
	
	static Optional<IShellCommand> find(String name) {
		return Optional.ofNullable(COMMADNS.get(name));
	}
	

	
	private IShellCommandManager() {};
}
