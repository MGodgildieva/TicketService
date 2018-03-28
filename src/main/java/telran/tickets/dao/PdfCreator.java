package telran.tickets.dao;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.barcodes.Barcode128;
import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

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
	public void createTicketInRectangle(EventSeat eventSeat, PdfDocument pdf) throws IOException {
		float width = pdf.getDefaultPageSize().getWidth();
	    float height = pdf.getDefaultPageSize().getHeight();
	    PdfCanvas pdfCanvas = new PdfCanvas(pdf.addNewPage());
	    Rectangle rectangle = new Rectangle(20, 500, width - 40, height - 520);
	    pdfCanvas.rectangle(rectangle);
	    ImageData background = ImageDataFactory.create("logoColoured@2x.png");
	    pdfCanvas.saveState();
        PdfExtGState state = new PdfExtGState();
        state.setFillOpacity(0.3f);
        pdfCanvas.setExtGState(state);
        pdfCanvas.addImage(background, 20, 550, width - 40, false);
        pdfCanvas.restoreState();
	    PdfFont boldFont = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
	    PdfFont italics = PdfFontFactory.createFont(FontConstants.TIMES_ITALIC);
	    Canvas canvas = new Canvas(pdfCanvas, pdf, rectangle);
	    canvas.setFontSize(24);
	    BarcodeQRCode qrCode = new BarcodeQRCode(eventSeat.getId().toString());
		Rectangle rect = qrCode.getBarcodeSize();
        PdfFormXObject template = new PdfFormXObject(new Rectangle(rect.getWidth(), rect.getHeight() + 10));
        PdfCanvas templateCanvas = new PdfCanvas(template, pdf);
        new Canvas(templateCanvas, pdf, new Rectangle(rect.getWidth(), rect.getHeight() + 10))
                .showTextAligned(new Paragraph().setFontSize(6), 0, rect.getHeight() + 2, TextAlignment.LEFT);
        qrCode.placeBarcode(templateCanvas, com.itextpdf.kernel.color.Color.BLACK);
        Image qrImage = new Image(template);
        qrImage.scaleToFit(200,200);
        qrImage.setFixedPosition(400, 660);
		Barcode128 code128 = new Barcode128(pdf);
	    code128.setCode(eventSeat.getId().toString());
	    code128.setCodeType(Barcode128.CODE128);
        rect = code128.getBarcodeSize();
        template = new PdfFormXObject(new Rectangle(rect.getWidth(), rect.getHeight() + 10));
        templateCanvas = new PdfCanvas(template, pdf);
        new Canvas(templateCanvas, pdf, new Rectangle(rect.getWidth(), rect.getHeight() + 10))
                .showTextAligned(new Paragraph().setFontSize(6), 0, rect.getHeight() + 2, TextAlignment.LEFT);
        code128.placeBarcode(templateCanvas, com.itextpdf.kernel.color.Color.BLACK, com.itextpdf.kernel.color.Color.BLACK);
        Image image = new Image(template);
        image.scaleToFit(300, 50);
        image.setFixedPosition(190, 500);
        Date date = eventSeat.getEvent().getDate();
        String dateStr = new SimpleDateFormat("dd/MM/yyyy").format(date);
		canvas.add(new Paragraph(eventSeat.getEvent().getTitle()).setMultipliedLeading((float) 1.2).setFirstLineIndent(10).setFont(boldFont).setMarginTop(30));
		canvas.add(new Paragraph(dateStr + " & " + eventSeat.getEvent().getTime()).setMultipliedLeading((float) 1.2).setFirstLineIndent(10).setFont(boldFont).setUnderline());
		canvas.add(new Paragraph(eventSeat.getEvent().getHall().getHallName()).setMultipliedLeading((float) 1).setFirstLineIndent(10).setFontSize(20).setMarginTop(15).setFont(italics));
		canvas.add(new Paragraph(eventSeat.getEvent().getHall().getCity() + ", " + eventSeat.getEvent().getHall().getStreet() + ", " + eventSeat.getEvent().getHall().getHouse()).setMultipliedLeading((float) 1).setFirstLineIndent(10).setFontSize(20).setFont(italics));
		canvas.add(new Paragraph("Seat: Row № " + eventSeat.getSeat().getRealRow() + ", Place № "  + eventSeat.getSeat().getRealPlace() + ", Price: #" + eventSeat.getPrice() + "$").setMultipliedLeading((float) 1.2).setFirstLineIndent(10).setFont(boldFont).setMarginTop(20).setUnderline());
		canvas.add(qrImage);
		canvas.add(image);
	    canvas.close();
	    pdfCanvas.stroke();
	}

	public byte[] createTicketWithoutSaving() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
		createTicketInRectangle(eventSeat, pdfDoc);
		pdfDoc.close();
		byte [] pdfToBytes = baos.toByteArray();
		baos.close();
		return pdfToBytes;
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
