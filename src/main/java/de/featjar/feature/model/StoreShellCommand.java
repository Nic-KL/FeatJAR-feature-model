package de.featjar.feature.model;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StoreShellCommand extends AbstractShellCommand {	
	private final Map<String, StoreShellCommand> subCommands = Map.of(
			"1", (ctx, arg) -> );

	public StoreShellCommand() {
		super("store");
	}

	@Override
	public void execute() {
		// TODO Typecheck
		

	}

	@Override
	public void execute(Path p) {
		System.out.println("Usage: load <type> <path>");
	}
	
    @Override
    public Optional<String> getShortName() {
        return Optional.of("store");
    }

}
