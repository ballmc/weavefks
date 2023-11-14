package com.shtruz.mod;

import net.weavemc.loader.api.ModInitializer;
import net.weavemc.loader.api.command.CommandBus;
import net.weavemc.loader.api.event.*;
import com.shtruz.mod.ExternalFinalsCounter;
import com.shtruz.mod.finalscounter.ChatMessageParser;
import com.shtruz.mod.command.*;
import com.shtruz.mod.listener.RenderGameOverlayListener;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;

public class ExampleMod implements ModInitializer {
    @Override
    public void preInit() {
        System.out.println("Initializing ExampleMod!");
        ExternalFinalsCounter externalFinalsCounter = ExternalFinalsCounter.getInstance();
        ChatMessageParser chatMessageParser = externalFinalsCounter.getChatMessageParser();
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
        System.out.println("Game started!");
        ExternalFinalsCounter externalFinalsCounter = ExternalFinalsCounter.getInstance();
        externalFinalsCounter.initialize(System.getProperty("user.home") + "/.weave/mods");
        EventBus.subscribe(new RenderGameOverlayListener());
    }
}
