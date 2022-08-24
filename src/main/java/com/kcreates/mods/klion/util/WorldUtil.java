package com.kcreates.mods.klion.util;

import com.kcreates.mods.klion.KLion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class WorldUtil {
    public static List<Entity> getEntities() {
        return StreamSupport.stream(KLion.mc.world.getEntities().spliterator(), false).filter(entity -> (entity instanceof LivingEntity)).collect(Collectors.toList());
    }
    public static List<LivingEntity> getEntities(float range) {
        Box box = new Box(KLion.mc.player.getBlockPos()).expand(range);
        return KLion.mc.world.getNonSpectatingEntities(LivingEntity.class, box);
    }
}
