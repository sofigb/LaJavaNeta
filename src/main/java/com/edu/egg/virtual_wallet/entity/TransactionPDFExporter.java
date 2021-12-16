package com.edu.egg.virtual_wallet.entity;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class TransactionPDFExporter {
    private List<Transaction> listTransaction;

    public TransactionPDFExporter(List<Transaction> listTransaction) {
        this.listTransaction = listTransaction;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.WHITE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("FECHA", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("NRO CUENTA", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("NOMBRE CONTACTO", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("NRO CUENTA CONTACTO", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("MONTO", font));
        table.addCell(cell);
//monto,fecha,numero de cuenta ,nombre y numero de cuenta del contacto frecuente
    }

    private void writeTableData(PdfPTable table) {
        for (Transaction transaction : listTransaction) {

            table.addCell(transaction.getTimeStamp().toString());
//numero del que envia la transaccion
            table.addCell(transaction.getSenderAccount().getNumber().toString());

            table.addCell(transaction.getPayee().getName());

            table.addCell(transaction.getPayee().getAccountNumber().toString());

            table.addCell(transaction.getAmount().toString());

        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("TRANSACCIONES", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);


        document.add(table);

        document.close();

    }


}
