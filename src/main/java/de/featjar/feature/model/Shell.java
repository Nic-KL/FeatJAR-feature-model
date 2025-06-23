package de.featjar.feature.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import de.featjar.base.FeatJAR;
import de.featjar.base.cli.Commands;
import de.featjar.base.cli.Option;
import de.featjar.base.data.Problem;
import de.featjar.base.data.Result;
import de.featjar.base.data.Problem.Severity;

public class Shell {
	
    private final List<String> cmdArg;
    
    private LinkedHashMap<String, Object> properties;
    
    /**
     * Option for printing usage information.
     */
    static final Option<IShellCommand> COMMAND_OPTION = Option.newOption(
                    "command", s -> FeatJAR.extensionPoint(ShellCommands.class)
                            .getMatchingExtension(s)
                            .orElseThrow())
            .setRequired(true)
            .setDescription("Classpath from command to execute");

	public static Shell shell = null;

	private Shell() {
		FeatJAR.initialize(); // TODO eventuell logger konfigurieren
		printArt();
		printStartMessage();
		cmdArg = new ArrayList<String>();
        properties = new LinkedHashMap<>();
		run();
	}

	public static Shell getInstance() {
		return (shell == null) ? (shell = new Shell()) : shell;
	}

	public static void main(String[] args) {
		Shell singletonShell = getInstance();
	}

	private void run() {
		ShellSession session = new ShellSession();
		ShellScanner shellScanner = new ShellScanner();
		
		ShellCommands shellCommands = new ShellCommands();
		
		List<Problem> problems = new ArrayList<Problem>();
		
		while (true) {
//			String inputLineString = ctx.readCommand("store - stores data, load - loads data from file, exit - leave shell");
//			List<String> inputLineString = List.of(shellScanner.readCommand("$"));
//			if (inputLineString.equals("exit")) {
//				printArt();
//				break;
//			}
//			IShellCommandManager.find(inputLineString)
//			.ifPresentOrElse(cmd -> cmd.execute(session, null), () ->FeatJAR.log().error("Command not found !"));

			
			List<String> cmdArg =  shellScanner.readCommand("$");
			
			
			shellCommands.getExtension(cmdArg.toString()).ifPresent(cmd -> ((IShellCommand) cmd).execute(session, null));
			
			
			
			System.out.print("bbbbbbbb: " + cmdArg);
			if (cmdArg.equals("exit")) {
				printArt();
				break;
			}
			
			
//			this.parseCommand(problems, cmdArg);

		}
	}
	
    private void parseCommand(List<Problem> problemList, List<String> commandLineArguments) {
        if (!commandLineArguments.isEmpty() && !commandLineArguments.get(0).startsWith("--")) {
            String commandString = commandLineArguments.get(0);
            ShellCommands shellCommandsExentionsPoint = FeatJAR.extensionPoint(ShellCommands.class);
            List<IShellCommand> commands = shellCommandsExentionsPoint.getExtensions().stream()
                    .filter(command -> command.getShortName()
                            .map(name -> Objects.equals(name, commandString))
                            .orElse(Boolean.FALSE))
                    .collect(Collectors.toList());

            if (commands.size() > 1) {
                addProblem(
                        problemList,
                        Severity.ERROR,
                        "Command name '%s' is ambiguous! It matches the following commands: \n%s",
                        commandString,
                        commands.stream().map(IShellCommand::getIdentifier).collect(Collectors.joining("\n")));
                return;
            }

            final IShellCommand command;
            if (commands.isEmpty()) {
                Result<IShellCommand> matchingExtension = shellCommandsExentionsPoint.getMatchingExtension(commandString);
                if (matchingExtension.isEmpty()) {
                    problemList.addAll(matchingExtension.getProblems());
                    addProblem(problemList, Severity.ERROR, "No command matched the name '%s'!", commandString);
                    return;
                }
                command = matchingExtension.get();
            } else {
                command = commands.get(0);
            }
//            commandLineArguments.remove(0);
            properties.put(COMMAND_OPTION.getName(), command);
        }
        System.out.println("Müll" + commandLineArguments);
    }
	
    private boolean addProblem(List<Problem> problemList, Severity severity, String message, Object... arguments) {
        return problemList.add(new Problem(String.format(message, arguments), severity));
    }

	private void printStartMessage() {
		System.out.println("interactive shell - supported commands are:");
		System.out.println("help - prints all commands");
		System.out.println("store - stores data");
		System.out.println("load - loads data from file");
		System.out.println("exit - leave shell");
	}

	private void printArt() {
		System.out.println(" _____             _       _    _     ____   ____   _            _  _ ");
		System.out.println("|  ___|___   __ _ | |_    | |  / \\   |  _ \\ / ___| | |__    ___ | || |");
		System.out.println("| |_  / _ \\ / _` || __|_  | | / _ \\  | |_) |\\___ \\ | '_ \\  / _ \\| || |");
		System.out.println("|  _||  __/| (_| || |_| |_| |/ ___ \\ |  _ <  ___) || | | ||  __/| || |");
		System.out.println("|_|   \\___| \\__,_| \\__|\\___//_/   \\_\\|_| \\_\\|____/ |_| |_| \\___||_||_|");
		System.out.println("");
	}
}
