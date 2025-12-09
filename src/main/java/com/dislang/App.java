package com.dislang;

import com.dislang.commands.*;
import com.dislang.config.EnvConfig;
import com.dislang.core.ICommand;
import com.dislang.service.ai.AIServiceRouter;
import com.dislang.service.ai.impl.*;
import java.util.HashMap;
import java.util.Map;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class App extends ListenerAdapter {

    private final Map<String, ICommand> commands = new HashMap<>();

    public static void main(String[] args) {
        String deepSeekKey = EnvConfig.get("OPENAI_API_KEY");
        String geminiKey = EnvConfig.get("GEMINI_API_KEY");

        GeminiService gemini = new GeminiService(geminiKey);
        DeepSeekService deepSeek = new DeepSeekService(deepSeekKey);

        AIServiceRouter aiRouter = new AIServiceRouter(deepSeek, gemini);

        App botApp = new App();

        botApp.registerCommand(new AskCommand(aiRouter));

        String token = EnvConfig.get("DISCORD_TOKEN");
        JDA jda = JDABuilder.createDefault(token)
            .addEventListeners(botApp)
            .build();

        try {
            jda.awaitReady();

            jda
                .updateCommands()
                .addCommands(
                    botApp.commands
                        .values()
                        .stream()
                        .map(ICommand::getCommandData)
                        .toList()
                )
                .queue();

            System.out.println("BOT da online");
        } catch (Exception e) {
            System.out.println(
                "BOT online khong thanh cong, loi: " + e.getMessage()
            );
        }
    }

    private void registerCommand(ICommand cmd) {
        commands.put(cmd.getName(), cmd);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        ICommand cmd = commands.get(event.getName());
        if (cmd != null) {
            cmd.handle(event);
        }
    }
}
