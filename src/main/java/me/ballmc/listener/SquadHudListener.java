package me.ballmc.weavefks.listener;

import net.weavemc.loader.api.event.RenderGameOverlayEvent;
import net.weavemc.loader.api.event.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class SquadHudListener {
    private static final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        renderSquadHUD();
    }

    private void renderSquadHUD() {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int screenWidth = scaledResolution.getScaledWidth();
        int screenHeight = scaledResolution.getScaledHeight();

        GlStateManager.disableTexture2D();
        GlStateManager.color(1f, 1f, 1f);

        // Set up HUD position and size
        int hudX = 10;
        int hudY = 10;
        int hudWidth = 200;
        int hudHeight = mc.theWorld.playerEntities.size() * 30;

        // Draw HUD background with border
        drawRectWithBorder(hudX, hudY, hudX + hudWidth, hudY + hudHeight, 0x80FFFFFF, 0xFF000000);

        // Render player information in the HUD
        List<EntityPlayer> playerList = new ArrayList<>(mc.theWorld.playerEntities);
        for (int i = 0; i < playerList.size(); i++) {
            EntityPlayer player = playerList.get(i);
            renderPlayerInfo(player, hudX + 5, hudY + 5 + i * 30);
        }

        GlStateManager.enableTexture2D();
    }

    private void renderPlayerInfo(EntityPlayer player, int x, int y) {
        // Render player head background (you may need to adjust the scale and position)
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, 16, 16, 16, 16);

        // Render player head (you may need to adjust the scale and position)
        // mc.getTextureManager().bindTexture(player.getLocationSkin());
        Gui.drawScaledCustomSizeModalRect(x + 2, y + 2, 8, 8, 12, 12, 12, 12, 64, 64);

        // Render player name and health with styling
        String playerInfo = " " + player.getName() + " | Health: " + (int) player.getHealth();
        mc.fontRendererObj.drawStringWithShadow(playerInfo, x + 20, y + 2, 0xFFFFFF);
    }

    private void drawRectWithBorder(int left, int top, int right, int bottom, int backgroundColor, int borderColor) {
        // Draw background
        Gui.drawRect(left + 1, top + 1, right - 1, bottom - 1, backgroundColor);

        // Draw border
        Gui.drawRect(left, top, right, top + 1, borderColor); // Top
        Gui.drawRect(right - 1, top + 1, right, bottom - 1, borderColor); // Right
        Gui.drawRect(left, bottom - 1, right, bottom, borderColor); // Bottom
        Gui.drawRect(left, top + 1, left + 1, bottom - 1, borderColor); // Left
    }
}
