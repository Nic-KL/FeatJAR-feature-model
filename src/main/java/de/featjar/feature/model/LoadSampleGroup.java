package de.featjar.feature.model;

import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import de.featjar.formula.assignment.BooleanAssignmentGroups;
import de.featjar.formula.io.BooleanAssignmentGroupsFormats;

public class LoadSampleGroup extends LoadShellCommand {
	
	@Override
	public void execute(ShellSession session, List<String> cmdParams) {
		//TODO global abort, catch Exception					
		
		String name = cmdParams.size() > 0 ? cmdParams.get(0) : 
			setName().orElse("sampleGroup" + (session.getSize() + 1));
		
        String path = cmdParams.size() > 1 ? cmdParams.get(1) : 
        	Shell.readCommand("Enter a path to load a BooleanAssignment:").orElse("");
        
        if(path.isBlank()) {
        	System.out.println("No SampleGroup specified");
        	return;
        }
        
        

		session.put(name, 
				loadAFormat(
						Paths.get(path), 
						BooleanAssignmentGroupsFormats.getInstance()), 
						BooleanAssignmentGroups.class);
		
//		session.listOfSampleLists.put(setName().orElse("sampleGroup" + (session.listOfSampleLists.size() + 1)), 
//				loadAFormat(
//						Paths.get(Shell.readCommand("Enter a vaild path to load a FeatureModel:\n")
//						.orElse("")), BooleanAssignmentGroupsFormats.getInstance()));		
	}	
    @Override
    public Optional<String> getShortName() {
        return Optional.of("load-GroupOfSamples");
    }
    @Override
    public Optional<String> getDescription(){
    	return Optional.of("load a sample group - <cmd> <name> <path>");
    }
}	
	

