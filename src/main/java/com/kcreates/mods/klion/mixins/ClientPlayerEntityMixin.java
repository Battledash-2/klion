package com.kcreates.mods.klion.mixins;

import com.kcreates.mods.klion.KLion;
import com.kcreates.mods.klion.events.ChatEvent;
import com.kcreates.mods.klion.events.EventHelper;
import com.kcreates.mods.klion.modules.ModuleManager;
import com.kcreates.mods.klion.util.FakeCommandSource;
import com.mojang.brigadier.StringReader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.stat.StatHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    private boolean firstTick = true;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (firstTick) {
            ModuleManager.onGameLoad();
            firstTick = false;
        }
        ModuleManager.onTick();
    }

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void onMessage(String message, Text preview, CallbackInfo ci) {
        EventHelper.handle(ci, new ChatEvent(message, true));
        if (!message.startsWith("/")) return;
        if (!message.substring(1).split(" ")[0].contentEquals("klion")) return;
        ci.cancel();
        StringReader reader = new StringReader(message);
        reader.skip();
        try {
            KLion.mc.getNetworkHandler().getCommandDispatcher().execute(reader, new FakeCommandSource(KLion.mc.player));
        } catch (Exception e) {
            KLion.mc.player.getCommandSource().sendFeedback(Text.literal(e.getMessage()).formatted(Formatting.RED), false);
        }
    }
}
