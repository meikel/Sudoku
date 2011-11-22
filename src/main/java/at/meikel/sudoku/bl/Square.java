package at.meikel.sudoku.bl;

public class Square extends AbstractSquare<Integer> {

	public Square() {
		super();
	}

	public Square(int n) {
		super(n);
	}

	public Square(Integer[] model) {
		super(model);
	}

	public void set(int x, int y, int value) {
		super.setValue(x, y, new Integer(value));
	}

	public int get(int x, int y) {
		return super.getValue(x, y).intValue();
	}

	@Override
	public Integer[] create() {
		Integer[] result = new Integer[getN() * getN()];
		for (int i = 0; i < result.length; i++) {
			result[i] = new Integer(0);
		}
		return result;
	}

	@Override
	public boolean isValidValue(Integer value) {
		if (value == null) {
			return false;
		}

		int n = getN();
		int v = value.intValue();
		if ((v < 1) || (v > n * n)) {
			return false;
		}

		return true;
	}

}
