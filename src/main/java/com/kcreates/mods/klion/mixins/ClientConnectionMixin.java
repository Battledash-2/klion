package com.kcreates.mods.klion.mixins;

import com.kcreates.mods.klion.events.ChatEvent;
import com.kcreates.mods.klion.events.EventHelper;
import com.kcreates.mods.klion.events.PacketEvent;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onPacketRead(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo ci) {
        if (packet instanceof ChatMessageC2SPacket) EventHelper.handle(ci, new ChatEvent(((ChatMessageC2SPacket) packet).chatMessage(), false));
        else EventHelper.handle(ci, new PacketEvent(packet, false));
    }
}
