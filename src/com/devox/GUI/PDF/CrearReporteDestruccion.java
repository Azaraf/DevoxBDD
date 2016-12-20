/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devox.GUI.PDF;

import static com.devox.GUI.PDF.ExportPDF.FUENTE_TITULO_APTO;
import com.devox.GUI.tables.DevoxTableModel;
import com.devox.LOG.Entidades.ContenidoDevolucion;
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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.inspira.devox.services.other.AccionesCodigo;
import org.inspira.devox.services.queries.FolioJoinSucursal;
import org.inspira.devox.shared.Codigo;
import org.inspira.devox.shared.Shareable;

/**
 *
 * @author azaraf
 */
public class CrearReporteDestruccion implements ExportPDF {

    private final DefaultTableModel model;
    private String tipo;
    private Document document;
    private PdfWriter writer;
    private static Image LOGO_DHL;
    private ContenidoDevolucion contenido;
    private boolean ANI = false;
    private FolioJoinSucursal fjs;

    public CrearReporteDestruccion(DefaultTableModel model, ContenidoDevolucion contenido, String qm) {
        this.model = model;
        this.contenido = contenido;
        this.ANI = qm.equals("ANI") || qm.equals("EPD") || qm.equals("AV");
        this.tipo = TIPO_DESTRUCCION;
    }

    public CrearReporteDestruccion(DevoxTableModel model, FolioJoinSucursal fjs, String qm) {
        this.model = model;
        this.fjs = fjs;
        this.ANI = qm.equals("ANI") || qm.equals("EPD") || qm.equals("AV");
        this.tipo = TIPO_DESTRUCCION;

        contenido = new ContenidoDevolucion();
        contenido.setFolioDHL(fjs.getFolio());
        contenido.setSucursal(fjs.getSucursal().getCodigoSucursal());
        contenido.setFolioCliente(fjs.getDescripcionDevolucion().getDocumentoCliente());
        contenido.setFolioAbbott(fjs.getDescripcionDevolucion().getFolioAbbot());
        contenido.setMotivo(fjs.getMotivo().getCodigo());
        contenido.setFactura(fjs.getDescripcionDevolucion().getFactura());
        contenido.setAlmacen(fjs.getTarimas()[0].getAlmacen_cv());
        contenido.setFechaCaptura(fjs.getDescripcionDevolucion().getFechaCaptura());
    }

