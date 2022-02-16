package benzenestudios.sulphate;

import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

@FunctionalInterface
public interface ButtonConstructor<T extends AbstractButton> {
	T create(int x, int y, int width, int height, Component text, Button.OnPress onPress, Button.OnTooltip onTooltip);
}
