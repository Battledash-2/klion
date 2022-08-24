package com.kcreates.mods.klion.mixins;

import com.kcreates.mods.klion.KLion;
import com.kcreates.mods.klion.events.EventHelper;
import com.kcreates.mods.klion.events.PacketEvent;
import com.kcreates.mods.klion.modules.Module;
import com.kcreates.mods.klion.modules.ModuleManager;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.util.telemetry.TelemetrySender;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.command.argument.TextArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.CommandTreeS2CPacket;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Shadow private CommandDispatcher<ServerCommandSource> commandDispatcher;

    private static LiteralArgumentBuilder<ServerCommandSource> literal(String msg) {
        return CommandManager.literal(msg);
    }
    private static RequiredArgumentBuilder argument(String nm, ArgumentType arg) {
        return CommandManager.argument(nm, arg);
    }
    private static void send(MutableText msg) {
//        KLion.mc.player.sendMessage(new LiteralText("["+KLion.MOD_NM+":"+KLion.MOD_VR+"] ")
//                .styled(style -> style.withColor(TextColor.fromRgb(new Color(235, 164, 89).getRGB())))
//                .styled(style -> style.withBold(true))
//                .append(msg.styled(style -> style.withBold(false)).formatted(Formatting.GRAY)), false);
        KLion.send(msg);
//        KLion.mc.player.sendMessage();
    }
    private static void send(String msg) {
        send(Text.literal(msg));
    }

    private void registerCommands() {
        LiteralArgumentBuilder<ServerCommandSource> klion = literal("klion");
        // Teleport
        klion.then(literal("tp").then(argument("pos", Vec3ArgumentType.vec3()).executes(ClientPlayNetworkHandlerMixin::teleport)));
        klion.then(literal("teleport").then(argument("pos", Vec3ArgumentType.vec3()).executes(ClientPlayNetworkHandlerMixin::teleport)));

        // Toggle Module
        klion.then(literal("toggle").then(argument("mod", MessageArgumentType.message()).executes(context -> {
            String m = MessageArgumentType.getMessage(context, "mod").getString();
            Module module = ModuleManager.getModule(m);
            if (module == null) send(Text.literal("Unknown module '"+m+"'").formatted(Formatting.RED));
            else {
                module.toggle();
                send(Text.literal(ModuleManager.getCase(module.getName()) + " " + (module.isToggled() ? "enabled." : "disabled.")));
            }
            return 0;
        })));

        // Clear Chat
        klion.then(literal("clearchat").executes(context -> {
            KLion.mc.inGameHud.getChatHud().clear(false);
            return 0;
        }));

        commandDispatcher.register(klion);
    }
    private static int teleport(CommandContext context) {
        Vec3d pos = Vec3ArgumentType.getPosArgument(context, "pos").toAbsolutePos((ServerCommandSource) context.getSource());
        KLion.mc.player.setPos(pos.getX(), pos.getY(), pos.getZ());
        String posStr = "("+pos.getX()+", "+pos.getY()+", "+pos.getZ()+")";
        String posStrR = "("+Math.round((float)pos.getX())+", "+Math.round((float)pos.getY())+", "+Math.round((float)pos.getZ())+")";
        send(Text.literal("Attempted to teleport to ").append(Text.literal(posStrR).styled(style ->
                style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal(posStr)))
        ).formatted(Formatting.BLUE, Formatting.BOLD)));
        return 0;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(MinecraftClient client, Screen screen, ClientConnection connection, GameProfile profile, TelemetrySender telemetrySender, CallbackInfo ci) {
        registerCommands();
    }
    @Inject(method = "onCommandTree", at = @At("TAIL"))
    private void onCommand(CommandTreeS2CPacket packet, CallbackInfo ci) {
        registerCommands();
    }

    @Inject(method = "sendPacket", at = @At("HEAD"), cancellable = true)
    private void onPacketSend(Packet<?> packet, CallbackInfo ci) {
        EventHelper.handle(ci, new PacketEvent(packet, true));
    }
}
