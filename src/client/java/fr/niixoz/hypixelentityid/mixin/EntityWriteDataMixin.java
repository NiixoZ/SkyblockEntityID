package fr.niixoz.hypixelentityid.mixin;

import fr.niixoz.hypixelentityid.api.SkyblockEntityIdHolder;
import net.minecraft.entity.Entity;
import net.minecraft.storage.WriteView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityWriteDataMixin {

    @Inject(method = "writeData", at = @At("TAIL"))
    private void hypixelentityid$writeSkyblockId(WriteView view, CallbackInfo ci) {
        String id = ((SkyblockEntityIdHolder) (Object) this).hypixelentityid$getSkyblockEntityId();
        if (id != null && !id.isEmpty()) {
            // Ajout au "NBT" sérialisé par WriteView
            view.putString("SKYBLOCK_ENTITY_ID", id);
        }
    }
}