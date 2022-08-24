package com.kcreates.mods.klion.modules.render;

import com.kcreates.mods.klion.Category;
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

public class EntityESP extends Module {
    public EntityESP() {
        super("EntityESP", GLFW.GLFW_KEY_UNKNOWN, Category.RENDER);
    }

    @Override
    public void onRender(WorldRenderContext context) {
        if (!this.isToggled()) return;
        // KLion.log("E");

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
            if (entity.isDead() || entity == mc.player) continue;
             RenderUtil.draw3dBox(buffer, matrix, entity.getBoundingBox(), 237, 107, 97, 100);
        }

        tessellator.draw();
        RenderUtil.rev();
        stack.pop();
    }
}
