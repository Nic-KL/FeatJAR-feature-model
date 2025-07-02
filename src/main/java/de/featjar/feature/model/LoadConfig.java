package de.featjar.feature.model;

import java.nio.file.Paths;
import java.util.Optional;

import de.featjar.formula.io.BooleanAssignmentGroupsFormats;

public class LoadConfig extends LoadShellCommand {
	@Override
	public void execute(ShellSession session) {
		//TODO global abort, catch Exception	
		session.configList.add(loadAFormat(Paths
				.get(Shell.readCommand("Enter a vaild path to load a Configuration:\n")), BooleanAssignmentGroupsFormats
				.getInstance())
				.getFirstGroup()
				.getFirst());		
	}
    @Override
    public Optional<String> getShortName() {
        return Optional.of("loadConfig");
    }
    @Override
    public Optional<String> getDescription(){
    	return Optional.of("load a configuration - <cmd> <path>");
    }
}
