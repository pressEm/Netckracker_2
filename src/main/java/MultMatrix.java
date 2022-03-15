public class MultMatrix extends Thread {

    private final int[][] firstMatrix;
    private final int[][] secondMatrix;
    private final int[][] resultMatrix;
    private final int firstIndex;
    private final int lastIndex;

    /**
     * @param firstMatrix
     * @param secondMatrix
     * @param resultMatrix
     * @param firstIndex index of start calculate
     * @param lastIndex index of end calculate
     */
    public MultMatrix(final int[][] firstMatrix,
                                 final int[][] secondMatrix,
                                 final int[][] resultMatrix,
                                 final int firstIndex,
                                 final int lastIndex) {
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.resultMatrix = resultMatrix;
        this.firstIndex = firstIndex;
        this.lastIndex = lastIndex;
    }

    private void calcValue(final int row, final int col) {
        int sum = 0;
        for (int i = 0; i <  secondMatrix.length; ++i)
            sum += firstMatrix[row][i] * secondMatrix[i][col];
        resultMatrix[row][col] = sum;
    }

    @Override
    public void run() {
        System.out.println(getName() + " | " + firstIndex + " - " + lastIndex + " |");

        final int colCount = secondMatrix[0].length;
        for (int index = firstIndex; index < lastIndex; ++index)
            calcValue(index / colCount, index % colCount);

        System.out.println(getName() + " finished");
    }


}
