package com.kcreates.mods.klion.modules.player;

import com.kcreates.mods.klion.Category;
import com.kcreates.mods.klion.modules.Module;
import org.lwjgl.glfw.GLFW;

public class FluidWalk extends Module {
    public FluidWalk() {
        super("FluidWalk", GLFW.GLFW_KEY_UNKNOWN, Category.PLAYER);
    }

    // See FluidBlockMixin.fluidCollision
}
