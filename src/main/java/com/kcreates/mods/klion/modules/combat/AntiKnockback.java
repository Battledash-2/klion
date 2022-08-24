package com.kcreates.mods.klion.modules.combat;

import com.kcreates.mods.klion.Category;
import com.kcreates.mods.klion.events.PacketEvent;
import com.kcreates.mods.klion.modules.Module;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import org.lwjgl.glfw.GLFW;

public class AntiKnockback extends Module {
    public AntiKnockback() {
        super("AntiKnockback", GLFW.GLFW_KEY_K, Category.COMBAT);
    }

    @Override
    public boolean onEvent(Object e, boolean cancelled) {
        if (!this.isToggled() || !(e instanceof PacketEvent) || cancelled) return cancelled;
        PacketEvent event = (PacketEvent)e;
        if (event.outgoing || !(event.packet instanceof EntityVelocityUpdateS2CPacket) ||
                ((EntityVelocityUpdateS2CPacket)event.packet).getId() != mc.player.getId()) return cancelled;
        return true;
    }
}