    @Override
    public void crear(File file) {
        try {
            document = new Document(PageSize.A4, 50f, 50f, 100f, 220f);
            file.createNewFile();
            writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            setLogo();
            CabeceraPieDePagina event = new CabeceraPieDePagina();
            writer.setPageEvent(event);
            document.newPage();
            Paragraph p = new Paragraph("RELACIÓN DE PRODUCTOS PARA DESTRUCCIÓN", FUENTE_TITULO_APTO);
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
        if (open == JOptionPane.YES_OPTION) {
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
            Phrase folio = new Phrase();
            folio.add(new Chunk("FOLIO DHL ", FUENTE_FOLIO_CHICA));
            folio.add(new Chunk(contenido.getFolioDHL(), FUENTE_FOLIO_CHICA_ROJA));
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
        }
        return table;
    }

    @Override
    public PdfPTable crearTabla() {
        PdfPTable table = new PdfPTable(6);
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("DESCRIPCIÓN", FUENTE_CABECERA_TABLA_CHICA));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorRight(GRIS_CLARO);
        cell.setFixedHeight(20f);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("LOTE", FUENTE_CABECERA_TABLA_CHICA));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorLeft(GRIS_CLARO);
        cell.setBorderColorRight(GRIS_CLARO);
        cell.setBorderColorTop(AMARILLO);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("CÓDIGO", FUENTE_CABECERA_TABLA_CHICA));
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

        cell = new PdfPCell(new Phrase("PRECIO UNITARIO", FUENTE_CABECERA_TABLA_CHICA));
        cell.setBackgroundColor(AMARILLO);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderColorLeft(GRIS_CLARO);
        cell.setBorderColorRight(GRIS_CLARO);
        cell.setBorderColorTop(AMARILLO);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("SUBTOTAL", FUENTE_CABECERA_TABLA_CHICA));
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
        }
        return table;

    }

    @Override
    public void agregarProductos(PdfPTable table) {
        int rows = model.getRowCount();
        int total = 0;
        float monto = 0f;

        for (int i = 0; i < rows; i++) {
            PdfPCell cell = new PdfPCell();
            cell.addElement(new Phrase((String) model.getValueAt(i, 2), FUENTE_TABLA));

            table.addCell(cell);
            if (ANI) {
                try {
                    String lotereal = ((String) model.getValueAt(i, 3));
                    System.out.println("Lote original: " + lotereal);
                    String lotecorto = lotereal.substring(0, 7);
                    System.out.println("Lote acortado: " + lotecorto);

                    table.addCell(new Phrase(lotecorto, FUENTE_TABLA));
                } catch (Exception ex) {
                    table.addCell(new Phrase((String) model.getValueAt(i, 3), FUENTE_TABLA));
                }
            } else {
                table.addCell(new Phrase((String) model.getValueAt(i, 3), FUENTE_TABLA));
            }
            table.addCell(new Phrase((String) model.getValueAt(i, 1), FUENTE_TABLA));
            int c = (Integer) model.getValueAt(i, 5);
            total += c;
            table.addCell(new Phrase(Integer.toString(c), FUENTE_TABLA));
            table.addCell(new Phrase("$ " + model.getValueAt(i, 4).toString(), FUENTE_TABLA));
            Float p = new Float(model.getValueAt(i, 6).toString());
            monto += p;
            table.addCell(new Phrase("$ " + String.format("%.2f", p), FUENTE_TABLA));
        }
        table.addCell(new Phrase("  ", FUENTE_BASE_TABLA));
        table.addCell(new Phrase("Total ", FUENTE_BASE_TABLA));
        table.addCell(new Phrase("  ", FUENTE_BASE_TABLA));
        table.addCell(new Phrase("" + total, FUENTE_BASE_TABLA));
        table.addCell(new Phrase("  ", FUENTE_BASE_TABLA));
        table.addCell(new Phrase("$ " + String.format("%.2f", monto), FUENTE_BASE_TABLA));

    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("fecha de impresion: " + Funciones.getTodayDate(), FUENTE_CABECERA), 450, 800, 0);
            ColumnText.showTextAligned(
                    writer.getDirectContent(),
                    Element.ALIGN_CENTER,
                    new Phrase(
                            getPhraseFromChunks(
                                    "FOLIO ",
                                    String.format("%06d", fjs.getFolio())
                            )),
                    360,
                    800,
                    0
            );
            try {
                LOGO_DHL.setAbsolutePosition(35, 750);
                LOGO_DHL.scalePercent(60);
                writer.getDirectContent().addImage(LOGO_DHL);
                contentByte = writer.getDirectContent();
                drawLine();
                writer.setRgbTransparencyBlending(true);

            } catch (Exception ex) {
                Log.print(ex);
            }
            createSigningTable().writeSelectedRows(0, -1, 0, -1, 40, 180, writer.getDirectContent());
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
                codigo = ((Codigo) s).getCodigo_actual_parte_2();
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

        private PdfPTable createSigningTable() {
            PdfPTable table = new PdfPTable(4);
            PdfPCell cell;
            cell = new PdfPCell(new Phrase("RMA", FUENTE_CABECERA_TABLA_MEDIANA));
            cell.setBackgroundColor(AMARILLO);
            cell.setBorderColorBottom(BaseColor.BLACK);
            cell.setBorderColorRight(GRIS_CLARO);
            cell.setFixedHeight(25f);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("ORDEN", FUENTE_CABECERA_TABLA_MEDIANA));
            cell.setBackgroundColor(AMARILLO);
            cell.setBorderColorBottom(BaseColor.BLACK);
            cell.setBorderColorLeft(GRIS_CLARO);
            cell.setBorderColorRight(GRIS_CLARO);
            cell.setBorderColorTop(AMARILLO);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("ELABORÓ", FUENTE_CABECERA_TABLA_MEDIANA));
            cell.setBackgroundColor(AMARILLO);
            cell.setBorderColorBottom(BaseColor.BLACK);
            cell.setBorderColorLeft(GRIS_CLARO);
            cell.setBorderColorRight(GRIS_CLARO);
            cell.setBorderColorTop(AMARILLO);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("DICTAMINÓ QA", FUENTE_CABECERA_TABLA_MEDIANA));
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

            cell = new PdfPCell(new Phrase("NOMBRE Y FIRMA", FUENTE_TABLA));
            cell.setFixedHeight(25f);
            cell.setBackgroundColor(GRIS_CLARO);
            for (int i = 0; i < 2; i++) {
                table.addCell(cell);
            }

            table.setTotalWidth(500);
            return table;
        }
    }
}
