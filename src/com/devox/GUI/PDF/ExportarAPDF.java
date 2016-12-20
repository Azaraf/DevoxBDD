/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.GUI.PDF;

import org.inspira.devox.logger.Log;
import com.devox.LOG.Admin.OptionPanels;
import com.devox.LOG.Entidades.ContenidoDevolucion;
import com.devox.LOG.Entidades.DatosTarima;
import com.devox.LOG.Funciones;
import com.devox.main.Main;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import javax.swing.table.DefaultTableModel;
import org.inspira.devox.services.other.AccionesCodigo;
import org.inspira.devox.services.queries.FolioJoinSucursal;
import org.inspira.devox.shared.Codigo;
import org.inspira.devox.shared.Shareable;

/**
 *
 * @author azaraf
 */
public class ExportarAPDF {

    private DefaultTableModel model;
    private FolioJoinSucursal folioJoinSucursal;
    private OptionPanels devolucionProductos;
    private static ContenidoDevolucion contenido;
    private DatosTarima datosTarima;
    private static final Font font_fecha = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, new BaseColor(96, 96, 96));
    private static final Font font_foliodhl = new Font(Font.FontFamily.HELVETICA, 18, Font.NORMAL, new BaseColor(68, 68, 68));
    private static final Font font_folionum = new Font(Font.FontFamily.HELVETICA, 18, Font.NORMAL, new BaseColor(152, 7, 10));
    private static final Font font_info = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, new BaseColor(83, 95, 101));
    private static final Font font_datos = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, new BaseColor(30, 79, 109));
    private static final Font font_headertable1 = new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD, new BaseColor(212, 15, 41));
    private static final Font font_table = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
    private static final Font font_bottomtable = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
    private static final Font font_headertable2 = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, new BaseColor(212, 15, 41));
    private static final Font font_tarimota = new Font(Font.FontFamily.HELVETICA, 80, Font.BOLD, BaseColor.BLACK);
    private static final Font font_tarimota_gris = new Font(Font.FontFamily.HELVETICA, 100, Font.BOLD, new BaseColor(152, 7, 10));

    private static final String REPORTE_DESTRUCCION = "Reporte destruccion";
    private static final String REPORTE_APTO = "Reporte apto";
    private static final String REPORTE_TARIMAS = "Reporte tarimas";
    private static BaseColor AMARILLO = new BaseColor(252, 204, 41);

    private static BaseColor GRIS_CLARO = new BaseColor(200, 211, 217);

    private static final int BY_MODEL = 0;
    private static final int BY_FOLIO = 1;
    private static final int BY_DEVOLUCION = 2;

    private static boolean ANI = false;

    public static void setANI(boolean ANI) {
        ExportarAPDF.ANI = ANI;
    }

    public ExportarAPDF() {
    }

    public void setContenido(ContenidoDevolucion contenido) {
        ExportarAPDF.contenido = contenido;
        if (contenido.getDivisionCliente().equals("Abbott Diagnostics Division")) {
            setANI(true);
        }
    }

    public void setDatosTarima(DatosTarima datosTarima) {
        this.datosTarima = datosTarima;
    }

    private int method;

    static Image dhl;

    public ExportarAPDF(DefaultTableModel model) {
        this.model = model;
        method = BY_MODEL;
        setLogo();

    }

    public ExportarAPDF(FolioJoinSucursal folioJoinSucursal) {
        this.folioJoinSucursal = folioJoinSucursal;
        method = BY_FOLIO;
        setLogo();
    }

    public ExportarAPDF(OptionPanels devolucionProductos) {
        this.devolucionProductos = devolucionProductos;
        method = BY_DEVOLUCION;
        setLogo();
    }

    public void crearDestruccion(File file) {
        try {
            Document document = new Document(PageSize.A4);
            file.createNewFile();
            PdfWriter w = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            setLogo();
            CabeceraPieDePagina event = new CabeceraPieDePagina();
            w.setPageEvent(event);
            document.setMargins(50, 50, 100, 220);
            document.newPage();
            document.add(setUpInformation());
            PdfPTable t = createTable();
            addProductos(model, t);
            document.add(t);
            //
            document.close();
        } catch (Exception ex) {
            Log.print(ex);
            Log.print(ex);
        }
    }

    public void crearApto(File file) {
        try {
            Document document = new Document(PageSize.A4);
            file.createNewFile();
            PdfWriter w = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            setLogo();
            CabeceraPieDePagina event = new CabeceraPieDePagina();
            w.setPageEvent(event);
            document.setMargins(50, 50, 100, 220);
            document.newPage();
            document.add(setUpInformationTarimas());
//            PdfPTable t = createTable();
//            addProductos(model, t);
//            document.add(t);
            //
            document.close();
        } catch (Exception ex) {
            Log.print(ex);
            Log.print(ex);
        }
    }

    public void crearTarimas(File file) {
        try {
            Document document = new Document(PageSize.A4.rotate());
            file.createNewFile();
            PdfWriter w = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            setLogo();
            CabeceraPieDePagina2 event = new CabeceraPieDePagina2();
            w.setPageEvent(event);
            document.setMargins(50, 50, 100, 50);
            document.newPage();
            document.add(setUpInformationTarimas());
            PdfPTable t = createTableTarimas();
            addProductosTarimas(t);
            document.add(t);
            //
            document.close();
        } catch (Exception ex) {
            Log.print(ex);
            Log.print(ex);
        }
    }

    public void crearDictamen(){
        
    }
    
    public void crearEntregaDLX(){
        
    }
    public void imprimirArchivo(String archivo) {

    }

    private void setLogo() {
        try {
            URL url = Main.class.getResource("dhl.png");
            dhl = (Image.getInstance(url));
        } catch (BadElementException | IOException ex) {
            Log.print(ex);
            System.err.println(ex.toString());
        }

    }

    private static void drawLine(PdfContentByte contentByte) {
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
    
    private static void drawLine2(PdfContentByte contentByte) {
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

    private static PdfPTable setUpInformation() {
        PdfPTable table = new PdfPTable(2);
        try {
            PdfPCell cell;
            Phrase folio = new Phrase();
            folio.add(new Chunk("FOLIO DHL ", font_foliodhl));
            folio.add(new Chunk(contenido.getFolioDHL(), font_folionum));
            cell = new PdfPCell(folio);
            cell.setRowspan(8);
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.setPaddingLeft(40);
            table.addCell(cell);
            cell = new PdfPCell(getPhraseFromChunks("Cliente ", contenido.getNombreCliente()));
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.setPaddingLeft(40);
            table.addCell(cell);
            cell = new PdfPCell(getPhraseFromChunks("Número de cliente ", contenido.getClaveCliente()));
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.setPaddingLeft(40);
            table.addCell(cell);
            cell = new PdfPCell(getPhraseFromChunks("Folio del cliente ", contenido.getFolioCliente()));
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.setPaddingLeft(40);
            table.addCell(cell);
            cell = new PdfPCell(getPhraseFromChunks("Folio Abbott ", contenido.getFolioAbbott()));
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.setPaddingLeft(40);
            table.addCell(cell);
            cell = new PdfPCell(getPhraseFromChunks("Motivo de devolución ", contenido.getMotivo().getCodigo() + " - " + contenido.getMotivo().getDescripcion()));
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.setPaddingLeft(40);
            table.addCell(cell);
            cell = new PdfPCell(getPhraseFromChunks("Factura ", contenido.getFactura()));
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.setPaddingLeft(40);
            table.addCell(cell);
            cell = new PdfPCell(getPhraseFromChunks("Almacén ", contenido.getAlmacen()));
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.setPaddingLeft(40);
            table.addCell(cell);
            cell = new PdfPCell(getPhraseFromChunks("Fecha de captura ", Funciones.getOtherDate(contenido.getFechaCaptura())));
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.setPaddingLeft(40);
            table.addCell(cell);

            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 3});

        } catch (DocumentException ex) {
            Log.print(ex);
            Log.print(ex);
        }
        return table;
    }

    private static PdfPTable createTable() {
        PdfPTable table = new PdfPTable(6);
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("DESCRIPCIÓN", font_headertable1));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorRight(GRIS_CLARO);
        cell.setFixedHeight(20f);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("LOTE", font_headertable1));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorLeft(GRIS_CLARO);
        cell.setBorderColorRight(GRIS_CLARO);
        cell.setBorderColorTop(AMARILLO);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("CÓDIGO", font_headertable1));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorLeft(GRIS_CLARO);
        cell.setBorderColorRight(GRIS_CLARO);
        cell.setBorderColorTop(AMARILLO);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("CANTIDAD", font_headertable1));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorLeft(GRIS_CLARO);
        cell.setBorderColorRight(GRIS_CLARO);
        cell.setBorderColorTop(AMARILLO);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("PRECIO UNITARIO", font_headertable1));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorLeft(GRIS_CLARO);
        cell.setBorderColorRight(GRIS_CLARO);
        cell.setBorderColorTop(AMARILLO);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("SUBTOTAL", font_headertable1));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorLeft(GRIS_CLARO);
        cell.setBorderColorRight(AMARILLO);
        cell.setBorderColorTop(AMARILLO);
        table.addCell(cell);

        try {
            table.setWidths(new float[]{5f, 2f, 3f, 1.2f, 1.4f, 1.5f});
            table.setWidthPercentage(98);

        } catch (DocumentException ex) {
            Log.print(ex);
            Log.print(ex);
        }
        return table;
    }

    private static PdfPTable createSigningTable() {
        PdfPTable table = new PdfPTable(4);
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("RMA", font_headertable2));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorRight(GRIS_CLARO);
        cell.setFixedHeight(25f);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("ORDEN", font_headertable2));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorLeft(GRIS_CLARO);
        cell.setBorderColorRight(GRIS_CLARO);
        cell.setBorderColorTop(AMARILLO);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("ELABORÓ", font_headertable2));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorLeft(GRIS_CLARO);
        cell.setBorderColorRight(GRIS_CLARO);
        cell.setBorderColorTop(AMARILLO);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("DICTAMINÓ QA", font_headertable2));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorLeft(GRIS_CLARO);
        cell.setBorderColorRight(GRIS_CLARO);
        cell.setBorderColorTop(AMARILLO);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("  "));
        cell.setFixedHeight(50f);
        for (int i = 0; i < 4; i++) {
            table.addCell(cell);
        }

        cell = new PdfPCell(new Phrase("  "));
        cell.setFixedHeight(25f);
        cell.setBackgroundColor(GRIS_CLARO);
        for (int i = 0; i < 2; i++) {
            table.addCell(cell);
        }

        cell = new PdfPCell(new Phrase("NOMBRE Y FIRMA", font_table));
        cell.setFixedHeight(25f);
        cell.setBackgroundColor(GRIS_CLARO);
        for (int i = 0; i < 2; i++) {
            table.addCell(cell);
        }

        table.setTotalWidth(500);
        return table;
    }

    private PdfPTable setUpInformationTarimas() {
        PdfPTable table = new PdfPTable(2);
        try {
            PdfPCell cell;
            Phrase tarima = new Phrase();
            tarima.add(new Chunk("TARIMA\n", font_tarimota));
            tarima.add(new Chunk(datosTarima.getNombreTarima(), font_tarimota_gris));
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
            cell = new PdfPCell(getPhraseFromChunks("FECHA DE CIERRE ", Funciones.getOtherDate(datosTarima.getFecha_Cierra())));
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

    private PdfPTable createTableTarimas() {
        PdfPTable table = new PdfPTable(4);
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("FOLIO DHL", font_headertable1));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorRight(GRIS_CLARO);
        cell.setFixedHeight(20f);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("PRODUCTO (Descripción)", font_headertable1));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorLeft(GRIS_CLARO);
        cell.setBorderColorRight(GRIS_CLARO);
        cell.setBorderColorTop(AMARILLO);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("CÓDIGO INTERNO", font_headertable1));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorLeft(GRIS_CLARO);
        cell.setBorderColorRight(GRIS_CLARO);
        cell.setBorderColorTop(AMARILLO);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("CANTIDAD", font_headertable1));
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
            Log.print(ex);
        }
        return table;
    }

    private void addProductosTarimas(PdfPTable table) {
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
        table.addCell(new Phrase("  ", font_bottomtable));
        table.addCell(new Phrase("Total ", font_bottomtable));
        table.addCell(new Phrase("  ", font_bottomtable));
        table.addCell(new Phrase("" + total, font_bottomtable));

    }

    static class CabeceraPieDePagina extends PdfPageEventHelper {

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("fecha de impresion: " + Funciones.getTodayDate(), font_fecha), 450, 800, 0);
            try {
                dhl.setAbsolutePosition(35, 750);
                writer.getDirectContent().addImage(dhl);
                PdfContentByte contentByte = writer.getDirectContent();
                drawLine(contentByte);
                writer.setRgbTransparencyBlending(true);

            } catch (Exception ex) {
                Log.print(ex);
                Log.print(ex);
            }
            createSigningTable().writeSelectedRows(0, -1, 0, -1, 40, 180, writer.getDirectContent());
            String ref = getReferenciaContraloria();
            String cod = getCodigoContraloria();
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(cod, font_fecha), 35, 30, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(ref, font_fecha), 35, 30, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Phrase("" + document.getPageNumber()), 550, 30, 0);

        }

        private String getCodigoContraloria() {
            AccionesCodigo cod = new AccionesCodigo();
            cod.loadDatabaseInstances();
            String codigo = null;
            for (Shareable s : cod.getEntityInstances()) {
                codigo = ((Codigo) s).getCodigo_actual_parte_1();
            }
            return codigo;
        }

        private String getReferenciaContraloria() {
            AccionesCodigo cod = new AccionesCodigo();
            cod.loadDatabaseInstances();
            String codigo = null;
            for (Shareable s : cod.getEntityInstances()) {
                codigo = ((Codigo) s).getCodigo_actual_parte_2();
            }
            return codigo;
        }
    }

    static class CabeceraPieDePagina2 extends PdfPageEventHelper {

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("fecha de impresion: " + Funciones.getTodayDate(), font_fecha), 720, 555, 0);
            try {
                dhl.setAbsolutePosition(45, 525);
                dhl.scalePercent(60);
                writer.getDirectContent().addImage(dhl);
                PdfContentByte contentByte = writer.getDirectContent();
                drawLine2(contentByte);
                writer.setRgbTransparencyBlending(true);

            } catch (Exception ex) {
                Log.print(ex);
                Log.print(ex);
            }
            String ref = getReferenciaContraloria();
            String cod = getCodigoContraloria();
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(cod, font_fecha), 45, 24, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(ref, font_fecha), 480, 24, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Phrase("" + document.getPageNumber()), 780, 24, 0);

        }

        private String getCodigoContraloria() {
            AccionesCodigo cod = new AccionesCodigo();
            cod.loadDatabaseInstances();
            String codigo = null;
            for (Shareable s : cod.getEntityInstances()) {
                codigo = ((Codigo) s).getCodigo_actual_parte_1();
            }
            return codigo;
        }

        private String getReferenciaContraloria() {
            AccionesCodigo cod = new AccionesCodigo();
            cod.loadDatabaseInstances();
            String codigo = null;
            for (Shareable s : cod.getEntityInstances()) {
                codigo = ((Codigo) s).getCodigo_actual_parte_2();
            }
            return codigo;
        }
    }

    
    private static Phrase getPhraseFromChunks(String a, String b) {
        Phrase phrase = new Phrase();
        phrase.add(new Chunk(a, font_info));
        phrase.add(new Chunk(b, font_datos));
        return phrase;
    }

    public void addProductos(DefaultTableModel model, PdfPTable table) {
        int rows = model.getRowCount();
        int total = 0;
        float monto = 0f;

        for (int i = 0; i < rows; i++) {
            PdfPCell cell = new PdfPCell();
            cell.addElement(new Phrase((String) model.getValueAt(i, 2)));

            table.addCell(cell);
            if (ANI) {
                String lote = ((String) model.getValueAt(i, 3));
                String[] l = lote.split("  ");
                String[] l2;
                l2 = l[0].split("00");

                table.addCell(l2[0]);

            } else {
                table.addCell((String) model.getValueAt(i, 3));
            }
            table.addCell((String) model.getValueAt(i, 1));
            int c = (Integer) model.getValueAt(i, 5);
            total += c;
            table.addCell(Integer.toString(c));
            table.addCell("$ " + model.getValueAt(i, 4).toString());
            Float p = new Float(model.getValueAt(i, 6).toString());
            monto += p;
            table.addCell("$ " + String.format("%.2f", p));
        }
        table.addCell(new Phrase("  ", font_bottomtable));
        table.addCell(new Phrase("Total ", font_bottomtable));
        table.addCell(new Phrase("  ", font_bottomtable));
        table.addCell(new Phrase("" + total, font_bottomtable));
        table.addCell(new Phrase("  ", font_bottomtable));
        table.addCell(new Phrase("$ " + String.format("%.2f", monto), font_bottomtable));

    }

}
