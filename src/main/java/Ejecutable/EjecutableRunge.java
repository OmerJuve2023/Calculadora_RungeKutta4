package Ejecutable;

import Dao.Runge;
import Datos.EulerTO;
import java.util.Scanner;

public class EjecutableRunge extends Runge {

    EulerTO Datos;
    double []t;
    double [][]xy;

    public void process(){

        Datos=ingreso();
        t=ejecucion_t(Datos.getRangoI(), Datos.getN(),Datos.getH());
        xy=ejecucion_mat(Datos.getH(), Datos.getFuncionA(), Datos.getFuncionB(), Datos.getX1(), Datos.getY1(), Datos.getN(),this.t);
        salida(t,xy,Datos.getN());
        grafica("",xy,t, Datos.getFuncionA(), Datos.getFuncionA());
        System.out.println("Desea Seguir:    si/no ");
        Scanner scanner=new Scanner(System.in);
        String rpta=scanner.next();
        if(rpta.equals("si")){
            process();
        }else{
            System.exit(0);
        }
    }
}
