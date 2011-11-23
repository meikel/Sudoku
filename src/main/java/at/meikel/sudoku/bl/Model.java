package at.meikel.sudoku.bl;

public class Model extends AbstractSquare<Square> {

    public Model(int n) {
        super(n);
    }

    public static Model fromArray(int[] values) {
        int n = (int) Math.sqrt(Math.sqrt(values.length));
        if (values.length != (n * n * n * n)) {
            throw new IllegalArgumentException();
        }

        Model result = new Model(n);
        int i = 0;
        for (int sy = 1; sy <= n; sy++) {
            for (int y = 1; y <= n; y++) {
                for (int sx = 1; sx <= n; sx++) {
                    Square square = result.get(sx, sy);
                    if (square == null) {
                        square = new Square(n);
                        result.set(sx, sy, square);
                    }
                    for (int x = 1; x <= n; x++) {
                        square.set(x, y, values[i++]);
                    }
                }
            }
        }
        return result;
    }

    public void set(int x, int y, Square value) {
        super.setValue(x, y, value);
    }

    public Square get(int x, int y) {
        return super.getValue(x, y);
    }

    @Override
    public Square[] create() {
        return new Square[getN() * getN()];
    }

    @Override
    public boolean isValidValue(Square value) {
        return value != null;
    }

    public void print() {
        int n = getN();
        for (int sy = 1; sy <= n; sy++) {
            for (int y = 1; y <= n; y++) {
                for (int sx = 1; sx <= n; sx++) {
                    for (int x = 1; x <= n; x++) {
                        Square square = get(sx, sy);
                        if (square == null) {
                            System.out.print(" (" + x + "," + y + ") == null ");
                        } else {
                            int value = square.get(x, y);
                            System.out.print(value == 0 ? " " : value);
                        }
                        if (x < n) {
                            System.out.print(" | ");
                        }
                    }
                    System.out.print("   ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

}
