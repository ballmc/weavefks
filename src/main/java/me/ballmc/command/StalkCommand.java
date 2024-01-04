package me.ballmc.command;

import me.ballmc.weavefks.WeaveFks;
import net.weavemc.loader.api.command.Command;
import org.jetbrains.annotations.NotNull;

public class StalkCommand extends Command {
    public StalkCommand() {
        super("stalk");
    }

    @Override
    public void handle(@NotNull String[] args) {
        WeaveFks weavefks = WeaveFks.getInstance();

        if (args.length != 1) {
            return;
        }

        String username;

        try {
            username = args[0];
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
            return;
        }

        try {
            // get className by querying hypixel api 
            String output = username + "is playing "  ;

            weavefks.addChatComponentText(output);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}