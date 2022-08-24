package com.kcreates.mods.klion.gui.TabGUI;

import com.kcreates.mods.klion.KLion;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.function.Consumer;

public class RangeSetting extends Setting {
    private float min;
    private float max;
    private float inc;
    private float value;
    public String dvalue;

    public float getMin() {
        return min;
    }
    public void setMin(float min) {
        this.min = min;
    }
    public float getMax() {
        return max;
    }
    public void setMax(float max) {
        this.max = max;
    }
    public float getIncrement() {
        return inc;
    }
    public void setIncrement(float inc) {
        this.inc = inc;
    }

    public float getValue() {
        return value;
    }
    public void setValue(float v) {
        this.value = v;
        this.dvalue = ""+this.value;
    }

    public RangeSetting(String text, Consumer<RangeSetting> callback, float min, float max, float increment, float defaultV) {
        super(text, callback, true);

        this.min = min;
        this.max = max;
        this.inc = increment;
        this.value = defaultV;
    }

    @Override
    public void onKey(int key) {
        if (key == GLFW.GLFW_KEY_UP && (max == -1 || value < max)) {
            if (this.value + this.inc > max && max != -1) this.value = this.max;
            else this.value += this.inc;
        } else if (key == GLFW.GLFW_KEY_DOWN && (min == -1 || value > min)) {
            if (this.value - this.inc < this.min && min != -1) this.value = this.min;
            else this.value -= this.inc;
        }
        this.dvalue = ""+this.value;
        onChange();
    }

    @Override
    public void onUKey(int key) {
        if (key == GLFW.GLFW_KEY_RIGHT && (max == -1 || value < max)) {
            this.value += this.inc;
            this.dvalue = ""+this.value;
            onChange();
        }
    }

    @Override
    public void render(MatrixStack stack, int to, int x, int y, int w, int h, int color) {
        super.render(stack, to, x, y, w, h, color);
        int x1 = x+w-2;
        TextRenderer tr = KLion.mc.textRenderer;
        String dv = dvalue == null ? ""+value : dvalue;
        tr.draw(stack, dv, x1-tr.getWidth(dv), y+((((y+h)-y)/2f)-(KLion.mc.textRenderer.fontHeight/2f)+1), new Color(232, 79, 79).getRGB());
    }

    public RangeSetting(String text, Consumer<RangeSetting> callback, float min, float max, float increment) {
        super(text, callback, true);

        this.min = min;
        this.max = max;
        this.inc = increment;
        this.value = min;
    }
}
