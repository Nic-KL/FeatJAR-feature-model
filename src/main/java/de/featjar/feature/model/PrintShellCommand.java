package de.featjar.feature.model;

import java.util.Optional;

import de.featjar.base.FeatJAR;

public class PrintShellCommand implements IShellCommand {

	@Override
	public void execute(ShellSession session) {
		System.out.println("Interactive shell - supported commands are (capitalization is not taken into account):\n");
		FeatJAR.extensionPoint(ShellCommands.class).getExtensions()
		.stream().map(c -> c.getShortName().orElse("").concat(" - " + c.getDescription().orElse("")))
		.forEach(System.out::println);		
		System.out.println();
	}
    @Override
    public Optional<String> getShortName() {
        return Optional.of("print");
    }
    @Override
    public Optional<String> getDescription(){
    	return Optional.of("print all commads");
    }
}
