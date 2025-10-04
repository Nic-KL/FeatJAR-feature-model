package de.featjar.feature.model;

import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import de.featjar.base.FeatJAR;
import de.featjar.feature.model.io.FeatureModelFormats;
import de.featjar.formula.assignment.BooleanAssignment;
import de.featjar.formula.io.BooleanAssignmentGroupsFormats;
import de.featjar.formula.io.FormulaFormats;

public class LoadConfig extends LoadShellCommand {
	@Override
	public void execute(ShellSession session, List<String> cmdParams) {
		// TODO global abort, catch Exception
//		String temp = Shell.readCommand("Enter a vaild path to load a Configuration:\n");
//		
//		if(temp.contains("abort")) {
//			FeatJAR.log().info("aborted");
//		} else {
//			session.configList.put("config" + (session.configList.size() + 1), loadAFormat(Paths
//					.get(temp), BooleanAssignmentGroupsFormats
//					.getInstance())
//					.getFirstGroup()
//					.getFirst());
//		}		

//	session.configList.put(setName().orElse("config" + (session.configList.size() + 1)), 
//			loadAFormat(
//					Paths.get(Shell.readCommand("Enter a vaild path to load a FeatureModel:\n")
//					.orElse("")), BooleanAssignmentGroupsFormats.getInstance()).getFirstGroup().getFirst());

		String name = cmdParams.size() > 0 ? cmdParams.get(0) : setName().orElse("config" + (session.getSize() + 1));

		String path = cmdParams.size() > 1 ? cmdParams.get(1)
				: Shell.readCommand("Enter a path to load a Configuration:").orElse("");

		if (path.isBlank()) {
			FeatJAR.log().info("No Configuration specified");
			return;
		}
		
		saveModel(loadFormat(
				Paths.get(path), BooleanAssignmentGroupsFormats.getInstance()), name, session);

//		session.put(name,
//				loadFormat(Paths.get(path), BooleanAssignmentGroupsFormats.getInstance()).get().getFirstGroup().getFirst(),
//				BooleanAssignment.class);
	}

	@Override
	public Optional<String> getShortName() {
		return Optional.of("loadConfig");
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("load a configuration - <cmd> <path>");
	}
}
