package com.wntime.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDFUtil {

    private Document document;
    private Font font;
    public static void main(String[] args) throws IOException, DocumentException {
        PDFUtil util = new PDFUtil();
        util.generateDocument("C:\\Users\\79448\\IdeaProjects\\foodSafetyMS\\aibus-common\\src\\main\\resources\\text.pdf").setFont();

        util.addText("态势感知").newline();

        PdfPTable table = util.createTable(7);
        table.addCell(util.createCell("有害生物事件数统计",Element.ALIGN_CENTER,7,true));
        table.addCell(util.createCell("",Element.ALIGN_CENTER));
        table.addCell(util.createCell("第一周",Element.ALIGN_CENTER));
        table.addCell(util.createCell("第二周",Element.ALIGN_CENTER));
        table.addCell(util.createCell("第三周",Element.ALIGN_CENTER));
        table.addCell(util.createCell("第四周",Element.ALIGN_CENTER));
        table.addCell(util.createCell("第五周",Element.ALIGN_CENTER));
        table.addCell(util.createCell("本月累积",Element.ALIGN_CENTER));

        table.addCell(util.createCell("本月",Element.ALIGN_CENTER));
        table.addCell(util.createCell("1",Element.ALIGN_CENTER));
        table.addCell(util.createCell("2",Element.ALIGN_CENTER));
        table.addCell(util.createCell("3",Element.ALIGN_CENTER));
        table.addCell(util.createCell("4",Element.ALIGN_CENTER));
        table.addCell(util.createCell("5",Element.ALIGN_CENTER));
        table.addCell(util.createCell("15",Element.ALIGN_CENTER));

        table.addCell(util.createCell("上月",Element.ALIGN_CENTER));
        table.addCell(util.createCell("5 ↑",Element.ALIGN_CENTER));
        table.addCell(util.createCell("4",Element.ALIGN_CENTER));
        table.addCell(util.createCell("3",Element.ALIGN_CENTER));
        table.addCell(util.createCell("2",Element.ALIGN_CENTER));
        table.addCell(util.createCell("1↓",Element.ALIGN_CENTER));
        table.addCell(util.createCell("15",Element.ALIGN_CENTER));

        util.addTable(table).addImage("C:\\Users\\79448\\Pictures\\BlueDream_1080.jpg");
        util.complete();
    }

    public PDFUtil generateDocument(String path) throws FileNotFoundException, DocumentException {
        Document document=new Document();
        document.setPageSize(PageSize.A4);
        PdfWriter.getInstance(document,new FileOutputStream(path));
        document.open();
        this.document=document;
        return this;
    }

    public PDFUtil addTable(PdfPTable table) throws DocumentException {
        this.document.add(table);
        return this;
    }

    public PDFUtil addImage(String path) throws IOException, DocumentException {
        Image img = Image.getInstance(path);
        img.setAlignment(Image.ALIGN_CENTER);
        img.scaleAbsolute(530,100);
        this.document.add(img);
        return this;
    }

    public PDFUtil setFont() throws IOException, DocumentException {
        Font font = new Font(BaseFont.createFont( "STSongStd-Light" ,"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED),12,Font.BOLD);
        font.setColor(BaseColor.BLACK);
        this.font=font;
        return this;
    }

    public PDFUtil addText(String text) throws DocumentException {
        document.add(new Paragraph(text,font));
        return this;
    }
    public PDFUtil newline() throws DocumentException {
        document.add(new Chunk("\n"));
        return this;
    }

    public void complete(){
        document.close();
    }


    public PdfPTable createTable(int colNumber){
        PdfPTable table = new PdfPTable(colNumber);

        table.setTotalWidth(500);
        table.setLockedWidth(true);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setBorder(1);
        return table;
    }

    public PdfPCell createCell(String value,int align){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value,font));
        return cell;
    }

    public PdfPCell createCell(String value){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(value,font));
        return cell;
    }

    public PdfPCell createCell(String value,int align,int colspan){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value,font));
        return cell;
    }
    public PdfPCell createCell(String value,int align,int colspan,boolean boderFlag){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value,font));
        cell.setPadding(3.0f);
        if(!boderFlag){
            cell.setBorder(0);
            cell.setPaddingTop(15.0f);
            cell.setPaddingBottom(8.0f);
        }
        cell.setBackgroundColor(BaseColor.GRAY);
        return cell;
    }

}
