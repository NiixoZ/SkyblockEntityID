package fr.niixoz.hypixelentityid.mixin;

import fr.niixoz.hypixelentityid.api.SkyblockEntityIdHolder;
import net.minecraft.entity.Entity;
import net.minecraft.storage.ReadView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityReadDataMixin {

    // require = 0 => si jamais la méthode n'existe pas dans ta version/mappings, ça ne crash pas au chargement
    @Inject(method = "readData", at = @At("TAIL"), require = 0)
    private void hypixelentityid$readSkyblockId(ReadView view, CallbackInfo ci) {
        String id = view.getString("SKYBLOCK_ENTITY_ID", "");
        if (!id.isEmpty()) {
            ((SkyblockEntityIdHolder) (Object) this).hypixelentityid$setSkyblockEntityId(id);
        }
    }
}