package com.kcreates.mods.klion.modules.movement;

import com.kcreates.mods.klion.Category;
import com.kcreates.mods.klion.modules.Module;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class Speed extends Module {
    public Speed() {
        super("Speed", GLFW.GLFW_KEY_H, Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (mc.player.isSneaking() || !this.isToggled() ||
            mc.player.forwardSpeed == 0 && mc.player.sidewaysSpeed == 0) return;
        if (mc.player.forwardSpeed > 0 && !mc.player.horizontalCollision)
            mc.player.setSprinting(true);
        if (!mc.player.isOnGround()) return;

        Vec3d pv = mc.player.getVelocity();
        mc.player.setVelocity(pv.x * 1.8, pv.y + 0.1, pv.z * 1.8);
    }
}
