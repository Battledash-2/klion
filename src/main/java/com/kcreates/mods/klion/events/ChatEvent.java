package com.kcreates.mods.klion.events;

import net.minecraft.entity.Entity;

import static com.kcreates.mods.klion.KLion.mc;

public class ChatEvent extends Event {
    public String message;
    public boolean outgoing;
    public String sender;

    public ChatEvent(String message, boolean outgoing) {
        this.message = message;
        this.outgoing = outgoing;
        if (!outgoing) this.sender = mc.player.getName().getString();
        else this.sender = message.contains("<") && message.contains(">") ? message.substring(1, message.substring(1).indexOf(">")-1) : "";
    }
}
