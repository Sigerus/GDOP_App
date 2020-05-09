package com.example.myapplication;

import java.lang.Object;

import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Handler;

import java.util.ArrayList;

import Jama.Matrix;

public class MatrixMath {

    private MainActivity Main;
    private MainActivity.RoomImageView roomImageView;

    public double[][] main(ArrayList<Point> PointList,int OX,int OY, int KX, int KY) {
        //int OX = 0; // начальная координата OX
        //int OY = 0; // начальная координата OY
        // int KX = 1315; // конечная координата OX
        //  int KY = 1315; // конечная координата OY
        int h = 1; // шаг
        //int n = Main.Key; // количество маяков
        int k = 0; // счёт
        int[][] SatPos = new int[2][PointList.size()];
        double[][] H = new double[PointList.size()][2];
        double[][] D = new double[PointList.size()][2]; /// копия  H
        double[][] Gdop = new double[KX][KY];
        double[][] Mult = new double[PointList.size()][PointList.size()];

        for (int i = 0; i < PointList.size(); i++) {
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
                D = H;
                Matrix A = new Matrix(H);
                Gdop[x][y] = Math.sqrt(((A.transpose().times(Matrix.constructWithCopy(H))).inverse()).trace());

                //Gdop[x][y] = java.lang.Math.sqrt(Trace(invert(Multi(H, FunT(H)))));
            }
        }
        /*
        Thread t;
        t = new Thread(new GDOPThread(Gdop, 0, 0, KX, KY / 2, PointList));
        t.setName("Поток " + 1);
        t.start();
        Thread t2;
        t2 = new Thread(new GDOPThread(Gdop, 0, KY / 2, KX, KY, PointList));
        t2.setName("Поток " + 2);
        t2.start();
   /*     Thread t3;
        t3 = new Thread(new GDOPThread(Gdop, KX/2, 0, KX, KY/2 , PointList));
        t3.setName("Поток " + 2);
        t3.start();
        Thread t4;
        t4 = new Thread(new GDOPThread(Gdop, KY/2, KY/2, KX, KY, PointList));
        t4.setName("Поток " + 2);
        t4.start();*/
        /*if (n < 0) {
            System.exit(1); // or break;
        }*/

        return Gdop;
    }

    class GDOPThread implements Runnable {
        int StartX, StartY, FinishX, FinishY;
        int h = 1; // шаг
        //int n = Main.Key; // количество маяков
        int k = 0; // счёт
        int[][] SatPos;// = new int[2][PointList.size()];
        int satPosCount;
        double[][] H;// = new double[PointList.size()][2];
        double[][] D;// = new double[PointList.size()][2]; /// копия  H
        double[][] Mult;// = new double[PointList.size()][PointList.size()];
        double[][] res;//результирующая матрица GDOP

        GDOPThread(double[][] res, int startX, int startY, int finishX, int finishY, ArrayList<Point> pointList) {
            StartX = startX;
            StartY = startY;
            FinishX = finishX;
            FinishY = finishY;
            satPosCount = pointList.size();
            SatPos = new int[2][satPosCount];
            for (int i = 0; i < pointList.size(); i++) {
                SatPos[0][i] = pointList.get(i).x;
                SatPos[1][i] = pointList.get(i).y;
            }
            H = new double[satPosCount][2];
            D = new double[satPosCount][2];
            Mult = new double[satPosCount][satPosCount];
            this.res = res;
        }

        @Override
        public void run() {
            // synchronized (res) {
            for (int x = StartX; x < FinishX; x = x + h) {
                for (int y = StartY; y < FinishY; y = y + h) {
                    while (k < satPosCount) {
                        H[k][0] = ((x - SatPos[0][k]) / (java.lang.Math.sqrt(java.lang.Math.pow(x - SatPos[0][k], 2)) + java.lang.Math.sqrt(java.lang.Math.pow(y - SatPos[1][k], 2))));
                        H[k][1] = ((y - SatPos[1][k]) / (java.lang.Math.sqrt(java.lang.Math.pow(x - SatPos[0][k], 2)) + java.lang.Math.sqrt(java.lang.Math.pow(y - SatPos[1][k], 2))));
                        k += 1;
                    }
                    k = 0;
                    D = H;
                    Matrix A = new Matrix(H);
                    res[x][y] = Math.sqrt(((A.transpose().times(Matrix.constructWithCopy(H))).inverse()).trace());

                }
            }
            //}
        }
    }
  /*  public class CalcThread extends Thread
    {
        int StartX,StartY,FinishX,FinishY;
        int h = 1; // шаг
        //int n = Main.Key; // количество маяков
        int k = 0; // счёт
        int[][] SatPos;// = new int[2][PointList.size()];
        int satPosCount;
        double[][] H;// = new double[PointList.size()][2];
        double[][] D;// = new double[PointList.size()][2]; /// копия  H
        double[][] Gdop;// = new double[KX][KY];
        double[][] Mult;// = new double[PointList.size()][PointList.size()];

        public CalcThread(int startX, int startY, int finishX, int finishY,ArrayList<Point> pointList) {
            StartX = startX;
            StartY = startY;
            FinishX = finishX;
            FinishY = finishY;
            satPosCount=pointList.size();
            SatPos = new int[2][satPosCount];
            H = new double[satPosCount][2];
            D = new double[satPosCount][2];
            Gdop = new double[finishX-startX][finishY-startY];
            Mult = new double[satPosCount][satPosCount];
        }

        @Override
        public void run() {

            for (int x = StartX; x < FinishX; x = x + h) {
                for (int y = StartY; y < FinishY; y = y + h) {
                    while (k < satPosCount) {
                        H[k][0] = ((x - SatPos[0][k]) / (java.lang.Math.sqrt(java.lang.Math.pow(x - SatPos[0][k], 2)) + java.lang.Math.sqrt(java.lang.Math.pow(y - SatPos[1][k], 2))));
                        H[k][1] = ((y - SatPos[1][k]) / (java.lang.Math.sqrt(java.lang.Math.pow(x - SatPos[0][k], 2)) + java.lang.Math.sqrt(java.lang.Math.pow(y - SatPos[1][k], 2))));
                        k += 1;
                    }
                    k = 0;
                    D = H;
                    Matrix A = new Matrix(H);
                    Gdop[x][y] = Math.sqrt(((A.transpose().times(Matrix.constructWithCopy(H))).inverse()).trace());

                    //Gdop[x][y] = java.lang.Math.sqrt(Trace(invert(Multi(H, FunT(H)))));
                }
            }
            //Do some stuff
        }
    }
   private class CalcGDOP extends AsyncTask<ArrayList<Point>, Void, double[][]> {
        /**
         * The system calls this to perform work in a worker thread and
         * delivers it the parameters given to AsyncTask.execute()
         */
    //  protected double[][] doInBackground(ArrayList<Point>... satpos) {

    //    return null;
    // }

    /**
     * The system calls this to perform work in the UI thread and delivers
     * the result from doInBackground()
     */
    //protected void onPostExecute(double[][] result) {

    //    }
    // }
}

