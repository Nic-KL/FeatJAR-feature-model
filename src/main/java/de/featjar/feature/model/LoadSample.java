package de.featjar.feature.model;

import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import de.featjar.base.FeatJAR;
import de.featjar.base.io.IO;
import de.featjar.feature.model.io.FeatureModelFormats;
import de.featjar.formula.assignment.BooleanAssignmentList;
import de.featjar.formula.io.BooleanAssignmentGroupsFormats;
import de.featjar.formula.io.FormulaFormats;

public class LoadSample extends LoadShellCommand {

	@Override
	public void execute(ShellSession session, List<String> cmdParams) {
		//TODO global abort		
		
		parseArguments(session, cmdParams, BooleanAssignmentGroupsFormats.getInstance());		
	}
    @Override
    public Optional<String> getShortName() {
        return Optional.of("load-Sample");
    }
    @Override
    public Optional<String> getDescription(){
    	return Optional.of("load a sample - <cmd> <name> <path>");
    }
	@Override
	public Optional<String> getFormatName() {
		return Optional.of("sample");
	}
	@Override
	public Optional<String> getDefaultName() {
		return Optional.of("sam");
	}
}
