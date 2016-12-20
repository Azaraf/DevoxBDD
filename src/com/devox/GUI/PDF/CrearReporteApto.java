/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.GUI.PDF;

import static com.devox.GUI.PDF.ExportPDF.AMARILLO;
import static com.devox.GUI.PDF.ExportPDF.FUENTE_CABECERA;
import static com.devox.GUI.PDF.ExportPDF.FUENTE_CABECERA_TABLA_MEDIANA;
import static com.devox.GUI.PDF.ExportPDF.FUENTE_DATOS_DER;
import static com.devox.GUI.PDF.ExportPDF.FUENTE_DATOS_IZQ;
import static com.devox.GUI.PDF.ExportPDF.FUENTE_TABLA;
import static com.devox.GUI.PDF.ExportPDF.GRIS_CLARO;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.inspira.devox.logger.Log;
import org.inspira.devox.services.other.AccionesCodigo;
import org.inspira.devox.shared.Codigo;
import org.inspira.devox.shared.Shareable;

/**
 *
 * @author azaraf
 */
public class CrearReporteApto implements ExportPDF {

    private static Image LOGO_DHL;
    private static Image TICK;
    private static Image CROSS;
    private String folio;
    private String factura;
    private String folioCliente;
    private String clienteSucursal;
    private String motivo;
    private Object[][] datos;

    private Document document;
    private PdfWriter writer;

    public CrearReporteApto() {
    }

    public CrearReporteApto(String folio, String factura, String folioCliente, String clienteSucursal, String motivo, Object[][] datos) {
        this.folio = folio;
        this.factura = factura;
        this.folioCliente = folioCliente;
        this.clienteSucursal = clienteSucursal;
        this.motivo = motivo;
        this.datos = datos;
    }

    public CrearReporteApto(Object[][] datos) {
        this.datos = datos;
    }

    public CrearReporteApto(String folio, Object[][] datos) {
        this.folio = folio;
        this.datos = datos;
    }

    @Override
    public void crear(File file) {
        try {
            document = new Document(PageSize.A4.rotate());
            file.createNewFile();
            writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            setLogo();
            CabeceraPieDePagina event = new CabeceraPieDePagina();
            writer.setPageEvent(event);
            document.setMargins(30, 30, 40, 360);
            document.newPage();
            Paragraph p = new Paragraph("RELACIÓN DE PRODUCTO APTO", FUENTE_TITULO_APTO);
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);
            document.add(configurarInformacion());
            PdfPTable table = crearTabla();
            agregarProductos(table);
            document.add(table);
            document.close();

        } catch (IOException | DocumentException ex) {
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
        cell = new PdfPCell(getPhraseFromChunks("FACTURA ", factura));
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(getPhraseFromChunks("FECHA FACTURA", ""));
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setRowspan(2);
        table.addCell(cell);
        cell = new PdfPCell(getPhraseFromChunks("FOLIO CLIENTE ", folioCliente));
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(getPhraseFromChunks("CLIENTE ", clienteSucursal));
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(getPhraseFromChunks("MOTIVO ", motivo));
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
        table.setTotalWidth(400);
        return table;
    }

