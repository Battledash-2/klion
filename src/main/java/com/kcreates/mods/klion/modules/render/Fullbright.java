package com.kcreates.mods.klion.modules.render;

import com.kcreates.mods.klion.Category;
import com.kcreates.mods.klion.modules.Module;
import org.lwjgl.glfw.GLFW;

public class Fullbright extends Module {
    public Fullbright() {
        super("Fullbright", GLFW.GLFW_KEY_UNKNOWN, Category.RENDER);
    }

    private double og;

    @Override
    public void onEnable() {
        og = mc.options.getGamma().getValue();
        mc.options.getGamma().setValue(16.0);
    }
    @Override
    public void onDisable() {
        mc.options.getGamma().setValue(og);
    }
}
