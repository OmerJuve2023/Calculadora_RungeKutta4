package Dao;

import Constantes.KL;
import Constantes.Operadores;
import Datos.Esqueleto;
import Datos.EulerTO;

import java.text.DecimalFormat;

public class Runge implements Esqueleto {
    Euler2Dao euler2Dao = new Euler2Dao();
    Operadores c = new Operadores();

    @Override
    public EulerTO ingreso() {
        System.out.println(c.red("*********************************************"));
        System.out.println(c.purple("\t\t\tMétodo Runge Kutta 4"));
        System.out.println(c.red("*********************************************"));
        return euler2Dao.ingreso();
    }

    @Override
    public double[] ejecucion_t(double v, int i, double v1) {
        return euler2Dao.ejecucion_t(v, i + 1, v1);
    }

    @Override
    public double[][] ejecucion_matriz(double h, String fa, String fb, double x1, double y2, double N) {
        double[][] array = new double[(int) N + 1][2];
        array[0][0] = x1;
        array[0][1] = y2;
        for (int i = 1; i < (N + 1); i++) {
            double k1 = funcion(fa, array[i - 1][0], array[i - 1][1]);
            double l1 = funcion(fb, array[i - 1][0], array[i - 1][1]);
            double k2 = funcion(fa, array[i - 1][0] + ((h / 2) * k1), array[i - 1][1] + ((h / 2) * l1));
            double l2 = funcion(fb, array[i - 1][0] + ((h / 2) * k1), array[i - 1][1] + ((h / 2) * l1));
            double k3 = funcion(fa, array[i - 1][0] + ((h / 2) * k2), array[i - 1][1] + ((h / 2) * l2));
            double l3 = funcion(fb, array[i - 1][0] + ((h / 2) * k2), array[i - 1][1] + ((h / 2) * l2));
            double k4 = funcion(fa, array[i - 1][0] + (h * k3), array[i - 1][1] + (h * l3));
            double l4 = funcion(fb, array[i - 1][0] + (h * k3), array[i - 1][1] + (h * l3));
            array[i][0] = array[i - 1][0] + ((h / 6) * (k1 + (2 * k2) + (2 * k3) + k4));
            array[i][1] = array[i - 1][1] + ((h / 6) * (l1 + (2 * l2) + (2 * l3) + l4));
        }
        return array;
    }

    public double[][] ejecucion_mat(double h, String fa, String fb, double x1, double y1, double N, double[] t) {
        double[][] xey = new double[(int) N + 1][2];
        xey[0][0] = x1;
        xey[0][1] = y1;
        double[] K = new double[4];
        double[] L = new double[4];
        DecimalFormat form = new DecimalFormat("####.######");

        for (int i = 1; i < (N + 1); i++) {

            K[0] = funcion(fa, xey[i - 1][0], xey[i - 1][1]);
            L[0] = funcion(fb, xey[i - 1][0], xey[i - 1][1]);

            K[1] = funcion(fa, xey[i - 1][0] + ((h / 2) * K[0]), xey[i - 1][1] + ((h / 2) * L[0]));
            L[1] = funcion(fb, xey[i - 1][0] + ((h / 2) * K[0]), xey[i - 1][1] + ((h / 2) * L[0]));

            K[2] = funcion(fa, xey[i - 1][0] + ((h / 2) * K[1]), xey[i - 1][1] + ((h / 2) * L[1]));
            L[2] = funcion(fb, xey[i - 1][0] + ((h / 2) * K[1]), xey[i - 1][1] + ((h / 2) * L[1]));

            K[3] = funcion(fa, xey[i - 1][0] + (h * K[2]), xey[i - 1][1] + (h * L[2]));
            L[3] = funcion(fb, xey[i - 1][0] + (h * K[2]), xey[i - 1][1] + (h * L[2]));

            xey[i][0] = xey[i - 1][0] + ((h / 6) * (K[0] + (2 * K[1]) + (2 * K[2]) + K[3]));
            xey[i][1] = xey[i - 1][1] + ((h / 6) * (L[0] + (2 * L[1]) + (2 * L[2]) + L[3]));

            String txt = c.red("iteracion:") + i + c.red("\t t:") + form.format(t[i]) + "\n";

            txt += c.blue(KL.K1.getkL()) + c.red("=") + c.green(form.format(K[0])) + c.blue("\t" +
                    KL.l1.getkL()) + c.red("= ") + c.green(form.format(L[0])) + "\n";

            txt += c.blue(KL.K2.getkL()) + c.red("=") + format(fa, K[1], L[1]) + c.red("= ") +
                    c.green(form.format(K[1])) + c.blue("\t\t" + KL.l2.getkL()) + c.red("= ") +
                    format(fb, K[1], L[1]) + c.red("= ") + c.green(form.format(L[1])) + "\n";

            txt += c.blue(KL.K3.getkL()) + c.red("=") + format(fa, K[2], L[2]) + c.red("= ") +
                    c.green(form.format(K[2])) + c.blue("\t\t" + KL.l3.getkL()) + c.red("= ") +
                    format(fb, K[2], L[2]) + c.red("= ") + c.green(form.format(L[2])) + "\n";

            txt += c.blue(KL.K4.getkL()) + c.red("=") + format(fa, K[3], L[3]) + c.red("= ") +
                    c.green(form.format(K[3])) + c.blue("\t\t" + KL.l4.getkL()) + c.red("= ") +
                    format(fb, K[3], L[3]) + c.red("= ") + c.green(form.format(L[1])) + "\n";
            txt += c.purple("-----------------------------------------------------------------------------------------------------------------------------------");

            System.out.println(txt);
        }

        return xey;
    }

    private String format(String funcion, double K, double L) {
        DecimalFormat format = new DecimalFormat("####.0#####");
        return funcion.replace("x", c.purple("(") + format.format(K) + c.purple(")")).replace("y", c.purple("(") + format.format(L) + c.purple(")"));
    }

    @Override
    public void salida(double[] doubles, double[][] doubles1, int i) {
        euler2Dao.salida(doubles, doubles1, i + 1);
    }

    @Override
    public void grafica(String s, double[][] doubles, double[] doubles1, String s1, String s2) {
        euler2Dao.grafica(s, doubles, doubles1, s1, s2);
    }

    @Override
    public double funcion(String s, double v, double v1) {
        return euler2Dao.funcion(s, v, v1);
    }
}