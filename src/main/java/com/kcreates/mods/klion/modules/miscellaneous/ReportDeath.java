package com.kcreates.mods.klion.modules.miscellaneous;

import com.kcreates.mods.klion.Category;
import com.kcreates.mods.klion.KLion;
import com.kcreates.mods.klion.events.PacketEvent;
import com.kcreates.mods.klion.modules.Module;
import com.kcreates.mods.klion.util.StringUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.DeathMessageS2CPacket;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

public class ReportDeath extends Module {
    public ReportDeath() {
        super("ReportDeath", GLFW.GLFW_KEY_UNKNOWN, Category.MISC);
    }

    @Override
    public boolean onEvent(Object e, boolean cancelled) {
        if (cancelled || !this.isToggled() || !(e instanceof PacketEvent)) return cancelled;
        PacketEvent event = (PacketEvent) e;
        if (event.outgoing) return cancelled;
        Packet<?> upacket = event.packet;
        if (!(upacket instanceof DeathMessageS2CPacket)) return cancelled;
        DeathMessageS2CPacket packet = (DeathMessageS2CPacket) upacket;
        if (packet.getEntityId() != mc.player.getId()) return cancelled;

        String pos = StringUtil.getRound(mc.player.getBlockPos());
        KLion.send(Text.literal("You died at ").append(Text.literal(pos)
                .styled(style -> style.withClickEvent(
                            new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @s "+pos.replace(",", "")))
                        .withHoverEvent(
                            new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Teleport"))))
                .formatted(Formatting.BLUE, Formatting.BOLD)));

        return cancelled;
    }
}
