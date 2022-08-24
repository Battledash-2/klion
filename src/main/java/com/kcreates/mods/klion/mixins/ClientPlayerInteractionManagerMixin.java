package com.kcreates.mods.klion.mixins;

import com.kcreates.mods.klion.events.AttackEntityEvent;
import com.kcreates.mods.klion.events.EventHelper;
import com.kcreates.mods.klion.modules.Module;
import com.kcreates.mods.klion.modules.ModuleManager;
import com.kcreates.mods.klion.modules.player.Reach;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    @Inject(method = "getReachDistance", at = @At("HEAD"), cancellable = true)
    private void getReachDistance(CallbackInfoReturnable<Float> cir) {
        Module reachModule = ModuleManager.getModule(Reach.class);
        if (reachModule.isToggled()) {
            float distance = ((Reach)ModuleManager.getModule(Reach.class)).range.getValue();
            cir.setReturnValue(distance);
        }
    }

    @Inject(method = "attackEntity", at = @At("HEAD"), cancellable = true)
    private void onAttackEntity(PlayerEntity player, Entity target, CallbackInfo ci) {
        EventHelper.handle(ci, new AttackEntityEvent<>(target, player));
    }
}
