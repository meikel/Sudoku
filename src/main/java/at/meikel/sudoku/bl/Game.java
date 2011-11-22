package at.meikel.sudoku.bl;

import JaCoP.constraints.XneqY;
import JaCoP.core.Store;
import JaCoP.core.IntVar;
import JaCoP.search.DepthFirstSearch;
import JaCoP.search.IndomainMin;
import JaCoP.search.InputOrderSelect;
import JaCoP.search.Search;
import JaCoP.search.SelectChoicePoint;
import com.sun.org.apache.xpath.internal.operations.Variable;

public class Game {

	private Model problem;
	private int n;
	private Store store;
	private IntVar[] variables;
	private Model[] solutions;

	public void setProblem(Model problem) {
		this.problem = problem;

		if (problem == null) {
			throw new IllegalStateException();
		}
		n = problem.getN();

		for (int sx = 1; sx <= n; sx++) {
			for (int sy = 1; sy <= n; sy++) {
				Square square = problem.getValue(sx, sy);
				if (square == null) {
					square = new Square(n);
					problem.setValue(sx, sy, square);
				}
			}
		}
	}

	public void solve() {
		store = new Store();
		n = problem.getN();
		variables = new IntVar[n * n * n * n];
		for (int sx = 1; sx <= n; sx++) {
			for (int sy = 1; sy <= n; sy++) {
				Square square = problem.getValue(sx, sy);
				for (int x = 1; x <= n; x++) {
					for (int y = 1; y <= n; y++) {
						int value = square.get(x, y);
						int min = 1;
						int max = n * n;
						if ((1 <= value) && (value <= n)) {
							min = value;
							max = value;
						}
						set(sx, sy, x, y, new IntVar(store, "v[" + sx + "]["
								+ sy + "][" + x + "][" + y + "]", min, max));
					}
				}
			}
		}

		// all values in a square must be different
		for (int sx = 1; sx <= n; sx++) {
			for (int sy = 1; sy <= n; sy++) {
				for (int x = 1; x <= n; x++) {
					for (int y = 1; y <= n; y++) {
						for (int i = 1; i <= n; i++) {
							for (int j = 1; j <= n; j++) {
								if ((x != i) || (y != j)) {
                                    IntVar v1 = get(sx, sy, x, y);
                                    IntVar v2 = get(sx, sy, i, j);
									XneqY xNeqY = new XneqY(v1, v2);
									store.impose(xNeqY);
								}
							}
						}
					}
				}
			}
		}

		// all values in a row must be different
		for (int sy = 1; sy <= n; sy++) {
			for (int y = 1; y <= n; y++) {
				for (int sx = 1; sx <= n; sx++) {
					for (int x = 1; x <= n; x++) {
						for (int i = 1; i <= n; i++) {
							for (int j = 1; j <= n; j++) {
								if ((sx != i) || (x != j)) {
                                    IntVar v1 = get(sx, sy, x, y);
                                    IntVar v2 = get(i, sy, j, y);
									XneqY xNeqY = new XneqY(v1, v2);
									store.impose(xNeqY);
								}
							}
						}
					}
				}
			}
		}

		// all values in a column must be different
		for (int sx = 1; sx <= n; sx++) {
			for (int x = 1; x <= n; x++) {
				for (int sy = 1; sy <= n; sy++) {
					for (int y = 1; y <= n; y++) {
						for (int i = 1; i <= n; i++) {
							for (int j = 1; j <= n; j++) {
								if ((i != sy) || (y != j)) {
                                    IntVar v1 = get(sx, sy, x, y);
                                    IntVar v2 = get(sx, i, x, j);
									XneqY xNeqY = new XneqY(v1, v2);
									store.impose(xNeqY);
								}
							}
						}
					}
				}
			}
		}

		Search search = new DepthFirstSearch();
		SelectChoicePoint select = new InputOrderSelect(store, variables,
				new IndomainMin());
		if (store.consistency()) {
			if (search.labeling(store, select)) {
				Model solution = new Model(n);
				for (int sx = 1; sx <= n; sx++) {
					for (int sy = 1; sy <= n; sy++) {
						Square square = new Square(n);
						solution.set(sx, sy, square);
						for (int x = 1; x <= n; x++) {
							for (int y = 1; y <= n; y++) {
								square.set(x, y, new Integer(get(sx, sy, x, y)
										.value()));
							}
						}
					}
				}
				solutions = new Model[] { solution };
				solution.print();
			}
		}

		if (solutions == null) {
			solutions = new Model[0];
		}
	}

	public Model[] getSolutions() {
		return solutions;
	}

	private void set(int sx, int sy, int x, int y, IntVar value) {
		variables[(sx - 1) * n * n + (sy - 1) * n * n * n + (x - 1) + (y - 1)
				* n] = value;
	}

	private IntVar get(int sx, int sy, int x, int y) {
		return variables[(sx - 1) * n * n + (sy - 1) * n * n * n + (x - 1)
				+ (y - 1) * n];
	}

}
