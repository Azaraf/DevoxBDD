/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.LOG;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author azaraf
 */
public class Funciones{
    public static final String ALIGN_LEFT = "left";
    public static final String ALIGN_RIGHT = "right";
    public static final String ALIGN_CENTER = "center";
    private static boolean superUserSession;
    
    private Funciones() {
    }

    public static String getFechaCompleta(Date today){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE d' de 'MMMM' del 'yyyy");
        String strDate = sdf.format(today);
        return strDate;
    }
    
    public static String setText(String text, String align){
        return "<html><div align=\""+align+"\">"+text+"</html>";
    }
    
    public static String getTodayDate(){
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = sdf.format(today);
        return strDate;
    }
    
    public static String getOtherDate(Date today){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = sdf.format(today);
        return strDate;
    }
    
    public static String getRutaPdf(){
        return "/";
    }
    
    public static void setSuperUserSession(boolean superUserSession){
        Funciones.superUserSession = superUserSession;
    }
    public static boolean isSuperUserSession(){
        return superUserSession;
    }
}
