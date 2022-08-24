package com.kcreates.mods.klion.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;

public class RenderUtil {
    public static void prep() {
        RenderSystem.disableCull();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
    }
    public static void rev() {
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
    }

    public static class Color {
        public int r;
        public int g;
        public int b;
        public int a;
        public Color(int r, int g, int b, int a) { this.r=r; this.g=g; this.b=b; this.a=a; }
    }

    public static void draw2d(BufferBuilder buffer, Matrix4f matrix, int x, int y, int z, int width, int height, int r, int g, int b, int a) {
        buffer.vertex(matrix, x, y, z).color(r, g, b, a).next();
        buffer.vertex(matrix, x+width, y, z).color(r, g, b, a).next();
        buffer.vertex(matrix, x+width, y+height, z).color(r, g, b, a).next();
        buffer.vertex(matrix, x, y+height, z).color(r, g, b, a).next();
    }
    public static void draw2d(BufferBuilder buffer, Matrix4f matrix, int x, int y, int z, int width, int height, Color color) {
        draw2d(buffer, matrix, x, y, z, width, height, color.r, color.g, color.b, color.a);
    }
    public static void draw3dBox(BufferBuilder buffer, Matrix4f matrix, Box box, int r, int g, int b, int a) {
        draw3dBox(MinecraftClient.getInstance().gameRenderer.getCamera().getPos(), buffer, matrix, box, r, g, b, a);
    }
    public static void draw3dBox(Vec3d cam, BufferBuilder buffer, Matrix4f matrix, Box box, int r, int g, int b, int a) {
        float minX = (float) ((float)box.minX-cam.x);
        float minY = (float) ((float)box.minY-cam.y);
        float minZ = (float) ((float)box.minZ-cam.z);
        float maxX = (float) ((float)box.maxX-cam.x);
        float maxY = (float) ((float)box.maxY-cam.y);
        float maxZ = (float) ((float)box.maxZ-cam.z);

        // top
        buffer.vertex(matrix, minX, maxY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, maxY, maxZ).color(r, g, b, a).next();

        buffer.vertex(matrix, maxX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, maxY, minZ).color(r, g, b, a).next();

        // bottom
        buffer.vertex(matrix, minX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, minY, maxZ).color(r, g, b, a).next();

        buffer.vertex(matrix, maxX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, minY, minZ).color(r, g, b, a).next();

        // back
        buffer.vertex(matrix, minX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, minY, minZ).color(r, g, b, a).next();

        buffer.vertex(matrix, maxX, maxY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, maxY, minZ).color(r, g, b, a).next();

        // front
        buffer.vertex(matrix, minX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, minY, maxZ).color(r, g, b, a).next();

        buffer.vertex(matrix, maxX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, maxY, maxZ).color(r, g, b, a).next();

        // left
        buffer.vertex(matrix, minX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, minY, maxZ).color(r, g, b, a).next();

        buffer.vertex(matrix, minX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, maxY, minZ).color(r, g, b, a).next();

        // right
        buffer.vertex(matrix, maxX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, minY, maxZ).color(r, g, b, a).next();

        buffer.vertex(matrix, maxX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, maxY, minZ).color(r, g, b, a).next();
    }
    public static void draw3d(BufferBuilder buffer, Matrix4f matrix, float x, float y, float z, VoxelShape shape, int r, int g, int b, int a) {
        shape.forEachBox((x1, y1, z1, x2, y2, z2) -> {
            float minX = (float) (x+x1);
            float minY = (float) (y+y1);
            float minZ = (float) (z+z1);
            float maxX = (float) (x+x2);
            float maxY = (float) (y+y2);
            float maxZ = (float) (z+z2);

            // top
            buffer.vertex(matrix, minX, maxY, minZ).color(r, g, b, a).next();
            buffer.vertex(matrix, minX, maxY, maxZ).color(r, g, b, a).next();

            buffer.vertex(matrix, maxX, maxY, maxZ).color(r, g, b, a).next();
            buffer.vertex(matrix, maxX, maxY, minZ).color(r, g, b, a).next();

            // bottom
            buffer.vertex(matrix, minX, minY, minZ).color(r, g, b, a).next();
            buffer.vertex(matrix, minX, minY, maxZ).color(r, g, b, a).next();

            buffer.vertex(matrix, maxX, minY, maxZ).color(r, g, b, a).next();
            buffer.vertex(matrix, maxX, minY, minZ).color(r, g, b, a).next();

            // back
            buffer.vertex(matrix, minX, minY, minZ).color(r, g, b, a).next();
            buffer.vertex(matrix, maxX, minY, minZ).color(r, g, b, a).next();

            buffer.vertex(matrix, maxX, maxY, minZ).color(r, g, b, a).next();
            buffer.vertex(matrix, minX, maxY, minZ).color(r, g, b, a).next();

            // front
            buffer.vertex(matrix, minX, minY, maxZ).color(r, g, b, a).next();
            buffer.vertex(matrix, maxX, minY, maxZ).color(r, g, b, a).next();

            buffer.vertex(matrix, maxX, maxY, maxZ).color(r, g, b, a).next();
            buffer.vertex(matrix, minX, maxY, maxZ).color(r, g, b, a).next();

            // left
            buffer.vertex(matrix, minX, minY, minZ).color(r, g, b, a).next();
            buffer.vertex(matrix, minX, minY, maxZ).color(r, g, b, a).next();

            buffer.vertex(matrix, minX, maxY, maxZ).color(r, g, b, a).next();
            buffer.vertex(matrix, minX, maxY, minZ).color(r, g, b, a).next();

            // right
            buffer.vertex(matrix, maxX, minY, minZ).color(r, g, b, a).next();
            buffer.vertex(matrix, maxX, minY, maxZ).color(r, g, b, a).next();

            buffer.vertex(matrix, maxX, maxY, maxZ).color(r, g, b, a).next();
            buffer.vertex(matrix, maxX, maxY, minZ).color(r, g, b, a).next();
        });
    }
    public static void draw3d(BufferBuilder buffer, Matrix4f matrix, int x, int y, int z, VoxelShape shape, Color color) {
        draw3d(buffer, matrix, x, y, z, shape, color.r, color.g, color.b, color.a);
    }
    public static void draw3d(BufferBuilder buffer, Matrix4f matrix, Vec3d vec, VoxelShape shape, Color color) {
        draw3d(buffer, matrix, (float)vec.x, (float)vec.y, (float)vec.z, shape, color.r, color.g, color.b, color.a);
    }
    public static void draw3d(BufferBuilder buffer, Matrix4f matrix, Vec3d vec, VoxelShape shape, int r, int g, int b, int a) {
        draw3d(buffer, matrix, (float)vec.x, (float)vec.y, (float)vec.z, shape, r, g, b, a);
    }
    public static Vec3d getPos(Camera cam, Vec3d pos) {
        return pos.subtract(cam.getPos());
    }
    public static Vec3d getPos(Camera cam, double x, double y, double z) {
        return getPos(cam, new Vec3d(x, y, z));
    }
    public static Vec3d getPos(double x, double y, double z) {
        return getPos(MinecraftClient.getInstance().gameRenderer.getCamera(), x, y, z);
    }
    public static Vec3d getPos(Vec3d vec) {
        return getPos(MinecraftClient.getInstance().gameRenderer.getCamera(), vec);
    }
    public static void drawLine(BufferBuilder buffer, Matrix4f matrix, float startX, float startY, float startZ, float endX, float endY, float endZ, float weight, int r, int g, int b, int a) {
        RenderSystem.lineWidth(weight);

        // Tessellator.getInstance().draw();
        // buffer.begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION_COLOR);
        buffer.vertex(matrix, startX, startY, startZ).color(r, g, b, a).next();
        buffer.vertex(matrix, endX, endY, endZ).color(r, g, b, a).next();

        buffer.vertex(matrix, endX+weight, endY+weight, endZ+weight).color(r, g, b, a).next();
        buffer.vertex(matrix, startX+weight, startY+weight, startZ+weight).color(r, g, b, a).next();
    }
    public static void drawLine(BufferBuilder buffer, Matrix4f matrix, Vec3d start, Vec3d end, float weight, int r, int g, int b, int a) {
        drawLine(buffer, matrix, (float)start.x, (float)start.y, (float)start.z, (float)end.x, (float)end.y, (float)end.z, weight, r, g, b, a);
    }

    public static void drawTracer(BufferBuilder buffer, Matrix4f matrix, LivingEntity entity, int r, int g, int b, int a) {
        Vec3d pos = getPos(entity.getPos());
        Vec3d eyeVec = getPos(new Vec3d(0.0, 0.0, 75)
                .rotateX((float)(-Math.toRadians(MinecraftClient.getInstance().cameraEntity.getPitch())))
                .rotateY((float)(-Math.toRadians(MinecraftClient.getInstance().cameraEntity.getYaw())))
                .add(MinecraftClient.getInstance().cameraEntity.getPos()
                        .add(0, MinecraftClient.getInstance().cameraEntity.getEyeHeight(MinecraftClient.getInstance().cameraEntity.getPose()), 0)));

        drawLine(buffer, matrix, pos, eyeVec, .3f, r, g, b, a);
    }
    private static void info(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        System.out.println("---------------- Min ----------------");
        System.out.println("MinX: "+minX);
        System.out.println("MinY: "+minY);
        System.out.println("MinZ: "+minZ);
        System.out.println("---------------- Max ----------------");
        System.out.println("MaxX: "+maxX);
        System.out.println("MaxY: "+maxY);
        System.out.println("MaxZ: "+maxZ);
    }
}
