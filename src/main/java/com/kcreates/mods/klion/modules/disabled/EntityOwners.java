package com.kcreates.mods.klion.modules.disabled;

import com.kcreates.mods.klion.Category;
import com.kcreates.mods.klion.modules.Module;
import com.kcreates.mods.klion.modules.ModuleManager;
import com.kcreates.mods.klion.util.WorldUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class EntityOwners extends Module {
    public EntityOwners() {
        super("EntityOwners", GLFW.GLFW_KEY_UNKNOWN, Category.MISC);
    }

    @Override
    public void onEnable() {
        ModuleManager.getModule(HealthTags.class).setToggled(false); // disable incompat module
    }

    @Override
    public void onDisable() {
        List<Entity> entities = WorldUtil.getEntities();
        for (Entity e : entities) {
            if (!(e instanceof TameableEntity entity)) continue;
            entity.setCustomNameVisible(false);
        }
    }

    @Override
    public void onTick() {
        if (!this.isToggled()) return;

        List<Entity> entities = WorldUtil.getEntities();
        for (Entity e : entities) {
            // KLion.log("" + (e instanceof TameableEntity) + " / " + ((e instanceof TameableEntity) ? ((TameableEntity)e).getOwner().getName().getString() : "nil"));
            if (!(e instanceof TameableEntity entity)) continue;
            if (entity.getOwner() != null) {
                LivingEntity owner = entity.getOwner();
                if (owner == null) continue;
                String ownerS = owner.getName().getString();
                // MutableText text = new LiteralText("Owner").formatted(Formatting.BOLD)
                //         .append(new LiteralText(": ").formatted(Formatting.RESET, Formatting.GRAY))
                //         .append(new LiteralText(ownerS).formatted(Formatting.RESET, Formatting.YELLOW));
                MutableText text = Text.literal("Owner").setStyle(Style.EMPTY.withBold(true))
                                .append(Text.literal(": ").setStyle(Style.EMPTY.withBold(false).withColor(Formatting.GRAY)))
                                .append(Text.literal(ownerS).setStyle(Style.EMPTY.withColor(Formatting.YELLOW)));
                entity.setCustomNameVisible(true);
                entity.setCustomName(text);
            }
        }
    }
}
