package benzenestudios.sulphate;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.Collection;
import java.util.List;

/**
 * Screen with more 1.17+ like methods for easier porting.
 */
public abstract class ModernScreen extends Screen {
	public ModernScreen(Component component) {
		super(component);
	}

	protected final List<Widget> renderables = Lists.newArrayList();

	@Override
	public void init(Minecraft minecraft, int width, int height) {
		// clear current renderables on screen (re)initialisation
		this.renderables.clear();
		super.init(minecraft, width, height);
	}

	/**
	 * Alias for addButton(T widget)
	 * @reason easy 1.17+ porting/backporting
	 * @return the given widget
	 */
	protected <T extends Widget & GuiEventListener> T addRenderableWidget(T widget) {
		this.renderables.add(widget);
		return this.addWidget(widget);
	}

	@Override
	protected <T extends AbstractWidget> T addButton(T button) {
		return this.addRenderableWidget(button);
	}

	/**
	 * Adds the given widget to be rendered, but not as a widget.
	 * @reason easy 1.17+ porting/backporting
	 * @return the given widget
	 */
	protected <T extends Widget> T addRenderableOnly(T widget) {
		this.renderables.add(widget);
		return widget;
	}

	@Override
	public void render(PoseStack matrices, int mouseX, int mouseY, float partialTicks) {
		for (int i = 0; i < this.renderables.size(); ++i) {
			this.renderables.get(i).render(matrices, mouseX, mouseY, partialTicks);
		}
	}

	public void removeRenderable(Widget element) {
		this.renderables.remove(element);
	}

	public Collection<Widget> renderables() {
		return this.renderables;
	}
}
