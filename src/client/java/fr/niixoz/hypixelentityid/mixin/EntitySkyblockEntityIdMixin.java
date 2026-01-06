package fr.niixoz.hypixelentityid.mixin;

import fr.niixoz.hypixelentityid.api.SkyblockEntityIdHolder;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Entity.class)
public class EntitySkyblockEntityIdMixin implements SkyblockEntityIdHolder {

    @Unique private String hypixelentityid$skyblockEntityId = "";
    @Unique private long hypixelentityid$lastScanTick = -10_000;

    @Override
    public String hypixelentityid$getSkyblockEntityId() {
        return hypixelentityid$skyblockEntityId;
    }

    @Override
    public void hypixelentityid$setSkyblockEntityId(String id) {
        hypixelentityid$skyblockEntityId = (id == null) ? "" : id;
    }

    @Override
    public long hypixelentityid$getLastSkyblockScanTick() {
        return hypixelentityid$lastScanTick;
    }

    @Override
    public void hypixelentityid$setLastSkyblockScanTick(long tick) {
        hypixelentityid$lastScanTick = tick;
    }
}