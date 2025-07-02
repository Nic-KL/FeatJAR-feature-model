package de.featjar.feature.model;

import java.nio.file.Paths;
import java.util.Optional;

import de.featjar.formula.io.BooleanAssignmentGroupsFormats;

public class LoadSample extends LoadShellCommand {

	@Override
	public void execute(ShellSession session) {
		//TODO global abort, catch Exception	
		session.sampleList.add(loadAFormat(Paths
				.get(Shell.readCommand("Enter a vaild path to load a Sample:\n")), BooleanAssignmentGroupsFormats
				.getInstance())
				.getFirstGroup());		
	}
    @Override
    public Optional<String> getShortName() {
        return Optional.of("loadSample");
    }
    public Optional<String> getDescription(){
    	return Optional.of("load a sample - <cmd> <path>");
    }
}
