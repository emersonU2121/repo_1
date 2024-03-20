/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author A5155456HP
 */
public class FechaConvert {
     public static String covertFecha(String fe) {
        String[] fecha = fe.split("-");

        return fecha[2] + "/" + fecha[1] + "/" + fecha[0];
    }

}
