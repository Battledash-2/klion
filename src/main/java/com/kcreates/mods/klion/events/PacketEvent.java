package com.kcreates.mods.klion.events;

import net.minecraft.network.Packet;

public class PacketEvent extends Event {
    public Packet<?> packet;
    public boolean outgoing;

    public PacketEvent(Packet<?> packet, boolean isOutgoing) {
        this.packet = packet;
        this.outgoing = isOutgoing;
    }
}
