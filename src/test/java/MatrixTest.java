import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {
    @Test
    void compareMatrix() {
        int[][] matrix1 = new int[10][10];    // Первая (левая) матрица.
        int[][] matrix2 = new int[10][10];  // Вторая (правая) матрица.
        Matrix matrix = new Matrix(10,10,10,10);
        matrix.randomMatrix(matrix1);
        matrix.randomMatrix(matrix2);
        int[][] result = matrix.multMatrThreads(matrix1, matrix2, matrix1[0].length);
        int[][] check = matrix.checlMult(matrix1, matrix2);
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                assertEquals(result[i][j], check[i][j]);
            }
        }
        matrix.printAllMatrix("MatrixRes.txt", matrix1, matrix2, result);
    }
}