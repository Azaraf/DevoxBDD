/*
 * Copyright (c) 2016, azaraf
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.devox.GUI.PDF;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.inspira.devox.services.devolucion.AccionesDescripcionDevolucion;
import org.inspira.devox.services.queries.FolioJoinSucursal;
import org.inspira.devox.services.queries.ReportePrincipal;
import org.inspira.devox.shared.DescripcionDevolucion;
import com.devox.LOG.Entidades.Producto;
import java.awt.Desktop;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.JOptionPane;
import org.inspira.devox.logger.Log;
import org.inspira.devox.shared.Shareable;

/**
 * Esta clase crea el reporte principal con las especificaciones que Miriam
 * pide, según el RMA.
 *
 * @author azaraf
 */
public class ReporteRMA {

    List<List<String>> contenido;
    ReportePrincipal rp = new ReportePrincipal();
    Date desde;
    Date hasta;
    List<Integer> folios;
    AccionesDescripcionDevolucion add;

    public ReporteRMA(Date desde, Date hasta) {
        contenido = new ArrayList<>();
        contenido = rp.general(desde, hasta);
        folios = rp.folios_por_fechas(desde, hasta);
        this.desde = desde;
        this.hasta = hasta;
    }

    public void crear(File file) throws IOException {
        file.createNewFile();
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(file));
            folios.stream().map((folio) -> {
                List<Object> info = rp.datosPorFolio(folio);
                List<List<Object>> prods = rp.datosProductosPorFolio(folio);
                String cadena = null;
                String almacen = "CV";
                cadena = primerRenglon((String) info.get(0), String.format("%06d", folio), (String) info.get(1));
                pw.print(cadena);
                pw.println();
                for (int i = 0; i < prods.size(); i++) {
                    cadena = segundoRenglon((String) prods.get(i).get(0), (Integer) prods.get(i).get(1), (Float) prods.get(i).get(2));
                    pw.print(cadena);
                    pw.println();
                }
                cadena = tercerRenglon((String) info.get(2));
                pw.print(cadena);
                pw.println();
                cadena = cuartoRenglon((Integer) info.get(3));
                pw.print(cadena);
                pw.println();
                cadena = quintoRenglon(new java.util.Date((long) info.get(4)), (Integer) info.get(5), almacen);
                return cadena;
            }).map((cadena) -> {
                pw.print(cadena);
                return cadena;
            }).forEach((_item) -> {
                pw.println();
            });

            pw.flush();

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
        if (open == JOptionPane.YES_OPTION) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException ex) {
                Log.print("No se encontró la ruta");
                JOptionPane.showMessageDialog(null, "Error en el archivo");
            }
        }
    }

    private String primerRenglon(String idSucursal, String folioDHL, String folioCliente) {
        String cadena;
        cadena = "1" + "1" + StringUtils.rightPad(idSucursal, 6, " ") + StringUtils.rightPad(folioDHL + folioCliente, 13, " ");
        return cadena;
    }

    private String segundoRenglon(String idProducto, int cantidad, float precio) {
        String cadena;
        cadena = "2"
                + StringUtils.rightPad(idProducto, 13, " ")
                + String.format("%06d", cantidad)
                + String.format("%010.2f", precio)
                + "CV";
        return cadena;
    }

    private String tercerRenglon(String observaciones) {
        String cadena;
        observaciones = StringUtils.replace(observaciones, "\n", " ");
        int len = observaciones.length();
        if (len < 50) {
            cadena = "3"
                    + StringUtils.rightPad(StringUtils.replace(observaciones, "\n", " "), 50, " ");
        } else if (len >= 50 && len < 100) {
            cadena = "3"
                    + StringUtils.substring(observaciones, 0, 50) + "\n"
                    + StringUtils.substring(observaciones, 50);
            cadena = StringUtils.rightPad(cadena, 101, " ");
        } else {
            cadena = "3"
                    + StringUtils.substring(observaciones, 0, 50) + "\n"
                    + StringUtils.substring(observaciones, 51, 101) + "\n"
                    + StringUtils.substring(observaciones, 102);
            cadena = StringUtils.rightPad(cadena, 152, " ");
        }
        return cadena;
    }

    private String cuartoRenglon(int idCodigoRazon) {
        String codigoRazon;
        if (idCodigoRazon == 1) {
            codigoRazon = "71";
        } else {
            codigoRazon = "71M";
        }
        String cadena;
        cadena = "4"
                + StringUtils.rightPad(codigoRazon, 3, " ");
        return cadena;
    }

    private String quintoRenglon(Date fecha, int motivo, String almacen) {
        String cadena;
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
        cadena = "5"
                + sdf.format(fecha)
                + StringUtils.rightPad(String.valueOf(motivo), 3, " ")
                + "CV";
        return cadena;
    }
}
