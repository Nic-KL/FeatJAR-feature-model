package de.featjar.feature.model;

import java.nio.file.Paths;
import de.featjar.formula.io.BooleanAssignmentGroupsFormats;

public class LoadSampleGroup extends LoadShellCommand {
	
	@Override
	public void execute(ShellSession session) {
		//TODO global abort, catch Exception			
		session.listOfSampleLists.add(loadAFormat(Paths.
				get(readCommand("Enter a vaild path:\n$$$")), BooleanAssignmentGroupsFormats
				.getInstance()));	
		
//			System.out.println(session.ifList.get(0));			
	}	
}	
	