    @Override
    public PdfPTable crearTabla() {
        PdfPTable table = new PdfPTable(8);
        //new float[]{2.4f, 3f, 1.25f, 1.2f, 1f, 1.15f, 1.1f, 1.55f}
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("CÓDIGO", FUENTE_CABECERA_TABLA_CHICA));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorRight(GRIS_CLARO);
        cell.setFixedHeight(20f);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("DESCRIPCIÓN", FUENTE_CABECERA_TABLA_CHICA));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorLeft(GRIS_CLARO);
        cell.setBorderColorRight(GRIS_CLARO);
        cell.setBorderColorTop(AMARILLO);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("LOTE", FUENTE_CABECERA_TABLA_CHICA));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorLeft(GRIS_CLARO);
        cell.setBorderColorRight(GRIS_CLARO);
        cell.setBorderColorTop(AMARILLO);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("FECHA DE CADUCIDAD", FUENTE_CABECERA_TABLA_CHICA));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorLeft(GRIS_CLARO);
        cell.setBorderColorRight(GRIS_CLARO);
        cell.setBorderColorTop(AMARILLO);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("CANTIDAD", FUENTE_CABECERA_TABLA_CHICA));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorLeft(GRIS_CLARO);
        cell.setBorderColorRight(GRIS_CLARO);
        cell.setBorderColorTop(AMARILLO);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("DICTAMEN", FUENTE_CABECERA_TABLA_CHICA));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorLeft(GRIS_CLARO);
        cell.setBorderColorRight(AMARILLO);
        cell.setBorderColorTop(AMARILLO);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("PRECIO", FUENTE_CABECERA_TABLA_CHICA));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorLeft(GRIS_CLARO);
        cell.setBorderColorRight(AMARILLO);
        cell.setBorderColorTop(AMARILLO);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("OBSERVACIONES", FUENTE_CABECERA_TABLA_CHICA));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorLeft(GRIS_CLARO);
        cell.setBorderColorRight(AMARILLO);
        cell.setBorderColorTop(AMARILLO);
        table.addCell(cell);
        try {
            table.setWidths(new float[]{2.4f, 3f, 1.25f, 1.2f, 1f, 1.15f, 1.1f, 1.55f});
            table.setWidthPercentage(100);

        } catch (DocumentException ex) {
            Log.print(ex);
        }
        return table;
    }

    @Override
    public void agregarProductos(PdfPTable table) {
        int rows = datos.length;
        Log.print("rows: " + rows);
        int total = 0;
        float precio = 0;
        for (int i = 0; i < rows; i++) {
            // Código
            PdfPCell cell = new PdfPCell(new Phrase(datos[i][0].toString(), FUENTE_TABLA));
            cell.setFixedHeight(21f);
            table.addCell(cell);
            // Descripción
            table.addCell(new Phrase(datos[i][1].toString(), FUENTE_TABLA));
            // Lote
            table.addCell(new Phrase(datos[i][2].toString(), FUENTE_TABLA));
            // Fecha de caducidad
            table.addCell(new Phrase("  "));
            // Cantidad
            int c = ((Integer) datos[i][3]);
            total += c;
            table.addCell(new Phrase(datos[i][3].toString(), FUENTE_TABLA));
            // Dictamen
            table.addCell(new Phrase("  "));
            // Precio
            float p = new Float(datos[i][4].toString());
            precio += (c * p);
            table.addCell(new Phrase(datos[i][4].toString(), FUENTE_TABLA));
            // Observaciones
            table.addCell(new Phrase("  "));

        }
        table.addCell(new Phrase("  ", FUENTE_BASE_TABLA));
        table.addCell(new Phrase("  ", FUENTE_BASE_TABLA));
        table.addCell(new Phrase("  ", FUENTE_BASE_TABLA));
        table.addCell(new Phrase("SUMA ", FUENTE_BASE_TABLA));
        table.addCell(new Phrase("" + total, FUENTE_BASE_TABLA));
        table.addCell(new Phrase("  ", FUENTE_BASE_TABLA));
        table.addCell(new Phrase("$ " + String.format("%.2f", precio), FUENTE_BASE_TABLA));
        table.addCell(new Phrase("  ", FUENTE_BASE_TABLA));
    }

    @Override
    public void setLogo() {
        try {
            URL url = Main.class.getResource("dhl.png");
            LOGO_DHL = (Image.getInstance(url));LOGO_DHL.scaleAbsolute(110f, 25.2f);
            URL url1 = Main.class.getResource("tick.png");
            TICK = (Image.getInstance(url1));
            URL url2 = Main.class.getResource("cross.png");
            CROSS = (Image.getInstance(url2));
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
            ColumnText.showTextAligned(
                    writer.getDirectContent(),
                    Element.ALIGN_CENTER,
                    new Phrase(
                            getPhraseFromChunks(
                                    "FOLIO ",
                                    folio
                            )),
                    720,
                    560,
                    0
            );
            try {
                LOGO_DHL.setAbsolutePosition(15, 560);
                writer.getDirectContent().addImage(LOGO_DHL);
                contentByte = writer.getDirectContent();
                drawLine();
                writer.setRgbTransparencyBlending(true);

            } catch (Exception ex) {
                Log.print(ex);
            }
            createVerificationTable().writeSelectedRows(0, -1, 0, -1, 30, 350, writer.getDirectContent());
            createSigningTable().writeSelectedRows(0, -1, 0, -1, 30, 120, writer.getDirectContent());
            String ref = getReferenciaContraloria();
            String cod = getCodigoContraloria();
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(cod, FUENTE_CABECERA), 480, 24, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(ref, FUENTE_CABECERA), 30, 24, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Phrase("Página " + document.getPageNumber()), 780, 24, 0);

        }

        @Override
        public String getCodigoContraloria() {
            AccionesCodigo cod = new AccionesCodigo();
            cod.loadDatabaseInstances();
            if (cod != null) {
                String codigo = null;
                for (Shareable s : cod.getEntityInstances()) {
                    codigo = ((Codigo) s).getCodigo_actual_parte_3();
                }
                
                return codigo;
            } else {
                return "SIN CODIGO";
            }
        }

        @Override
        public String getReferenciaContraloria() {
            AccionesCodigo cod = new AccionesCodigo();
            cod.loadDatabaseInstances();
            if (cod != null) {
                String codigo = null;
                for (Shareable s : cod.getEntityInstances()) {
                    codigo = ((Codigo) s).getCodigo_actual_parte_1();
                }
                return codigo;
            } else {
                return "SIN CODIGO";
            }
        }

        @Override
        public void drawLine() {
        }

        private PdfPTable createVerificationTable() {
            PdfPTable table = new PdfPTable(7);
            PdfPCell cell;
            cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
            cell.setBackgroundColor(AMARILLO);
            cell.setBorderColorBottom(BaseColor.BLACK);
            cell.setBorderColorRight(GRIS_CLARO);
            cell.setFixedHeight(19f);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("LOTE 1", FUENTE_CABECERA_TABLA_MEDIANA));
            cell.setBackgroundColor(AMARILLO);
            cell.setBorderColorBottom(BaseColor.BLACK);
            cell.setBorderColorLeft(GRIS_CLARO);
            cell.setBorderColorRight(GRIS_CLARO);
            cell.setBorderColorTop(AMARILLO);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("LOTE 2", FUENTE_CABECERA_TABLA_MEDIANA));
            cell.setBackgroundColor(AMARILLO);
            cell.setBorderColorBottom(BaseColor.BLACK);
            cell.setBorderColorLeft(GRIS_CLARO);
            cell.setBorderColorRight(GRIS_CLARO);
            cell.setBorderColorTop(AMARILLO);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("LOTE 3", FUENTE_CABECERA_TABLA_MEDIANA));
            cell.setBackgroundColor(AMARILLO);
            cell.setBorderColorBottom(BaseColor.BLACK);
            cell.setBorderColorLeft(GRIS_CLARO);
            cell.setBorderColorRight(GRIS_CLARO);
            cell.setBorderColorTop(AMARILLO);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("LOTE 4", FUENTE_CABECERA_TABLA_MEDIANA));
            cell.setBackgroundColor(AMARILLO);
            cell.setBorderColorBottom(BaseColor.BLACK);
            cell.setBorderColorLeft(GRIS_CLARO);
            cell.setBorderColorRight(GRIS_CLARO);
            cell.setBorderColorTop(AMARILLO);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("LOTE 5", FUENTE_CABECERA_TABLA_MEDIANA));
            cell.setBackgroundColor(AMARILLO);
            cell.setBorderColorBottom(BaseColor.BLACK);
            cell.setBorderColorLeft(GRIS_CLARO);
            cell.setBorderColorRight(GRIS_CLARO);
            cell.setBorderColorTop(AMARILLO);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("DICTAMEN", FUENTE_BASE_TABLA));
            cell.setBackgroundColor(AMARILLO);
            cell.setBorderColorBottom(BaseColor.BLACK);
            cell.setBorderColorLeft(GRIS_CLARO);
            cell.setBorderColorRight(GRIS_CLARO);
            cell.setBorderColorTop(AMARILLO);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("No. de piezas muestreadas"));
            cell.setFixedHeight(18.3f);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(" "));
            for (int i = 0; i < 5; i++) {
                table.addCell(cell);
            }

            Phrase p = new Phrase();
            p.add("Cumple ");
            p.add(new Chunk(TICK, 0, 0, true));
            cell = new PdfPCell(p);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("No. de lista"));
            cell.setFixedHeight(18.3f);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(" "));
            for (int i = 0; i < 5; i++) {
                table.addCell(cell);
            }

            p = new Phrase();
            p.add("No cumple ");
            p.add(new Chunk(CROSS, 0, 0, true));
            cell = new PdfPCell(p);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Lote"));
            cell.setFixedHeight(18.3f);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(" "));
            for (int i = 0; i < 5; i++) {
                table.addCell(cell);
            }

            p = new Phrase();
            p.add("No Aplica ");
            p.add(new Chunk("N/A", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD)));
            cell = new PdfPCell(p);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Caducidad"));
            cell.setFixedHeight(18.3f);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(" "));
            for (int i = 0; i < 5; i++) {
                table.addCell(cell);
            }

            cell = new PdfPCell(new Phrase(" "));
            cell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Presentación"));
            cell.setFixedHeight(18.3f);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(" "));
            for (int i = 0; i < 5; i++) {
                table.addCell(cell);
            }

            cell = new PdfPCell(new Phrase(" "));
            cell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Calidad del producto"));
            cell.setFixedHeight(18.3f);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(" "));
            for (int i = 0; i < 5; i++) {
                table.addCell(cell);
            }

            cell = new PdfPCell(new Phrase(" "));
            cell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Código de etiqueta"));
            cell.setFixedHeight(18.3f);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(" "));
            for (int i = 0; i < 5; i++) {
                table.addCell(cell);
            }

            cell = new PdfPCell(new Phrase(" "));
            cell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Código de caja individual"));
            cell.setFixedHeight(18.3f);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(" "));
            for (int i = 0; i < 5; i++) {
                table.addCell(cell);
            }

            cell = new PdfPCell(new Phrase(" "));
            cell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Etiqueta de caja colectiva"));
            cell.setFixedHeight(18.3f);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(" "));
            for (int i = 0; i < 5; i++) {
                table.addCell(cell);
            }

            cell = new PdfPCell(new Phrase(" "));
            cell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Inspección visual"));
            cell.setFixedHeight(18.3f);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(" "));
            for (int i = 0; i < 5; i++) {
                table.addCell(cell);
            }

            cell = new PdfPCell(new Phrase(" "));
            cell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell);
            /**
             *
             */
            try{
            table.setWidths(new float[]{1.4f,1f,1f,1f,1f,1f,1f});
            table.setTotalWidth(800);
            }catch(DocumentException ex){
                ex.printStackTrace();
            }

            return table;
        }

        private PdfPTable createSigningTable() {
            PdfPTable table = new PdfPTable(4);
            PdfPCell cell;
            cell = new PdfPCell(new Phrase("RECIBIÓ", FUENTE_CABECERA_TABLA_MEDIANA));
            cell.setBackgroundColor(AMARILLO);
            cell.setBorderColorBottom(BaseColor.BLACK);
            cell.setBorderColorRight(GRIS_CLARO);
            cell.setFixedHeight(20f);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("DICTAMINÓ QA", FUENTE_CABECERA_TABLA_MEDIANA));
            cell.setBackgroundColor(AMARILLO);
            cell.setBorderColorBottom(BaseColor.BLACK);
            cell.setBorderColorLeft(GRIS_CLARO);
            cell.setBorderColorRight(GRIS_CLARO);
            cell.setBorderColorTop(AMARILLO);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("CAPTURÓ DHL", FUENTE_CABECERA_TABLA_MEDIANA));
            cell.setBackgroundColor(AMARILLO);
            cell.setBorderColorBottom(BaseColor.BLACK);
            cell.setBorderColorLeft(GRIS_CLARO);
            cell.setBorderColorRight(GRIS_CLARO);
            cell.setBorderColorTop(AMARILLO);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("RECIBIÓ ABBOTT", FUENTE_CABECERA_TABLA_MEDIANA));
            cell.setBackgroundColor(AMARILLO);
            cell.setBorderColorBottom(BaseColor.BLACK);
            cell.setBorderColorLeft(GRIS_CLARO);
            cell.setBorderColorRight(GRIS_CLARO);
            cell.setBorderColorTop(AMARILLO);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("  "));
            cell.setFixedHeight(36f);
            for (int i = 0; i < 4; i++) {
                table.addCell(cell);
            }

            cell = new PdfPCell(new Phrase("NOMBRE Y FIRMA", FUENTE_TABLA));
            cell.setFixedHeight(20f);
            cell.setBackgroundColor(GRIS_CLARO);
            for (int i = 0; i < 4; i++) {
                table.addCell(cell);
            }

            table.setTotalWidth(700);
            return table;
        }
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getFolioCliente() {
        return folioCliente;
    }

    public void setFolioCliente(String folioCliente) {
        this.folioCliente = folioCliente;
    }

    public String getClienteSucursal() {
        return clienteSucursal;
    }

    public void setClienteSucursal(String clienteSucursal) {
        this.clienteSucursal = clienteSucursal;
    }

    public Object[][] getDatos() {
        return datos;
    }

    public void setDatos(Object[][] datos) {
        this.datos = datos;
    }

    
}
