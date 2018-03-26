package telran.tickets.dao;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

public class PdfCreator {
	String ticketId;
	String eventTitle;
	String eventDate;
	String eventTime;
	String hallName;
	String row;
	String place;
	String price;
	
	
	public PdfCreator(String ticketId, String eventTitle, String eventDate, String eventTime, String hallName,
			String row, String place, String price) {
		this.ticketId = ticketId;
		this.eventTitle = eventTitle;
		this.eventDate = eventDate;
		this.eventTime = eventTime;
		this.hallName = hallName;
		this.row = row;
		this.place = place;
		this.price = price;
	}

	public PdfCreator() {
	}


	public void createTicketInFolder() throws IOException {
		PdfWriter writer = new PdfWriter("D:\\TicketDocs\\ticket_example.pdf");
		PdfDocument pdf = new PdfDocument(writer);
		Document document = createTicket(pdf);
		document.close();
	}
	
	public Document createTicket(PdfDocument pdf) throws IOException {
		Document document = new Document(pdf);
		document.setMargins(50, 50, 50, 50);
		PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
		PdfFont boldFont = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
		document.setFont(font);
		document.setFontSize(18);
		Border border = new SolidBorder(1);
		Image logo = new Image(ImageDataFactory.create("D:\\TicketDocs\\trash\\logoColoured.png"));
		logo.setFixedPosition(370, 750);
		BarcodeQRCode barcode = new BarcodeQRCode(ticketId);
		java.awt.Image barcodeImg = barcode.createAwtImage(Color.BLACK, Color.WHITE);
		Image barcodeImage = new Image(ImageDataFactory.create(barcodeImg, null));
		barcodeImage.setFixedPosition(370, 550);
		barcodeImage.setWidth(150);
		barcodeImage.setHeight(150);
		Paragraph title = new Paragraph("Ticket Service");
		title.setFont(boldFont);
		title.setFontSize(24);
		title.setUnderline();
		title.setHeight(60);
		document.add(title);
		document.add(logo);
		document.add(new Paragraph(eventTitle + "\n" + eventDate + " & " + eventTime).setWidth(200).setBorder(border).setFont(boldFont).setKeepTogether(true));
		document.add(new Paragraph(hallName).setKeepWithNext(true));
		document.add(new Paragraph("Seat: " + row + ", " + place).setKeepWithNext(true));
		document.add(new Paragraph(price));
		document.add(barcodeImage);
		return document;
	}

	public byte[] createTicketWithoutSaving() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
		Document doc =  createTicket(pdfDoc);
		doc.close();
		byte [] pdfToBytes = baos.toByteArray();
		baos.close();
		return pdfToBytes;
	}
	
	public void getPdfFromByteArray(byte[] arr) throws IOException {
		File someFile = new File("D:\\TicketDocs\\ticket.pdf");
        FileOutputStream fos = new FileOutputStream(someFile);
        fos.write(arr);
        fos.flush();
        fos.close();
	}

}
