package me.ballmc.weavefks.mixins;

import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.client.network.NetworkPlayerInfo;


import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;


@Mixin(GuiPlayerTabOverlay.class)
public abstract class GuiPlayerTabOverlayMixin {
    private ScoreObjective p_175247_1_;

    private String p_175247_3_;

  private static EnumChatFormatting getHPColor(float maxHealthPoints, float healthPoints) {
        if (healthPoints > maxHealthPoints) {
            // Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("dark_green: " + EnumChatFormatting.DARK_GREEN));
            return EnumChatFormatting.DARK_GREEN;
        } else if (healthPoints > maxHealthPoints * 3f / 4f) {
            // Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("green: " + EnumChatFormatting.GREEN));
            return EnumChatFormatting.GREEN;
        } else if (healthPoints > maxHealthPoints / 2f) {
            // Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("yellow: " + EnumChatFormatting.YELLOW));
            return EnumChatFormatting.YELLOW;
        } else if (healthPoints > maxHealthPoints / 4f) {
            // Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("red: " + EnumChatFormatting.RED));
            return EnumChatFormatting.RED;
        } else {
            // Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("dark red: " + EnumChatFormatting.DARK_RED));
            return EnumChatFormatting.DARK_RED;
        }
    }

   private static EnumChatFormatting getColoredHP(int healthPoints) { 
    final float maxHealthPoints;
    maxHealthPoints = Minecraft.getMinecraft().thePlayer.getMaxHealth();
    // Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Current hp: " + healthPoints + "max hp: " + maxHealthPoints));
    return getHPColor(maxHealthPoints, healthPoints);
   }

    @ModifyVariable(method = "drawScoreboardValues", at = @At(value = "HEAD"), argsOnly = true)
    private ScoreObjective setter(ScoreObjective p_175247_1_) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("set so!"));
        this.p_175247_1_ = p_175247_1_;
        return p_175247_1_;
    }
    

    @ModifyVariable(method = "drawScoreboardValues", at = @At(value = "HEAD"), argsOnly = true)
    private String setter(String p_175247_3_) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("set str!"));
        this.p_175247_3_ = p_175247_3_;
        return p_175247_3_;
    }

    @Redirect(method = "drawScoreboardValues", at = @At(value = "FIELD", target = "Lnet/minecraft/util/EnumChatFormatting;YELLOW:Lnet/minecraft/util/EnumChatFormatting;", opcode = Opcodes.GETSTATIC))
    private EnumChatFormatting redirectDrawScoreboardValues() {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("redir: " + this.p_175247_1_.getScoreboard().getValueFromObjective(this.p_175247_3_, this.p_175247_1_).getScorePoints()));
        // EnumChatFormatting coloredHP = getColoredHP(variableIndex);
        return getColoredHP(p_175247_1_.getScoreboard().getValueFromObjective(this.p_175247_3_, this.p_175247_1_).getScorePoints());
    }

}


