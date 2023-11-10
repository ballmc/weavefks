package com.shtruz.mod.command;

import com.shtruz.mod.ExternalFinalsCounter;
import net.weavemc.loader.api.command.Command;
import org.jetbrains.annotations.NotNull;

public class SetScaleCommand extends Command {
    public SetScaleCommand() {
        super("setscale");
    }

    @Override
    public void handle(@NotNull String[] args) {
        ExternalFinalsCounter externalFinalsCounter = ExternalFinalsCounter.getInstance();

        if (args.length != 1) {
            return;
        }

        double scale;

        try {
            scale = Double.parseDouble(args[0]);
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
            return;
        }

        if (scale <= 0) {
            return;
        }

        externalFinalsCounter.getConfig().finalsCounterScale = scale;

        externalFinalsCounter.saveConfig();

        try {
            String output = "Set scale to " + scale + "%";

            externalFinalsCounter.addChatComponentText(output);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
