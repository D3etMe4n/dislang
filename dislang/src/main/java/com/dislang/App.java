package com.dislang;

import com.dislang.commands.*;
import com.dislang.config.EnvConfig;
import com.dislang.core.BotListener;
import com.dislang.core.CommandManager;
import com.dislang.service.ai.AIServiceRouter;
import com.dislang.service.ai.impl.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class App {

    public static void main(String[] args) {
        String deepSeekKey = EnvConfig.get("OPENAI_API_KEY");
        String geminiKey = EnvConfig.get("GEMINI_API_KEY");

        GeminiService gemini = new GeminiService(geminiKey);
        DeepSeekService deepSeek = new DeepSeekService(deepSeekKey);

        AIServiceRouter aiRouter = new AIServiceRouter(deepSeek, gemini);

        CommandManager commandManager = new CommandManager();

        commandManager.addCommand(new AskCommand(aiRouter));

        BotListener botListener = new BotListener(commandManager);

        String token = EnvConfig.get("DISCORD_TOKEN");
        JDA jda = JDABuilder.createDefault(token)
            .addEventListeners(botListener)
            .build();

        try {
            jda.awaitReady();

            jda
                .updateCommands()
                .addCommands(commandManager.getAllCommandData())
                .queue();

            System.out.println("BOT da online");
        } catch (Exception e) {
            System.out.println(
                "BOT online khong thanh cong, loi: " + e.getMessage()
            );
        }
    }
}
