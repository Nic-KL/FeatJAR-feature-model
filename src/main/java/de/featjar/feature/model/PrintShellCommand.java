package de.featjar.feature.model;

import de.featjar.base.FeatJAR;

public class PrintShellCommand extends AbstractShellCommand {

	@Override
	public void execute(ShellSession session) {
		System.out.println(FeatJAR.extensionPoint(ShellCommands.class).getExtensions().stream());
		
	}

}
