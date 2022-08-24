package com.kcreates.mods.klion.mixins;

import com.kcreates.mods.klion.modules.ModuleManager;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    private void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        // 1 = down
        // 2 = press
        // 0 = up
        if (action != 1) return;
        boolean cancel = ModuleManager.onKey(key);
        if (cancel) ci.cancel();
    }
}
