package com.parkinglabel.util;

import java.util.Map;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.parkinglabel.model.Label;
 
/**
 * This view class generates a PDF document 'on the fly' based on the data
 * contained in the model.
 *
 */
public class PDFBuilder extends AbstractITextPdfView {
 
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // get data model which is passed by the Spring container
        Label label = (Label) model.get("label");
        
        Font fontbold = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
        
        Paragraph heading = new Paragraph("DEPARTMENT OF ELECTRONICS & INFORMATION TECHNOLOGY",fontbold);
        heading.setAlignment(Element.ALIGN_CENTER); 
        doc.add(heading);
       
        Paragraph subheading = new Paragraph("6,CGO COMPLEX, ELECTRONICS NIKETAN",fontbold);
        subheading.setAlignment(Element.ALIGN_CENTER); 
        doc.add(subheading);
        
        Paragraph SUBheading2 = new Paragraph("NEW DELHI - 110093",fontbold);
        SUBheading2.setAlignment(Element.ALIGN_CENTER); 
        doc.add(SUBheading2);
        
        doc.add( Chunk.NEWLINE );
        
        Paragraph miniheading = new Paragraph("PERFORMA",fontbold);
        miniheading.setAlignment(Element.ALIGN_CENTER); 
        doc.add(miniheading);
        
        Paragraph miniheadingdesc = new Paragraph("(For issue of Parking Label)",fontbold);
        miniheadingdesc.setAlignment(Element.ALIGN_CENTER); 
        doc.add(miniheadingdesc);
        
