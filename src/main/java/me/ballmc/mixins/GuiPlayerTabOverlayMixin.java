package me.ballmc.weavefks.mixins;

import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiPlayerTabOverlay.class)
public class GuiPlayerTabOverlayMixin {
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
  //  @Redirect(method = "drawScoreboardValues", at = @At(value = "FIELD", target = "Lnet/minecraft/util/EnumChatFormatting;YELLOW:Lnet/minecraft/util/EnumChatFormatting;"))
  //   private void onDrawScoreboardValues(int i) {
  //       Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("colored hp: " + getColoredHP(i) + "" + i));
  //       getColoredHP(i);
  //   }
//   @ModifyVariable(method = "drawScoreboardValues", at = @At(value = "STORE", ordinal = 0), name = "s1")
//   private String redirectDrawScoreboardValues(String s1) {
//         Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("colored hp: " + getColoredHP(4) + "" + 27));
//         return getColoredHP(4) + "" + 27;
//     }

    @Redirect(method = "drawScoreboardValues", at = @At(value = "FIELD", target = "Lnet/minecraft/util/EnumChatFormatting;YELLOW:Lnet/minecraft/util/EnumChatFormatting;", opcode = Opcodes.GETSTATIC))
    private EnumChatFormatting redirectDrawScoreboardValues() {
        // The index of the variable on the stack after the GETSTATIC instruction
        int variableIndex = 7;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("colored hp: " + getColoredHP(variableIndex) + "" + variableIndex));
        // EnumChatFormatting coloredHP = getColoredHP(variableIndex);
        return EnumChatFormatting.DARK_GREEN;
    }

}


