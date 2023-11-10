package com.shtruz.mod.listener;

import net.weavemc.loader.api.event.RenderGameOverlayEvent;
import net.weavemc.loader.api.event.SubscribeEvent;
import net.minecraft.client.renderer.GlStateManager;

import com.shtruz.mod.ExternalFinalsCounter;

import static org.lwjgl.opengl.GL11.*;

public class RenderGameOverlayListener {
    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        ExternalFinalsCounter externalFinalsCounter = ExternalFinalsCounter.getInstance();
        externalFinalsCounter.getFinalsCounterRenderer().render();
        // System.out.println("RenderGameOverlayListener");
        // externalFinalsCounter.addChatComponentText("RenderGameOverlayListener");
    }
}
