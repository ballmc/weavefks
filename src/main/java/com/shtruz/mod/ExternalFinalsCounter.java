package com.shtruz.mod;

import com.google.gson.Gson;
import com.shtruz.mod.Config;
import com.shtruz.mod.finalscounter.ChatMessageParser;
import com.shtruz.mod.finalscounter.FinalsCounterRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class ExternalFinalsCounter {
    public static ExternalFinalsCounter instance;
    private final ChatMessageParser chatMessageParser = new ChatMessageParser(this);
    private final FinalsCounterRenderer finalsCounterRenderer = new FinalsCounterRenderer(this);
    private File configFile;
    private Config config = new Config();
    private final Gson gson = new Gson();
    
    public ExternalFinalsCounter() {
        instance = this;
    }

    public static ExternalFinalsCounter getInstance() {
        if (instance == null) {
            instance = new ExternalFinalsCounter();
        }
        return instance;
    }

    public boolean initialize(String workingDirectory) {
        configFile = new File(workingDirectory, "ExternalFinalsCounter.json");

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

    // public void onPrintChatMessage(IChatComponent iChatComponent) {
    //     chatMessageParser.onChat(iChatComponent);
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

    public void onRender() {
        finalsCounterRenderer.render();
    }

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
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(text));

    }
}