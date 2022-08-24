package com.kcreates.mods.klion.modules.miscellaneous;

import com.kcreates.mods.klion.Category;
import com.kcreates.mods.klion.modules.Module;
import org.lwjgl.glfw.GLFW;

public class AntiCactus extends Module {
    public AntiCactus() {
        super("AntiCactus", GLFW.GLFW_KEY_UNKNOWN, Category.MISC);
    }

    // See CactusBlockMixin.collisionShape
}
