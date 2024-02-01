package me.ballmc.weavefks.command;

import me.ballmc.weavefks.WeaveFks;
import net.weavemc.loader.api.command.Command;
import org.jetbrains.annotations.NotNull;

public class PartyHudDisplayCommand extends Command {
    public PartyHudDisplayCommand() {
        super("phuddisplay");
    }

    @Override
    public void handle(@NotNull String[] args) {
        WeaveFks weavefks = WeaveFks.getInstance();
        weavefks.getConfig().displayPartyHUD = !weavefks.getConfig().displayPartyHUD;
        weavefks.saveConfig();

        try {
            String output = (weavefks.getConfig().displayPartyHUD ? "Enabled" : "Disabled") + " Party HUD";
            weavefks.addChatComponentText(output);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
