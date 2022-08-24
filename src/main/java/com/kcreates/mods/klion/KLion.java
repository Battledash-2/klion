package com.kcreates.mods.klion;

import com.kcreates.mods.klion.gui.ModList;
import com.kcreates.mods.klion.modules.ModuleManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

import java.awt.*;
import java.util.logging.Logger;

public class KLion implements ModInitializer {
    public static final String MOD_ID = "klion";
    public static final String MOD_NM = "KLion";
    public static final String MOD_VR = FabricLoader.getInstance().getModContainer(MOD_ID)
            .get().getMetadata().getVersion().getFriendlyString();
    public static final String MOD_AU = "Battledash_2";
    public static final MinecraftClient mc = MinecraftClient.getInstance();

    private static final Logger logger = Logger.getLogger(MOD_ID+MOD_VR);

    public static void log(String msg) {
        logger.info("["+MOD_NM+":"+MOD_VR+"] "+msg);
    }
    public static void log(Object msg) {
        log(msg.toString());
    }

    public static void send(MutableText msg, boolean abar) {
        KLion.mc.player.sendMessage(Text.literal("["+KLion.MOD_NM+":"+KLion.MOD_VR+"] ")
                .styled(style -> style.withColor(TextColor.fromRgb(new Color(235, 164, 89).getRGB())))
                .styled(style -> style.withBold(true))
                .append(msg.styled(style -> style.withBold(false)).formatted(Formatting.GRAY)), abar);
    }
    public static void send(MutableText msg) {
        send(msg, false);
    }
    public static void send(String msg) {
        send(Text.literal(msg));
    }

    public static ModuleManager moduleManager;

    public static boolean isInGui() {
        // return mc.currentScreen instanceof ChatScreen || mc.currentScreen instanceof KeybindsScreen;
        return mc.currentScreen != null;
    }

    @Override
    public void onInitialize() {
        log("Initializing render hooks...");
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
            ModList.render(matrixStack);
            ModuleManager._each(module -> module.onHudRender(matrixStack));
        });
        WorldRenderEvents.AFTER_TRANSLUCENT.register(context -> ModuleManager._each(module -> module.onRender(context)));

        log("Initializing...");
        moduleManager = new ModuleManager();
        log("Initialized.");
    }
}
