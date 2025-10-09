package de.featjar.feature.model;

import java.util.List;
import java.util.Optional;

import de.featjar.base.shell.ShellSession;
import de.featjar.feature.model.io.FeatureModelFormats;


public class LoadFeatureModel extends LoadShellCommand {

	@Override
	public void execute(ShellSession session, List<String> cmdParams) {			
		//TODO global abort, catch Exception		
		
		parseArguments(session, cmdParams, FeatureModelFormats.getInstance());		
	}		
    @Override
    public Optional<String> getShortName() {
        return Optional.of("loadFeatureModel");
    }
    @Override
    public Optional<String> getDescription(){
    	return Optional.of("load a feature model - <cmd> <name> <path>");
    }
	@Override
	public Optional<String> getFormatName() {
		return Optional.of("FeatureModel");
	}
	@Override
	public Optional<String> getDefaultName() {
		return Optional.of("fm");
	}
}

