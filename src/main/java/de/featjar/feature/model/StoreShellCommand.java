package de.featjar.feature.model;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import de.featjar.base.FeatJAR;
import de.featjar.base.data.Problem;
import de.featjar.base.data.Result;
import de.featjar.base.data.Problem.Severity;
import de.featjar.base.io.IO;
import de.featjar.base.io.format.AFormats;
import de.featjar.base.io.format.IFormat;
import de.featjar.base.shell.IShellCommand;
import de.featjar.base.shell.Shell;
import de.featjar.base.shell.ShellSession;
import de.featjar.feature.model.io.FeatureModelFormats;
import de.featjar.formula.assignment.BooleanAssignment;
import de.featjar.formula.assignment.BooleanAssignmentGroups;
import de.featjar.formula.assignment.BooleanAssignmentList;
import de.featjar.formula.io.BooleanAssignmentFormats;
import de.featjar.formula.io.BooleanAssignmentGroupsFormats;
import de.featjar.formula.io.BooleanAssignmentListFormats;
import de.featjar.formula.io.FormulaFormats;
import de.featjar.formula.structure.IFormula;

public class StoreShellCommand implements IShellCommand {	
	
	public void execute(ShellSession session, List<String> cmdParams) {		
		run(session, cmdParams);
	}
	
	private <T> void run(ShellSession session, List<String> cmdParams) {

		String inputVar = setVariableName(cmdParams);
		
		session.getElement(inputVar).ifPresentOrElse(element -> {
			Result<IFormat<T>> inputFormat = setFormat(session, inputVar, cmdParams);
			
			inputFormat.ifPresent(f -> {
				final Path path = Paths.get("" + inputVar);
				try {
					IO.save((T) element, path, f);
				} catch (IOException e) {
					FeatJAR.log().error(e);
				}
	            FeatJAR.log().message("Storing of " + inputVar +" Successful");
			});
		}, () -> FeatJAR.log().error(inputVar + " is no vaild variable name") );
	}
	
	private <T> Result<AFormats<T>> getElementType (ShellSession session, String inputVar) {
		Class<T> elementType = (Class<T>) session.getType(inputVar).orElseThrow();
		return getFormatType(session, inputVar, elementType);
	}
	
	private <T> Result<AFormats<T>> getFormatType(ShellSession session, final String variable, Class<T> classType) {
		AFormats<T> format;		
			if (classType.isAssignableFrom(FeatureModel.class)){
				format =  (AFormats<T>) FeatureModelFormats.getInstance();
			} else if(classType.isAssignableFrom(IFormula.class)) {
				format = (AFormats<T>) FormulaFormats.getInstance();
			} else if(classType.isAssignableFrom(BooleanAssignment.class)) {
				format = (AFormats<T>) BooleanAssignmentFormats.getInstance();
			} else if(classType.isAssignableFrom(BooleanAssignmentList.class)) {
				format = (AFormats<T>) BooleanAssignmentListFormats.getInstance();
			} else if(classType.isAssignableFrom(BooleanAssignmentGroups.class)) {
				format = (AFormats<T>) BooleanAssignmentGroupsFormats.getInstance();
			} else {
				return Result.empty(addProblem(Severity.ERROR, "Could not find the variable %s or its format", variable));
			}
		return Result.of(format);
	}
	
	private String setVariableName(List<String> cmdParams) {
		String input = "";
		if(cmdParams.isEmpty()) {
			while(input.isBlank()) {
				input = Shell.readCommand("Enter the variable that you want to store:\n").orElse("");
			}
		} else {
			input = cmdParams.get(0);
		}
		return input;
	}
	
    private Problem addProblem(Severity severity, String message, Object... arguments) {
        return new Problem(String.format(message, arguments), severity);
    }
    
    private <T> Result<IFormat<T>> setFormat(ShellSession session, String inputVar, List<String> cmdParams) {
    	
    	AFormats<T> formatExtensionPoint = (AFormats<T>) getElementType(session, inputVar).orElseThrow();   	
		List<IFormat<T>> possibleFormats = formatExtensionPoint.getExtensions()
        		.stream().filter(f -> f.supportsWrite()).collect(Collectors.toList());    	
		IFormat<T> format = null;
		String input = "";
    	
		if(cmdParams.isEmpty() || cmdParams.size() == 1) {
			FeatJAR.log().message("Select the format for the variable to store:\n");
			return selectFormat(possibleFormats);
		} else {
			input = cmdParams.get(1);
			String javaMull = input;
			List<IFormat<T>> formats = possibleFormats.stream().filter(f -> f.getName().toLowerCase().startsWith(javaMull))
	        		.collect(Collectors.toList());

	        if (formats.isEmpty()) {
	            Result<IFormat<T>> matchingExtension = formatExtensionPoint.getMatchingExtension(input);
	            if (matchingExtension.isEmpty()) {
	                FeatJAR.log().warning("No format matched the name '%s'!: Choose your format", input);
	            	return selectFormat(possibleFormats);
	            }
	            format = matchingExtension.get();
	        } else {
	            if (formats.get(0).getName().toLowerCase().matches(input)) {
	            	format = formats.get(0);
	                return Result.of(format);
	            }
	            String choice = Shell.readCommand(
	                            "Do you mean: " + formats.get(0).getName() + "? (ENTER) or (a)bort\n")
	                    .orElse("");
	            if (choice.isEmpty()) {
	            	format = formats.get(0);
	            } else {
	                return Result.empty(addProblem(Severity.ERROR, "No format matched the name '%s'!", choice));
	            }
	        }
		}
		return Result.of(format);
    }
    
    private <T> Result<IFormat<T>> selectFormat(List<IFormat<T>> possibleFormats) {
        for (int i = 0; i < possibleFormats.size(); i++) {
        	FeatJAR.log().message("%d. %s", i, possibleFormats.get(i).getName());
        }

        String choice = Shell.readCommand("").orElse("");

        if (choice.isBlank()) {
            return Result.empty();
        }
        int parsedChoice;
        try {
            parsedChoice = Integer.parseInt(choice);
        } catch (NumberFormatException e) {
            return Result.empty(addProblem(Severity.ERROR, String.format("'%s' is no vaild number", choice), e));
        }
        
        for (int i = 0; i < possibleFormats.size(); i++) {
        	if(i == parsedChoice){
        		return Result.of(possibleFormats.get(i));
        	}
        }
		return Result.empty(addProblem(Severity.ERROR, "No format matched the name '%s'!", choice));
	}
	
    @Override
    public Optional<String> getShortName() {
        return Optional.of("store");
    }
    @Override
    public Optional<String> getDescription(){
    	return Optional.of("<var> <format> - save seesion variables on your hard drive");
    }
}
