package me.ballmc.weavefks;

import com.google.gson.Gson;
import me.ballmc.weavefks.Config;
import me.ballmc.weavefks.finalscounter.ChatMessageParser;
import me.ballmc.weavefks.finalscounter.FinalsCounterRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import net.weavemc.loader.api.event.ChatReceivedEvent;
import net.weavemc.loader.api.event.SubscribeEvent;


import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class WeaveFks {
    public static WeaveFks instance;
    private final ChatMessageParser chatMessageParser = new ChatMessageParser(this);
    private final FinalsCounterRenderer finalsCounterRenderer = new FinalsCounterRenderer(this);
    private File configFile;
    private Config config = new Config();
    private final Gson gson = new Gson();
    
    public WeaveFks() {
        instance = this;
    }

    public static WeaveFks getInstance() {
        if (instance == null) {
            instance = new WeaveFks();
        }
        return instance;
    }

    public boolean initialize(String workingDirectory) {
        System.out.println("WeaveFks.initialize");
        configFile = new File(workingDirectory, "WeaveFks.json");

        if (configFile.exists()) {
            try {
                String jsonConfig = new String(Files.readAllBytes(configFile.toPath()), StandardCharsets.UTF_8);
                config = gson.fromJson(jsonConfig, Config.class);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else {
            saveConfig();
        }
        return true;
    }

    // @SubscribeEvent
    // public void onPrintChatMessage(ChatReceivedEvent e) {
    //     if (Minecraft.getMinecraft().thePlayer == null) return;
    //     if (Minecraft.getMinecraft().theWorld == null) return;
    //     System.out.println("WeaveFks.onPrintChatMessage");
    //     System.out.println("WeaveFks.onPrintChatMessage: " + e.getMessage());
    //     chatMessageParser.onChat(e.getMessage());
    //     instance.addChatComponentText("onPrintChatMessage");
    // }

    // public void onPrintChatMessage(IChatComponent iChatComponent, int chatLineId, CallbackInfo ci) {
    //     chatMessageParser.onChat(iChatComponent);
    //     instance.addChatComponentText("onPrintChatMessage");
    // }

    // public boolean onSendChatMessage(String message) {
    //     message = message.trim();

    //     if (message.startsWith(".")) {
    //         message = message.substring(1);

    //         if (!message.isEmpty()) {
    //             return commandManager.executeCommand(message);
    //         }
    //     }

    //     return false;
    // }
    
    // public void onRender() {
    //     finalsCounterRenderer.render();
    // }

    public ChatMessageParser getChatMessageParser() {
        return chatMessageParser;
    }

    public FinalsCounterRenderer getFinalsCounterRenderer() {
        return finalsCounterRenderer;
    }

    public Config getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            if (!configFile.exists()) {
                if (!configFile.createNewFile()) {
                    System.err.println("Failed to create config file!");
                    return;
                }
            }

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(configFile));
            bufferedWriter.write(gson.toJson(config));
            bufferedWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void addChatComponentText(String text) {
        if (Minecraft.getMinecraft().thePlayer == null) return;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(text));
    }

    public void sendMessage(String message) {
        if (Minecraft.getMinecraft().thePlayer == null) return;
        Minecraft.getMinecraft().thePlayer.sendChatMessage(message);
    }
}