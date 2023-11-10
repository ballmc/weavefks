package com.shtruz.mod.command;
import com.shtruz.mod.ExternalFinalsCounter;
import net.minecraft.client.Minecraft;
import net.weavemc.loader.api.command.Command;
import org.jetbrains.annotations.NotNull;

public class DisplayFinalsCounterCommand extends Command {
  public DisplayFinalsCounterCommand() {
      super("displayfinalscounter");
  }

  @Override
  public void handle(@NotNull String[] args) {
      ExternalFinalsCounter externalFinalsCounter = ExternalFinalsCounter.getInstance();
      externalFinalsCounter.getConfig().displayFinalsCounter = !externalFinalsCounter.getConfig().displayFinalsCounter;
      externalFinalsCounter.saveConfig();
      String output = (externalFinalsCounter.getConfig().displayFinalsCounter ? "Enabled" : "Disabled") + " finals counter HUD";
      externalFinalsCounter.addChatComponentText(output);
      String workingDirectory = System.getProperty("user.home") + "/.weave/mods";
      externalFinalsCounter.addChatComponentText("DIR: " + workingDirectory);
  }
}