package com.shtruz.mod.finalscounter;

import com.shtruz.mod.ExternalFinalsCounter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors; 


public class FinalsCounterRenderer {
    private final ExternalFinalsCounter externalFinalsCounter;
    private final Map<String, String> teamStrings = new LinkedHashMap<>();

    public FinalsCounterRenderer(ExternalFinalsCounter externalFinalsCounter) {
        this.externalFinalsCounter = externalFinalsCounter;
    }

    public void update() {
        // Put the strings into a LinkedHashMap to maintain the order of insertion
        LinkedHashMap<String, Map<String, Integer>> teamData = new LinkedHashMap<>();
        teamData.put("Blue", externalFinalsCounter.getChatMessageParser().getBlue());
        teamData.put("Green", externalFinalsCounter.getChatMessageParser().getGreen());
        teamData.put("Red", externalFinalsCounter.getChatMessageParser().getRed());
        teamData.put("Yellow", externalFinalsCounter.getChatMessageParser().getYellow());

        LinkedHashMap<String, Map<String, Integer>> sortedTeams = sortByValues(teamData);
    
        // Update teamStrings based on the sorted order
        teamStrings.clear();
        for (Map.Entry<String, Map<String, Integer>> entry : sortedTeams.entrySet()) {
            String teamName = entry.getKey();
            String teamString = printTeam(teamName, getPrefix(teamName), entry.getValue());
            teamStrings.put(teamName, teamString);
        }
    }

    private static LinkedHashMap<String, Map<String, Integer>> sortByValues(Map<String, Map<String, Integer>> map) {
        List<Map.Entry<String, Map<String, Integer>>> entries = new LinkedList<>(map.entrySet());
    
        Collections.sort(entries, Comparator.comparing(entry ->
                entry.getValue()
                        .values()
                        .stream()
                        .mapToInt(Integer::intValue)
                        .sum(),
                Comparator.reverseOrder()));
    
        LinkedHashMap<String, Map<String, Integer>> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Map<String, Integer>> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
    
        return sortedMap;
    }

    // public void update() {
    //     // Creating a LinkedHashMap to maintain the order of insertion
    //     LinkedHashMap<String, Integer> teamData = new LinkedHashMap<>();
    //     teamData.put("Blue", 1);
    //     teamData.put("Green", 2);
    //     teamData.put("Red", 2);
    //     teamData.put("Yellow", 4);

    //     // Sorting the map by values in descending order
    //     LinkedHashMap<String, Integer> sortedTeamData = sortByValues(teamData);

    //     // Update teamStrings based on the sorted order
    //     for (Map.Entry<String, Integer> entry : sortedTeamData.entrySet()) {
    //         String teamName = entry.getKey();
    //         String teamString = printTeam(teamName, getPrefix(teamName), Collections.singletonMap("finals", entry.getValue()));
    //         teamStrings.put(teamName, teamString);
    //     }
    // }

    // private LinkedHashMap<String, Integer> sortByValues(Map<String, Integer> map) {
    //     List<Map.Entry<String, Integer>> entries = new LinkedList<>(map.entrySet());

    //     // Custom comparator to sort by values in descending order
    //     Collections.sort(entries, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));

    //     // Creating a new LinkedHashMap to maintain the order of insertion
    //     LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
    //     for (Map.Entry<String, Integer> entry : entries) {
    //         sortedMap.put(entry.getKey(), entry.getValue());
    //     }

    //     return sortedMap;
    // }

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
                    // System.out.println("teamName: " + teamName + ", teamString: " + teamString + ", x: " + x + ", y: " + y);
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