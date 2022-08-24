package com.kcreates.mods.klion.modules.combat;

import com.kcreates.mods.klion.Category;
import com.kcreates.mods.klion.events.AttackEntityEvent;
import com.kcreates.mods.klion.modules.Module;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class Criticals extends Module {
    public Criticals() {
        super("Criticals", GLFW.GLFW_KEY_C, Category.COMBAT);
    }

    private void send(double x, double y, double z, boolean o) {
        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, o));
    }

    @Override
    public boolean onEvent(Object e, boolean cancelled) {
        if (!this.isToggled() || !(e instanceof AttackEntityEvent) || cancelled) return cancelled;
        AttackEntityEvent event = (AttackEntityEvent) e;
        if (event.player != mc.player || event.entity == null) return false;
        Vec3d pos = mc.player.getPos();
        send(pos.x, pos.y+0.1d, pos.z, false);
        send(pos.x, pos.y, pos.z, false);
        mc.player.addCritParticles(event.entity);

        return false;
    }
}
