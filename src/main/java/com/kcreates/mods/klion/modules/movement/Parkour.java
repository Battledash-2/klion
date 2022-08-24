package com.kcreates.mods.klion.modules.movement;

import com.kcreates.mods.klion.Category;
import com.kcreates.mods.klion.modules.Module;
import org.lwjgl.glfw.GLFW;

// Automatically jumps when the block below the player is empty
public class Parkour extends Module {
    public Parkour() {
        super("Parkour", GLFW.GLFW_KEY_UNKNOWN, Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (!this.isToggled()) return;
        if (mc.player.isOnGround() && !mc.player.isSneaking() &&
                mc.world.getBlockState(mc.player.getBlockPos().add(0, -1, 0)).isAir()) {
            mc.player.jump();
        }
    }
}
