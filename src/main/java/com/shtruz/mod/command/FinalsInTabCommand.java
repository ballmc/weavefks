package com.shtruz.mod.command;

import com.shtruz.mod.ExternalFinalsCounter;
import net.weavemc.loader.api.command.Command;
import org.jetbrains.annotations.NotNull;

public class FinalsInTabCommand extends Command {
    public FinalsInTabCommand() {
        super("finalsintab");
    }

    @Override
    public void handle(@NotNull String[] args) {
        ExternalFinalsCounter externalFinalsCounter = ExternalFinalsCounter.getInstance();

        externalFinalsCounter.getConfig().finalsInTab = !externalFinalsCounter.getConfig().finalsInTab;
        externalFinalsCounter.saveConfig();

        try {
            String output = (externalFinalsCounter.getConfig().finalsInTab ? "Enabled" : "Disabled") + " finals in tab";
            externalFinalsCounter.addChatComponentText(output);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
