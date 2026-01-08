package dashketch.mods.pycap.client;

import dashketch.mods.pycap.PycapConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class PycapClient implements ClientModInitializer {
    public static final KeyBinding.Category PYCAP_CATEGORY = new KeyBinding.Category(
            Identifier.of("pycap", "snapper")
    );

    // Create an instance of your config
    public static PycapConfig config = PycapConfig.load();

    public static KeyBinding recordKey;
    public static KeyBinding snapKey;
    private float savedPitch = 0f;
    private float savedYaw = 0f;

    @Override
    public void onInitializeClient() {

        recordKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.pycap.record",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                PYCAP_CATEGORY
        ));

        snapKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.pycap.snap",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                PYCAP_CATEGORY
        ));

        // Logic Listener
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            while (recordKey.wasPressed()) {
                savedPitch = client.player.getPitch();
                savedYaw = client.player.getYaw();

                if (config.showMessages) {
                    client.player.sendMessage(Text.literal("§a[Pycap] Position Recorded!"), true);
                }
            }

            while (snapKey.wasPressed()) {
                client.player.setPitch(savedPitch);
                client.player.setYaw(savedYaw);

                if (config.showMessages) {
                    client.player.sendMessage(Text.literal("§e[Pycap] Snapped!"), true);
                }
            }
        });
    }
}