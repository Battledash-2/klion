package com.kcreates.mods.klion.gui;

import com.kcreates.mods.klion.KLion;
import com.kcreates.mods.klion.modules.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public class ModList extends DrawableHelper {
    private static MinecraftClient mc = KLion.mc;
    public static TextRenderer tr = mc.textRenderer;
    private static int row = 0;

    public static void render(MatrixStack stack) {
        fill(stack, 5, 5, 100, 16, new Color(33, 33, 33, 100).getRGB());
        tr.draw(stack, KLion.MOD_NM, 8, 7, new Color(237, 107, 97).getRGB());

        String fps = "FPS: " + mc.fpsDebugString.split(" ")[0];
        // width: 95 (start: 5, end: 100)
        tr.draw(stack, fps, 100 - tr.getWidth(fps) - 3, 7, -1);

        row = 0;
        ModuleManager._each(module -> {
            if (!module.isToggled()) return;
            String text = module.getName() + " -";
            int x = mc.getWindow().getScaledWidth() - tr.getWidth(text) - 5;
            tr.draw(stack, text, x, (row+1)*tr.fontHeight-3, new Color(235, 189, 63).getRGB());
            row++;
        });
    }
}
