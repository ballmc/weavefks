package me.ballmc.weavefks;

import net.weavemc.loader.api.ModInitializer;
import net.weavemc.loader.api.command.CommandBus;
import net.weavemc.loader.api.event.*;
import me.ballmc.weavefks.WeaveFks;
import me.ballmc.weavefks.finalscounter.ChatMessageParser;
import me.ballmc.weavefks.command.*;
import me.ballmc.weavefks.listener.RenderGameOverlayListener;
import me.ballmc.weavefks.listener.SquadHudListener;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;

public class Main implements ModInitializer {
    @Override
    public void preInit() {
        System.out.println("Initializing weavefks!");
        WeaveFks weavefks = WeaveFks.getInstance();
        ChatMessageParser chatMessageParser = weavefks.getChatMessageParser();
        CommandBus.register(new DisplayFinalsCounterCommand());
        CommandBus.register(new FinalsCommand());
        CommandBus.register(new FinalsInTabCommand());
        CommandBus.register(new PlayerFinalsCommand());
        CommandBus.register(new ResetFinalsCommand());
        CommandBus.register(new SayFks());
        CommandBus.register(new SetPosCommand());
        CommandBus.register(new SetScaleCommand());
        EventBus.subscribe(this);
        EventBus.subscribe(ChatReceivedEvent.class, e -> {
            chatMessageParser.onChat(e.getMessage());
        });
    }
    @SubscribeEvent
    public void onGameStart(StartGameEvent e) {
        WeaveFks weavefks = WeaveFks.getInstance();
        weavefks.initialize(System.getProperty("user.home") + "/.weave/mods");
        EventBus.subscribe(new RenderGameOverlayListener());
        // EventBus.subscribe(new SquadHudListener());
    }
}