        doc.add(Chunk.NEWLINE);

        
        
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] {4.0f, 6.0f});
        table.setSpacingBefore(10); 
        
        // define table cells
        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);
        cell.setBorder(Rectangle.NO_BORDER);
        
        PdfPCell cell2 = new PdfPCell();
        cell2.setPadding(5);
        cell2.setBorder(Rectangle.BOTTOM);
        
        //Row 1
        cell.setPhrase(new Phrase("1. Name"));
        table.addCell(cell);
    
        cell2.setPhrase(new Phrase(label.getName().toUpperCase()));
        table.addCell(cell2);
        
        //Row 2
        cell.setPhrase(new Phrase("2. Designation"));
        table.addCell(cell);
    
        cell2.setPhrase(new Phrase(label.getDesignation().toUpperCase()));
        table.addCell(cell2);
        
        //Row 3
        cell.setPhrase(new Phrase("3. Division/ Section/ Soiety where    Working"));
        table.addCell(cell);
    
        cell2.setPhrase(new Phrase(label.getDivision().toUpperCase()));
        table.addCell(cell2);
        
        //Row 4
        cell.setPhrase(new Phrase("4. Intercom / Telephone No. (O)"));
        table.addCell(cell);
    
        cell2.setPhrase(new Phrase(label.getTelephone()));
        table.addCell(cell2);
        
        //Row 4.1
        cell.setPhrase(new Phrase("   Mobile No."));
        table.addCell(cell);
    
        cell2.setPhrase(new Phrase(label.getMobile()));
        table.addCell(cell2);
        
        //Row 5
        cell.setPhrase(new Phrase("5. Present Local Address"));
        table.addCell(cell);
    
        cell2.setPhrase(new Phrase(label.getAddress().toUpperCase()));
        table.addCell(cell2);
        
        //Row 6
        cell.setPhrase(new Phrase("6. Make of Vehicle"));
        table.addCell(cell);
    
        cell2.setPhrase(new Phrase(label.getMake().toUpperCase()));
        table.addCell(cell2);
         
        //Row 7
        cell.setPhrase(new Phrase("7. Model of Vehicle"));
        table.addCell(cell);
    
        cell2.setPhrase(new Phrase(label.getName().toUpperCase()));
        table.addCell(cell2);
        
        //Row 8
        cell.setPhrase(new Phrase("8. Self or Relationship with the Owner of Vehicle"));
        table.addCell(cell);
    
        cell2.setPhrase(new Phrase(label.getRelationship().toUpperCase()));
        table.addCell(cell2);
    
        //Row 9
        cell.setPhrase(new Phrase("9. Name of Registered Owner of    Vehicle"));
        table.addCell(cell);
    
        cell2.setPhrase(new Phrase(label.getRegisteredownername().toUpperCase()));
        table.addCell(cell2);
    
        //Row 9.1
        cell.setPhrase(new Phrase("   Address of Registered Owner of    Vehicle"));
        table.addCell(cell);
    
        cell2.setPhrase(new Phrase(label.getAddress().toUpperCase()));
        table.addCell(cell2);
    
        //Row 10
        cell.setPhrase(new Phrase("10. Registration Number"));
        table.addCell(cell);
    
        cell2.setPhrase(new Phrase(label.getRegistrationnum().toUpperCase()));
        table.addCell(cell2);
        
        //Row 11
        cell.setPhrase(new Phrase("11. I Card Number"));
        table.addCell(cell);
    
        cell2.setPhrase(new Phrase(label.getIcardnum().toUpperCase()));
        table.addCell(cell2);
        
        //Row 12
        cell.setPhrase(new Phrase("12. Hired Taxi"));
        table.addCell(cell);
        
        String hired;
        if(label.isHiredtaxi()){
        	hired="YES";
        }
        else{
        	hired="NO";
        }
        cell2.setPhrase(new Phrase(hired));
        table.addCell(cell2);
        
        doc.add(table);
        
        doc.add(Chunk.NEXTPAGE);
        
        doc.add(Chunk.NEWLINE);
        doc.add(Chunk.NEWLINE);
        
        doc.add(new Paragraph("I hereby certify that I commute by this vehicle to office and the vehicle is parked in Electronics Niketan during office hour's time. Also I certify that the information furnished above are correct to the best of my knowledge and belief. I undertake to intimate the office about any change in the above information already given."));
        
        doc.add(Chunk.NEWLINE);
        
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
        Paragraph sign1 = new Paragraph("Signature of applicant",font);
        sign1.setAlignment(Element.ALIGN_RIGHT);
        doc.add(sign1);
        
        Paragraph sign1base = new Paragraph("Mobile No. & Intercom No. :- "+label.getMobile()+" / "+label.getTelephone(),font);
        sign1base.setAlignment(Element.ALIGN_RIGHT);
        doc.add(sign1base);
        
        doc.add(Chunk.NEWLINE);
        doc.add(Chunk.NEWLINE);
        
        Paragraph certificate = new Paragraph("CERTIFICATE",font);
        certificate.setAlignment(Element.ALIGN_CENTER); 
        doc.add(certificate);
        
        doc.add(Chunk.NEWLINE);
        
        doc.add(new Paragraph("I recomend the case of Mr./Ms.  "+label.getName()+"  for issue of Parking Label. I hereby indemnify myself on account of any untoward incidents due to aforesaid vehicle."));
        
        doc.add(Chunk.NEWLINE);
        Paragraph sign2 = new Paragraph("Name & Signature of the sponsoring Authority",font);
        sign2.setAlignment(Element.ALIGN_RIGHT);
        doc.add(sign2);
        
        Paragraph sign2base = new Paragraph("Stamp With Designation",font);
        sign2base.setAlignment(Element.ALIGN_RIGHT);
        doc.add(sign2base);
        
        doc.add(Chunk.NEWLINE);
        doc.add(Chunk.NEWLINE);
        doc.add(Chunk.NEWLINE);
        
        Paragraph sign3 = new Paragraph("Signature of the Section Officer",font);
        sign3.setAlignment(Element.ALIGN_RIGHT);
        doc.add(sign3);
        
        doc.add(Chunk.NEWLINE);
        
        Paragraph sign4 = new Paragraph("Signature of the Security Officer",font);
        sign4.setAlignment(Element.ALIGN_RIGHT);
        doc.add(sign4);
        
        doc.add(Chunk.NEWLINE);
        doc.add(Chunk.NEWLINE);
        
        Paragraph hr = new Paragraph("___________________________________________________________________________");
        hr.setAlignment(Element.ALIGN_CENTER);
        doc.add(hr);
        
        doc.add(Chunk.NEWLINE);
        
        Paragraph security = new Paragraph("(Security Section)");
        security.setAlignment(Element.ALIGN_CENTER); 
        doc.add(security);
        
        Paragraph p1 = new Paragraph("Returned in original with remarks that",font); 
        doc.add(p1);
        
        
        doc.add(new Paragraph("1. The requisition is incomplete ( SI. No._______________________________ )"));
        doc.add(new Paragraph("2. The requisition form is not sponsored by the controlling officer or authorized officer of the society."));
        doc.add(new Paragraph("3. Any other reason ___________________________ "));
        
        doc.add(Chunk.NEWLINE);
        doc.add(Chunk.NEWLINE);
        Paragraph sign5 = new Paragraph("(Signature of Issuing Authority)",font);
        sign5.setAlignment(Element.ALIGN_RIGHT);
        doc.add(sign5);
        

        
    }
 
}