package benzenestudios.sulphate.tests;

import benzenestudios.sulphate.Anchor;
import benzenestudios.sulphate.SulphateScreen;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.TextComponent;
import org.lwjgl.glfw.GLFW;

public class SulphateTests implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		System.out.println("Sulphate Tests Loaded");

		KeyMapping keybind = KeyBindingHelper.registerKeyBinding(new KeyMapping(
				"key.sulphate_tests.openstupid",
				InputConstants.Type.KEYSYM,
				GLFW.GLFW_KEY_UP,
				"category.sulphate_tests"
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (keybind.consumeClick()) {
				System.out.println("Pressed Stupid Keybind");
				client.setScreen(new StupidScreen());
			}
		});
	}
}

class StupidScreen extends SulphateScreen {
	protected StupidScreen() {
		super(new TextComponent("Some Stupid Screen"));

		int offsetX = (int) ((150 + this.getXSeparation()) * 3.0/2.0);
		this.setAnchorX(Anchor.LEFT, () -> this.width / 2 - offsetX);

		this.setRows(3);
	}

	@Override
	protected void addWidgets() {
		this.addButton(new TextComponent("I am a button"), System.out::println);
		this.addButton(new TextComponent("I am also a button"), System.out::println);
		this.addButton(new TextComponent("You did not need to add me >:("), System.out::println);
		this.addButton(new TextComponent("Yes he did, shut up."), System.out::println);
		this.addButton(new TextComponent("im cool tho"), System.out::println);

		this.addDone();
	}
}