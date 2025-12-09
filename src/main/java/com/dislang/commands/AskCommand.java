package com.dislang.commands;

import com.dislang.core.CommandBase;
import com.dislang.service.ai.AIServiceRouter;
import com.dislang.service.i18n.LocalizationService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public class AskCommand extends CommandBase {

    private final AIServiceRouter router;

    public AskCommand(AIServiceRouter router) {
        super("ask", "Ask AI a question");
        this.router = router;
    }

    @Override
    public CommandData getCommandData() {
        return createCommandData().addOption(
            OptionType.STRING,
            "question",
            "Question content",
            true
        );
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        String question = event.getOption("question").getAsString();

        String watitingMsg = LocalizationService.get("vi", "ui.waiting");

        event.reply(watitingMsg).queue();

        router.ask("gemini", question, response -> {
            event.getHook().sendMessage(response).queue();
        });
    }
}
