package com.example.myapplication;

import android.graphics.Point;

import java.util.ArrayList;

import Jama.Matrix;

import static java.lang.Math.*;

public class TDoA_Method {

private MainActivity Main;
   // private MainActivity.RoomImageView roomImageView;

    public double [][] main(ArrayList<Point> PointList, int KX, int KY) {
        int OX = 0; // начальная координата OX
        int OY = 0; // начальная координата OY
        int h = 1; // шаг
        int k = 0; // счёт
        int[][] SatPos = new int[2][PointList.size()];
        double[][] H = new double [PointList.size() - 1][2];
        double[][] Gdop = new double[KX][KY];


        for (int i = 0; i < PointList.size(); i++)
        {
            SatPos[0][i] = PointList.get(i).x;
            SatPos[1][i] = PointList.get(i).y;
        }

        double X = PointList.get(PointList.size() - 1).x;
        double Y = PointList.get(PointList.size() - 1).y;
        double SQRT1 = 0;
        double SQRT2 = 0;
        for (int x = 0; x < KX; x = x + h) {
            for (int y = 0; y < KY; y = y + h) {
                while (k < PointList.size() - 1) {
                    SQRT1 = (sqrt(pow((x - SatPos[0][k]),2) + pow((y - SatPos[1][k]),2)));
                    SQRT2 = (sqrt(pow((x - X),2) + pow((y - Y),2)));
                    H[k][0] = (((x - SatPos[0][k])/SQRT1) - ((x - X)/SQRT2));
                    H[k][1] = (((y - SatPos[1][k])/SQRT1) - ((y - Y)/SQRT2));
//                    H[k][0] = (((x - SatPos[0][k])/(sqrt(pow((x - SatPos[0][k]),2) + pow((y - SatPos[1][k]),2)))) - ((x - X)/(sqrt(pow((x - X),2) + pow((y - Y),2)))));
//                    H[k][1] = (((y - SatPos[1][k])/(sqrt(pow((x - SatPos[0][k]),2) + pow((y - SatPos[1][k]),2)))) - ((y - Y)/(sqrt(pow((x - X),2) + pow((y - Y),2)))));
//                    H[k][0] = (((x - SatPos[0][k])/(sqrt(pow((x - SatPos[0][k]),2) + pow((y - SatPos[1][k]),2)))) - ((x - SatPos[0][0])/(sqrt(pow((x - SatPos[0][0]),2) + pow((y - SatPos[1][0]),2)))));
//                    H[k][1] = (((y - SatPos[1][k])/(sqrt(pow((x - SatPos[0][k]),2) + pow((y - SatPos[1][k]),2)))) - ((y - SatPos[1][0])/(sqrt(pow((x - SatPos[0][0]),2) + pow((y - SatPos[1][0]),2)))));
                    k += 1;

                }
                k = 0;
                Matrix A = new Matrix(H);
                Gdop[x][y] = sqrt(((A.transpose().times(Matrix.constructWithCopy(H))).inverse()).trace());

                //Gdop[x][y] = java.lang.Math.sqrt(Trace(invert(Multi(H, FunT(H)))));

            }

        }
        return Gdop;

    }
}
