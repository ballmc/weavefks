package me.ballmc.weavefks.command;

import me.ballmc.weavefks.WeaveFks;
import net.weavemc.loader.api.command.Command;
import org.jetbrains.annotations.NotNull;

public class PartyHudRemoveCommand extends Command {
    public PartyHudRemoveCommand() {
        super("phudremove");
    }

    @Override
    public void handle(@NotNull String[] args) {
        WeaveFks weavefks = WeaveFks.getInstance();

        if (args.length != 1) {
            return;
        }

        String playerName = args[0];
        weavefks.removePartyMember(playerName);

        try {
            String output = "Removed " + playerName + " from the party HUD";
            weavefks.addChatComponentText(output);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}