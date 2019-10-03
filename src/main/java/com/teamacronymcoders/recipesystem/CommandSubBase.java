package com.teamacronymcoders.recipesystem;

import javax.annotation.Nonnull;
import java.util.stream.Collectors;

public class CommandSubBase extends Command {
    private final String name;

    public CommandSubBase(String name) {
        this.name = name;
    }

    @Override
    @Nonnull
    public String getName() {
        return name;
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/" + name + " " + this.getSubCommands()
                .stream()
                .map(ICommand::getName)
                .collect(Collectors.joining(" | "));
    }
}