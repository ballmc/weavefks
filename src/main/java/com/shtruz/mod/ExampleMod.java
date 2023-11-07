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


        // CommandBus.register(new TestCommand());
        CommandBus.register(new DisplayFinalsCounterCommand());
        EventBus.subscribe(StartGameEvent.class, this::onGameStart);
        // EventBus.subscribe(KeyboardEvent.class, e -> {
        //     if (Minecraft.getMinecraft().currentScreen == null && e.getKeyState()) {
        //         Minecraft.getMinecraft().thePlayer.addChatMessage(
        //                 new ChatComponentText("Key Pressed: " + Keyboard.getKeyName(e.getKeyCode()))
        //         );
        //     }
        // });
        // EventBus.subscribe(RenderHandEvent.class, e -> e.setCancelled(true));

        // EventBus.subscribe(new RenderGameOverlayListener());
    }
    @SubscribeEvent
    public void onGameStart(StartGameEvent e) {
        System.out.println("ExternalFinalsCounter.initialize");
        ExternalFinalsCounter externalFinalsCounter = ExternalFinalsCounter.getInstance();
        externalFinalsCounter.initialize(System.getProperty("user.dir") + "/.weave/mods");
    }
}
