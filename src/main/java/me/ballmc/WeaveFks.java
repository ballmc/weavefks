package me.ballmc.weavefks;

import com.google.gson.Gson;
import me.ballmc.weavefks.Config;
import me.ballmc.weavefks.finalscounter.ChatMessageParser;
import me.ballmc.weavefks.finalscounter.FinalsCounterRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
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
import java.util.ArrayList;
import java.util.List;

public class WeaveFks {
    public static WeaveFks instance;
    private final ChatMessageParser chatMessageParser = new ChatMessageParser(this);
    private final FinalsCounterRenderer finalsCounterRenderer = new FinalsCounterRenderer(this);
    private File configFile;
    private Config config = new Config();
    private final Gson gson = new Gson();

    private static List<String> partyMembers = new ArrayList<>();
    
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

    public List<String> getPartyMembers() {
        return new ArrayList<>(partyMembers);
    }

    public void addPartyMember(String playerName) {
        partyMembers.add(playerName);
    }

    public void removePartyMember(String playerName) {
        partyMembers.remove(playerName);
    }

    public void clearPartyMembers() {
        partyMembers.clear();
    }

    public void addSelfToPartyMembers() {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player != null) {
            String playerName = player.getGameProfile().getName();
            if (!partyMembers.contains(playerName)) {
                partyMembers.add(playerName);
            }
        }
    }

    public EntityPlayer getPlayerByName(String playerName) {
        for (Entity entity : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                String playerNameInGame = player.getGameProfile().getName();
                // Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("playerbyname: " + playerNameInGame));
                if (playerNameInGame != null && playerNameInGame.equals(playerName)) {
                    return player;
                }
            }
        }
        return null; // Player not found
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
        if (Minecraft.getMinecraft().thePlayer == null) return;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(text));
    }

    public void sendMessage(String message) {
        if (Minecraft.getMinecraft().thePlayer == null) return;
        Minecraft.getMinecraft().thePlayer.sendChatMessage(message);
    }
}