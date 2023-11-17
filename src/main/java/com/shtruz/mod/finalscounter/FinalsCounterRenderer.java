package com.shtruz.mod.finalscounter;

import com.shtruz.mod.ExternalFinalsCounter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class FinalsCounterRenderer {
    private final ExternalFinalsCounter externalFinalsCounter;
    private final Map<String, String> teamStrings = new HashMap<>();

    public FinalsCounterRenderer(ExternalFinalsCounter externalFinalsCounter) {
        this.externalFinalsCounter = externalFinalsCounter;
    }

    public void update() {
        // Put the strings into a HashMap
        Map<String, Map<String, Integer>> teamData = new HashMap<>();
        teamData.put("Blue", externalFinalsCounter.getChatMessageParser().getBlue());
        teamData.put("Green", externalFinalsCounter.getChatMessageParser().getGreen());
        teamData.put("Red", externalFinalsCounter.getChatMessageParser().getRed());
        teamData.put("Yellow", externalFinalsCounter.getChatMessageParser().getYellow());

        // Sort the HashMap based on most finals
        List<Map.Entry<String, Map<String, Integer>>> sortedTeams = new ArrayList<>(teamData.entrySet());
        sortedTeams.sort(Comparator.comparing(entry ->
                entry.getValue()
                        .values()
                        .stream()
                        .mapToInt(Integer::intValue)
                        .sum(),
                Comparator.reverseOrder()));

        // Update teamStrings based on the sorted order
        teamStrings.clear();
        for (Map.Entry<String, Map<String, Integer>> entry : sortedTeams) {
            String teamName = entry.getKey();
            String teamString = printTeam(teamName, getPrefix(teamName), entry.getValue());
            teamStrings.put(teamName, teamString);
        }
    }

    public void render() {
        try {
            Minecraft mc = Minecraft.getMinecraft();
            // externalFinalsCounter.addChatComponentText("in render function");
            boolean inGameHasFocus = mc.inGameHasFocus;

            GameSettings gameSettings = mc.gameSettings;
            boolean showDebugInfo = gameSettings.showDebugInfo;

            if (externalFinalsCounter.getConfig().displayFinalsCounter
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

                // Iterate over the sorted team order
                for (Map.Entry<String, String> entry : teamStrings.entrySet()) {
                    String teamName = entry.getKey();
                    String teamString = entry.getValue();
                    fontRenderer.drawString(teamString, x, y, -1, false);
                    y += 10; // Adjust the spacing between teams as needed
                }

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

    private String getPrefix(String teamName) {
        switch (teamName) {
            case "Blue":
                return externalFinalsCounter.getChatMessageParser().getBluePrefix();
            case "Green":
                return externalFinalsCounter.getChatMessageParser().getGreenPrefix();
            case "Red":
                return externalFinalsCounter.getChatMessageParser().getRedPrefix();
            case "Yellow":
                return externalFinalsCounter.getChatMessageParser().getYellowPrefix();
            default:
                return "";
        }
    }
}