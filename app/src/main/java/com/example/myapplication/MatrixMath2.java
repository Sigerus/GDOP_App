package com.example.myapplication;

public class MatrixMath2 {
        private int nrows;
        private int ncols;
        private double[][] data;

        public MatrixMath2(double[][] dat) {
            this.data = dat;
            this.nrows = dat.length;
            this.ncols = dat[0].length;
        }

        public MatrixMath2(int nrow, int ncol) {
            this.nrows = nrow;
            this.ncols = ncol;
            data = new double[nrow][ncol];
        }
    public int getNrows() {
        		return nrows;
         	}

        	public void setNrows(int nrows) {
        		this.nrows = nrows;
        	}

        	public int getNcols() {
        		return ncols;
         	}

          	public void setNcols(int ncols) {
          		this.ncols = ncols;
         	}

          	public double[][] getValues() {
          		return data;
          	}

          	public void setValues(double[][] values) {
          		this.data = values;
          	}

           	public void setValueAt(int row, int col, double value) {
         		data[row][col] = value;
         	}

           	public double getValueAt(int row, int col) {
          		return data[row][col];
          	}

          	public boolean isSquare() {
          		return nrows == ncols;
          	}

        	public int size() {
         		if (isSquare())
              			return nrows;
        		return -1;
          	}

           	public MatrixMath2 multiplyByConstant(double constant) {
                MatrixMath2 mat = new MatrixMath2(nrows, ncols);
          		for (int i = 0; i < nrows; i++) {
            			for (int j = 0; j < ncols; j++) {
                				mat.setValueAt(i, j, data[i][j] * constant);
                  			}
             		}
        		return mat;
        	}

  	public MatrixMath2 insertColumnWithValue1() {
        MatrixMath2 X_ = new MatrixMath2(this.getNrows(), this.getNcols() + 1);
        for (int i = 0; i < X_.getNrows(); i++) {
            for (int j = 0; j < X_.getNcols(); j++) {
                if (j == 0)
                    X_.setValueAt(i, j, 1.0);
                else
                    X_.setValueAt(i, j, this.getValueAt(i, j - 1));

            }
        }
        return X_;
    }


        public static MatrixMath2 transpose(MatrixMath2 matrix) {
            MatrixMath2 transposedMatrix = new MatrixMath2(matrix.getNcols(), matrix.getNrows());
        for (int i=0;i<matrix.getNrows();i++) {
            for (int j=0;j<matrix.getNcols();j++) {
                transposedMatrix.setValueAt(j, i, matrix.getValueAt(i, j));
            }
        }
        return transposedMatrix;
    }
  /*  double[][] SatPos;//Массив с координатами маяков вида [x1, x2, ... , xn]
    //                                  [y1, y2, ... , yn]
    double[][] SatClone;
    double[][] Grad;//Градиентная матрица
    double[][] Z;//Матрица со значениеми геометрического фактора в каждой точке помещения
    double[][] Tran;//Транспонированная матрица вида [x1, y1]
    //[x2, y2]
    //[......]
    //[xn, yn]
    double[][] Multi;//Перемноженная матрицад для расчетов

    private void Transp(int N)//Транспонированная матрица
    {
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < 2; j++)
            {
                Tran[j][i] = Grad[i][j];
            }
        }
    }
    private void multi(int kol)//Перемножение матриц
    {
        for (int i = 0; i < 2; i++)
        {
            for (int j = 0; j < 2; j++)
            {
                Multi[i][j] = 0;
                for (int k = 0; k < kol; k++)
                {
                    Multi[i][j] += Tran[i][k] * Grad[k][j];
                }
            }
        }
    }
    private void inversionMatrix(int N)//Обратная матрица
    {
        double temp;
        double[][] B = new double[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            {
                B[i][j] = 0.0;

                if (i == j)
                    B[i][j] = 1.0f;
            }
        for (int k = 0; k < N; k++)
        {
            temp = Multi[k][k];
            for (int j = 0; j < N; j++)
            {
                Multi[k][j] /= temp;
                B[k][j] /= temp;
            }
            for (int i = k + 1; i < N; i++)
            {
                temp = Multi[i][k];
                for (int j = 0; j < N; j++)
                {
                    Multi[i][j] -= Multi[k][j] * temp;
                    B[i][j] -= B[k][j] * temp;
                }
            }
        }
        for (int k = N - 1; k > 0; k--)
        {
            for (int i = k - 1; i >= 0; i--)
            {
                temp = Multi[i][k];
                for (int j = 0; j < N; j++)
                {
                    Multi[i][j] -= Multi[k][j] * temp;
                    B[i][j] -= B[k][j] * temp;
                }
            }
        }
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                Multi[i][j] = B[i][j];
            }
        }
    }

    private double trace(int N)//Расчет GDOP(Матрица Z)
    {
        double trac = 0;
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                if (i == j)
                {
                    trac += Multi[i][j];
                }
            }
        }
        trac = Math.sqrt(trac);
        return trac;
    }
    private void D(int kol1)//Решение для дальномерного метода
    {
        SatClone = new double[2, beaconqunt + 1];
        for (int h = 0; h < beaconqunt; h++)
        {
            SatClone[0][h] = SatPos[0][h];
            SatClone[1][h] = SatPos[1][h];
        }
        BoxClone = new double[2][uglqunt + 1];
        for (int h = 0; h < uglqunt; h++)
        {
            BoxClone[0][h] = BoxPos[0][h];
            BoxClone[1][h] = BoxPos[1][h];
        }
        Z = new double[1000, 1000];
        int i = 0;
        for (int x = 0; x < 1000; x++)
        {
            for (int y = 0; y < 1000; y++)
            {
                if (walluqnt == 0)
                    vidimost(x, y);
                else
                    vidimostwall(x, y);
                kol1 = kolich;
                if (kol1 < 2)
                    Z[x][y] = 0;
                    else
                {
                    while (i < kol1)
                    {
                        Grad[i][0] = (x - SatPos[0][i]) / (Math.sqrt(Math.pow((x - SatPos[0][i]), 2) + Math.pow((y - SatPos[1][i]), 2)));
                        Grad[i][1] = (y - SatPos[1][i]) / (Math.sqrt(Math.pow((x - SatPos[0][i]), 2) + Math.pow((y - SatPos[1][i]), 2)));
                        i += 1;
                    }
                    Transp(kol1);
                    multi(kol1);
                    inversionMatrix(2);
                    Z[x][y] = trace(2);
                }
                i = 0;
                kol1 = beaconqunt;
                SatPos = new double[2, beaconqunt + 1];
                for (int h = 0; h < beaconqunt; h++)
                {
                    SatPos[0][h] = SatClone[0][h];
                    SatPos[1][h] = SatClone[1][h];
                }

            }
        }
    }*/
}
