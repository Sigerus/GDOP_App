package com.example.myapplication;

import android.graphics.Point;

import java.util.ArrayList;

import Jama.Matrix;

public class ToF_Method {

private MainActivity Main;
    private MainActivity.RoomImageView roomImageView;

    public double [][] main(ArrayList<Point> PointList,int KX,int KY) {
        int OX = 0; // начальная координата OX
        int OY = 0; // начальная координата OY
        int h = 1; // шаг
        int k = 0; // счёт
        int[][] SatPos = new int[2][PointList.size()];
        double[][] H = new double [PointList.size()][2];
        double[][] Gdop = new double[KX][KY];



        for (int i = 0; i < PointList.size(); i++)
        {
            SatPos[0][i] = PointList.get(i).x;
            SatPos[1][i] = PointList.get(i).y;
        }


        for (int x = 0; x < KX; x = x + h) {
            for (int y = 0; y < KY; y = y + h) {
                while (k < PointList.size()) {
                    H[k][0] = ((x - SatPos[0][k]) / (java.lang.Math.sqrt(java.lang.Math.pow(x - SatPos[0][k], 2)) + java.lang.Math.sqrt(java.lang.Math.pow(y - SatPos[1][k], 2))));
                    H[k][1] = ((y - SatPos[1][k]) / (java.lang.Math.sqrt(java.lang.Math.pow(x - SatPos[0][k], 2)) + java.lang.Math.sqrt(java.lang.Math.pow(y - SatPos[1][k], 2))));
                    k += 1;
                }
                k = 0;
                Matrix A = new Matrix(H);
                Gdop[x][y] = Math.sqrt(((A.transpose().times(Matrix.constructWithCopy(H))).inverse()).trace());

                //Gdop[x][y] = java.lang.Math.sqrt(Trace(invert(Multi(H, FunT(H)))));

            }

        }

        return Gdop;

    }

}

