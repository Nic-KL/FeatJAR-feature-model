package de.featjar.feature.model;

import java.nio.file.Path;
import java.util.Optional;
import de.featjar.base.io.IO;
import de.featjar.base.io.format.AFormats;
import de.featjar.base.log.Log.Verbosity;

public abstract class LoadShellCommand implements IShellCommand {

	public LoadShellCommand() {
		super();
	}
	
	public static <T> T loadAFormat(Path path, AFormats<T> format) {		
		return IO.load(path, format).orElseLog(Verbosity.WARNING);
	}
	
    @Override
    public Optional<String> getShortName() {
        return Optional.of("load");
    }
}
