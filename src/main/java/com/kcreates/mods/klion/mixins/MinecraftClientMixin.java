package com.kcreates.mods.klion.mixins;

import com.kcreates.mods.klion.KLion;
import com.kcreates.mods.klion.modules.Module;
import com.kcreates.mods.klion.modules.ModuleManager;
import com.kcreates.mods.klion.modules.render.XRay;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "getWindowTitle", at = @At("HEAD"), cancellable = true)
    private void getTitle(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(KLion.MOD_NM + " | " + KLion.MOD_VR);
    }

    @Inject(method = "isAmbientOcclusionEnabled", at = @At("HEAD"), cancellable = true)
    private static void ambientOcclusion(CallbackInfoReturnable<Boolean> cir) {
        Module xray = ModuleManager.getModule(XRay.class); // No need to cast
        if (xray.isToggled()) {
            cir.setReturnValue(false);
        }
    }
}
