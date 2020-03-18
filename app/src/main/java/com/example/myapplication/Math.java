package com.example.myapplication;

public class Math {
    public void main(String[] args) {
        int OX = 0; // начальная координата OX
        int OY = 0; // начальная координата OY
        int KX = 100; // конечная координата OX
        int KY = 100; // конечная координата OY
        int h = 5; // шаг
        int n = 3; // количество маяков
        int k = 0;
        int[][] SatPos = new int[n][2];
        double [][] R = new double [n][2];
        double[][] Grad = new double[KY - OY][KX - OX];
        if (n < 0) {
            System.exit(1); // or break;
        }
        for (int i = 0; i < KX; i = i + h) {
            for (int j = 0; j < KY; j = j + h) {
                while (k <= n) {
                    R[k][0] = ((i - SatPos[0][k]) / (java.lang.Math.sqrt(java.lang.Math.pow(i - SatPos[0][k], 2)) + java.lang.Math.sqrt(java.lang.Math.pow(j - SatPos[1][k], 20))));
                    R[k][1] = ((k - SatPos[1][k]) / (java.lang.Math.sqrt(java.lang.Math.pow(i - SatPos[0][k], 2)) + java.lang.Math.sqrt(java.lang.Math.pow(j - SatPos[1][k], 20))));
                    k += 1;
                }
                Grad[j + 1][i + 1] = java.lang.Math.sqrt(Trace(Multi(R, FunT(R))));

            }

        }


    }



/*------------------------------------------Траспонирование матрицы-----------------------------------------*/
public double [][] FunT(double[][] A) {
    int N = A.length;
    int M = A[0].length;
    double [][] An = new double [N][M];
    for (int i = 0; i < N; i++ )
        for (int j = i + 1; j < M; j++){
            double temp = An[i][j];
            An[i][j] = An[j][i];
            An[j][i] = temp;
        }
    return An;
}
/*------------------------------------------Перемножение матриц-----------------------------------------*/
public double[][] Multi(double[][] A, double[][] B) {
    int N = A.length;
    int M = A[0].length;
    int N1 = B.length;
    int M1 = B[0].length;
    if (M != N1) {
        System.exit(1);
    }
    double[][] An = new double[N][M1];
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M1; j++) {
            An[i][j] = 0;
            for (int s = 0; s < M; s++) {
                An[i][j] += A[i][s] * B[s][j];
            }
        }

    }
    return An;
}
 /*------------------------------------------Нахождение обратной матрицы-----------------------------------------*/
public double[][] АгтInv(double[][] A) {
    int N = A.length;
    double temp;
    double [][] An = new double [N][N];

    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
        {
            An[i][j] = 0d;

            if (i == j)
                An[i][j] = 1d;
        }

    for (int k = 0; k < N; k++)
    {
        temp = A[k][k];

        for (int j = 0; j < N; j++)
        {
            A[k][j] /= temp;
            An[k][j] /= temp;
        }

        for (int i = k + 1; i < N; i++)
        {
            temp = A[i][k];

            for (int j = 0; j < N; j++)
            {
                A[i][j] -= A[k][j] * temp;
                An[i][j] -= An[k][j] * temp;
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
                An[i][j] -= An[k][j] * temp;
            }
        }
    }

    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            A[i][j] = An[i][j];

    return A;
}
/*------------------------------------------Trace-----------------------------------------*/
public double Trace(double [][] A)  {
    int N = A.length;
    double Sum = 0;
    double [][] An = new double [N][N];
    for(int i = 0; i < N; i ++)
        for(int j = 0; j < N; j ++)
            Sum += An[i][i];

    return Sum;
}

}

