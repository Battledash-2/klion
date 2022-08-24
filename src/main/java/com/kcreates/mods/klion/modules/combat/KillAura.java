package com.kcreates.mods.klion.modules.combat;

import com.kcreates.mods.klion.Category;
import com.kcreates.mods.klion.KLion;
import com.kcreates.mods.klion.gui.TabGUI.RangeSetting;
import com.kcreates.mods.klion.modules.Module;
import com.kcreates.mods.klion.util.Timer;
import com.kcreates.mods.klion.util.WorldUtil;
import net.minecraft.entity.LivingEntity;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class KillAura extends Module {
    private Timer waitTimer = new Timer(0, true);

    private RangeSetting range = new RangeSetting("Range", (o)->{},
            0.5f,
            4.5f,
            0.5f,
            4.5f);
    private RangeSetting wait = new RangeSetting("Time", (o)->waitTimer.setLength(Timer.getTicks(o.getValue()*60)),
            0.0f,
            10.0f,
            1.0f,
            2.0f);

    public KillAura() {
        super("KillAura", GLFW.GLFW_KEY_U, Category.COMBAT);
        addSettings(range, wait);
    }

    @Override
    public void onGameLoad() {
        range.setMax(mc.interactionManager.getReachDistance());
    }

    @Override
    public void onEnable() {
        range.setMax(mc.interactionManager.getReachDistance());
        waitTimer.setLength(Timer.getTicks(wait.getValue()*60));
    }

    @Override
    public void onTick() {
        waitTimer.setLength(Timer.getTicks(wait.getValue()*60));
        if (!this.isToggled() || !waitTimer.tick()) return;

        if (range.getMax() < mc.interactionManager.getReachDistance()) range.setMax(mc.interactionManager.getReachDistance());
        List<LivingEntity> entities = WorldUtil.getEntities(range.getValue());
        for (LivingEntity entity : entities) {
            if (!entity.isAlive() || !entity.isAttackable() || entity == mc.player) continue;
            mc.interactionManager.attackEntity(mc.player, entity);
            mc.player.swingHand(mc.player.getActiveHand());
        }
    }
}
