package com.kcreates.mods.klion.gui.TabGUI;

import com.kcreates.mods.klion.KLion;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.util.function.Consumer;

public class Setting<T extends Setting<?>> extends DrawableHelper {
    public String text;
    private Consumer<T> callback;
    private static final MinecraftClient mc = KLion.mc;
    private boolean shadow = false;

    public Setting(String text, Consumer<T> callback, boolean shadow) {
        this.text = text;
        this.callback = callback;
        this.shadow = shadow;
    }
    public Setting(String text, Consumer<T> callback) {
        this.text = text;
        this.callback = callback;
        this.shadow = false;
    }

    public void onChange() { callback.accept((T)this); }
//    public void onChange(int key, boolean normal) {
//        if (normal) {
//            callback.accept(this);
//            onKey(key);
//        } else onUKey(key);
//    }

    public void onKey(int key) {};
    public void onUKey(int key) {};

    public void render(MatrixStack stack, int to, int x, int y, int w, int h, int color) {
        int x1 = x+w;
        int y1 = y+h;

        if (shadow) fill(stack, x-1, y-1, x1-1, y1-1, new Color(46, 46, 46, 100).getRGB());
        fill(stack, x, y, x1, y1, color);
        mc.textRenderer.draw(stack, this.text, x+1+to, y+(((y1-y)/2f)-(mc.textRenderer.fontHeight/2f)+1), new Color(235, 164, 89).getRGB());
        // draw on the very left so there's room for actual widgets like sliders or checkboxes
    }
    public void render(MatrixStack stack, int x, int y, int w, int h, int color) {
        render(stack, 0, x, y, w, h, color);
    }
}
