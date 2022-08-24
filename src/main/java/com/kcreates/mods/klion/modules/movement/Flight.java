package com.kcreates.mods.klion.modules.movement;

import com.kcreates.mods.klion.Category;
import com.kcreates.mods.klion.modules.Module;
import org.lwjgl.glfw.GLFW;

public class Flight extends Module {
    public Flight() {
        super("Flight", GLFW.GLFW_KEY_G, Category.MOVEMENT);
    }

    private float og = 0;

    @Override
    public void onTick() {
        if (!this.isToggled()) return;
        mc.player.getAbilities().allowFlying = true;
        mc.player.getAbilities().flying = true;
        if (mc.options.forwardKey.isPressed()) mc.player.getAbilities().setFlySpeed(.1f);
    }

    @Override
    public void onEnable() {
        og = mc.player.getAbilities().getFlySpeed();
    }

    @Override
    public void onDisable() {
        if (mc.player.getAbilities().allowFlying && !mc.player.getAbilities().creativeMode) {
            mc.player.getAbilities().allowFlying = false;
            mc.player.getAbilities().flying = false;
            mc.player.getAbilities().setFlySpeed(.1f);
        }
    }
}
