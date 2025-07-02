package de.featjar.feature.model;

import java.nio.file.Paths;
import de.featjar.formula.io.BooleanAssignmentGroupsFormats;

public class LoadSampleGroup extends LoadShellCommand {
	
	@Override
	public void execute(ShellSession session) {
		//TODO global abort, catch Exception			
		session.listOfSampleLists.add(loadAFormat(Paths.
				get(Shell.readCommand("Enter a vaild path to load a SampleGroup:\n")), BooleanAssignmentGroupsFormats
				.getInstance()));	
		
//			System.out.println(session.ifList.get(0));			
	}	
}	
	

