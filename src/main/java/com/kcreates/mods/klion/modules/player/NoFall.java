package com.kcreates.mods.klion.modules.player;

import com.kcreates.mods.klion.Category;
import com.kcreates.mods.klion.gui.TabGUI.MultiOptionSetting;
import com.kcreates.mods.klion.modules.Module;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.lwjgl.glfw.GLFW;

public class NoFall extends Module {
    MultiOptionSetting mode = new MultiOptionSetting("Mode", (o)->{}, new String[]{
        "Packet",
        "Legit"
    }, 0);
    public NoFall() {
        super("NoFall", GLFW.GLFW_KEY_B, Category.PLAYER);
        addSetting(mode);
    }

    @Override
    public void onTick() {
        if (this.isToggled() && mc.player.fallDistance > 2) {
            if (mode.getCurrentChoice() == 0) {
                mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
            } else if (mode.getCurrentChoice() == 1 &&
                    mc.world.getBlockState(mc.player.getBlockPos().add(0, -1, 0)).isAir()) {
                mc.player.setVelocity(mc.player.getVelocity().x, -.1f, mc.player.getVelocity().z);
            }
        }
    }
}
