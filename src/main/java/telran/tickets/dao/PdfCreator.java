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

import telran.tickets.entities.objects.EventSeat;

public class PdfCreator {
	EventSeat eventSeat;

	public PdfCreator() {
	}

	public PdfCreator(EventSeat eventSeat) {
		this.eventSeat = eventSeat;
	}




	public void createTicketInFolder() throws IOException {
		PdfWriter writer = new PdfWriter("D:\\TicketDocs\\ticket_example.pdf");
		PdfDocument pdf = new PdfDocument(writer);
		Document document = new Document(pdf);
		createPage(eventSeat, document);
		document.close();
	}
	
	public void createPage(EventSeat eventSeat, Document document) throws IOException {
		PdfFont boldFont = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
		Border border = new SolidBorder(1);
		Image logo = new Image(ImageDataFactory.create("logoColoured.png"));
		logo.setFixedPosition(370, 750);
		BarcodeQRCode barcode = new BarcodeQRCode(eventSeat.getId().toString());
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
		document.add(new Paragraph(eventSeat.getEvent().getTitle() + "\n" + eventSeat.getEvent().getDate().toString() + " & " + eventSeat.getEvent().getTime()).setWidth(200).setBorder(border).setFont(boldFont).setKeepTogether(true));
		document.add(new Paragraph(eventSeat.getEvent().getHall().getHallName()).setKeepWithNext(true));
		document.add(new Paragraph(eventSeat.getEvent().getHall().getCity() + ", " + eventSeat.getEvent().getHall().getStreet() + ", "+eventSeat.getEvent().getHall().getHouse()));
		document.add(new Paragraph("Seat: Row: " + eventSeat.getSeat().getRealRow() + ", Place: " + eventSeat.getSeat().getRealPlace()).setKeepWithNext(true));
		document.add(new Paragraph(eventSeat.getPrice()));
		document.add(barcodeImage);
	}

	public byte[] createTicketWithoutSaving() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = createTicket(baos);
		createPage(eventSeat, document);
		return saveDocToByteArray(document, baos);
	}
	
	public Document createTicket(ByteArrayOutputStream baos) throws IOException {
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
		Document document = new Document(pdfDoc);
		document.setMargins(50, 50, 50, 50);
		PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
		document.setFont(font);
		document.setFontSize(18);
		return document;
	}
	public byte[] saveDocToByteArray(Document document, ByteArrayOutputStream baos) throws IOException {
		document.close();
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
