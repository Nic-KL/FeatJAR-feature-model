package de.featjar.feature.model;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import de.featjar.base.FeatJAR;
import de.featjar.base.data.Problem;
import de.featjar.base.data.Problem.Severity;
import de.featjar.base.data.Result;

public class Shell {

	public static Shell shell = null;
	ShellSession session = new ShellSession();
	private static final Scanner shellScanner = new Scanner(System.in);

	private Shell() {
		FeatJAR.initialize(); // TODO perhaps configure logger 
		printArt();		
		new HelpShellCommand().execute(session);		
		run();
	}

	public static Shell getInstance() {
		return (shell == null) ? (shell = new Shell()) : shell;
	}

	public static void main(String[] args) {
		Shell singletonShell = getInstance();
	}

	private void run() {
		while (true) {

			List<String> cmdArg = Arrays.stream(readCommand("$").split("\\s+")).collect(Collectors.toList());
			Result<IShellCommand> command = parseCommand(cmdArg.get(0));

			command.ifPresent(cmd -> cmd.execute(session));
		}
	}

	private Result<IShellCommand> parseCommand(String commandString) {
		ShellCommands shellCommandsExentionsPoint = FeatJAR.extensionPoint(ShellCommands.class);
		List<IShellCommand> commands = shellCommandsExentionsPoint
				.getExtensions().stream().filter(command -> command.getShortName()
						.map(name -> name.toLowerCase().startsWith(commandString)).orElse(Boolean.FALSE))
				.collect(Collectors.toList());

		if (commands.size() > 1) {
			FeatJAR.log().info("Command name " + commandString + " is ambiguous! Found " + commands.size() + " commands: ");
			commands.forEach(c -> System.out.println(c));
			return Result.empty(addProblem(Severity.ERROR,
					"Command name '%s' is ambiguous! It matches the following commands: \n%s", commandString,
					commands.stream().map(IShellCommand::getIdentifier).collect(Collectors.joining("\n"))));
		}

		final IShellCommand command;
		if (commands.isEmpty()) {
			Result<IShellCommand> matchingExtension = shellCommandsExentionsPoint.getMatchingExtension(commandString);
			if (matchingExtension.isEmpty()) {
				FeatJAR.log().info("No such command '%s'", commandString);
				return Result.empty(addProblem(Severity.ERROR, "No command matched the name '%s'!", commandString));
			}
			command = matchingExtension.get();
		} else {
			command = commands.get(0);
		}
		return Result.of(command);
	}

	private Problem addProblem(Severity severity, String message, Object... arguments) {
		return new Problem(String.format(message, arguments), severity);
	}
	
	//TODO use lambdas etc to add better custom prompt with inheritance
	public static String readCommand(String prompt) {
		System.out.println(prompt);
		return shellScanner.nextLine().trim();
	}

	public static void printArt() {
		System.out.println(" _____             _       _    _     ____   ____   _            _  _ ");
		System.out.println("|  ___|___   __ _ | |_    | |  / \\   |  _ \\ / ___| | |__    ___ | || |");
		System.out.println("| |_  / _ \\ / _` || __|_  | | / _ \\  | |_) |\\___ \\ | '_ \\  / _ \\| || |");
		System.out.println("|  _||  __/| (_| || |_| |_| |/ ___ \\ |  _ <  ___) || | | ||  __/| || |");
		System.out.println("|_|   \\___| \\__,_| \\__|\\___//_/   \\_\\|_| \\_\\|____/ |_| |_| \\___||_||_|");
		System.out.println("");
	}
}
