package me.ballmc.weavefks.listener;

import net.weavemc.loader.api.event.RenderGameOverlayEvent;
import net.weavemc.loader.api.event.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import me.ballmc.weavefks.WeaveFks;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class PartyHudListener {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final WeaveFks weavefks = WeaveFks.getInstance();
    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        render();
    }

    public void render() {
    try {
        // mc.thePlayer.addChatMessage(new ChatComponentText("in phud listener"));
        if (mc.thePlayer == null) {
            return;
        }
        weavefks.addSelfToPartyMembers();
        if (weavefks.getPartyMembers() == null || weavefks.getPartyMembers().isEmpty()) {
            return;
        }
        Minecraft mc = Minecraft.getMinecraft();
        boolean inGameHasFocus = mc.inGameHasFocus;
        GameSettings gameSettings = mc.gameSettings;
        boolean showDebugInfo = gameSettings.showDebugInfo;
        if (weavefks.getConfig().displayPartyHUD && inGameHasFocus && !showDebugInfo) {
            float x = weavefks.getConfig().partyHUDX;
            float y = weavefks.getConfig().partyHUDY;

            double scale = weavefks.getConfig().partyHUDScale / 100.0;

            x /= scale;
            y /= scale;

            GlStateManager.pushMatrix();

            GlStateManager.scale(scale, scale, 1.0);

            FontRenderer fontRenderer = mc.fontRendererObj;

            for (EntityPlayer loadedPlayer : mc.theWorld.playerEntities) {
                String loadedPlayerName = loadedPlayer.getGameProfile().getName();
                // mc.thePlayer.addChatMessage(new ChatComponentText("Loaded Player: " + loadedPlayerName));
            }
        
            for (String playerName : weavefks.getPartyMembers()) {
                EntityPlayer player = weavefks.getPlayerByName(playerName);
            
                if (player != null) {
                    float playerHealth = player.getHealth();
                    String displayString = player.getDisplayName().getFormattedText() + " - " + Math.round(playerHealth) + " HP";
                    fontRenderer.drawString(displayString, x, y, -1, false);
                    y += 10; 
                }
            }

            GlStateManager.popMatrix();
        }
    } catch (Exception exception) {
        exception.printStackTrace();
    }
}
}
