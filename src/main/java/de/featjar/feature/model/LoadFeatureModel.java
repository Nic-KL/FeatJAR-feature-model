package de.featjar.feature.model;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import de.featjar.base.FeatJAR;
import de.featjar.feature.model.io.FeatureModelFormats;
import de.featjar.formula.io.BooleanAssignmentGroupsFormats;

public class LoadFeatureModel extends LoadShellCommand {

	@Override
	public void execute(ShellSession session, List<String> cmdParams) {			
		//TODO global abort, catch Exception					
		
		String name = cmdParams.size() > 0 ? cmdParams.get(0) : 
			setName().orElse("fm" + (session.getSize() + 1));
		
        String path = cmdParams.size() > 1 ? cmdParams.get(1) : 
        	Shell.readCommand("Enter a path to load a FeatureModel:").orElse("");
        
        if(path.isBlank()) {
        	FeatJAR.log().info("No FeatureModel specified");
        	return;
        }
        
		saveModel(loadFormat(
				Paths.get(path), FeatureModelFormats.getInstance()), name, session);
	}		
    @Override
    public Optional<String> getShortName() {
        return Optional.of("load-FeatureModel");
    }
    @Override
    public Optional<String> getDescription(){
    	return Optional.of("load a feature model - <cmd> <name> <path>");
    }
}

