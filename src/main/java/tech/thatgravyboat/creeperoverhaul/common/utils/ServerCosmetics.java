package tech.thatgravyboat.creeperoverhaul.common.utils;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.UUID;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import tech.thatgravyboat.creeperoverhaul.common.network.NetworkHandler;
import tech.thatgravyboat.creeperoverhaul.common.network.packets.ClientboundCosmeticPacket;

public class ServerCosmetics {

    private static final Object2BooleanMap<UUID> PLAYER = new Object2BooleanOpenHashMap<>();

    public static void setCosmeticShown(Player player, boolean shown) {
        PLAYER.put(player.getUUID(), shown);
        MinecraftServer server = player.level().getServer();
        if (server == null) return;
        NetworkHandler.NETWORK.sendToAllPlayers(new ClientboundCosmeticPacket(PLAYER), server);
    }

    public static void sendToPlayer(Player player) {
        NetworkHandler.NETWORK.sendToPlayer(new ClientboundCosmeticPacket(PLAYER), player);
    }

    public static void onPlayerJoin(Player player) {
        sendToPlayer(player);
    }
}
