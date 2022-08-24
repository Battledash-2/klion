package com.kcreates.mods.klion.modules.render;

import com.kcreates.mods.klion.Category;
import com.kcreates.mods.klion.modules.Module;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;
import org.lwjgl.glfw.GLFW;

public class XRay extends Module {
    public XRay() {
        super("XRay", GLFW.GLFW_KEY_X, Category.RENDER);
    }

    private double og;

    @Override
    public void onEnable() {
        og = mc.options.getGamma().getValue();
        mc.options.getGamma().setValue(16.0);
        mc.worldRenderer.reload();
    }
    @Override
    public void onDisable() {
        mc.options.getGamma().setValue(og);
        mc.worldRenderer.reload();
    }
    // See MinecraftClientMixin.ambientOcclusion
    // See AbstractBlockStateMixin.ambientOcclusionLevel
    // See BlockMixin.onDrawSide

    public static boolean shouldDrawBlock(Block block) {
        String name = Registry.BLOCK.getId(block).toString();
        // ores, shulker boxes and ancient debris
        if (name.contains("ore") || name.contains("shulker") ||
            name.contains("debris") || name.contains("obsidian")) return true;
        return false;
    }
}
