package de.featjar.feature.model;

import java.util.List;
import java.util.Optional;

import de.featjar.base.shell.ShellSession;
import de.featjar.feature.model.io.FeatureModelFormats;


public class LoadFeatureModel extends ALoadShellCommand {

	@Override
	public void execute(ShellSession session, List<String> cmdParams) {					
		parseArguments(session, cmdParams, FeatureModelFormats.getInstance());		
	}		
    @Override
    public Optional<String> getShortName() {
        return Optional.of("loadFeatureModel");
    }
    @Override
    public Optional<String> getDescription(){
    	return Optional.of("<var> <path> - load a feature model");
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

