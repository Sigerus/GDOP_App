package com.example.myapplication;

import android.graphics.Point;

import java.util.ArrayList;

public class Math {

private MainActivity Main;
    private MainActivity.PointImageView pointImageView;

    public double [][] main(ArrayList<Point> PointList) {
        int OX = 0; // начальная координата OX
        int OY = 0; // начальная координата OY
        int KX = 1315; // конечная координата OX
        int KY = 1315; // конечная координата OY
        int h = 1; // шаг
        //int n = Main.Key; // количество маяков
        int k = 0; // счёт
        int[][] SatPos = new int[2][PointList.size()];
        double[][] H = new double [PointList.size()][2];
        double[][] Gdop = new double[1315][1315];



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
                Gdop[y][x] = java.lang.Math.sqrt(Trace(invert(Multi(H, FunT(H)))));
                //return Gdop[y][x];

            }

        }
        return Gdop;

    }


/*------------------------------------------Траспонирование матрицы-----------------------------------------*/
public static double [][] FunT(double[][] A) {
    int Col = A.length; //количество строк
    int Row = A[0].length; //количество столбцов в i-той строке
    double [][] An = new double [Row][Col];

    for(int i = 0; i < Col; ++i)
        for(int j = 0; j < Row; ++j)
            An[j][i] = A[i][j];


        return An;

}

/*------------------------------------------Перемножение матриц-----------------------------------------*/
public static double[][] Multi(double[][] A, double[][] B) {
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
/*public double[][] АгтInv(double[][] A) {
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
}*/

    public static double[][] invert(double a[][])
    {
        int n = a.length;
        double x[][] = new double[n][n];
        double b[][] = new double[n][n];
        int index[] = new int[n];
        for (int i=0; i<n; ++i)
            b[i][i] = 1;

        // Ебашим гаусом
        gaussian(a, index);

        // Update the matrix b[i][j] with the ratios stored
        for (int i=0; i<n-1; ++i)
            for (int j=i+1; j<n; ++j)
                for (int k=0; k<n; ++k)
                    b[index[j]][k] -= a[index[j]][i]*b[index[i]][k];

        // Perform backward substitutions
        for (int i=0; i<n; ++i)
        {
            x[n-1][i] = b[index[n-1]][i]/a[index[n-1]][n-1];
            for (int j=n-2; j>=0; --j)
            {
                x[j][i] = b[index[j]][i];
                for (int k=j+1; k<n; ++k)
                {
                    x[j][i] -= a[index[j]][k]*x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        return x;
    }

// Method to carry out the partial-pivoting Gaussian
// elimination.  Here index[] stores pivoting order.

    public static void gaussian(double a[][], int index[])
    {
        int n = index.length;
        double c[] = new double[n];

        // Initialize the index
        for (int i=0; i<n; ++i)
            index[i] = i;

        // Find the rescaling factors, one from each row
        for (int i=0; i<n; ++i)
        {
            double c1 = 0;
            for (int j=0; j<n; ++j)
            {
                double c0 = java.lang.Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }

        // Search the pivoting element from each column
        int k = 0;
        for (int j=0; j<n-1; ++j)
        {
            double pi1 = 0;
            for (int i=j; i<n; ++i)
            {
                double pi0 = java.lang.Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1)
                {
                    pi1 = pi0;
                    k = i;
                }
            }

            // Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i=j+1; i<n; ++i)
            {
                double pj = a[index[i]][j]/a[index[j]][j];

                // Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;

                // Modify other elements accordingly
                for (int l=j+1; l<n; ++l)
                    a[index[i]][l] -= pj*a[index[j]][l];
            }
        }
    }

    /*------------------------------------------Trace-----------------------------------------*/
public static double Trace(double [][] A)  {
    int N = A.length;
    double Sum = 0;
    double [][] An = new double [N][N];
    for(int i = 0; i < N; i++)
        for(int j = 0; j < N; j++)
            Sum += An[i][i];

    return Sum;
}

}

