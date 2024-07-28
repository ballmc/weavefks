package me.ballmc.weavefks.mixins;

import me.ballmc.weavefks.WeaveFks;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import com.mojang.authlib.GameProfile;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.regex.Pattern;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Shadow;


@Mixin(GuiPlayerTabOverlay.class)
public abstract class GuiPlayerTabOverlayMixin {
    private ScoreObjective p_175247_1_;
    private String p_175247_3_;
    private static final WeaveFks weavefks = WeaveFks.getInstance();


  private static EnumChatFormatting getHPColor(float maxHealthPoints, float healthPoints) {
        if (healthPoints > maxHealthPoints) {
            return EnumChatFormatting.GREEN;
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

    @ModifyVariable(method = "drawScoreboardValues", at = @At(value = "HEAD"), argsOnly = true)
    private ScoreObjective setter(ScoreObjective p_175247_1_) {
        this.p_175247_1_ = p_175247_1_;
        return p_175247_1_;
    }
    
    @ModifyVariable(method = "drawScoreboardValues", at = @At(value = "HEAD"), argsOnly = true)
    private String setter(String p_175247_3_) {
        this.p_175247_3_ = p_175247_3_;
        return p_175247_3_;
    }

    @Redirect(method = "drawScoreboardValues", at = @At(value = "FIELD", target = "Lnet/minecraft/util/EnumChatFormatting;YELLOW:Lnet/minecraft/util/EnumChatFormatting;", opcode = Opcodes.GETSTATIC))
    private EnumChatFormatting redirectDrawScoreboardValues() {
        return getColoredHP(this.p_175247_1_.getScoreboard().getValueFromObjective(this.p_175247_3_, this.p_175247_1_).getScorePoints());
    }

    private static final String goldColorCode = "\u00A76";
    private static final Pattern FORMATTING_COLOR_CODES_PATTERN = Pattern.compile("(?i)\\u00a7[0-9A-FK-OR]");
    private static final Pattern BRACKETS_PATTERN = Pattern.compile("\\[.*?\\]");

    private static final String stripColorCodes(String text) {
        return text.isEmpty() ? text : FORMATTING_COLOR_CODES_PATTERN.matcher(text).replaceAll("");
    }

    private static String stripBrackets(String text) {
        return text.isEmpty() ? text : BRACKETS_PATTERN.matcher(text).replaceAll("");
    }

    @Inject(method = "getPlayerName", at = @At("RETURN"), cancellable = true)
    private void onGetPlayerName(NetworkPlayerInfo networkPlayerInfoIn, CallbackInfoReturnable<String> cir) {
        String playerName = cir.getReturnValue();
        if (playerName != null) {
            String strippedPlayerName = stripBrackets(stripColorCodes(playerName)).trim();
            String appendString = goldColorCode + weavefks.getChatMessageParser().getFinalsInTabString(strippedPlayerName);
            playerName += appendString;
            cir.setReturnValue(playerName);
        }
    }
}


