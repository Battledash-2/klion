package com.kcreates.mods.klion.modules.render;

import com.kcreates.mods.klion.Category;
import com.kcreates.mods.klion.gui.TabGUI.RangeSetting;
import com.kcreates.mods.klion.modules.Module;
import com.kcreates.mods.klion.util.RenderUtil;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Matrix4f;
import org.lwjgl.glfw.GLFW;

public class Tracers extends Module {
    private RangeSetting range = new RangeSetting("Range", (o)->{},
            5.0f,
            105.0f, // 101 wouldn't work because our increment is five, meaning undoing going to 101 would cause the increment to be off
            5.0f,
            20.0f);

    public Tracers() {
        super("Tracers", GLFW.GLFW_KEY_UNKNOWN, Category.RENDER);
        addSetting(range);
    }

    @Override
    public void onRender(WorldRenderContext context) {
        if (!this.isToggled()) return;
        // KLion.log("E");

        if (range.getValue() > 100.0f) range.dvalue = "Infinite";

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        MatrixStack stack = context.matrixStack();
        Matrix4f matrix = stack.peek().getPositionMatrix();
        stack.push();
        RenderUtil.prep();

        Iterable<Entity> entities = mc.world.getEntities();
        for (Entity e : entities) {
            if (!(e instanceof LivingEntity)) continue;
            LivingEntity entity = (LivingEntity)e;
            if (entity.isDead() || entity == mc.player) continue; // range.getValue() <= 100
            if (entity.distanceTo(mc.player) > range.getValue() && !(range.getValue() > 100)) continue;
            RenderUtil.drawTracer(buffer, matrix, entity, 237, 107, 97, 120);
            // RenderUtil.draw3dBox(buffer, matrix, entity.getBoundingBox(), 237, 107, 97, 100);
        }

        tessellator.draw();
        RenderUtil.rev();
        stack.pop();
    }
}
