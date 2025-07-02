package de.featjar.feature.model;

import java.nio.file.Paths;

import de.featjar.feature.model.io.FeatureModelFormats;

public class LoadFeatureModel extends LoadShellCommand {

	@Override
	public void execute(ShellSession session) {			
		//TODO global abort, catch Exception			
		session.featureModelList.add(loadAFormat(Paths.
				get(Shell.readCommand("Enter a vaild path to load a FeatureModel:\n")), FeatureModelFormats
				.getInstance()));
		
//			System.out.println(session.featureModelList.get(0));
	}		
}

