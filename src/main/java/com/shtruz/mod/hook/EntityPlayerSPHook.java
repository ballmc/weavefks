package com.shtruz.mod.hook;

import net.weavemc.loader.api.Hook;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.Opcodes;
import org.jetbrains.annotations.NotNull;
import com.shtruz.mod.ExternalFinalsCounter;


public class EntityPlayerSPHook extends Hook {
    public EntityPlayerSPHook() {
        super("net.minecraft.client.entity.EntityPlayerSP");
    }

    @Override
    public void transform(@NotNull ClassNode classNode, @NotNull AssemblerConfig assemblerConfig) {
        for (MethodNode method : classNode.methods) {
            if (method.name.equals("sendChatMessage") && method.desc.equals("(Ljava/lang/String;)V")) {
                InsnList insnList = new InsnList();

                insnList.add(new VarInsnNode(Opcodes.ALOAD, 0)); // Load 'this'
                insnList.add(new VarInsnNode(Opcodes.ALOAD, 1)); // Load the String argument
                insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/shtruz/mod/ExternalFinalsCounter", "onSendChatMessage", "(Lnet/minecraft/client/entity/EntityPlayerSP;Ljava/lang/String;)Z", false));

                LabelNode ifeq = new LabelNode();
                insnList.add(new JumpInsnNode(Opcodes.IFEQ, ifeq));
                insnList.add(new InsnNode(Opcodes.RETURN));
                insnList.add(ifeq);

                method.instructions.insertBefore(method.instructions.getFirst(), insnList);

                break;
            }
        }
    }
    @SuppressWarnings("unused")
    public static void onStartGame() {
        System.out.println("sp hook");
    }
}
