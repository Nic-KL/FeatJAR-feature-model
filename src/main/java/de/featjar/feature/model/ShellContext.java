package de.featjar.feature.model;

import java.util.Scanner;

public class ShellContext {
	private final Scanner in = new Scanner(System.in);
	
	//TODO use lambdas etc to add better custom prompt with inheritance
	public String readCommand(String prompt) {
		System.out.print(prompt);
		return in.nextLine().trim();
	}
}
