package benzenestudios.sulphate;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;

@FunctionalInterface
public interface WidgetConstructor<T extends AbstractWidget> {
	T create(int x, int y, int width, int height, Component text);
}
