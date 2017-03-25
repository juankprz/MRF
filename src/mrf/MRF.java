/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mrf;

import java.beans.Expression;
import java.math.BigDecimal;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 *
 * @author JUAN
 */
public class MRF {
    String funcion;
    double inicial;
    double fin;
    int iteration;
    double error;

    public MRF(String funcion, double inicial, double fin, int iteration, double error) {
        this.funcion = funcion;
        this.inicial = inicial;
        this.fin = fin;
        this.iteration = iteration;
        this.error = error;
    }
       
    
    /**
     * Función a resolver
     * @param x valor de incognita
     * @return double
     */
    public double fx(double x){
               //x^3 - 2x^2 + 3x -8 
               String funcion="x^3 - 2x^2 + 3x +54";
           net.objecthunter.exp4j.Expression e = new ExpressionBuilder(funcion)
                .variables("x")
                .build()
                .setVariable("x",x);
    
        double result = e.evaluate();
           return result;
    }
    
    /**
     * @param x1 Limite inferior
     * @param x2 Limite superior
     * @param iterations numero de iteraciones
     * @param err Error permitido
     * @return String resultado encontrado
     *                NULL en caso de encontrar algun error
     *                EMPTY en caso de no hallar convergencia al limite de iteraciones permitidas
     */
    
    public String resolver(){
        
        int numDec=5;
        double x3, y3;
        double xp=inicial;
        double y1=fx(inicial);
        double y2=fx(fin);   
        int iteraciones=iteration;
        
        
        
        for(int i=1; i<=iteraciones;i++){            
            System.out.println("Iteración N°"+i);
            if ( (y2-y1) == 0 ){
               System.err.println("Error: no converge x=NaN; iteración="+i);
               return null;
            }
            
            x3=(y2*inicial-y1*fin)/(y2-y1);
            System.out.println(" x3 = (y2*x1-y1*x2)/(y2-y1)");
            System.out.println(" x3 = ((" + round(y2,numDec)+")*("+round(inicial,numDec)+")-("+round(y1,numDec)+")*("+round(fin,numDec)+"))/(("+round(y2,numDec)+")-("+round(y1,numDec)+")) = " + round(x3,numDec) );         
            System.out.println("Error |"+round(xp,numDec)+"/"+round(x3,numDec)+"-1|<"+round(error,numDec)  + "  " + (Math.abs(xp/x3-1)<error));
            System.out.println("Error |"+Math.abs(xp/x3-1)+"|");
             System.out.println("-----------------------------------------------------------------");
            if (Math.abs(xp/x3-1)<error){                
                System.out.println(round(Math.abs(xp/x3-1),numDec)+"<"+round(error,numDec) + " -> termina programa");
                return "Iteración:" + i + " Valor x=" + String.valueOf(x3);
              
            }
            
            y3 = fx(x3); 
           
            System.out.println(" y1 = " + round(y1,numDec));
            System.out.println(" y3 = fx(x3) = fx("+round(x3,numDec)+") = " + round(y3,numDec));
            System.out.println(" y1*y3 < 0 " + (y1*y3 < 0));
            if (y1*y3<0) {
                System.out.println("   x2=x3=" + round(x3,numDec));
                System.out.println("   y2=y3=" + round(y3,numDec));
                fin=x3;
                y2=y3;
            } else {
                System.out.println("   x1=x3=" + round(x3,numDec));
                System.out.println("   y1=y3=" + round(y3,numDec));
                inicial=x3;
                y1=y3;
            }
            xp=x3;
        }
        return "";
        
    }
    
    /**
     * Redondea un numero al inmediato superior
     * @param valor numero a redondear
     * @param decimales cantidad de decimales a mostrar
     * @return double numero redondeado
     */
    private double round(double valor, int decimales){
        BigDecimal valueBD = new BigDecimal(valor);
        valueBD = valueBD.setScale(decimales, BigDecimal.ROUND_HALF_UP);
        return valueBD.doubleValue();
    }
        
    public static void main(String[] args){
        String Funcion="";
        
        MRF mrf = new MRF(Funcion,-6,-2,10,0.01);
        String res = mrf.resolver();
        System.out.println(res);
    }
}
