package benzenestudios.sulphate.tests;

import benzenestudios.sulphate.Anchor;
import benzenestudios.sulphate.SulphateScreen;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class SulphateTests implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		System.out.println("Sulphate Tests Loaded");

		KeyMapping keybind = KeyBindingHelper.registerKeyBinding(new KeyMapping(
				"key.sulphate_tests.open_stupid",
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

@SuppressWarnings("deprecation")
class StupidScreen extends SulphateScreen {
	protected StupidScreen() {
		super(Component.literal("Some Stupid Screen"));

		int offsetX = (int) ((150 + this.getXSeparation()) * 3.0/2.0);
		this.setAnchorX(Anchor.LEFT, () -> this.width / 2 - offsetX);

		this.setRows(3);
	}

	@Override
	protected void addWidgets() {
		this.addButton(Component.literal("I am a button"), System.out::println);
		this.addButton(Component.literal("I am also a button"), System.out::println).setTooltip(
				Tooltip.create(Component.literal("Okay Mr. Cool but consider: my tooltip is MODERN."))
		);
		this.addButton(Component.literal("You did not need to add me >:("), System.out::println);
		this.addButton(Component.literal("Yes he did, shut up."), System.out::println);
		this.addButton(Component.literal("im cool tho"), System.out::println, (bn, graphics, x, y) -> {
			graphics.renderTooltip(this.font, Component.literal("Cause I have a *tooltip*"), x, y);
		});

		this.addDone();
	}
}