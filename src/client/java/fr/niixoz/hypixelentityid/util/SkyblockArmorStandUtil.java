package fr.niixoz.hypixelentityid.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.Comparator;
import java.util.List;

public final class SkyblockArmorStandUtil {
    private SkyblockArmorStandUtil() {}

    public static String getArmorStandName(Entity entity) {
        return getArmorStandName(entity.getEntityWorld(), entity.getBoundingBox());
    }

    public static String getArmorStandName(World world, Box box) {
        List<ArmorStandEntity> stands = getArmorStands(world, box);
        if (stands.isEmpty()) return "";

        // Choisir celui le plus proche du centre de la box (souvent le label “principal”)
        double cx = (box.minX + box.maxX) * 0.5;
        double cy = (box.minY + box.maxY) * 0.5;
        double cz = (box.minZ + box.maxZ) * 0.5;

        stands.sort(Comparator.comparingDouble(s -> s.squaredDistanceTo(cx, cy, cz)));
        return stands.getFirst().getName().getString();
    }

    public static List<ArmorStandEntity> getArmorStands(World world, Box box) {
        Box inflated = box.expand(0.0, 2.0, 0.0); // équivalent de inflate(0,2,0)
        return world.getEntitiesByClass(
                ArmorStandEntity.class,
                inflated,
                stand -> stand.isAlive() && stand.hasCustomName()
        );
    }
}