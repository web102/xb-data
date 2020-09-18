package com.bobandata.iot.basedb.common;

import com.bobandata.iot.util.Constant;
import com.bobandata.iot.util.Result;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * @Author: lizhipeng
 * @Company: 上海博般数据技术有限公司
 * @Date: 2018/11/22 22:04
 * @Description:
 */

@RestController
@RequestMapping("/pdf")
public class PDFReport {


    @PostMapping("/pdfReport")
    public Result pdfReport(@RequestBody List<String> msg) throws IOException {
        if(msg.size()==0){
            return new Result(Constant.MethodResult.FAIL.getMethodResult(), true);
        }

        Document document = new Document();// 建立一个Document对象
        SimpleDateFormat sdf = new SimpleDateFormat("dd-HH-mm-ss");
        String pdfName = sdf.format(new Date());


        String path = ".\\pdfReport\\";
        if (!new File(path).isDirectory())
        {
            new File(path).mkdir();
        }
        File file = new File(path + pdfName + ".pdf");

        document.setPageSize(PageSize.A4);// 设置页面大小
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
        } catch (Exception e) {
            e.printStackTrace();
        }


//中文字体
        BaseFont bfChinese = null;
        try {
            bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Font font = new Font(bfChinese, 12, Font.NORMAL);

        for (String message : msg) {
            Paragraph paragraph = new Paragraph(message,font);
            try {
                document.add(paragraph);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        document.close();
        return new Result(Constant.MethodResult.SUCCESS.getMethodResult(), true);
    }
}
