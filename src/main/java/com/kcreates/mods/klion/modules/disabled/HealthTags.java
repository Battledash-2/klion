package com.kcreates.mods.klion.modules.disabled;

import com.kcreates.mods.klion.Category;
import com.kcreates.mods.klion.modules.Module;
import com.kcreates.mods.klion.modules.ModuleManager;
import com.kcreates.mods.klion.util.NameUtil;
import com.kcreates.mods.klion.util.WorldUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class HealthTags extends Module {
    public HealthTags() {
        super("HealthTags", GLFW.GLFW_KEY_UNKNOWN, Category.MISC);
    }

    @Override
    public void onEnable() {
        ModuleManager.getModule(EntityOwners.class).setToggled(false); // disable incompat module
    }

    @Override
    public void onTick() {
        if (!this.isToggled()) return;
        List<Entity> entities = WorldUtil.getEntities();
        for (Entity e : entities) {
            if (!(e instanceof LivingEntity entity) || e == mc.player) continue;
            String health = ""+entity.getHealth();
            // KLion.log(entity.getDisplayName());
            String entityName = NameUtil.getName(entity.getName().getString(), entity);
            MutableText text = Text.literal(entityName).append(Text.literal(" Health").setStyle(Style.EMPTY.withBold(true))
                    .append(Text.literal(": ").setStyle(Style.EMPTY.withBold(false).withColor(Formatting.GRAY)))
                    .append(Text.literal(health).setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
            // MutableText text = new LiteralText(entityName).append(new LiteralText(" "+health).setStyle(Style.EMPTY.withColor(Formatting.GREEN)));
            entity.setCustomNameVisible(true);
            entity.setCustomName(text);
        }
    }
}
