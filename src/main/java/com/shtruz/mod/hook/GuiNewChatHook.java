package com.shtruz.mod.hook;

import net.weavemc.loader.api.Hook;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.Opcodes;
import org.jetbrains.annotations.NotNull;
import com.shtruz.mod.ExternalFinalsCounter;

public class GuiNewChatHook extends Hook {
    public GuiNewChatHook() {
        super("net.minecraft.client.gui.GuiNewChat");
    }

    @Override
    public void transform(@NotNull ClassNode classNode, @NotNull AssemblerConfig assemblerConfig) {
        for (MethodNode method : classNode.methods) {
            if (method.name.equals("printChatMessage") && method.desc.equals("(Lnet/minecraft/util/IChatComponent;)V")) {
                InsnList insnList = new InsnList();
                
                insnList.add(new FieldInsnNode(Opcodes.GETSTATIC, "com/shtruz/mod/ExternalFinalsCounter", "instance", "Lcom/shtruz/mod/ExternalFinalsCounter;"));
                insnList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                insnList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "com/shtruz/mod/ExternalFinalsCounter", "onPrintChatMessage", "(Lnet/minecraft/util/IChatComponent;)V", false));

                method.instructions.insertBefore(method.instructions.getFirst(), insnList);
                break;
            }
        }
    }
    @SuppressWarnings("unused")
    public static void onStartGame() {
        System.out.println("GuiNewChatHook");
    }
}