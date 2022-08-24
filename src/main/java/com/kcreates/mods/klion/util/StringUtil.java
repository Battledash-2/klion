package com.kcreates.mods.klion.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class StringUtil {
    public static String getPos(LivingEntity entity) {
        Vec3d pos = entity.getPos();
        return pos.x + ", " + pos.y + ", " + pos.z;
    }
    public static String getRoundPos(LivingEntity entity) {
        Vec3d pos = entity.getPos();
        return (float)pos.x + ", " + (float)pos.y + ", " + (float)pos.z;
    }
    public static String getRound(LivingEntity entity) {
        Vec3d pos = entity.getPos();
        return Math.round((float)pos.x) + ", " + Math.round((float)pos.y) + ", " + Math.round((float)pos.z);
    }
    public static String getRound(BlockPos pos) {
        return pos.getX() + ", " + pos.getY() + ", " + pos.getZ();
    }
    public static String getPos(Vec3d pos) {
        return pos.x + ", " + pos.y + ", " + pos.z;
    }
    public static String getRoundPos(Vec3d pos) {
        return (float)pos.x + ", " + (float)pos.y + ", " + (float)pos.z;
    }
    public static String getKeyString(int key) {
        if (key == GLFW.GLFW_KEY_LEFT_SHIFT) return "L-SHIFT";
        if (key == GLFW.GLFW_KEY_RIGHT_SHIFT) return "R-SHIFT";
        if (key == GLFW.GLFW_KEY_LEFT_ALT) return "L-ALT";
        if (key == GLFW.GLFW_KEY_RIGHT_ALT) return "R-ALT";
        if (key == GLFW.GLFW_KEY_RIGHT_BRACKET) return "R-BRACKET";
        if (key == GLFW.GLFW_KEY_LEFT_BRACKET) return "L-BRACKET";
        if (key == GLFW.GLFW_KEY_LEFT_CONTROL) return "L-CONTROL";
        if (key == GLFW.GLFW_KEY_RIGHT_CONTROL) return "R-CONTROL";
        if (key == GLFW.GLFW_KEY_LEFT) return "LEFT";
        if (key == GLFW.GLFW_KEY_RIGHT) return "RIGHT";
        if (key == GLFW.GLFW_KEY_UP) return "UP";
        if (key == GLFW.GLFW_KEY_DOWN) return "DOWN";
        if (key == GLFW.GLFW_KEY_BACKSPACE) return "BACKSPACE";
        if (key == GLFW.GLFW_KEY_DELETE) return "DELETE";
        if (key == GLFW.GLFW_KEY_END) return "END";
        if (key == GLFW.GLFW_KEY_HOME) return "HOME";
        if (key == GLFW.GLFW_KEY_PAGE_DOWN) return "P-DOWN";
        if (key == GLFW.GLFW_KEY_PAGE_UP) return "P-UP";
        if (key == GLFW.GLFW_KEY_INSERT) return "INSERT";
        if (key == GLFW.GLFW_KEY_TAB) return "TAB";
        if (key == GLFW.GLFW_KEY_ESCAPE) return "ESCAPE";
        if (key == GLFW.GLFW_MOUSE_BUTTON_1) /*left*/ return "LMB";
        if (key == GLFW.GLFW_MOUSE_BUTTON_2) /*right*/ return "RMB";
        if (key == GLFW.GLFW_MOUSE_BUTTON_3) /*middle*/ return "MIDDLE-MB";
        if (key == GLFW.GLFW_MOUSE_BUTTON_4) return "MOUSE-4";
        if (key == GLFW.GLFW_MOUSE_BUTTON_5) return "MOUSE-5";
        if (key == GLFW.GLFW_MOUSE_BUTTON_6) return "MOUSE-6";
        if (key == GLFW.GLFW_MOUSE_BUTTON_7) return "MOUSE-7";
        if (key == GLFW.GLFW_MOUSE_BUTTON_8) return "MOUSE-8";

        return key == GLFW.GLFW_KEY_UNKNOWN || GLFW.glfwGetKeyName(key, GLFW.glfwGetKeyScancode(key)) == null ? "UNKNOWN" : GLFW.glfwGetKeyName(key, GLFW.glfwGetKeyScancode(key)).toUpperCase();
    }
}
