package benzenestudios.sulphate.mixin;

import java.util.Collection;
import java.util.List;

import net.minecraft.client.gui.components.Renderable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import benzenestudios.sulphate.ExtendedScreen;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

@Mixin(Screen.class)
public class MixinScreen implements ExtendedScreen {
	@Shadow
	@Final
	@Mutable
	private List<GuiEventListener> children;
	@Shadow
	@Final
	@Mutable
	private List<NarratableEntry> narratables;
	@Shadow
	@Final
	@Mutable
	private List<Renderable> renderables;

	@Shadow
	@Final
	@Mutable
	protected Component title;
	
	@Override
	public void removeChild(GuiEventListener element) {
		this.children.remove(element);
		if (element instanceof NarratableEntry) this.narratables.remove((NarratableEntry) element);
		if (element instanceof Renderable) this.renderables.remove((Renderable) element);
	}

	@Override
	public Collection<Renderable> getWidgets() {
		return this.renderables;
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
