
class Solution {

    class Matrix {
        int row;
        int col;

        public Matrix(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getMulOpCount(Matrix m) {
            return row * col * m.col;
        }
    }

    int nrMat;
    Matrix[] matSizes = new Matrix[501];
    Matrix[][] mergedMat = new Matrix[501][501];

    public Solution(BufferedReader reader) throws IOException {
        nrMat = Integer.parseInt(reader.readLine());

        for (int i = 0; i < nrMat; i++) {
            String[] line = reader.readLine().split(" ");
            matSizes[i+1] = new Matrix(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
        }
    }

    public void run() {
        int answer = 0;
        // 매트릭스는 어쨌든 연속적인 순서로 곱해야 한다.
        // dp[i][j] = i번째부터 j번째까지의 행렬을 곱했을 때의 최소 연산 횟수
        // mergedMat = i번째부터 j번째까지의 행렬을 곱했을 때의 결과 행렬의 크기
        // dp[i][j] = min(dp[i][j], dp[i][x] + dp[x][j] + OPCount(dp[i][x] 에서 구해진 매트릭스 x dp[x+1][j] 에서 구해진 매트릭스))
        int[][] dp = new int[nrMat+1][nrMat+1];

        for(int i = 1 ; i <= nrMat ; i++) {
            mergedMat[i][i] = new Matrix(matSizes[i].row, matSizes[i].col);
            dp[i][i] = 0;
        }

        for(int i = 1 ; i < nrMat ; i++) {
            for(int j = 1 ; j + i <= nrMat ; j++) {
                int k = i + j;
                dp[j][k] = Integer.MAX_VALUE;

                for(int mid = j ; mid < k ; mid++) {
                    dp[j][k] = Math.min(dp[j][k], dp[j][mid] + dp[mid+1][k]  + mergedMat[j][mid].getMulOpCount(mergedMat[mid+1][k]));
                }
                mergedMat[j][k] = new Matrix(matSizes[j].row, matSizes[k].col);
            }
        }

        System.out.println(dp[1][nrMat]);
    }
}
