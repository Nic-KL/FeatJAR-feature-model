package de.featjar.feature.model;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import de.featjar.base.FeatJAR;



public class Shell {
	
	public static Shell shell = null;
	
	private Scanner scanner;
	
	private Shell() {
//		this.scanner = new Scanner(System.in);
		printArt();
		printStartMessage();
		run();
		
	}
	
	public static Shell getInstance() {	
		return (shell == null) ? (shell = new Shell()) : shell;
	}

	public static void main(String[] args) {
		Shell singletonShell = getInstance();
	}
	
	private void run() {
//		String runInput;
		FeatJAR.initialize();
		
//		do {
//			runInput = scanInput();
////			evaluateInput(runInput);
//			
//			if(runInput.isEmpty()) {continue;}
//			
////			String inputPartString[] = runInput.split("\\s", 2);
////			
//////			System.out.println(inputPartString[0]);
//////			System.out.println(inputPartString[1]);
////			
////			String aString = "/home/hans/Dokumente/Uni/Arbeit/Sebastian/FeatJAR/formula/src/testFixtures/resources/testFeatureModels/500-100.xml";
////			
////			try {
////
////			
////				String bString = inputPartString[1];
////
////				Path p2 = Paths.get(bString);
////
////			Path p = Paths.get(aString);
////
////			
////			IShellCommandManager.find(inputPartString[0])
////			.ifPresentOrElse(cmd -> cmd.execute(p2), () -> System.out.println("Command not found !\n"));
////			
////			} catch (InvalidPathException e) {
////				System.out.print("Invalid Path" + e);
////			}
//		
//			
//		
//
//			
//		}while(!runInput.equals("exit"));
//		printArt();
		
		ShellContext ctx = new ShellContext();
		while(true) {
//			String inputLineString = ctx.readCommand("store - stores data, load - loads data from file, exit - leave shell");
			String inputLineString = ctx.readCommand("$");
			if(inputLineString.equals("exit")) {break;}
			IShellCommandManager.find(inputLineString)
			.ifPresentOrElse(cmd -> cmd.execute(ctx, null), () -> System.out.println("Command not found !"));
		}
		
	}
	
		
		
		
	private String scanInput() {
		try {
			return scanner.nextLine().trim(); 
		} catch (Exception e) {
			System.out.println("PANIC");
			// TODO: handle exception
			return "";
		}		
	}
	
	private void evaluateInput (String input) {
		switch (input) {
		case "exit":
			break;
		case "help":
			System.out.println("help" + " printed");
			break;
		case "load":
			prepareLoad();
			break;
		case "store":
			System.out.println("store" + " printed");
			break;
		default:
			System.out.println(input + " is not a valid command");
			break;
		}
	}	
	
	private void prepareLoad() {
		while(true) {
			System.out.println("What do you want to load ?\n1) for FeatureModel, 2) for Formula, 3) for ...");
			String a =scanInput();
			switch (a) {
			case "exit":
				break;
			case "1":
				Path path = Paths.get("/home/hans/Dokumente/Uni/Arbeit/Sebastian/FeatJAR/formula/src/testFixtures/resources/testFeatureModels/500-100.xml");
				System.out.print(path);
				IFeatureModel fModel = IOShellStuff.loadFeatureModel(path);
				System.out.println(fModel.getFeatures());
				
				break;
			case "2":
				System.out.println("2");
			case "3":
				System.out.println("3");
				break;
			default:
				System.out.println(" is not a valid command");
				break;
			}
		}

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
	
//	private void testZeug() {
//		System.out.print("abc1");
//		Shell testShell = getInstance();
//		testShell.testString = "abc2";
//		System.out.println(testShell.testString);
//		Shell s1 = getInstance();
//		System.out.println(s1.testString);
//		if(s1 == testShell) {
//			System.out.println("Juhu");
//		}		
//	}

}
