import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import com.shtruz.mod.ExternalFinalsCounter;

@Mixin(GuiNewChat.class)
public class GuiNewChatMixin {
    @Inject(method = "printChatMessage", at = @At("HEAD"))
    private void onPrintChatMessage(IChatComponent chatComponent, CallbackInfo ci) {
        ExternalFinalsCounter.getInstance().onPrintChatMessage(chatComponent);
        System.out.println("GuiNewChatMixin");
    }
}