package fr.niixoz.hypixelentityid.client;

import fr.niixoz.hypixelentityid.api.SkyblockEntityIdHolder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

import java.lang.reflect.Method;

public final class DebugEntityNbtDump {
    private DebugEntityNbtDump() {}

    public static void dumpTarget(MinecraftClient client) {
        if (client.player == null) return;

        HitResult hr = client.crosshairTarget;
        if (!(hr instanceof EntityHitResult ehr)) {
            client.player.sendMessage(Text.literal("[HypixelEntityID] Pas d'entité visée."), false);
            return;
        }

        Entity target = ehr.getEntity();
        NbtCompound nbt = new NbtCompound();

        // Dump vanilla (robuste aux renommages)
        invokeBestEffortNbtWrite(target, nbt);

        // Ajout de ton “tag” client-side dans le dump
        String skyId = ((SkyblockEntityIdHolder)(Object)target).hypixelentityid$getSkyblockEntityId();
        if (!skyId.isEmpty()) {
            nbt.putString("SKYBLOCK_ENTITY_ID", skyId);
        }

        String s = nbt.toString();
        client.keyboard.setClipboard(s);

        client.player.sendMessage(Text.literal("[HypixelEntityID] NBT copié dans le presse-papier. (" + s.length() + " chars)"), false);
        client.player.sendMessage(Text.literal("[HypixelEntityID] SKYBLOCK_ENTITY_ID = " + (skyId.isEmpty() ? "<vide>" : skyId)), false);
    }

    private static void invokeBestEffortNbtWrite(Entity entity, NbtCompound out) {
        // Cherche une méthode plausible dans la hiérarchie
        // (writeNbt(NbtCompound) -> NbtCompound) (saveNbt(NbtCompound) -> boolean) (writeData(NbtCompound) -> void) etc.
        String[] names = {"writeNbt", "saveNbt", "saveSelfNbt", "writeData"};
        for (String name : names) {
            Method m = findMethod(entity.getClass(), name, NbtCompound.class);
            if (m == null) continue;

            try {
                m.setAccessible(true);
                Object ret = m.invoke(entity, out);
                if (ret instanceof NbtCompound compound) {
                    // certains write* renvoient le compound
                    out.copyFrom(compound);
                }
                return;
            } catch (Throwable ignored) {
                // on tente le prochain
            }
        }
    }

    private static Method findMethod(Class<?> start, String name, Class<?>... params) {
        Class<?> c = start;
        while (c != null) {
            try {
                return c.getDeclaredMethod(name, params);
            } catch (NoSuchMethodException ignored) {
                c = c.getSuperclass();
            }
        }
        return null;
    }
}