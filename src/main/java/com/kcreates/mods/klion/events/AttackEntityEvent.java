package com.kcreates.mods.klion.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class AttackEntityEvent<E extends Entity, P extends PlayerEntity> extends Event {
    public E entity;
    public P player;

    public AttackEntityEvent(E entity, P player) {
        this.entity = entity;
        this.player = player;
    }
}
