import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.shtruz.mod.ExternalFinalsCounter;

@Mixin(GuiNewChat.class)
public class GuiNewChatMixin {
    @Inject(method = "printChatMessageWithOptionalDeletion", at = @At("HEAD"), cancellable = true)
    public void printChatMessageWithOptionalDeletion(IChatComponent chatComponent, int chatLineId, CallbackInfo ci) {
        System.out.println("GuiNewChatMixin");
        // ExternalFinalsCounter.getInstance().onPrintChatMessage(chatComponent, 0, ci);
    }
}