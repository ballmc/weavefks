package com.shtruz.mod.finalscounter;

import com.shtruz.mod.ExternalFinalsCounter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.*;
import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap;
import java.util.Map;

public class FinalsCounterRenderer {
    private final ExternalFinalsCounter externalFinalsCounter;
    private String blue = "";
    private String green = "";
    private String red = "";
    private String yellow = "";

    public FinalsCounterRenderer(ExternalFinalsCounter externalFinalsCounter) {
        this.externalFinalsCounter = externalFinalsCounter;
    }

    public void update() {
        blue = printTeam("Blue", externalFinalsCounter.getChatMessageParser().getBluePrefix(), externalFinalsCounter.getChatMessageParser().getBlue());
        green = printTeam("Green", externalFinalsCounter.getChatMessageParser().getGreenPrefix(), externalFinalsCounter.getChatMessageParser().getGreen());
        red = printTeam("Red", externalFinalsCounter.getChatMessageParser().getRedPrefix(), externalFinalsCounter.getChatMessageParser().getRed());
        yellow = printTeam("Yellow", externalFinalsCounter.getChatMessageParser().getYellowPrefix(), externalFinalsCounter.getChatMessageParser().getYellow());
    }

    public void render() {
        try {
            Minecraft mc = Minecraft.getMinecraft();
            // externalFinalsCounter.addChatComponentText("in render function");
            boolean inGameHasFocus = mc.inGameHasFocus;

            GameSettings gameSettings = mc.gameSettings;
            boolean showDebugInfo = gameSettings.showDebugInfo;

            if (externalFinalsCounter.getConfig().displayFinalsCounter
                    && !externalFinalsCounter.getChatMessageParser().getAllPlayers().isEmpty()
                    && inGameHasFocus
                    && !showDebugInfo) {
                float x = externalFinalsCounter.getConfig().finalsCounterX;
                float y = externalFinalsCounter.getConfig().finalsCounterY;

                double scale = externalFinalsCounter.getConfig().finalsCounterScale / 100.0;

                x /= scale;
                y /= scale;

                GlStateManager.pushMatrix();

                GlStateManager.scale(scale, scale, 1.0);

                FontRenderer fontRenderer = mc.fontRendererObj;

                fontRenderer.drawString(blue, x, y, -1, false);
                fontRenderer.drawString(green, x, y + 10, -1, false);
                fontRenderer.drawString(red, x, y + 20, -1, false);
                fontRenderer.drawString(yellow, x, y + 30, -1, false);

                GlStateManager.popMatrix();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private String printTeam(String team, String prefix, Map<String, Integer> players) {
        int finals = players
                .values()
                .stream()
                .reduce(0, Integer::sum);

        Map.Entry<String, Integer> highestFinalsPlayer = players
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElse(new AbstractMap.SimpleImmutableEntry<>("", 0));

        return prefix + team + " "
                + "\u00A7f"
                + "(" + finals + ") - "
                + highestFinalsPlayer.getKey()
                + " (" + highestFinalsPlayer.getValue() + ")";
    }
}