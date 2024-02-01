package me.ballmc.weavefks.listener;

import net.weavemc.loader.api.event.RenderGameOverlayEvent;
import net.weavemc.loader.api.event.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldSettings;
import me.ballmc.weavefks.WeaveFks;

import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mojang.authlib.GameProfile;

import static org.lwjgl.opengl.GL11.*;

public class PartyHudListener {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final WeaveFks weavefks = WeaveFks.getInstance();
    String scoreString;
    String finalsString;

    private static EnumChatFormatting getHPColor(float maxHealthPoints, float healthPoints) {
        if (healthPoints > maxHealthPoints) {
            return EnumChatFormatting.DARK_GREEN;
        } else if (healthPoints > maxHealthPoints * 3f / 4f) {
            return EnumChatFormatting.GREEN;
        } else if (healthPoints > maxHealthPoints / 2f) {
            return EnumChatFormatting.YELLOW;
        } else if (healthPoints > maxHealthPoints / 4f) {
            return EnumChatFormatting.RED;
        } else {
            return EnumChatFormatting.DARK_RED;
        }
    }

   private static EnumChatFormatting getColoredHP(int healthPoints) { 
    final float maxHealthPoints;
    maxHealthPoints = Minecraft.getMinecraft().thePlayer.getMaxHealth();
    return getHPColor(maxHealthPoints, healthPoints);
   }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        render();
    }

    public void render() {
    try {
        Minecraft mc = Minecraft.getMinecraft();
        final Scoreboard scoreboard = mc.theWorld.getScoreboard();
        final ScoreObjective scoreobjective = scoreboard.getObjectiveInDisplaySlot(0);


        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }

        if (mc.isIntegratedServerRunning() && scoreobjective == null) {
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
                NetworkPlayerInfo playerInfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(playerName);
                if (playerInfo != null) {
                    scoreString = "";
                    finalsString = weavefks.getChatMessageParser().getFinalsPlayer(playerName);
                    if (scoreobjective != null && playerInfo.getGameType() != WorldSettings.GameType.SPECTATOR && scoreobjective.getRenderType() != IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
                        final int scorePoints = scoreobjective.getScoreboard().getValueFromObjective(playerInfo.getGameProfile().getName(), scoreobjective).getScorePoints();
                        scoreString = getColoredHP(scorePoints) + " " + scorePoints;
                    }
                    String displayString = ScorePlayerTeam.formatPlayerName(playerInfo.getPlayerTeam(), playerInfo.getGameProfile().getName());
                    fontRenderer.drawStringWithShadow(displayString + finalsString + scoreString, x, y, 0xFFFFFF);
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
