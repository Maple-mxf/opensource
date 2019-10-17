package io.jopen.core.algorithm;

import org.junit.Test;

import java.util.ArrayList;

/**
 * @author maxuefeng
 */
public class CalculateProfile {


    /**
     * P = 164000*X+136000*Y+88000*Z-2.88*N
     * <p>
     * X【1，5】
     * Y【1，10】
     * Z【1，15】
     * N【50000，300000】
     */


    class R {

        int x;

        int y;

        int z;

        int n;

        double p;

        public R(int x, int y, int z, int n, double p) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.n = n;
            this.p = p;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getZ() {
            return z;
        }

        public void setZ(int z) {
            this.z = z;
        }

        public int getN() {
            return n;
        }

        public void setN(int n) {
            this.n = n;
        }

        public double getP() {
            return p;
        }

        public void setP(double p) {
            this.p = p;
        }

        @Override
        public String toString() {
            return "R{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    ", n=" + n +
                    ", p=" + p +
                    '}';
        }
    }

    public ArrayList<R> calculate(int N) {

        ArrayList<R> rs = new ArrayList<>();

        int X = 1, Y = 1, Z = 1;

        int maxX = 5, maxY = 10, maxZ = 15;

        // int N = 50000, maxN = 300000;

        // 利润计算

        double P;

        for (; X <= maxX; X++) {

            for (; Y <= maxY; Y++) {

                for (; Z <= maxZ; Z++) {
                    P = 164000 * X + 136000 * Y + 88000 * Z - 2.88 * N;

                    R r = new R(X, Y, Z, N, P);
                    rs.add(r);
                }

            }
        }

        return rs;
    }

    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 300000; i++) {
            ArrayList<R> calculate = calculate(i);
            Thread.sleep(1000);
        }
    }


}
