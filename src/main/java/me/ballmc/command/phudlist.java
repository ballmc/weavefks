package me.ballmc.weavefks.command;

import me.ballmc.weavefks.WeaveFks;
import net.weavemc.loader.api.command.Command;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class phudlist extends Command {
    public phudlist() {
        super("phudlist");
    }

    @Override
    public void handle(@NotNull String[] args) {
        WeaveFks weavefks = WeaveFks.getInstance();

        // Get the current list of players in the party HUD (modify the method as needed)
        List<String> partyMembers = weavefks.getPartyMembers();

        try {
            if (partyMembers.isEmpty()) {
                weavefks.addChatComponentText("Party HUD is empty.");
            } else {
                weavefks.addChatComponentText("Party HUD members:");
                for (String playerName : partyMembers) {
                    weavefks.addChatComponentText("- " + playerName);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}