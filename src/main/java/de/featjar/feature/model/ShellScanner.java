package de.featjar.feature.model;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import de.featjar.base.FeatJAR;

public class ShellScanner {
	private final Scanner in = new Scanner(System.in);
	
	//TODO use lambdas etc to add better custom prompt with inheritance
//	public String[] readCommand(String prompt) {
//		FeatJAR.log().message(prompt);  // TODO eventuell stream aus FEATJAR.log nehemen
//		String[] dmString = in.nextLine().trim().split("\\s+");
//		System.out.print(dmString.toString());
//		return dmString;
//	}
	
    public List<String> readCommand(String prompt) {
        System.out.print(prompt);
        String line = in.nextLine();
        System.out.println(line);
        String[] parts = line.trim().split("\\s+");
        System.out.println(Arrays.toString(parts));
        List<String> lStrings = Arrays.stream(parts)
                .collect(Collectors.toList());
        System.out.println("aaaaaaaaaaa: " + lStrings);
        return lStrings;
    }
}
