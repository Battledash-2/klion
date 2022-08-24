package com.kcreates.mods.klion.gui.TabGUI;

import com.kcreates.mods.klion.KLion;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.function.Consumer;

public class MultiOptionSetting extends Setting {
    private String[] choices;
    private int currentChoice;
    private String currentChoiceStr;

    public String[] getChoices() {
        return choices;
    }
    public void setChoices(String[] choices) {
        this.choices = choices;
    }
    public int getCurrentChoice() {
        return currentChoice;
    }
    public void setCurrentChoice(int currentChoice) {
        this.currentChoice = currentChoice;
    }
    public String getCurrentChoiceStr() {
        return currentChoiceStr;
    }
    public void setCurrentChoiceStr(String currentChoiceStr) {
        this.currentChoiceStr = currentChoiceStr;
    }

    public MultiOptionSetting(String text, Consumer<MultiOptionSetting> callback, String[] choices, int defaultV) {
        super(text, callback, true);
        this.choices = choices;
        this.currentChoice = defaultV;
        this.currentChoiceStr = choices[defaultV];
    }

    @Override
    public void onKey(int key) {
        if (key == GLFW.GLFW_KEY_UP) {
            if (currentChoice == choices.length-1) currentChoice = 0;
            else currentChoice++;
        } else if (key == GLFW.GLFW_KEY_DOWN) {
            if (currentChoice <= 0) currentChoice = choices.length-1;
            else currentChoice--;
        }
        currentChoiceStr = choices[currentChoice];
        onChange();
    }

    @Override
    public void render(MatrixStack stack, int to, int x, int y, int w, int h, int color) {
        super.render(stack, to, x, y, w, h, color);
        int x1 = x+w-2;
        TextRenderer tr = KLion.mc.textRenderer;
        tr.draw(stack, "< " + currentChoiceStr + " >", x1-tr.getWidth("< " + currentChoiceStr + " >"), y+((((y+h)-y)/2f)-(KLion.mc.textRenderer.fontHeight/2f)+1), new Color(79, 227, 124).getRGB());
    }
}
