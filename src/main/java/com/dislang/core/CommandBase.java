package com.dislang.core;

import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public abstract class CommandBase implements ICommand {

    protected final String name;
    protected final String help;

    public CommandBase(String name, String help) {
        this.name = name;
        this.help = help;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getHelp() {
        return this.help;
    }

    protected SlashCommandData createCommandData() {
        return Commands.slash(this.name, this.help);
    }
}
