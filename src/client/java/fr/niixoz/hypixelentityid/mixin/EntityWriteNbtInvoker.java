package fr.niixoz.hypixelentityid.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityWriteNbtInvoker {

    // Si la signature change sur 1.21.10, IntelliJ te le dira.
    // Le but est de bypass l’access (protected) et pouvoir l’appeler côté mod.
    @Invoker("writeNbt")
    NbtCompound hypixelentityid$invokeWriteNbt(NbtCompound nbt);
}