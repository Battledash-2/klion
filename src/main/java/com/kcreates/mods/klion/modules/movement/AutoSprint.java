package com.kcreates.mods.klion.modules.movement;

import com.kcreates.mods.klion.Category;
import com.kcreates.mods.klion.modules.Module;
import org.lwjgl.glfw.GLFW;

public class AutoSprint extends Module {
    public AutoSprint() {
        super("AutoSprint", GLFW.GLFW_KEY_N, Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (!this.isToggled()) return;
        if (mc.player.isSprinting() || mc.player.isSneaking() ||
            !mc.player.isOnGround() || (mc.player.isTouchingWater() && !mc.player.isSubmergedInWater()) ||
            !mc.player.input.pressingForward) return;
        mc.player.setSprinting(true);
    }
}
