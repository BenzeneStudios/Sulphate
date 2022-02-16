package benzenestudios.sulphate;

import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;

import java.util.Collection;

/**
 * An extension to the mechanics a {@linkplain net.minecraft.client.gui.screens.Screen screen} provides.
 */
public interface ExtendedScreen {
	/**
	 * Removes the given element from the screen.
	 */
	void removeChild(GuiEventListener element);

	/**
	 * @return a collection of widgets ("renderables") on the screen.
	 */
	Collection<Widget> getWidgets();

	/**
	 * @return a collection of all children on the screen. This collection may be larger than the one returned by {@linkplain ExtendedScreen#getWidgets()}.
	 */
	Collection<GuiEventListener> getChildren();

	/**
	 * @return the title component of this screen.
	 */
	Component getTitle();

	/**
	 * Sets the title of the screen.
	 * @param title the title to set.
	 */
	void setTitle(Component title);
}
