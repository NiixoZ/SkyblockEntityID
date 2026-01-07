package fr.niixoz.hypixelentityid.client;

import fr.niixoz.hypixelentityid.api.SkyblockEntityIdHolder;
import fr.niixoz.hypixelentityid.util.SkyblockArmorStandUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;

public final class SkyblockEntityIdScanner {
    private SkyblockEntityIdScanner() {}

    private static final double SCAN_RADIUS = 64.0;     // blocs
    private static final double SCAN_RADIUS_SQ = SCAN_RADIUS * SCAN_RADIUS;
    private static final long RESCAN_TICKS = 3;        // 0.15s

    public static void tick(MinecraftClient client) {
        if (client.world == null || client.player == null) return;

        ClientWorld world = client.world;
        long now = world.getTime(); // tick world

        // ClientWorld#getEntities() existe (Iterable<Entity>) dans les versions 1.21.x :contentReference[oaicite:3]{index=3}
        for (Entity e : world.getEntities()) {
            if (e == client.player) continue;
            if (e instanceof ArmorStandEntity) continue;

            if (e.squaredDistanceTo(client.player) > SCAN_RADIUS_SQ) continue;

            SkyblockEntityIdHolder holder = (SkyblockEntityIdHolder) (Object) e;

            // rate limit
            if (now - holder.hypixelentityid$getLastSkyblockScanTick() < RESCAN_TICKS) continue;
            holder.hypixelentityid$setLastSkyblockScanTick(now);

            // déjà trouvé
            if (!holder.hypixelentityid$getSkyblockEntityId().isEmpty()) continue;

            String label = SkyblockArmorStandUtil.getArmorStandName(e);
            String id = SkyblockEntityIdParser.toEntityId(label);

            if (!id.isEmpty()) {
                holder.hypixelentityid$setSkyblockEntityId(id);
            }
        }
    }
}