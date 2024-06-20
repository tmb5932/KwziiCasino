import java.util.Arrays;
import java.util.Random;

public class RubixCube {
    public enum Color {
        RED,
        BLUE,
        GREEN,
        ORANGE,
        WHITE,
        YELLOW
    }

    public enum RFace {
        FRONT,
        RIGHT,
        BACK,
        LEFT,
        TOP,
        BOTTOM
    }

    private Color[][][] cube;

    public RubixCube() {
        generateCube();
    }

    /**
     * Method to generate a new cube
     */
    public void generateCube() {
        cube = new Color[6][3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cube[0][i][j] = Color.RED;
                cube[1][i][j] = Color.BLUE;
                cube[2][i][j] = Color.ORANGE;
                cube[3][i][j] = Color.GREEN;
                cube[4][i][j] = Color.WHITE;
                cube[5][i][j] = Color.YELLOW;
            }
        }
    }

    /**
     * Method to randomize the cube
     */
    public void randomizeCube() {
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        for (int moves = rand.nextInt(25, 40); moves > 0; moves--) {
            int rotate = rand.nextInt(0, 12);
            if (rotate < 3) {
                makeVertRotation(rotate, true);
            } else if (rotate < 6) {
                makeHorzRotation(rotate - 3, true);
            } else if (rotate < 9) {
                makeHorzRotation(rotate - 6, false);
            } else {
                makeVertRotation(rotate - 9, false);
            }
        }
    }

    /**
     * Method to get a certain squares color from the of the cube
     * @param row the row of the square
     * @param col the column of the square
     * @return the Color enum value of that square
     */
    public Color getColor(RFace face, int row, int col) {
        if (row < 0 || col < 0 || row >= 3 || col >= 3) {
            System.out.println("INVALID INDEX FOR GET FRONT COLOR: " + row + ", " + col);
            return null;
        }
        return cube[face.ordinal()][row][col];
    }

    /**
     * Method to rotate cube vertically
     * @param col the column to rotate
     * @param topRotate true if rotating up, false if rotating down
     */
    public void makeVertRotation(int col, boolean topRotate) {
        if (col < 0 || col >= 3) {
            System.out.println("INVALID INDEX FOR COLUMN FOR VERT ROTATION: " + col);
            return;
        }

        Color[] temp = new Color[3];
        for (int i = 0; i < 3; i++) {
            temp[i] = cube[RFace.FRONT.ordinal()][i][col];
        }

        if (topRotate) {
            for (int i = 0; i < 3; i++) {
                cube[RFace.FRONT.ordinal()][i][col] = cube[RFace.BOTTOM.ordinal()][i][col];
            }
            for (int i = 0; i < 3; i++) {
                cube[RFace.BOTTOM.ordinal()][i][col] = cube[RFace.BACK.ordinal()][i][col];
            }
            for (int i = 0; i < 3; i++) {
                cube[RFace.BACK.ordinal()][i][col] = cube[RFace.TOP.ordinal()][i][col];
            }
            for (int i = 0; i < 3; i++) {
                cube[RFace.TOP.ordinal()][i][col] = temp[i];
            }
        } else {
            for (int i = 0; i < 3; i++) {
                cube[RFace.FRONT.ordinal()][i][col] = cube[RFace.TOP.ordinal()][i][col];
            }
            for (int i = 0; i < 3; i++) {
                cube[RFace.TOP.ordinal()][i][col] = cube[RFace.BACK.ordinal()][i][col];
            }
            for (int i = 0; i < 3; i++) {
                cube[RFace.BACK.ordinal()][i][col] = cube[RFace.BOTTOM.ordinal()][i][col];
            }
            for (int i = 0; i < 3; i++) {
                cube[RFace.BOTTOM.ordinal()][i][col] = temp[i];
            }
        }
    }

    /**
     * Method to rotate the Rubix Cube horizontally
     * @param row the row to rotate
     * @param rightRotate true if rotating right, false if rotating left
     */
    public void makeHorzRotation(int row, boolean rightRotate) {
        if (row < 0 || row >= 3) {
            System.out.println("INVALID INDEX FOR ROW FOR HORZ ROTATION: " + row);
            return;
        }

        Color[] temp;
        temp = cube[RFace.FRONT.ordinal()][row];

        if (rightRotate) {
            cube[RFace.FRONT.ordinal()][row] = cube[RFace.LEFT.ordinal()][row];

            cube[RFace.LEFT.ordinal()][row] = cube[RFace.BACK.ordinal()][row];

            cube[RFace.BACK.ordinal()][row] = cube[RFace.RIGHT.ordinal()][row];

            cube[RFace.RIGHT.ordinal()][row] = temp;
        } else {
            cube[RFace.FRONT.ordinal()][row] = cube[RFace.RIGHT.ordinal()][row];

            cube[RFace.RIGHT.ordinal()][row] = cube[RFace.BACK.ordinal()][row];

            cube[RFace.BACK.ordinal()][row] = cube[RFace.LEFT.ordinal()][row];

            cube[RFace.LEFT.ordinal()][row] = temp;
        }
    }

    /**
     * Method to print out the cube.
     * For testing purposes.
     */
    public void printCube() {
        System.out.println(Arrays.deepToString(cube[RFace.TOP.ordinal()]));
        System.out.print(Arrays.deepToString(cube[RFace.RIGHT.ordinal()]));
        System.out.print(Arrays.deepToString(cube[RFace.FRONT.ordinal()]));
        System.out.print(Arrays.deepToString(cube[RFace.LEFT.ordinal()]));
        System.out.println(Arrays.deepToString(cube[RFace.BACK.ordinal()]));
        System.out.print(Arrays.deepToString(cube[RFace.BOTTOM.ordinal()]));
    }
}