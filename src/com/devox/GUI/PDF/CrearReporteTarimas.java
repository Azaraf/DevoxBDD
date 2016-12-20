/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.GUI.PDF;

import static com.devox.GUI.PDF.ExportPDF.AMARILLO;
import static com.devox.GUI.PDF.ExportPDF.FUENTE_CABECERA;
import static com.devox.GUI.PDF.ExportPDF.FUENTE_CABECERA_TABLA_MEDIANA;
import static com.devox.GUI.PDF.ExportPDF.GRIS_CLARO;
import com.devox.LOG.Entidades.DatosTarima;
import com.devox.LOG.Funciones;
import org.inspira.devox.logger.Log;
import com.devox.main.Main;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.inspira.devox.services.other.AccionesCodigo;
import org.inspira.devox.shared.Codigo;
import org.inspira.devox.shared.Shareable;

/**
 *
 * @author azaraf
 */
public class CrearReporteTarimas implements ExportPDF {

    private static Image LOGO_DHL;
    private DatosTarima datosTarima;
    

    public CrearReporteTarimas(DatosTarima datosTarima) {
        this.datosTarima = datosTarima;
    }

    public CrearReporteTarimas(String name, Object[][] obj) {
        this.datosTarima = new DatosTarima(name, obj);
    }

    public CrearReporteTarimas() {
        this.datosTarima = new DatosTarima();
    }
    
    
    @Override
    public void crear(File file) {
        try {
            Document document = new Document(PageSize.A4.rotate());
            file.createNewFile();
            PdfWriter w = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            setLogo();
            CabeceraPieDePagina event = new CabeceraPieDePagina();
            w.setPageEvent(event);
            document.setMargins(50, 50, 100, 50);
            document.newPage();
            document.add(configurarInformacion());
            PdfPTable table = crearTabla();
            agregarProductos(table);
            document.add(table);
            document.close();
        } catch (Exception ex) {
            Log.print(ex);
        }
        
        Object[] options = {"Abrir PDF",
                    "No"};
            int open = JOptionPane.showOptionDialog(
                    null, 
                    "¿Desea abrir el reporte?", 
                    "Reporte guardado", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE,
                    new javax.swing.ImageIcon(getClass().getResource("/com/devox/GUI/images/print.png")),
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

    @Override
    public PdfPTable configurarInformacion() {
        PdfPTable table = new PdfPTable(2);
        try {
            PdfPCell cell;
            Phrase tarima = new Phrase();
            tarima.add(new Chunk("TARIMA\n", FUENTE_TARIMA_NEGRO));
            tarima.add(new Chunk(datosTarima.getNombreTarima(), FUENTE_TARIMA_ROJO));
            cell = new PdfPCell(tarima);
            cell.setRowspan(5);
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.setPaddingLeft(40);
            cell.setFixedHeight(200f);
            table.addCell(cell);
            cell = new PdfPCell(getPhraseFromChunks("DIVISIÓN ", datosTarima.getDivision()));
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.setPaddingLeft(40);
            table.addCell(cell);
            cell = new PdfPCell(getPhraseFromChunks("ALMACÉN ", datosTarima.getAlmacen()));
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.setPaddingLeft(40);
            table.addCell(cell);
            cell = new PdfPCell(getPhraseFromChunks("FECHA DE APERTURA ", Funciones.getOtherDate(datosTarima.getFecha_Apertura())));
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.setPaddingLeft(40);
            table.addCell(cell);
            cell = new PdfPCell(getPhraseFromChunks("FECHA DE CIERRE ", (datosTarima.getFecha_Cierra() == null ? "TARIMA ABIERTA" : Funciones.getOtherDate(datosTarima.getFecha_Cierra()))));
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.setPaddingLeft(40);
            table.addCell(cell);
            cell = new PdfPCell(getPhraseFromChunks("DESTRUCCIÓN FISCAL ", Integer.toString(datosTarima.getDestruccionFiscal())));
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.setPaddingLeft(40);
            table.addCell(cell);

            table.setWidthPercentage(100);
            table.setWidths(new int[]{2, 1});
        } catch (DocumentException de) {
            Log.print(de);
        }
        return table;
    }

    @Override
    public PdfPTable crearTabla() {
        PdfPTable table = new PdfPTable(4);
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("FOLIO DHL", FUENTE_CABECERA_TABLA_MEDIANA));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorRight(GRIS_CLARO);
        cell.setFixedHeight(20f);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("PRODUCTO (Descripción)", FUENTE_CABECERA_TABLA_MEDIANA));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorLeft(GRIS_CLARO);
        cell.setBorderColorRight(GRIS_CLARO);
        cell.setBorderColorTop(AMARILLO);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("CÓDIGO INTERNO", FUENTE_CABECERA_TABLA_MEDIANA));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorLeft(GRIS_CLARO);
        cell.setBorderColorRight(GRIS_CLARO);
        cell.setBorderColorTop(AMARILLO);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("CANTIDAD", FUENTE_CABECERA_TABLA_MEDIANA));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorLeft(GRIS_CLARO);
        cell.setBorderColorRight(GRIS_CLARO);
        cell.setBorderColorTop(AMARILLO);
        table.addCell(cell);

        
        try {
            table.setWidths(new float[]{1f, 1.5f, 2.5f, 1f});
            table.setWidthPercentage(100);

        } catch (DocumentException ex) {
            Log.print(ex);
        }
        return table;
    }

    @Override
    public void agregarProductos(PdfPTable table) {
        int rows = datosTarima.getListaProductos().size();
        int total = 0;

        for (int i = 0; i < rows; i++) {
            PdfPCell cell = new PdfPCell();
            cell.addElement(new Phrase(datosTarima.getListaProductos().get(i)[0]));

            table.addCell(cell);
            table.addCell(new Phrase(datosTarima.getListaProductos().get(i)[1]));
            
            table.addCell(new Phrase(datosTarima.getListaProductos().get(i)[2]));
            int c = Integer.parseInt(datosTarima.getListaProductos().get(i)[3]);
            total += c;
            table.addCell(datosTarima.getListaProductos().get(i)[3]);
            
        }
        table.addCell(new Phrase("  ", FUENTE_BASE_TABLA));
        table.addCell(new Phrase("Total ", FUENTE_BASE_TABLA));
        table.addCell(new Phrase("  ", FUENTE_BASE_TABLA));
        table.addCell(new Phrase("" + total, FUENTE_BASE_TABLA));
    }

    @Override
    public void setLogo() {
        try {
            URL url = Main.class.getResource("dhl.png");
            LOGO_DHL = (Image.getInstance(url));
        } catch (BadElementException | IOException ex) {
            Log.print(ex);
        }
    }

    private static Phrase getPhraseFromChunks(String a, String b) {
        Phrase phrase = new Phrase();
        phrase.add(new Chunk(a, FUENTE_DATOS_IZQ));
        phrase.add(new Chunk(b, FUENTE_DATOS_DER));
        return phrase;
    }
    
    private class CabeceraPieDePagina extends PdfPageEventHelper implements CabeceraPieDePaginaInterface {

        private PdfContentByte contentByte;

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("fecha de impresion: " + Funciones.getTodayDate(), FUENTE_CABECERA), 720, 555, 0);
            try {
                LOGO_DHL.setAbsolutePosition(45, 525);
                LOGO_DHL.scalePercent(60);
                writer.getDirectContent().addImage(LOGO_DHL);
                contentByte = writer.getDirectContent();
                drawLine();
                writer.setRgbTransparencyBlending(true);

            } catch (Exception ex) {
                Log.print(ex);
            }
            String ref = getReferenciaContraloria();
            String cod = getCodigoContraloria();
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(ref, FUENTE_CABECERA), 45, 24, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(cod, FUENTE_CABECERA), 480, 24, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Phrase("" + document.getPageNumber()), 780, 24, 0);

        }

        @Override
        public String getCodigoContraloria() {
            AccionesCodigo cod = new AccionesCodigo();
            cod.loadDatabaseInstances();
            String codigo = null;
            for (Shareable s : cod.getEntityInstances()) {
                codigo = ((Codigo) s).getCodigo_actual_parte_1();
            }
            return codigo;
        }

        @Override
        public String getReferenciaContraloria() {
            AccionesCodigo cod = new AccionesCodigo();
            cod.loadDatabaseInstances();
            String codigo = null;
            for (Shareable s : cod.getEntityInstances()) {
                codigo = ((Codigo) s).getCodigo_actual_parte_6();
            }
            return codigo;
        }

        @Override
        public void drawLine() {
            contentByte.saveState();
            contentByte.moveTo(45, 35);
            contentByte.lineTo(795, 35);
            contentByte.moveTo(45, 550);
            contentByte.lineTo(795, 550);
            contentByte.setLineWidth(3);
            contentByte.setColorStroke(new BaseColor(252, 204, 41));
            contentByte.stroke();
            contentByte.restoreState();
        }

    }
}
