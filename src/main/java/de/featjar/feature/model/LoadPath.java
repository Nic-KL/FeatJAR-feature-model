package de.featjar.feature.model;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import de.featjar.base.FeatJAR;
import de.featjar.base.shell.ShellSession;

public class LoadPath extends ALoadShellCommand {

	@Override
	public void execute(ShellSession session, List<String> cmdParams) {
		String name = setVarName(session, cmdParams);
		String path = setPath(cmdParams);
		
		File file = new File(path);
		
		if (file.exists()){
			session.put(name, Paths.get(path), Path.class);
		} else {
			FeatJAR.log().error("'%s' is no valid path to a file", path);
		}
	}
	
    public Optional<String> getShortName() {
        return Optional.of("LoadPath");
    }
    
    public Optional<String> getDescription(){
    	return Optional.of("<var> <path> - load just a path into the shell such that it can be used by non-shell commands");
    }

	@Override
	public Optional<String> getFormatName() {
		return Optional.of("Path");
	}

	@Override
	public Optional<String> getDefaultName() {
		return Optional.of("path");
	}

}
