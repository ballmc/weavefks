package com.shtruz.mod.command;

import com.shtruz.mod.ExternalFinalsCounter;
import net.weavemc.loader.api.command.Command;
import org.jetbrains.annotations.NotNull;

public class ResetFinalsCommand extends Command {
    public ResetFinalsCommand() {
        super("resetfinals");
    }

    @Override
    public void handle(@NotNull String[] args) {
        ExternalFinalsCounter externalFinalsCounter = ExternalFinalsCounter.getInstance();

        externalFinalsCounter.getChatMessageParser().reset();

        try {
            externalFinalsCounter.addChatComponentText("Reset finals");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}