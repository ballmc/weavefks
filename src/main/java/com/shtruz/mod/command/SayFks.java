package com.shtruz.mod.command;

import com.shtruz.mod.ExternalFinalsCounter;
import net.weavemc.loader.api.command.Command;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class SayFks extends Command {
    public SayFks() {
        super("sayfks");
    }

    @Override
    public void handle(@NotNull String[] args) {
        ExternalFinalsCounter externalFinalsCounter = ExternalFinalsCounter.getInstance();

        try {
            int blueFinals = externalFinalsCounter.getChatMessageParser().getBlue()
                    .values()
                    .stream()
                    .reduce(0, Integer::sum);

            int greenFinals = externalFinalsCounter.getChatMessageParser().getGreen()
                    .values()
                    .stream()
                    .reduce(0, Integer::sum);

            int redFinals = externalFinalsCounter.getChatMessageParser().getRed()
                    .values()
                    .stream()
                    .reduce(0, Integer::sum);

            int yellowFinals = externalFinalsCounter.getChatMessageParser().getYellow()
                    .values()
                    .stream()
                    .reduce(0, Integer::sum);
            
            int[] finalsArray = {blueFinals, greenFinals, redFinals, yellowFinals};
            String[] teamNames = {"BLUE", "GREEN", "RED", "YELLOW"};
            String[] sortedTeams = Arrays.stream(teamNames)
            .sorted(Comparator.comparingInt(team -> -finalsArray[Arrays.asList(teamNames).indexOf(team)]))
            .toArray(String[]::new);

            String sortedFinals = Arrays.stream(sortedTeams)
            .map(team -> team + ": " + finalsArray[Arrays.asList(teamNames).indexOf(team)])
            .collect(Collectors.joining(" "));

            // String finals = "BLUE: " + blueFinals + " " +
            //                 "GREEN: " + greenFinals + " " +
            //                 "RED: " + redFinals + " " +
            //                 "YELLOW: " + yellowFinals;

            externalFinalsCounter.sendMessage(sortedFinals);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
