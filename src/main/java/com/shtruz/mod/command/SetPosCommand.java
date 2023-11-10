package com.shtruz.mod.command;

import com.shtruz.mod.ExternalFinalsCounter;
import net.weavemc.loader.api.command.Command;
import org.jetbrains.annotations.NotNull;

public class SetPosCommand extends Command {
    public SetPosCommand() {
        super("setpos");
    }

    @Override
    public void handle(@NotNull String[] args) {
        ExternalFinalsCounter externalFinalsCounter = ExternalFinalsCounter.getInstance();

        if (args.length != 2) {
            return;
        }

        int x;
        int y;

        try {
            x = Integer.parseInt(args[0]);
            y = Integer.parseInt(args[1]);
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
            return;
        }

        if (x < 0 || y < 0) {
            return;
        }

        externalFinalsCounter.getConfig().finalsCounterX = x;
        externalFinalsCounter.getConfig().finalsCounterY = y;

        externalFinalsCounter.saveConfig();

        try {
            String output = "Set pos to X: " + x + ", Y: " + y;

            externalFinalsCounter.addChatComponentText(output);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
