package me.ballmc.weavefks.command;

import me.ballmc.weavefks.WeaveFks;
import net.weavemc.loader.api.command.Command;
import org.jetbrains.annotations.NotNull;

public class PartyHudClearCommand extends Command {
    public PartyHudClearCommand() {
        super("phudclear");
        System.out.println("phudclear");
    }

    @Override
    public void handle(@NotNull String[] args) {
        WeaveFks weavefks = WeaveFks.getInstance();

        // Clear all players from the party HUD (modify the method as needed)
        weavefks.clearPartyMembers();
        weavefks.addSelfToPartyMembers();

        try {
            String output = "Cleared all players from the party HUD";

            weavefks.addChatComponentText(output);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}