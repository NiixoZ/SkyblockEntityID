package fr.niixoz.hypixelentityid.api;

public interface SkyblockEntityIdHolder {
    String hypixelentityid$getSkyblockEntityId();
    void hypixelentityid$setSkyblockEntityId(String id);

    long hypixelentityid$getLastSkyblockScanTick();
    void hypixelentityid$setLastSkyblockScanTick(long tick);
}