package com.shtruz.externalfinalscounter.instrument.transformer.transformers;

import net.weavemc.loader.api.Hook;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.Opcodes;

public class EntityPlayerSPTransformerHook extends Hook {
    public EntityPlayerSPTransformerHook() {
        super("net.minecraft.client.entity.EntityPlayerSP");
    }

    @Override
    public void transform(@NotNull ClassNode classNode, @NotNull AssemblerConfig assemblerConfig) {
        for (MethodNode method : classNode.methods) {
            if (method.name.equals("sendChatMessage") && method.desc.equals("(Ljava/lang/String;)V")) {
                InsnList insnList = new InsnList();

                insnList.add(new VarInsnNode(ALOAD, 0)); // Load 'this'
                insnList.add(new VarInsnNode(ALOAD, 1)); // Load the String argument
                insnList.add(new MethodInsnNode(INVOKESTATIC, "com/shtruz/externalfinalscounter/ExternalFinalsCounter", "onSendChatMessage", "(Lnet/minecraft/client/entity/EntityPlayerSP;Ljava/lang/String;)Z", false));

                LabelNode ifeq = new LabelNode();
                insnList.add(new JumpInsnNode(IFEQ, ifeq));
                insnList.add(new InsnNode(RETURN));
                insnList.add(ifeq);

                method.instructions.insertBefore(method.instructions.getFirst(), insnList);

                break;
            }
        }
    }
}
