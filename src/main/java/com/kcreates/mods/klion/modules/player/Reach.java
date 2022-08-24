package com.kcreates.mods.klion.modules.player;

import com.kcreates.mods.klion.Category;
import com.kcreates.mods.klion.gui.TabGUI.RangeSetting;
import com.kcreates.mods.klion.modules.Module;
import org.lwjgl.glfw.GLFW;

public class Reach extends Module {
    public RangeSetting range = new RangeSetting("Range", (o)->{},
            0.0f,
            8.0f,
            0.5f,
            6.5f);

    public Reach() {
        super("Reach", GLFW.GLFW_KEY_R, Category.PLAYER);
        addSetting(range);
    }

    // See ClientPlayerInteractionManagerMixin.getReachDistance
}
