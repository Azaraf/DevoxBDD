/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.GUI.PDF;

import org.inspira.devox.logger.Log;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.inspira.devox.services.queries.ReportePrincipal;

/**
 * Esta clase es para crear el reporte principal en un archivo CSV, solo separado con comas
 * @author azaraf
 */
public class CrearReportePrincipal {

    List<List<String>> contenido;
    ReportePrincipal rp = new ReportePrincipal();
    Date desde;
    Date hasta;
    

    public CrearReportePrincipal(Date desde, Date hasta) {
        contenido = new ArrayList<>();
        
        contenido = rp.general(desde, hasta);
        
        this.desde = desde;
        this.hasta = hasta;
    }

    public void crear(File file) throws IOException {
        file.createNewFile();
        try {
            FileOutputStream fout = new FileOutputStream(file);
            fout.write("Folio DHL,Clave cliente,Folio cliente,Cliente,Codigo producto,Descripcion,Cantidad,Precio,Monto total,Lote,Division,Destruccion o apto,Codigo de razon,Motivo de devolucion,Tarima,Fecha de recibido,Fecha de captura".getBytes());
            fout.write(("\n").getBytes());
            for (List<String> lst : contenido) {
                String value = "\"" + lst.get(0) + "\"," +
                        "\"" + lst.get(1) + "\"," +
                        "\"" + lst.get(2) + "\"," +
                        "\"" + lst.get(3) + "\"," +
                        "\"" + lst.get(4) + "\"," +
                        "\"" + lst.get(5) + "\"," +
                        "\"" + lst.get(6) + "\"," +
                        "\"" + lst.get(7) + "\"," +
                        "\"" + lst.get(8) + "\"," +
                        "\"" + lst.get(9) + "\"," +
                        "\"" + lst.get(10) + "\"," +
                        "\"" + lst.get(11) + "\"," +
                        "\"" + lst.get(12) + "\"," +
                        "\"" + lst.get(13) + "\"," +
                        "\"" + lst.get(14) + "\"," +
                        "\"" + lst.get(15) + "\"," +
                        "\"" + lst.get(16) + "\"\n" ;
                fout.write(value.getBytes());
            }
            fout.flush();
            fout.close();
        } catch (FileNotFoundException ex) {
            Log.print(ex);
        }
        
        Object[] options = {"Abrir",
                    "No"};
            int open = JOptionPane.showOptionDialog(
                    null, 
                    "¿Desea abrir el reporte?", 
                    "Reporte guardado", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE,
                    new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/txt.png")),
                    options,
                    options[0]);
            if (open == JOptionPane.YES_OPTION){
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException ex) {
                    Log.print("No se encontró la ruta");
                    JOptionPane.showMessageDialog(null, "Error en el archivo");
                }
            }

    }

}
