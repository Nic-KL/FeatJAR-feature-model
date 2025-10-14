package de.featjar.feature.model;

import java.util.List;
import java.util.Optional;

import de.featjar.base.shell.ShellSession;
import de.featjar.formula.io.BooleanAssignmentGroupsFormats;

public class LoadSample extends ALoadShellCommand {

	@Override
	public void execute(ShellSession session, List<String> cmdParams) {
		parseArguments(session, cmdParams, BooleanAssignmentGroupsFormats.getInstance());		
	}
    @Override
    public Optional<String> getShortName() {
        return Optional.of("loadSample");
    }
    @Override
    public Optional<String> getDescription(){
    	return Optional.of("load a sample - <cmd> <name> <path>");
    }
	@Override
	public Optional<String> getFormatName() {
		return Optional.of("sample");
	}
	@Override
	public Optional<String> getDefaultName() {
		return Optional.of("sam");
	}
}
