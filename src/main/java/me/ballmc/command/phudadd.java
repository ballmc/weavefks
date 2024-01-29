package me.ballmc.weavefks.command;

import me.ballmc.weavefks.WeaveFks;
import net.weavemc.loader.api.command.Command;
import org.jetbrains.annotations.NotNull;

public class phudadd extends Command {
    public phudadd() {
        super("phudadd");
    }

    @Override
    public void handle(@NotNull String[] args) {
        WeaveFks weavefks = WeaveFks.getInstance();

        if (args.length != 1) {
            return;
        }

        String playerName = args[0];

        // Add the player to the party HUD (modify the method as needed)
        weavefks.addPartyMember(playerName);

        try {
            String output = "Added " + playerName + " to the party HUD";

            weavefks.addChatComponentText(output);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}