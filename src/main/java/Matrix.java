
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Matrix {
    private int MATRIX_1_ROWS = 20;
    private int MATRIX_1_COLS = 8;
    private int MATRIX_2_ROWS = MATRIX_1_COLS;
    private int MATRIX_2_COLS = 6;

    public Matrix() {
    }

    public Matrix(int MATRIX_1_ROWS, int MATRIX_1_COLS, int MATRIX_2_ROWS, int MATRIX_2_COLS) {
        this.MATRIX_1_ROWS = MATRIX_1_ROWS;
        this.MATRIX_1_COLS = MATRIX_1_COLS;
        this.MATRIX_2_ROWS = MATRIX_2_ROWS;
        this.MATRIX_2_COLS = MATRIX_2_COLS;
    }

    public void randomMatrix(int[][] matrix) {
        Random random = new Random();
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[i].length; ++j)
                matrix[i][j] = random.nextInt(100);
        }
    }

    private void printMatrix(FileWriter fileWriter,
                             int[][] matrix) throws IOException {
        String formatString = "%" + 10 + "d";
        for (int[] row : matrix) {
            for (int element : row)
                fileWriter.write(String.format(formatString, element));
            fileWriter.write("\n");
        }
    }


    public void printAllMatrix(String fileName,
                               int[][] matrix1,
                               int[][] matrix2,
                               int[][] matrixRes) {
        try (FileWriter fileWriter = new FileWriter(fileName, false)) {
            fileWriter.write("First matrix:\n");
            printMatrix(fileWriter, matrix1);

            fileWriter.write("\nSecond matrix:\n");
            printMatrix(fileWriter, matrix2);

            fileWriter.write("\nResult matrix:\n");
            printMatrix(fileWriter, matrixRes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * multiply in one thread
     *
     * @param matrix1
     * @param matrix2
     * @return res matrix
     */
    public int[][] checlMult(final int[][] matrix1,
                             final int[][] matrix2) {
        int rowCount = matrix1.length;
        int colCount = matrix2[0].length;
        int sumLength = matrix2.length;
        int[][] result = new int[rowCount][colCount];

        for (int row = 0; row < rowCount; ++row) {
            for (int col = 0; col < colCount; ++col) {
                int sum = 0;
                for (int i = 0; i < sumLength; ++i)
                    sum += matrix1[row][i] * matrix2[i][col];
                result[row][col] = sum;
            }
        }
        return result;
    }

    /**
     * multithreading
     *
     * @param matrix1
     * @param matrix2
     * @param threadCount thread count
     * @return result matrix
     */
    public int[][] multMatrThreads(int[][] matrix1,
                                   int[][] matrix2,
                                   int threadCount) {
        System.out.println("threadCount: " + threadCount);
        int rowCount = matrix1.length;
        int colCount = matrix2[0].length;
        int[][] result = new int[rowCount][colCount];

        int cellsForThread = (rowCount * colCount) / threadCount;
        System.out.println(cellsForThread);
        int firstIndex = 0;
        MultMatrix[] threads = new MultMatrix[threadCount];

        for (int i = 0; i < threadCount; i++) {
            int lastIndex = firstIndex + cellsForThread;
            if (i == threadCount - 1) {
                lastIndex = rowCount * colCount;
            }
            threads[i] = new MultMatrix(matrix1, matrix2, result, firstIndex, lastIndex);
            threads[i].start();
            firstIndex = lastIndex;
        }
        try {
            for (MultMatrix multiplierThread : threads)
                multiplierThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
