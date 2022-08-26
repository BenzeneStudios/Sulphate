package benzenestudios.sulphate.mixin;

import java.util.Collection;
import java.util.List;

import benzenestudios.sulphate.ModernScreen;
import net.minecraft.client.gui.components.AbstractWidget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import benzenestudios.sulphate.ExtendedScreen;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

@Mixin(Screen.class)
public class MixinScreen implements ExtendedScreen {
	@Shadow
	@Final
	private List<GuiEventListener> children;
	@Shadow
	@Final
	private List<AbstractWidget> buttons;

	@Shadow
	@Final
	@Mutable
	private Component title;
	
	@Override
	public void removeChild(GuiEventListener element) {
		this.children.remove(element);
		if (((Object) this) instanceof ModernScreen && element instanceof Widget) ((ModernScreen) (Object) this).removeRenderable((Widget) element);
		if (element instanceof AbstractWidget) this.buttons.remove((AbstractWidget) element);
	}

	@Override
	public Collection<Widget> getWidgets() {
		return (((Object) this) instanceof ModernScreen) ? ((ModernScreen) (Object) this).renderables() : (Collection<Widget>) (Object) this.buttons;
	}

	@Override
	public Collection<GuiEventListener> getChildren() {
		return this.children;
	}

	@Override
	public void setTitle(Component title) {
		this.title = title;
	}

	@Override
	public Component getTitle() {
		return this.title;
	}
}
