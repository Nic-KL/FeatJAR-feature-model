package de.featjar.feature.model;

import java.nio.file.Paths;

import de.featjar.formula.io.BooleanAssignmentGroupsFormats;

public class LoadSample extends LoadShellCommand {

	@Override
	public void execute(ShellSession session) {
		//TODO global abort, catch Exception	
		session.sampleList.add(loadAFormat(Paths
				.get(readCommand("Enter a vaild path:\n$$$")), BooleanAssignmentGroupsFormats
				.getInstance())
				.getFirstGroup());		
	}
}
