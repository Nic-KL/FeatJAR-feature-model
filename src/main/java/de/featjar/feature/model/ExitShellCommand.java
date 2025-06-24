package de.featjar.feature.model;

import java.util.Optional;

public class ExitShellCommand extends AbstractShellCommand {

	public ExitShellCommand() {
		super();
	}
	
	@Override
	public void execute(ShellSession session) {		
		System.out.println(" _____             _       _    _     ____   ____   _            _  _ ");
		System.out.println("|  ___|___   __ _ | |_    | |  / \\   |  _ \\ / ___| | |__    ___ | || |");
		System.out.println("| |_  / _ \\ / _` || __|_  | | / _ \\  | |_) |\\___ \\ | '_ \\  / _ \\| || |");
		System.out.println("|  _||  __/| (_| || |_| |_| |/ ___ \\ |  _ <  ___) || | | ||  __/| || |");
		System.out.println("|_|   \\___| \\__,_| \\__|\\___//_/   \\_\\|_| \\_\\|____/ |_| |_| \\___||_||_|");
		System.out.println("");
		System.exit(0);
	}	
    @Override
    public Optional<String> getShortName() {
        return Optional.of("exit");
    }
}
