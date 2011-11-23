package at.meikel.sudoku.bl;

import org.junit.After;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class BusinessLogicTest {

    private Game game;

    @Before
    public void setUp() throws Exception {
        game = new Game();
    }

    @After
    public void tearDown() throws Exception {
        game = null;
    }

    @Test
    public void testSquare() {
        Square square = new Square(3);
        square.set(1, 2, 9);
        square.set(2, 1, 8);
        square.set(2, 3, 6);
        square.set(3, 2, 2);

        assertEquals(0, square.get(1, 1));
        assertEquals(9, square.get(1, 2));
        assertEquals(0, square.get(1, 3));

        assertEquals(8, square.get(2, 1));
        assertEquals(0, square.get(2, 2));
        assertEquals(6, square.get(2, 3));

        assertEquals(0, square.get(3, 1));
        assertEquals(2, square.get(3, 2));
        assertEquals(0, square.get(3, 3));
    }

    @Test
    public void testBL() {
        Model problem = new Model(3);

        Square square = new Square(3);
        square.set(1, 2, 9);
        square.set(2, 1, 8);
        square.set(2, 3, 6);
        square.set(3, 2, 2);
        problem.set(1, 1, square);

        square = new Square(3);
        square.set(1, 2, 3);
        square.set(1, 3, 5);
        square.set(2, 1, 2);
        square.set(2, 3, 4);
        square.set(3, 1, 7);
        square.set(3, 3, 8);
        problem.set(1, 2, square);

        square = new Square(3);
        square.set(1, 2, 2);
        square.set(2, 1, 3);
        square.set(2, 3, 1);
        square.set(3, 2, 4);
        problem.set(1, 3, square);

        square = new Square(3);
        square.set(1, 2, 6);
        square.set(1, 2, 1);
        square.set(1, 3, 4);
        square.set(3, 1, 2);
        square.set(3, 2, 7);
        square.set(3, 3, 5);
        problem.set(2, 1, square);

        square = new Square(3);
        problem.set(2, 2, square);

        square = new Square(3);
        square.set(1, 1, 5);
        square.set(1, 2, 3);
        square.set(1, 3, 7);
        square.set(3, 1, 4);
        square.set(3, 2, 8);
        square.set(3, 3, 9);
        problem.set(2, 3, square);

        square = new Square(3);
        square.set(1, 2, 4);
        square.set(2, 1, 1);
        square.set(2, 3, 3);
        square.set(3, 2, 6);
        problem.set(3, 1, square);

        square = new Square(3);
        square.set(1, 1, 6);
        square.set(3, 2, 1);
        square.set(2, 1, 4);
        square.set(2, 3, 9);
        square.set(3, 1, 8);
        square.set(3, 3, 3);
        problem.set(3, 2, square);

        square = new Square(3);
        square.set(1, 2, 9);
        square.set(2, 1, 7);
        square.set(2, 3, 2);
        square.set(3, 2, 5);
        problem.set(3, 3, square);

        problem.print();

        game.setProblem(problem);
        game.solve();

        Model[] solutions = game.getSolutions();
        assertNotNull(solutions);
        assertEquals(1, solutions.length);
        assertTrue(contains(solutions, Model.fromArray(new int[]{
                4, 8, 3, 6, 9, 2, 5, 1, 7,
                9, 5, 2, 1, 3, 7, 4, 8, 6,
                7, 6, 1, 4, 8, 5, 2, 3, 9,
                3, 2, 7, 9, 5, 1, 6, 4, 8,
                1, 9, 6, 8, 4, 3, 7, 5, 2,
                5, 4, 8, 2, 7, 6, 1, 9, 3,
                6, 3, 9, 5, 2, 4, 8, 7, 1,
                2, 7, 4, 3, 1, 8, 9, 6, 5,
                8, 1, 5, 7, 6, 9, 3, 2, 4})));
    }

    private boolean contains(Model[] solutions, Model model) {
        if ((solutions == null) || (model == null)) {
            return false;
        }

        for (Model solution : solutions) {
            if (model.equals(solution)) {
                return true;
            }
        }
        return false;
    }
}
