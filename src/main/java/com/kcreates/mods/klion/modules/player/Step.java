package com.kcreates.mods.klion.modules.player;

import com.kcreates.mods.klion.Category;
import com.kcreates.mods.klion.gui.TabGUI.RangeSetting;
import com.kcreates.mods.klion.modules.Module;
import org.lwjgl.glfw.GLFW;

public class Step extends Module {
    private RangeSetting height = new RangeSetting("Height", (o)->{}, 0.0f, 4.0f, 0.5f, 1.0f);

    public Step() {
        super("Step", GLFW.GLFW_KEY_UNKNOWN, Category.PLAYER);
        addSetting(height);
    }

    private float og; // original step height
    @Override
    public void onTick() {
        if (!this.isToggled()) return;
        mc.player.stepHeight = height.getValue();
    }

    @Override
    public void onDisable() {
        mc.player.stepHeight = og;
    }
    @Override
    public void onEnable() {
        og = mc.player.stepHeight;
    }
}
