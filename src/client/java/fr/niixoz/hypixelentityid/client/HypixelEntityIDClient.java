package fr.niixoz.hypixelentityid.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class HypixelEntityIDClient implements ClientModInitializer {

    private static KeyBinding DUMP_KEY;

    @Override
    public void onInitializeClient() {
        DUMP_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.hypixelentityid.dump_nbt",
                GLFW.GLFW_KEY_N,
                KeyBinding.Category.MISC
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // scan ids
            SkyblockEntityIdScanner.tick(client);

            // debug
            while (DUMP_KEY.wasPressed()) {
                DebugEntityNbtDump.dumpTarget(client);
            }
        });
    }
}