package com.kcreates.mods.klion.gui.TabGUI;

import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.function.Consumer;

public class BooleanSetting extends Setting {
    private boolean value;

    public BooleanSetting(String text, Consumer<BooleanSetting> callback, boolean defaultV) {
        super(text, callback, true);
        this.value = defaultV;
    }

    @Override
    public void onKey(int key) {
        toggleValue();
    }

    @Override
    public void onUKey(int key) {
        if (key == GLFW.GLFW_KEY_RIGHT) toggleValue();
    }

    @Override
    public void render(MatrixStack stack, int to, int x, int y, int w, int h, int color) {
        super.render(stack, to, x, y, w, h, color);
        int wi = 7;
        fill(stack, (x+w)-wi-3, y+2, (x+w)-3, (y+h)-2, new Color(38, 38, 38).getRGB());
        fill(stack, (x+w)-wi-2, y+3, (x+w)-4, (y+h)-3, value ? new Color(109, 222, 120).getRGB() : new Color(230, 109, 85).getRGB());
    }

    public boolean getValue() {
        return value;
    }
    public void setValue(boolean v) {
        this.value = v;
    }
    public boolean toggleValue() {
        this.value = !value;
        onChange();
        return value;
    }
}
