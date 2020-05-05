package com.example.myapplication;

import android.graphics.Point;

import java.util.ArrayList;

import Jama.Matrix;

import static java.lang.Math.*;

public class MatrixMath2 {

private MainActivity Main;
    private MainActivity.RoomImageView roomImageView;

    public double [][] main(ArrayList<Point> PointList, int KX, int KY) {
        int OX = 0; // начальная координата OX
        int OY = 0; // начальная координата OY
        // int KX = 1315; // конечная координата OX
        //  int KY = 1315; // конечная координата OY
        int h = 1; // шаг
        //int n = Main.Key; // количество маяков
        int k = 0; // счёт
        int[][] SatPos = new int[2][PointList.size()];
        double[][] H = new double [PointList.size()][2];
        double[][] D = new double [PointList.size()][2]; /// копия  H
        double[][] Gdop = new double[KX][KY];
        double[][] Mult = new double[PointList.size()][PointList.size()];


        for (int i = 0; i < PointList.size(); i++)
        {
            SatPos[0][i] = PointList.get(i).x;
            SatPos[1][i] = PointList.get(i).y;
        }

        double X = PointList.get(0).x;
        double Y = PointList.get(0).y;
        double Krat = 0;



        for (int x = 0; x < KX; x = x + h) {
            for (int y = 0; y < KY; y = y + h) {
                while (k < PointList.size()) {
                    Krat = ((x - X)/(sqrt(pow((x - X),2) + pow((y - Y),2))));
                    H[k][0] = ((x - SatPos[0][k])/(sqrt(pow((x - SatPos[0][k]),2) + pow((y - SatPos[1][k]),2)))) - ((x - X)/Krat);
                    H[k][0] = ((y - SatPos[1][k])/(sqrt(pow((x - SatPos[0][k]),2) + pow((y - SatPos[1][k]),2)))) - ((y - Y)/Krat);
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
