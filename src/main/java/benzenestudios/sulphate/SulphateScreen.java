package benzenestudios.sulphate;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.function.IntSupplier;

/**
 * Screen that handles automatic placement of widgets.
 */
public abstract class SulphateScreen extends Screen {
	protected SulphateScreen(Component title) {
		this(title, null);
	}

	protected SulphateScreen(Component title, @Nullable Screen parent) {
		super(title);
		this.parent = parent;
	}

	// mostly appending and iterating so LinkedList
	private List<AbstractWidget> toRePositionY = new LinkedList<>();

	private Anchor anchor = Anchor.CENTRE;
	private IntSupplier anchorY = () -> this.height / 2;
	private IntSupplier anchorX = () -> this.width / 2;
	private int rows = 1;
	private int ySeparation = 24;
	private int xSeparation = 10;

	private int yOff;
	protected final Screen parent;

	// settings

	protected void setAnchorX(Anchor xAnchor) {
		this.setAnchorX(xAnchor, this.anchorX);
	}

	protected void setAnchorX(Anchor xAnchor, IntSupplier position) {
		this.anchor = this.anchor.withX(xAnchor.xAnchor);
		this.anchorX = position;
	}

	protected void setAnchorY(Anchor yAnchor) {
		this.setAnchorX(yAnchor, this.anchorX);
	}

	protected void setAnchorY(Anchor yAnchor, IntSupplier position) {
		this.anchor = this.anchor.withY(yAnchor.xAnchor);
		this.anchorY = position;
	}

	protected void setAnchor(Anchor anchor) {
		this.setAnchor(anchor, this.anchorX, this.anchorY);
	}

	protected void setAnchor(Anchor anchor, IntSupplier anchorX, IntSupplier anchorY) {
		this.anchor = anchor;
		this.anchorX = anchorX;
		this.anchorY = anchorY;
	}

	protected void setYSeparation(int separation) {
		this.ySeparation = separation;
	}

	protected void setXSeparation(int separation) {
		this.xSeparation = separation;
	}

	protected void setSeparation(int x, int y) {
		this.ySeparation = y;
		this.xSeparation = x;
	}

	public int getXSeparation() {
		return this.xSeparation;
	}

	public int getYSeparation() {
		return this.ySeparation;
	}

	protected void setRows(int rows) {
		this.rows = rows;
	}

	// implement this

	protected abstract void addWidgets();

	// adding stuff

	protected <T extends AbstractWidget> T addWidget(WidgetConstructor<T> constr, Component text) {
		return this.addWidget(constr, text, 200 - ((this.rows - 1) * 50), 20);
	}

	protected <T extends AbstractWidget> T addWidget(WidgetConstructor<T> constr, Component text, int width, int height) {
		T widget = constr.create(0, 0, width, height, text);
		this.toRePositionY.add(widget);
		return (T) super.addRenderableWidget(widget);
	}

	// the 10 billion overloads of addButton

	private static int defaultWidthFor(int rows) {
//		int width = 200 - 50 * (rows / 2);
//		if (width <= 50) width = 100;
		return rows == 1 ? 200 : 150;
	}

	protected Button addButton(Component text, Button.OnPress onPress) {
		return this.addButton(Button::new, text, defaultWidthFor(this.rows), 20, onPress, Button.NO_TOOLTIP);
	}

	protected <T extends AbstractButton> T addButton(ButtonConstructor<T> constr, Component text, Button.OnPress onPress) {
		return this.addButton(constr, text, defaultWidthFor(this.rows), 20, onPress, Button.NO_TOOLTIP);
	}

	protected Button addButton(Component text, Button.OnPress onPress, Button.OnTooltip onTooltip) {
		return this.addButton(Button::new, text, defaultWidthFor(this.rows), 20, onPress, onTooltip);
	}

	protected <T extends AbstractButton> T addButton(ButtonConstructor<T> constr, Component text, Button.OnPress onPress, Button.OnTooltip onTooltip) {
		return this.addButton(constr, text, defaultWidthFor(this.rows), 20, onPress, onTooltip);
	}

