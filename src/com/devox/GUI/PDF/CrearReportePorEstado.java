/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.GUI.PDF;

import static com.devox.GUI.PDF.ExportPDF.AMARILLO;
import static com.devox.GUI.PDF.ExportPDF.FUENTE_CABECERA;
import static com.devox.GUI.PDF.ExportPDF.FUENTE_DATOS_DER;
import static com.devox.GUI.PDF.ExportPDF.FUENTE_DATOS_IZQ;
import static com.devox.GUI.PDF.ExportPDF.GRIS_CLARO;
import com.devox.LOG.Funciones;
import org.inspira.devox.logger.Log;
import com.devox.main.Main;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
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
import java.util.Date;
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
public class CrearReportePorEstado implements ExportPDF {

    private static Image LOGO_DHL;
    private Object[][] data;
    private static String estado;
    private String fechaInicio;
    private String fechaFin;

    public CrearReportePorEstado() {
    }

    public CrearReportePorEstado(Object[][] data) {
        this.data = data;
    }

    public CrearReportePorEstado(Object[][] data, String estado) {
        this.data = data;
        CrearReportePorEstado.estado = estado;
    }

    public CrearReportePorEstado(Object[][] data, String estado, String fechaInicio, String fechaFin) {
        this.data = data;
        CrearReportePorEstado.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
    
    public CrearReportePorEstado(Object[][] data, String estado, Date fechaInicio, Date fechaFin) {
        this.data = data;
        CrearReportePorEstado.estado = estado;
        this.fechaInicio = Funciones.getFechaCompleta(fechaInicio);
        this.fechaFin = Funciones.getFechaCompleta(fechaFin);
    }

    @Override
    public void crear(File file) {
        try {
            Document document = new Document(PageSize.A4, 50f, 50f, 65f, 100f);
            file.createNewFile();
            PdfWriter w = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            setLogo();
            CabeceraPieDePagina event = new CabeceraPieDePagina();
            w.setPageEvent(event);
            document.newPage();
            document.add(configurarInformacion());
            document.add(new Paragraph("\n"));
            PdfPTable table = crearTabla();
            agregarProductos(table);
            document.add(table);
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("TOTAL: " + data.length + "FOLIOS.", FUENTE_FOLIO_CHICA));
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
        PdfPCell cell;

        cell = new PdfPCell(getPhraseFromChunks("DEL ", fechaInicio));
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setPaddingLeft(40);
        table.addCell(cell);
        cell = new PdfPCell(getPhraseFromChunks("AL ", fechaFin));
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setPaddingLeft(40);
        table.addCell(cell);
        table.setWidthPercentage(100);
        return table;
    }

    @Override
    public PdfPTable crearTabla() {
        PdfPTable table = new PdfPTable(2);
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("FOLIO DHL", FUENTE_CABECERA_TABLA_MEDIANA));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorRight(GRIS_CLARO);
        cell.setFixedHeight(20f);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("DESTINO", FUENTE_CABECERA_TABLA_MEDIANA));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorLeft(GRIS_CLARO);
        cell.setBorderColorRight(GRIS_CLARO);
        cell.setBorderColorTop(AMARILLO);
        table.addCell(cell);

        return table;
    }

    @Override
    public void agregarProductos(PdfPTable table) {
        int rows = data.length;

        for (int i = 0; i < rows; i++) {
            table.addCell(new Phrase(data[i][0].toString()));
            table.addCell(new Phrase(data[i][1].toString()));
        }
    }

    @Override
    public void setLogo() {
        try {
            URL url = Main.class.getResource("dhl.png");
            LOGO_DHL = (Image.getInstance(url));
        } catch (BadElementException | IOException ex) {Log.print(ex);
        }
    }

    private static Phrase getPhraseFromChunks(String a, String b) {
        Phrase phrase = new Phrase();
        phrase.add(new Chunk(a, FUENTE_DATOS_IZQ));
        phrase.add(new Chunk(b, FUENTE_DATOS_DER));
        return phrase;
    }

    private static class CabeceraPieDePagina extends PdfPageEventHelper implements CabeceraPieDePaginaInterface {

        private PdfContentByte contentByte;

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("fecha de impresion: " + Funciones.getTodayDate(), FUENTE_CABECERA), 450, 800, 0);
            try {
                LOGO_DHL.setAbsolutePosition(35, 800);
                LOGO_DHL.scalePercent(60);
                writer.getDirectContent().addImage(LOGO_DHL);
                contentByte = writer.getDirectContent();
                drawLine();
                writer.setRgbTransparencyBlending(true);

            } catch (Exception ex) {
                Log.print(ex);
            }
            createTitleTable().writeSelectedRows(0, -1, 0, -1, 190, 810, writer.getDirectContent());
            createSigningTable().writeSelectedRows(0, -1, 0, -1, 60, 75, writer.getDirectContent());
            String ref = getReferenciaContraloria();
            String cod = getCodigoContraloria();
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(ref, FUENTE_CABECERA), 35, 30, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(cod, FUENTE_CABECERA), 320, 30, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Phrase("" + document.getPageNumber()), 550, 30, 0);

        }

        @Override
        public String getCodigoContraloria() {
            AccionesCodigo cod = new AccionesCodigo();
            cod.loadDatabaseInstances();
            String codigo = null;
            for (Shareable s : cod.getEntityInstances()) {
                if ("DICTAMEN".equals(estado)) {
                    codigo = ((Codigo) s).getCodigo_actual_parte_4();
                } else {
                    codigo = ((Codigo) s).getCodigo_actual_parte_5();
                }
            }
            return codigo;
        }

        @Override
        public String getReferenciaContraloria() {
            AccionesCodigo cod = new AccionesCodigo();
            cod.loadDatabaseInstances();
            String codigo = null;
            for (Shareable s : cod.getEntityInstances()) {
                codigo = ((Codigo) s).getCodigo_actual_parte_1();
            }
            return codigo;
        }

        @Override
        public void drawLine() {
            contentByte.saveState();
            contentByte.moveTo(550, 795);
            contentByte.lineTo(35, 795);
            contentByte.moveTo(550, 45);
            contentByte.lineTo(35, 45);
            contentByte.setLineWidth(3);
            contentByte.setColorStroke(new BaseColor(252, 204, 41));
            contentByte.stroke();
            contentByte.restoreState();
        }

        private PdfPTable createTitleTable() {
            PdfPTable table = new PdfPTable(1);
            PdfPCell cell;

            cell = new PdfPCell(new Phrase(getPhraseFromChunks("DEVOLUCIONES PARA ", estado)));
            cell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell);

            table.setTotalWidth(500);
            return table;
        }

        private PdfPTable createSigningTable() {
            PdfPTable table1 = new PdfPTable(1);
            PdfPCell cell = new PdfPCell(new Phrase("RECIBIÓ", FUENTE_FOLIO_CHICA_ROJA));
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.setFixedHeight(40f);
            table1.addCell(cell);

            table1.setTotalWidth(500);
            return table1;
        }

    }
}
