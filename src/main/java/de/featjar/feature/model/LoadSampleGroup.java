package de.featjar.feature.model;

import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import de.featjar.base.FeatJAR;
import de.featjar.formula.assignment.BooleanAssignmentGroups;
import de.featjar.formula.io.BooleanAssignmentGroupsFormats;
import de.featjar.formula.io.FormulaFormats;

public class LoadSampleGroup extends LoadShellCommand {
	
	@Override
	public void execute(ShellSession session, List<String> cmdParams) {
		//TODO global abort, catch Exception					
		
		String name = cmdParams.size() > 0 ? cmdParams.get(0) : 
			setName().orElse("sampleGroup" + (session.getSize() + 1));
		
        String path = cmdParams.size() > 1 ? cmdParams.get(1) : 
        	Shell.readCommand("Enter a path to load a SampleGroup:").orElse("");
        
        if(path.isBlank()) {
        	FeatJAR.log().info("No SampleGroup specified");
        	return;
        }
        
		saveModel(loadFormat(
				Paths.get(path), BooleanAssignmentGroupsFormats.getInstance()), name, session);
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
	

