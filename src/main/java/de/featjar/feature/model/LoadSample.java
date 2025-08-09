package de.featjar.feature.model;

import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import de.featjar.formula.assignment.BooleanAssignmentList;
import de.featjar.formula.io.BooleanAssignmentGroupsFormats;

public class LoadSample extends LoadShellCommand {

	@Override
	public void execute(ShellSession session, List<String> cmdParams) {
		//TODO global abort, catch Exception			
		
		String name = cmdParams.size() > 0 ? cmdParams.get(0) : 
			setName().orElse("sample" + (session.getSize() + 1));
		
        String path = cmdParams.size() > 1 ? cmdParams.get(1) : 
        	Shell.readCommand("Enter a path to load a Sample:").orElse("");
        
        if(path.isBlank()) {
        	System.out.println("No Sample specified");
        	return;
        }

		session.put(name, 
				loadAFormat(
						Paths.get(path), 
						BooleanAssignmentGroupsFormats.getInstance()).getFirstGroup(), 
						BooleanAssignmentList.class);
		
//		session.sampleList.put(setName().orElse("sample" + (session.sampleList.size() + 1)), 
//		loadAFormat(
//				Paths.get(Shell.readCommand("Enter a vaild path to load a FeatureModel:\n")
//				.orElse("")), BooleanAssignmentGroupsFormats.getInstance()).getFirstGroup());
		
	}
    @Override
    public Optional<String> getShortName() {
        return Optional.of("load-Sample");
    }
    @Override
    public Optional<String> getDescription(){
    	return Optional.of("load a sample - <cmd> <name> <path>");
    }
}
