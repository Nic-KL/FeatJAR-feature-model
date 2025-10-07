package de.featjar.feature.model;

import java.util.List;
import java.util.Optional;

import de.featjar.formula.io.BooleanAssignmentGroupsFormats;

public class LoadConfig extends LoadShellCommand {
	@Override
	public void execute(ShellSession session, List<String> cmdParams) {
		// TODO global abort
		
		parseArguments(session, cmdParams, BooleanAssignmentGroupsFormats.getInstance());
	}

	@Override
	public Optional<String> getShortName() {
		return Optional.of("loadConfig");
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("load a configuration - <cmd> <path>");
	}

	@Override
	public Optional<String> getFormatName() {
		return Optional.of("Configuration");
	}

	@Override
	public Optional<String> getDefaultName() {
		return Optional.of("config");
	}
}
