package de.featjar.feature.model;

import java.util.Optional;

import de.featjar.base.FeatJAR;

public class PrintShellCommand implements IShellCommand {

	@Override
	public void execute(ShellSession session) {
		System.out.println("Possible commads are (capitalization is not taken into account):");
		FeatJAR.extensionPoint(ShellCommands.class).getExtensions().stream().map(c -> c.getShortName().get()).forEach(System.out::println);		
	}
    @Override
    public Optional<String> getShortName() {
        return Optional.of("print");
    }
}
