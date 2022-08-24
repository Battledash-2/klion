package com.kcreates.mods.klion.modules.render;

import com.kcreates.mods.klion.Category;
import com.kcreates.mods.klion.KLion;
import com.kcreates.mods.klion.gui.TabGUI.Setting;
import com.kcreates.mods.klion.modules.Module;
import com.kcreates.mods.klion.modules.ModuleManager;
import com.kcreates.mods.klion.util.StringUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;

import java.awt.Color;
import java.util.List;

public class TabGUI extends Module {
    public TabGUI() {
        super("TabGUI", GLFW.GLFW_KEY_RIGHT_SHIFT, Category.RENDER, true);
    }

    private int row;
    private int row1;
    private int row2;

    private int cRow;
    private int cRow1;
    private int cRow2;

    private Category cCat;
    private Module cMod;
    private Setting cSet;

    private int layer = 0;
    private boolean focused = false;

    @Override
    public void onHudRender(MatrixStack stack) {
        if (!this.isToggled() || KLion.isInGui()) return;
        row = 0;
        row1 = 0;
        row2 = 0;

        stack.push();
        RenderSystem.applyModelViewMatrix();
        stack.translate(5, getY(), 0);

        int x = mc.textRenderer.getWidth(StringUtil.getRound(mc.player))+14;
        fill(stack, 0, 0, 10, 10, new Color(24, 82, 91).getRGB());
        fill(stack, 9, 0, x, 10, new Color(24, 82, 91, 50).getRGB());

        mc.textRenderer.draw(stack, StringUtil.getRound(mc.player),
                x-mc.textRenderer.getWidth(StringUtil.getRound(mc.player))-2, 1, -1);

        stack.translate(0, 12, 0);
        // TODO: Make the UI
        // layer 0
        for (Category category : ModuleManager.categories) {
            String name = ModuleManager.getCategoryName(category);
            // height: 10 mpx (minecraft scaled pixel)
            // margin: 2  mpx  ^
            fill(stack, -1, row*12-1, 94, row*12+9, new Color(46, 46, 46, 100).getRGB());
            fill(stack, 0, row*12, 95, row*12+10, -1);
            mc.textRenderer.draw(stack, name, 1, row*12+1, new Color(235, 164, 89).getRGB());

            row++;
        }
        fill(stack, 0, cRow*12, 95, cRow*12+10, new Color(24, 82, 91, 50).getRGB());

        // layer 1
        if (layer >= 1) {
            int xp = 97;
            for (Module m : ModuleManager.getModules(cCat)) {
                String name = m.getName();

                fill(stack, xp-1, row1*12-1, xp+95-1, row1*12+9, new Color(46, 46, 46, 100).getRGB());
                fill(stack, xp, row1*12, xp+95, row1*12+10, -1);
                mc.textRenderer.draw(stack, name, xp+1, row1*12+1, new Color(235, 164, 89).getRGB());

                row1++;
            }
            fill(stack, xp, cRow1*12, xp+95, cRow1*12+10, new Color(24, 82, 91, 50).getRGB());
        }
        // layer 2
        if (layer == 2 && cMod != null) {
            int xp = 97+97;
            for (Setting s : cMod.getSettings()) {
                s.render(stack, 2, xp, row2*12, 95, 10, -1);
                row2++;
            }
            fill(stack, xp, cRow2*12, xp+95, cRow2*12+10, new Color(24, 82, 91, 50).getRGB());
        }

        // Setting setting = new Setting("test");
        // setting.render(stack, 2, 100, 100, 100, 12, new Color(0, 0, 0).getRGB());

        stack.pop();
        RenderSystem.applyModelViewMatrix();
    }

    @Override
    public boolean onKey(int key, boolean cancelled, boolean inGui) {
        if (cancelled || inGui || !this.isToggled()) return cancelled;
        boolean jn = false;

        // layer 2 not focused
        if (layer == 2 && !focused) {
            if (cMod == null) {
                if (key == GLFW.GLFW_KEY_LEFT) {
                    cSet = null;
                    layer--;
                }
                return cancelled;
            }
            List<Setting> settings = cMod.getSettings();
            if (key == GLFW.GLFW_KEY_LEFT) {
                cSet = null;
                layer--;
                jn = true;
            } else if (key == GLFW.GLFW_KEY_UP) {
                if (cRow2 != 0) cRow2--;
                else cRow2 = settings.size()-1;
            } else if (key == GLFW.GLFW_KEY_DOWN) {
                if (cRow2 < settings.size()-1) cRow2++;
                else cRow2 = 0;
            } else if (key == GLFW.GLFW_KEY_RIGHT) {
                focused = true;
                cSet = settings.get(cRow2);
                jn = true;
            }
        }
        // layer 2 focused
        if (layer == 2 && focused && !jn) {
            if (key == GLFW.GLFW_KEY_LEFT) {
                focused = false;
            } else if (cSet != null) {
                if (key == GLFW.GLFW_KEY_UP) {
                    cSet.onKey(key);
                } else if (key == GLFW.GLFW_KEY_DOWN) {
                    cSet.onKey(key);
                } else {
                    cSet.onUKey(key);
                }
            }
        }

        // layer 1
        if (layer == 1 && !jn) {
            List<Module> modules = ModuleManager.getModules(cCat);
            if (key == GLFW.GLFW_KEY_LEFT) {
                cCat = null;
                layer--;
            } else if (key == GLFW.GLFW_KEY_UP) {
                if (cRow1 != 0) cRow1--;
                else cRow1 = modules.size()-1;
            } else if (key == GLFW.GLFW_KEY_DOWN) {
                if (cRow1 < modules.size()-1) cRow1++;
                else cRow1 = 0;
            } else if (key == GLFW.GLFW_KEY_RIGHT) {
                layer++;
                cMod = ModuleManager.getModule(cCat, cRow1);
                if (cMod != null && cRow2 > cMod.getSettings().size()-1) {
                    cRow2 = cMod.getSettings().size()-1;
                }
            }
        }

        // layer 0
        if (layer != 0 && key == GLFW.GLFW_KEY_ESCAPE && mc.currentScreen == null) {
            layer = 0;
            focused = false;
            cCat = null;
            cMod = null;
            // cRow = 0;
            cRow1 = 0;
            cRow2 = 0;
            return true; // cancel the event
        }
        if (layer == 0) {
            if (key == GLFW.GLFW_KEY_UP) {
                if (cRow != 0) cRow--;
                else cRow = ModuleManager.categories.length-1;
            } else if (key == GLFW.GLFW_KEY_DOWN) {
                if (cRow < ModuleManager.categories.length-1) cRow++;
                else cRow = 0;
            } else if (key == GLFW.GLFW_KEY_RIGHT) {
                if (ModuleManager.getModules(ModuleManager.getCategory(cRow)).size() > 0) {
                    if (cRow1 > ModuleManager.getModules(ModuleManager.getCategory(cRow)).size()-1) {
                        cRow1 = ModuleManager.getModules(ModuleManager.getCategory(cRow)).size()-1;
                    }
                    cCat = ModuleManager.getCategory(cRow);
                    layer++;
                }
            }
        }

        return cancelled;
    }
}
