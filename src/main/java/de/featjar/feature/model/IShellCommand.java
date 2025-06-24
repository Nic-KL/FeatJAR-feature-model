package de.featjar.feature.model;

import java.util.Optional;

import de.featjar.base.extension.IExtension;

public interface IShellCommand extends IExtension{ 
	
	void execute(ShellSession session);
	
    /**
     * {@return this command's short name, if any} The short name can be used to call this command from the CLI.
     */
    default Optional<String> getShortName() {
        return Optional.empty();
    }
}
