package com.shtruz.mod;

import net.weavemc.loader.api.ModInitializer;
import net.weavemc.loader.api.command.CommandBus;
import net.weavemc.loader.api.event.*;
import com.shtruz.mod.ExternalFinalsCounter;
import com.shtruz.mod.command.*;
import com.shtruz.mod.listener.RenderGameOverlayListener;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;

public class ExampleMod implements ModInitializer {
    @Override
    public void preInit() {
        System.out.println("Initializing ExampleMod!");

        CommandBus.register(new DisplayFinalsCounterCommand());
        CommandBus.register(new FinalsCommand());
        CommandBus.register(new FinalsInTabCommand());
        CommandBus.register(new PlayerFinalsCommand());
        CommandBus.register(new ResetFinalsCommand());
        CommandBus.register(new SetPosCommand());
        CommandBus.register(new SetScaleCommand());
        EventBus.subscribe(this);
        // EventBus.subscribe(KeyboardEvent.class, e -> {
        //     if (Minecraft.getMinecraft().currentScreen == null && e.getKeyState()) {
        //         Minecraft.getMinecraft().thePlayer.addChatMessage(
        //                 new ChatComponentText("Key Pressed: " + Keyboard.getKeyName(e.getKeyCode()))
        //         );
        //     }
        // });
        // EventBus.subscribe(RenderHandEvent.class, e -> e.setCancelled(true));
    }
    @SubscribeEvent
    public void onGameStart(StartGameEvent e) {
        System.out.println("ExternalFinalsCounter.initialize");
        ExternalFinalsCounter externalFinalsCounter = ExternalFinalsCounter.getInstance();
        externalFinalsCounter.initialize(System.getProperty("user.home") + "/.weave/mods");
        EventBus.subscribe(new RenderGameOverlayListener());
        System.out.println("Subscribed RenderGameOverlayListener");
    }
}
