package de.featjar.feature.model;

import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import de.featjar.base.FeatJAR;
import de.featjar.base.io.IO;
import de.featjar.formula.assignment.BooleanAssignmentGroups;
import de.featjar.formula.io.BooleanAssignmentGroupsFormats;
import de.featjar.formula.io.FormulaFormats;

public class LoadSampleGroup extends LoadShellCommand {
	
	@Override
	public void execute(ShellSession session, List<String> cmdParams) {
		//TODO global abort
		
		parseArguments(session, cmdParams, BooleanAssignmentGroupsFormats.getInstance());	
	}	
    @Override
    public Optional<String> getShortName() {
        return Optional.of("load-GroupOfSamples");
    }
    @Override
    public Optional<String> getDescription(){
    	return Optional.of("load a sample group - <cmd> <name> <path>");
    }
	@Override
	public Optional<String> getFormatName() {
		return Optional.of("sampleGroup");
	}
	@Override
	public Optional<String> getDefaultName() {
		return Optional.of("sg");
	}
}	
	

