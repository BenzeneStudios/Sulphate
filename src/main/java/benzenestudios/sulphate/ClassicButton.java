package benzenestudios.sulphate;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

/**
 * A classic minecraft button class, with its usage embedded in the ancient days before even beta 1.7.3.
 * This exists for code portability. Newer versions can change their buttons much less by using this class instead of
 * switching every button to a builder.
 */
public class ClassicButton extends Button {
	public ClassicButton(int x, int y, int width, int height, Component component, OnPress onPress) {
		this(x, y, width, height, component, onPress, NO_TOOLTIP, Button.DEFAULT_NARRATION);
	}

	public ClassicButton(int x, int y, int width, int height, Component component, OnPress onPress, OnTooltip onTooltip) {
		this(x, y, width, height, component, onPress, onTooltip, Button.DEFAULT_NARRATION);
	}

	/**
	 * In case you want that juicy modern CreateNarration goodness.
	 */
	public ClassicButton(int x, int y, int width, int height, Component component, OnPress onPress, OnTooltip onTooltip, CreateNarration createNarration) {
		super(x, y, width, height, component, onPress, createNarration);
		this.onTooltip = onTooltip;
	}

	protected OnTooltip onTooltip;

	@Override
	public void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
		super.updateWidgetNarration(narrationElementOutput);
		this.onTooltip.narrateTooltip(component -> narrationElementOutput.add(NarratedElementType.HINT, component));
	}

	/**
	 * The old OnTooltip functional interface from 1.19.2 and below.
	 */
	@FunctionalInterface
	public interface OnTooltip {
		void onTooltip(Button button, PoseStack poseStack, int x, int y);

		default void narrateTooltip(Consumer<Component> narrationConsumer) {
		}
	}

	public static final OnTooltip NO_TOOLTIP = (button, poseStack, x, y) -> {
	};
}
