/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.GUI.PDF;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.File;

/**
 *
 * @author azaraf
 */
public interface ExportPDF {
    public static final Font FUENTE_CABECERA = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, new BaseColor(96, 96, 96));
    public static final Font FUENTE_FOLIO_CHICA = new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL, new BaseColor(68, 68, 68));
    public static final Font FUENTE_FOLIO_CHICA_ROJA = new Font(Font.FontFamily.HELVETICA, 16, Font.NORMAL, new BaseColor(152, 7, 10));
    public static final Font FUENTE_DATOS_IZQ = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, new BaseColor(83, 95, 101));
    public static final Font FUENTE_DATOS_DER = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, new BaseColor(30, 79, 109));
    public static final Font FUENTE_CABECERA_TABLA_CHICA = new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD, new BaseColor(212, 15, 41));
    public static final Font FUENTE_TABLA = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.BLACK);
    public static final Font FUENTE_BASE_TABLA = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
    public static final Font FUENTE_CABECERA_TABLA_MEDIANA = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, new BaseColor(212, 15, 41));
    public static final Font FUENTE_TARIMA_NEGRO = new Font(Font.FontFamily.HELVETICA, 60, Font.BOLD, BaseColor.BLACK);
    public static final Font FUENTE_TARIMA_ROJO = new Font(Font.FontFamily.HELVETICA, 60, Font.BOLD, new BaseColor(152, 7, 10));
    public static final Font FUENTE_TITULO_APTO = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
    public static BaseColor AMARILLO = new BaseColor(252, 204, 41);
    public static BaseColor GRIS_CLARO = new BaseColor(200, 211, 217);
    public static final String TIPO_DESTRUCCION = "Reporte destruccion";
    public static final String TIPO_APTO = "Reporte apto";
    public static final String TIPO_TARIMAS = "Reporte tarimas";
    public static final String TIPO_DICTAMEN = "Reporte dictamen";
    public static final String TIPO_ENTREGADLX = "Reporte Entrega DLX";
    public static final String PATH = "/Reportes/";
    public void crear(File file);
    public PdfPTable configurarInformacion();
    public PdfPTable crearTabla();
    public void agregarProductos(PdfPTable table);
    public void setLogo();
}
