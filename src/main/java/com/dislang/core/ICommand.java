package com.dislang.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public interface ICommand {
    String getName();
    String getHelp();
    CommandData getCommandData();
    void handle(SlashCommandInteractionEvent event);
}
