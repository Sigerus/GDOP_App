package com.example.myapplication;
/*
public class Math {
    public static void main(String[] args) {
        int OX = 0; // начальная координата OX
        int OY = 0; // начальная координата OY
        int KX = 100; // конечная координата OX
        int KY = 100; // конечная координата OY
        int h = 5; // шаг
        int n = 3; // количество маяков
        int k = 0;
        int[][] SatPos = new int[n][2];
        int[][] R = new int[n][2];
        int[][] Grad = new int[KY - OY][KX - OX];
        if (n < 0) {
            System.exit(1); // or break;
        }
        for (int i = 0; i < KX; i = i + h) {
            for (int j = 0; j < KY; j = j + h) {
                while (k <= n) {
                    R[k][0] = (int) ((i - SatPos[0][k]) / (java.lang.Math.sqrt(java.lang.Math.pow(i - SatPos[0][k], 2)) + java.lang.Math.sqrt(java.lang.Math.pow(j - SatPos[1][k], 20))));
                    R[k][1] = (int) ((k - SatPos[1][k]) / (java.lang.Math.sqrt(java.lang.Math.pow(i - SatPos[0][k], 2)) + java.lang.Math.sqrt(java.lang.Math.pow(j - SatPos[1][k], 20))));
                    k += 1;
                }
                Grad[j + 1][i + 1] = java.lang.Math.sqrt(R.invert)

            }

        }


    }
}
/**
    private int[][] backInBlack(int[][] matr,int length) {
        float[][] backMatr = new float[length] [2];
        for (int i=0; i<length; i++)
            for (int j = 0; j < 2; j++)
            {
                if (i == j)
                    backMatr[i][j] = 1;
                else
                    backMatr[i][j] = 0;
            }

        double p = 0, q = 0, s =0;
        for (int i = 0; i < length; i++)
        {
            p = matr[i] [i];
            for (int j = i + 1; j < length; j++)
            {
                q = matr[j] [i];
                for (int k = 0; k < length; k++)
                {
                    matr[j] [k] = (float) (matr[i] [k] * q - matr[j] [k] * p);
                    backMatr[j] [k] = (float) (backMatr[i][k] * q - backMatr[j][k] * p);
                }
            }
        }
        for (int i = 0; i < length; i++)
        {
            for (int j = length - 1; j >= 0; j--)
            {
                s = 0;
                for (int k = length - 1; k > j; k--)
                    s += matr[j][ k] * backMatr[k][i];
                backMatr[j][i] = (float) ((backMatr[j][i] - s) / matr[j][j]);
            }
        }
        return backMatr;
    }

}
*/

/**
    public static double determinant(double[][] input) {
        int rows = nRows(input);        //number of rows in the matrix
        int columns = nColumns(input); //number of columns in the matrix
        double determinant = 0;

        if ((rows== 1) && (columns == 1)) return input[0][0];

        int sign = 1;
        for (int column = 0; column < columns; column++) {
            double[][] submatrix = getSubmatrix(input, rows, columns,column);
            determinant = determinant + sign*input[0][column]*determinant(submatrix);
            sign*=-1;
        }
        return determinant;
    }*/


/**
    void inversion(float [][]A, int N)
    {
        double temp;

        float [][] E = new float [N][N];


        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            {
                E[i][j] = 0f;

                if (i == j)
                    E[i][j] = 1f;
            }

        for (int k = 0; k < N; k++)
        {
            temp = A[k][k];

            for (int j = 0; j < N; j++)
            {
                A[k][j] /= temp;
                E[k][j] /= temp;
            }

            for (int i = k + 1; i < N; i++)
            {
                temp = A[i][k];

                for (int j = 0; j < N; j++)
                {
                    A[i][j] -= A[k][j] * temp;
                    E[i][j] -= E[k][j] * temp;
                }
            }
        }

        for (int k = N - 1; k > 0; k--)
        {
            for (int i = k - 1; i >= 0; i--)
            {
                temp = A[i][k];

                for (int j = 0; j < N; j++)
                {
                    A[i][j] -= A[k][j] * temp;
                    E[i][j] -= E[k][j] * temp;
                }
            }
        }

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                A[i][j] = E[i][j];

    }*/






