package com.dislang.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

public class CommandManager {

    private final Map<String, ICommand> commands = new HashMap<>();

    public void addCommand(ICommand command) {
        if (commands.containsKey(command.getName())) {
            System.err.println("Command " + command.getName() + " exists");
        }
        commands.put(command.getName(), command);
    }

    public void handle(@NotNull SlashCommandInteractionEvent event) {
        String commandName = event.getName();

        if (commands.containsKey(commandName)) {
            ICommand cmd = commands.get(commandName);
            try {
                cmd.handle(event);
            } catch (Exception e) {
                event
                    .reply("Error when processing command")
                    .setEphemeral(true)
                    .queue();
            }
        } else {
            event.reply("Command not found").setEphemeral(true).queue();
        }
    }

    public List<CommandData> getAllCommandData() {
        List<CommandData> dataList = new ArrayList<>();
        for (ICommand cmd : commands.values()) {
            dataList.add(cmd.getCommandData());
        }
        return dataList;
    }
}
