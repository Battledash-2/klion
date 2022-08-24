package com.kcreates.mods.klion.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class NameUtil {
    // Ex:
    //    5651b663-46a5-4abc-8599-c717295aaae8
    //    24e87951-45eb-4c19-bb0a-4508ae40ea2b
    public static String getName(String str, Entity entity) {
        if (str.toLowerCase().startsWith("health:")) return "";
        if (str.toLowerCase().contains("health:")) str = str.substring(0, str.toLowerCase().lastIndexOf("h")-1);
        if (entity instanceof PlayerEntity) return str;
        if (str.length() == "24e87951-45eb-4c19-bb0a-4508ae40ea2b".length()) return "";
        return str;
    }
}
