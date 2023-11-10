package com.shtruz.mod.command;

import com.shtruz.mod.ExternalFinalsCounter;
import net.weavemc.loader.api.command.Command;
import org.jetbrains.annotations.NotNull;

public class FinalsCommand extends Command {
    public FinalsCommand() {
        super("finals");
    }

    @Override
    public void handle(@NotNull String[] args) {
        ExternalFinalsCounter externalFinalsCounter = ExternalFinalsCounter.getInstance();

        try {
            int blueFinals = externalFinalsCounter.getChatMessageParser().getBlue()
                    .values()
                    .stream()
                    .reduce(0, Integer::sum);

            int greenFinals = externalFinalsCounter.getChatMessageParser().getGreen()
                    .values()
                    .stream()
                    .reduce(0, Integer::sum);

            int redFinals = externalFinalsCounter.getChatMessageParser().getRed()
                    .values()
                    .stream()
                    .reduce(0, Integer::sum);

            int yellowFinals = externalFinalsCounter.getChatMessageParser().getYellow()
                    .values()
                    .stream()
                    .reduce(0, Integer::sum);

            String finals = externalFinalsCounter.getChatMessageParser().getBluePrefix() + "BLUE: " + "\u00A7f" + blueFinals + "\n"
                    + externalFinalsCounter.getChatMessageParser().getGreenPrefix() + "GREEN: " + "\u00A7f" + greenFinals + "\n"
                    + externalFinalsCounter.getChatMessageParser().getRedPrefix() + "RED: " + "\u00A7f" + redFinals + "\n"
                    + externalFinalsCounter.getChatMessageParser().getYellowPrefix() + "YELLOW: " + "\u00A7f" + yellowFinals;

            externalFinalsCounter.addChatComponentText(finals);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
