package de.featjar.feature.model;

import java.nio.file.Path;

public class ExitShellCommand extends AbstractShellCommand {

	public ExitShellCommand() {
		super("exit");
	}
	
	@Override
	public void execute(ShellContext ctx, String[] args) {		
		System.out.println(" _____             _       _    _     ____   ____   _            _  _ ");
		System.out.println("|  ___|___   __ _ | |_    | |  / \\   |  _ \\ / ___| | |__    ___ | || |");
		System.out.println("| |_  / _ \\ / _` || __|_  | | / _ \\  | |_) |\\___ \\ | '_ \\  / _ \\| || |");
		System.out.println("|  _||  __/| (_| || |_| |_| |/ ___ \\ |  _ <  ___) || | | ||  __/| || |");
		System.out.println("|_|   \\___| \\__,_| \\__|\\___//_/   \\_\\|_| \\_\\|____/ |_| |_| \\___||_||_|");
		System.out.println("");
		System.exit(0);
	}
}
