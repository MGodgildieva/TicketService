package telran.tickets.dao;

import java.io.ByteArrayOutputStream;
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
}
