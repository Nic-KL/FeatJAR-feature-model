package de.featjar.feature.model;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import de.featjar.base.FeatJAR;

public abstract class AbstractShellCommand implements IShellCommand {
	
	private final String name;
	
	private static final Scanner in = new Scanner(System.in);
	
	public AbstractShellCommand() {
		this.name = getShortName().toString();
	}
	
	//TODO use lambdas etc to add better custom prompt with inheritance
	public static String readCommand(String prompt) {
		System.out.println(prompt); // TODO eventuell stream aus FEATJAR.log nehemen
		return in.nextLine().trim();
	}
}
