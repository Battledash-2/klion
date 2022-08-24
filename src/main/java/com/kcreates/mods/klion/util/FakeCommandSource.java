package com.kcreates.mods.klion.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;

public class FakeCommandSource extends ServerCommandSource {
    public FakeCommandSource(PlayerEntity player) {
        super(player, player.getPos(), player.getRotationClient(), null, 0, player.getName().getString(), player.getDisplayName(), null, player);
    }
}