	protected Button addButton(int width, int height, Component text, Button.OnPress onPress) {
		return this.addButton(Button::new, text, width, height, onPress, Button.NO_TOOLTIP);
	}

	protected <T extends AbstractButton> T addButton(ButtonConstructor<T> constr, Component text, int width, int height, Button.OnPress onPress) {
		return this.addButton(constr, text, width, height, onPress, Button.NO_TOOLTIP);
	}

	protected Button addButton(int width, int height, Component text, Button.OnPress onPress, Button.OnTooltip onTooltip) {
		return this.addButton(Button::new, text, width, height, onPress, onTooltip);
	}

	protected <T extends AbstractButton> T addButton(ButtonConstructor<T> constr, Component text, int width, int height, Button.OnPress onPress, Button.OnTooltip onTooltip) {
		T widget = constr.create(AUTO, AUTO, width, height, text, onPress, onTooltip);
		this.toRePositionY.add(widget);
		return (T) super.addRenderableWidget(widget);
	}

	// it's everywhere

	private AbstractButton done;

	protected AbstractButton addDone() {
		return this.addDone(Button::new, AUTO);
	}

	protected AbstractButton addDone(ButtonConstructor<?> cstr) {
		return this.addDone(cstr, AUTO);
	}

	protected AbstractButton addDone(int y) {
		return this.addDone(Button::new, y);
	}

	protected AbstractButton addDone(ButtonConstructor<?> cstr, int y) {
		this.addRenderableWidget(this.done = cstr.create(this.width / 2 - 100, y, 200, 20, CommonComponents.GUI_DONE, button -> this.onClose(), Button.NO_TOOLTIP));
		return this.done;
	}

	// impl stuff

	@Override
	protected final void init() {
		yOff = 2 * this.width / 3; // just in case
		this.addWidgets();

		if (!this.toRePositionY.isEmpty()) {
			yOff = this.anchorY.getAsInt();

			switch (this.anchor.yAnchor) {
			case -1:
				break;
			case 1:
				yOff -= (this.ySeparation * this.toRePositionY.size()) / this.rows;
				break;
			default:
				yOff -= (this.ySeparation * this.toRePositionY.size()) / (2 * this.rows);
				break;
			}

			int objs = 0;

			// so we can centre everything along x axis
			List<AbstractWidget> toRePositionX = new LinkedList<>();
			int xOffset = 0;

			for (AbstractWidget widget : this.toRePositionY) {
				widget.y = yOff;
				++objs;
				xOffset += widget.getWidth() + this.xSeparation;
				toRePositionX.add(widget);

				if (objs == this.rows) {
					this.repositionX(toRePositionX, xOffset);

					yOff += this.ySeparation;
					objs = xOffset = 0;
					toRePositionX = new LinkedList<>();
				}
			}

			if (!toRePositionX.isEmpty()) {
				this.repositionX(toRePositionX, xOffset);
			}

			yOff += 3 * this.ySeparation / 2; // for the done button
		}

		if (this.done != null && this.done.y == AUTO) {
			this.done.y = yOff;
		}
	}

	private void repositionX(List<AbstractWidget> toRePositionX, int xOffset) {
		xOffset -= this.xSeparation; // clear the final spacer

		int x = this.anchorX.getAsInt();

		switch (this.anchor.xAnchor) {
		case -1:
			break;
		case 1:
			x -= xOffset;
			break;
		default:
			x -= xOffset / 2;
			break;
		}

		for (AbstractWidget widget : toRePositionX) {
			widget.x = x;
			x += widget.getWidth() + this.xSeparation;
		}
	}

	@Override
	public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		drawCenteredString(matrices, this.font, this.title, this.width / 2, 15, 0xFFFFFF);
		super.render(matrices, mouseX, mouseY, delta);
	}

	@Override
	protected void clearWidgets() {
		super.clearWidgets();
		this.toRePositionY.clear();
	}

	@Override
	public void onClose() {
		this.minecraft.setScreen(this.parent);
	}

	// this is when cosmetica has been providing better minecraft cosmetics for free since
	private static int AUTO = 42069;
}
