package at.meikel.sudoku.bl;

import JaCoP.constraints.XneqY;
import JaCoP.core.Store;
import JaCoP.core.IntVar;
import JaCoP.search.DepthFirstSearch;
import JaCoP.search.IndomainMin;
import JaCoP.search.InputOrderSelect;
import JaCoP.search.Search;
import JaCoP.search.SelectChoicePoint;

public class CopyOfGame {

	private Square problem;
	private int n;
	private Store store;
	private IntVar[] variables;
	private Square[] solutions;

	public void setProblem(Square problem) {
		this.problem = problem;

		if (problem == null) {
			throw new IllegalStateException();
		}
	}

	public void solve() {
		store = new Store();
		n = problem.getN();
		variables = new IntVar[n * n];
		for (int x = 1; x <= n; x++) {
			for (int y = 1; y <= n; y++) {
				int value = problem.get(x, y);
				int min = 1;
				int max = n*n;
				if ((1 <= value) && (value <= n)) {
					min = value;
					max = value;
				}
				set(x, y, new IntVar(store, "v[" + x + "][" + y + "]", min,
						max));
			}
		}

		// all values must be different
		for (int i = 1; i <= n * n; i++) {
			for (int j = 1; j <= n * n; j++) {
				if (i != j) {
					XneqY xNeqY = new XneqY(variables[i - 1], variables[j - 1]);
					store.impose(xNeqY);
				}
			}
		}

		Search search = new DepthFirstSearch();
		SelectChoicePoint select = new InputOrderSelect(store, variables,
				new IndomainMin());
		if (store.consistency()) {
			if (search.labeling(store, select)) {
				Square solution = new Square(n);
				for (int x = 1; x <= n; x++) {
					for (int y = 1; y <= n; y++) {
						solution.set(x, y, get(x, y).value());
					}
				}
				solutions = new Square[] { solution };
				// solution.print();
			}
		}

		if (solutions == null) {
			solutions = new Square[0];
		}
	}

	public Square[] getSolutions() {
		return solutions;
	}

	private void set(int x, int y, IntVar value) {
		variables[(x - 1) + (y - 1) * n] = value;
	}

	private IntVar get(int x, int y) {
		return variables[(x - 1) + (y - 1) * n];
	}
}
