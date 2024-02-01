package me.ballmc.weavefks.listener;

import net.weavemc.loader.api.event.RenderGameOverlayEvent;
import net.weavemc.loader.api.event.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
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
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }

        weavefks.addSelfToPartyMembers();

        if (weavefks.getPartyMembers() == null || weavefks.getPartyMembers().isEmpty()) {
            return;
        }

        boolean inGameHasFocus = mc.inGameHasFocus;
        GameSettings gameSettings = mc.gameSettings;
        boolean showDebugInfo = gameSettings.showDebugInfo;

        if (weavefks.getConfig().displayPartyHUD && inGameHasFocus && !showDebugInfo) {
            float x = (float) (weavefks.getConfig().partyHUDX / 2.0);
            float y = (float) (weavefks.getConfig().partyHUDY / 2.0);

            double scale = weavefks.getConfig().partyHUDScale / 100.0;

            x /= scale;
            y /= scale;

            GlStateManager.pushMatrix();

            GlStateManager.scale(scale, scale, 1.0);

            FontRenderer fontRenderer = mc.fontRendererObj;

            for (String playerName : weavefks.getPartyMembers()) {
                List<NetworkPlayerInfo> partyMembersInfo = weavefks.getPartyMembersNetworkPlayerInfo(playerName);
                for (NetworkPlayerInfo playerInfo : partyMembersInfo) {
                    String playerDisplayName = playerInfo.getGameProfile().getName();
                    fontRenderer.drawString(playerDisplayName, x, y, -1, false);
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
