package at.meikel.sudoku.bl;

public abstract class AbstractSquare<T> {

	private int n;
	private T[] values;

	public AbstractSquare() {
	}

	public AbstractSquare(int n) {
		if (n < 1) {
			throw new IllegalArgumentException();
		}
		this.n = n;
		values = create();
	}

	public AbstractSquare(T[] model) {
		this((int) Math.sqrt(model.length));
		if (model.length != (getN() * getN())) {
			throw new IllegalArgumentException();
		}

		for (int i = 0; i < model.length; i++) {
			values[i] = model[i];
		}
	}

	abstract public T[] create();

	abstract public boolean isValidValue(T value);

	public int getN() {
		return n;
	}

	public void setValue(int x, int y, T value) {
		if (x < 1) {
			throw new IllegalArgumentException();
		}
		if (x > n) {
			throw new IllegalArgumentException();
		}
		if (y < 1) {
			throw new IllegalArgumentException();
		}
		if (y > n) {
			throw new IllegalArgumentException();
		}
		if (!isValidValue(value)) {
			return;
		}

		values[(x - 1) + (y - 1) * n] = value;
	}

	public T getValue(int x, int y) {
		if (x < 1) {
			throw new IllegalArgumentException();
		}
		if (x > n) {
			throw new IllegalArgumentException();
		}
		if (y < 1) {
			throw new IllegalArgumentException();
		}
		if (y > n) {
			throw new IllegalArgumentException();
		}

		return values[(x - 1) + (y - 1) * n];
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object object) {
		try {
			AbstractSquare<T> other = (AbstractSquare<T>) object;
			return values.equals(other.values);
		} catch (Exception e) {
			// ignore
			return false;
		}
	}

}
