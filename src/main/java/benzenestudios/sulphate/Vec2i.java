package benzenestudios.sulphate;

import java.util.Objects;

/**
 * A simple pair of two integers.
 */
public final class Vec2i {
	Vec2i(int x, int y) {
		this.x = x;
		this.y = y;
	}

	private final int x;
	private final int y;

	public int x() {
		return this.x;
	}

	public int y() {
		return this.y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		Vec2i that = (Vec2i) obj;
		return this.x == that.x &&
				this.y == that.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.x, this.y);
	}

	@Override
	public String toString() {
		return "Vec2i[" +
				"x=" + x + ", " +
				"y=" + y + ']';
	}

	public Vec2i add(Vec2i other) {
		return this.add(other.x, other.y);
	}

	public Vec2i add(int x, int y) {
		return new Vec2i(this.x + x, this.y + y);
	}

	public Vec2i sub(Vec2i other) {
		return this.sub(other.x, other.y);
	}

	public Vec2i sub(int x, int y) {
		return new Vec2i(this.x - x, this.y - y);
	}

	public Vec2i mul(Vec2i other) {
		return this.mul(other.x, other.y);
	}

	public Vec2i mul(int x, int y) {
		return new Vec2i(this.x * x, this.y * y);
	}

	public Vec2i scale(float by) {
		return new Vec2i((int) (this.x * by), (int) (this.y * by));
	}
}
