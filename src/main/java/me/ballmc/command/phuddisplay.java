package me.ballmc.weavefks.command;

import me.ballmc.weavefks.WeaveFks;
import net.weavemc.loader.api.command.Command;
import org.jetbrains.annotations.NotNull;

public class phuddisplay extends Command {
    public phuddisplay() {
        super("phuddisplay");
    }

    @Override
    public void handle(@NotNull String[] args) {
        WeaveFks weavefks = WeaveFks.getInstance();

        // Toggle the displayPartyHud variable
        weavefks.getConfig().displayPartyHUD = !weavefks.getConfig().displayPartyHUD;
        weavefks.saveConfig();

        try {
            String output = "Toggled displayPartyHUD to " + weavefks.getConfig().displayPartyHUD;

            weavefks.addChatComponentText(output);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
