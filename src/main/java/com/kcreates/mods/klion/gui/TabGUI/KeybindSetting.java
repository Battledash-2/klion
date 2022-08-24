package com.kcreates.mods.klion.gui.TabGUI;

import com.kcreates.mods.klion.KLion;
import com.kcreates.mods.klion.util.StringUtil;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.function.Consumer;

public class KeybindSetting extends Setting {
    private int keybind;
    public void setKeybind(int key) {
        this.keybind = key;
    }
    public int getKeybind() {
        return keybind;
    }
    public static String getKeyString(int key) {
        return StringUtil.getKeyString(key);
    }
    public String getKeyString() {
        return getKeyString(this.keybind);
    }

    public KeybindSetting(String text, Consumer<KeybindSetting> callback, int defaultV) {
        super(text, callback, true);
        this.keybind = defaultV;
    }

    @Override
    public void onUKey(int key) {
        if (key == GLFW.GLFW_KEY_BACKSPACE) {
            this.keybind = GLFW.GLFW_KEY_UNKNOWN;
            onChange();
            return;
        }
        this.keybind = key;
        onChange();
    }

    @Override
    public void render(MatrixStack stack, int to, int x, int y, int w, int h, int color) {
        super.render(stack, to, x, y, w, h, color);
        int x1 = x+w-2;
        TextRenderer tr = KLion.mc.textRenderer;
        String display = getKeyString();
        tr.draw(stack, display, x1-tr.getWidth(display), y+((((y+h)-y)/2f)-(KLion.mc.textRenderer.fontHeight/2f)+1), new Color(79, 227, 124).getRGB());
    }
}
