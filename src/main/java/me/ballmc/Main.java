package me.ballmc.weavefks;

import net.weavemc.loader.api.ModInitializer;
import net.weavemc.loader.api.command.CommandBus;
import net.weavemc.loader.api.event.*;
import me.ballmc.weavefks.WeaveFks;
import me.ballmc.weavefks.finalscounter.ChatMessageParser;
import me.ballmc.weavefks.command.*;
import me.ballmc.weavefks.listener.RenderGameOverlayListener;
import me.ballmc.weavefks.listener.PartyHudListener;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;

public class Main implements ModInitializer {
    @Override
    public void preInit() {
        System.out.println("Initializing weavefks!");
        EventBus.subscribe(this);
        
    }
    @SubscribeEvent
    public void onGameStart(StartGameEvent.Post e) {
        WeaveFks weavefks = WeaveFks.getInstance();
        ChatMessageParser chatMessageParser = weavefks.getChatMessageParser();
        EventBus.subscribe(ChatReceivedEvent.class, ce -> {
            chatMessageParser.onChat(ce.getMessage());
        });
        CommandBus.register(new DisplayFinalsCounterCommand());
        CommandBus.register(new FinalsCommand());
        CommandBus.register(new FinalsInTabCommand());
        CommandBus.register(new PartyHudAddCommand());
        CommandBus.register(new PartyHudClearCommand());
        CommandBus.register(new PartyHudDisplayCommand());
        CommandBus.register(new PartyHudListCommand());
        CommandBus.register(new PartyHudPositionCommand());
        CommandBus.register(new PartyHudScaleCommand());
        CommandBus.register(new PlayerFinalsCommand());
        CommandBus.register(new ResetFinalsCommand());
        CommandBus.register(new SayFks());
        CommandBus.register(new SetPosCommand());
        CommandBus.register(new SetScaleCommand());
        weavefks.initialize(System.getProperty("user.home") + "/.weave/mods");
        EventBus.subscribe(new RenderGameOverlayListener());
        EventBus.subscribe(new PartyHudListener());
    }
}
