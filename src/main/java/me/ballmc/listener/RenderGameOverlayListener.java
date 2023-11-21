package me.ballmc.weavefks.listener;

import net.weavemc.loader.api.event.RenderGameOverlayEvent;
import net.weavemc.loader.api.event.SubscribeEvent;
import net.minecraft.client.renderer.GlStateManager;

import me.ballmc.weavefks.WeaveFks;

import static org.lwjgl.opengl.GL11.*;

public class RenderGameOverlayListener {
    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        WeaveFks weavefks = WeaveFks.getInstance();
        weavefks.getFinalsCounterRenderer().render();
        // System.out.println("RenderGameOverlayListener");
        // weavefks.addChatComponentText("RenderGameOverlayListener");
    }
}
