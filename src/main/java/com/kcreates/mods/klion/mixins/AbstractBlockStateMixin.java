package com.kcreates.mods.klion.mixins;

import com.kcreates.mods.klion.modules.Module;
import com.kcreates.mods.klion.modules.ModuleManager;
import com.kcreates.mods.klion.modules.render.XRay;
import net.minecraft.block.AbstractBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class AbstractBlockStateMixin {
    @Inject(method = "getAmbientOcclusionLightLevel", at = @At("HEAD"), cancellable = true)
    private void ambientOcclusionLevel(BlockView world, BlockPos pos, CallbackInfoReturnable<Float> cir) {
        Module xray = ModuleManager.getModule(XRay.class);
        if (xray.isToggled()) {
            cir.setReturnValue(1.0f);
        }
    }
}
