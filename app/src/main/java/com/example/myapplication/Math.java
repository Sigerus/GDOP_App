package com.example.myapplication;

import android.graphics.Point;

import java.util.ArrayList;

public class Math {

private MainActivity Main;
    private MainActivity.PointImageView pointImageView;

    public double[][] main(ArrayList<Point> PointList) {
        int OX = 0; // начальная координата OX
        int OY = 0; // начальная координата OY
        int KX = 100; // конечная координата OX
        int KY = 100; // конечная координата OY
        int h = 1; // шаг
        //int n = Main.Key; // количество маяков
        int k = 0; // счёт
        int[][] SatPos = new int[2][PointList.size()];
        double[][] H = new double [PointList.size()][2];
        double[][] Gdop = new double[KY - OY][KX - OX];



        for (int i = 0; i < PointList.size(); i++)
        {
            SatPos[0][i] = PointList.get(i).x;
            SatPos[1][i] = PointList.get(i).y;
        }


        /*if (n < 0) {
            System.exit(1); // or break;
        }*/
        for (int x = 0; x < KX; x = x + h) {
            for (int y = 0; y < KY; y = y + h) {
                while (k < PointList.size()) {
                    H[k][0] = ((x - SatPos[0][k]) / (java.lang.Math.sqrt(java.lang.Math.pow(x - SatPos[0][k], 2)) + java.lang.Math.sqrt(java.lang.Math.pow(y - SatPos[1][k], 2))));
                    H[k][1] = ((y - SatPos[1][k]) / (java.lang.Math.sqrt(java.lang.Math.pow(x - SatPos[0][k], 2)) + java.lang.Math.sqrt(java.lang.Math.pow(y - SatPos[1][k], 2))));
                    k += 1;
                }
                k = 0;
                Gdop[y][x] = java.lang.Math.sqrt(Trace(АгтInv(Multi(H, FunT(H)))));

            }

        }
        return Gdop;

    }


/*------------------------------------------Траспонирование матрицы-----------------------------------------*/
public double [][] FunT(double[][] A) {
    int Col = A.length; //количество строк
    int Row = A[0].length; //количество столбцов в i-той строке
    double [][] An = new double [Row][Col];

    for(int i = 0; i < Col; ++i)
        for(int j = 0; j < Row; ++j)
            An[j][i] = A[i][j];


        return An;

}

/*------------------------------------------Перемножение матриц-----------------------------------------*/
public double[][] Multi(double[][] A, double[][] B) {
    int N = A.length;
    int M = A[0].length;
    int N1 = B.length;
    int M1 = B[0].length;
    //if (M != N1) {
     //   System.exit(1);
    //}
    double[][] An = new double[N][M1];
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M1; j++) {
            An[i][j] = 0;
            for (int s = 0; s < N1; s++) {
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

