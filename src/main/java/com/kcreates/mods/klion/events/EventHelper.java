package com.kcreates.mods.klion.events;

import com.kcreates.mods.klion.modules.ModuleManager;
import org.spongepowered.asm.mixin.injection.callback.Cancellable;

public class EventHelper {
    public static <T extends Cancellable> void handle(T ci, Object event) {
        if (ModuleManager.onEvent(event)) ci.cancel();
    }
}
