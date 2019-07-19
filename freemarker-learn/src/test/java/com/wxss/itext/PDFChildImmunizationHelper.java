package com.wxss.itext;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.lowagie.text.pdf.PdfCell;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.print.Doc;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Author:Created by wx on 2019/6/25
 * Desc:使用itext 原生API 生成 PDF文档
 *      目前存在问题：
 *          1.存在重复代码，需要进一步封装
 *          2.PDF中的复选框暂未找到合适的解决方案
 */
public class PDFChildImmunizationHelper {
    private static final float[] WIDTH1_OF_NEST_TABLE_ = {150f, 50f};
    private static final float[] WIDTH2_OF_NEST_TABLE_ = {200f, 50f};
    private static final float[] WIDTH3_OF_NEST_TABLE_ = {100f, 50f,100f,50f};
    private static final float[] WIDTH4_OF_NEST_TABLE_ = {50f, 50f};

    private BaseFont bf = null;
    //标题字体 蓝色
    private Font title = null;
    //一般 灰色
    private Font chs2 = null;
    //加粗 黑色
    private Font chs3 = null;
    // 表格文本：一般-黑色-8f
    private Font chs4 = null;
    private Font chs8 = null;
    private Font chs9 = null;
    private Font chs10 = null;
    private Font chs11 = null;
    private Font chs20 = null;
    private BaseColor bg = new BaseColor(0xfff5fbff);

    public PDFChildImmunizationHelper() {
        try {
            Resource resource = new ClassPathResource("ping_fang_regular.ttf");
            bf = BaseFont.createFont(resource.getURL().toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (Exception e) {
        }
        if (bf == null) {
            throw new RuntimeException("中文字体生成失败" + "");
        }
        title = new Font(bf, 20f, Font.BOLD, new BaseColor(0x3C9BFF));
        chs2 = new Font(bf, 10f, Font.NORMAL, BaseColor.GRAY);
        chs3 = new Font(bf, 10f, Font.BOLD, BaseColor.BLACK);
        chs4 = new Font(bf, 8f, Font.NORMAL, BaseColor.BLACK);
        chs8 = new Font(bf, 8f, Font.NORMAL, BaseColor.GRAY);
        chs9 = new Font(bf, 8f, Font.BOLD, BaseColor.BLACK);
        chs10 = new Font(bf, 7f, Font.NORMAL, BaseColor.GRAY);
        chs11 = new Font(bf, 6f, Font.NORMAL, BaseColor.BLACK);
        chs20 = new Font(bf, 10f, Font.BOLD, new BaseColor(0xffD43C33));
    }


    /**
     *  // 换行
     *         Paragraph p = new Paragraph("国家免疫规划疫苗儿童免疫程序表"+"\n"+"\n",title);
     *
     *         // 分割线
     *         doc.add(new LineSeparator());
     * @throws Exception
     */
    @Test
    public void generatePdf() throws Exception {
        Document doc = new Document();
        File file = new File("C:\\Users\\Administrator\\Desktop\\临时文件夹\\儿童测试.pdf");
        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(file));
        doc.open();

//        // 文本定位:x y不好确定
//        PdfContentByte cb = writer.getDirectContent();
//        cb.beginText();
//        cb.setFontAndSize(bf, 12);
//        cb.setTextMatrix(500, 800);
//        cb.showText("Text at position 1,1.测试");
//        cb.endText();
        // 标题
        Paragraph tableTitle = new Paragraph("国家免疫规划疫苗儿童免疫程序表"+"\n"+"\n",title);
        tableTitle.setAlignment(Element.ALIGN_CENTER); // 居中
        doc.add(tableTitle);
        // 姓名和编号

        try {
            PdfPTable table = new PdfPTable(6);
            Phrase phrase = new Phrase("姓名：",chs3);
            phrase.add("测试人员A");
            PdfPCell cell = new PdfPCell(phrase);

            cell.setColspan(3);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT); // 2
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);


            phrase = new Phrase("编号",chs3);
            phrase.add("123456789");
            cell = new PdfPCell(phrase);
            cell.setColspan(3);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);  // 0
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            // 为了设置姓名和编号与表格的间距
            cell = new PdfPCell();
            cell.setFixedHeight(5f);
            cell.setColspan(6);
            /**暂时设为有边框*/
            cell.setBorder(Rectangle.BOTTOM); // 15
            table.addCell(cell);

            // word中表格:
            // 第一行：
            phrase = new Phrase("填表日期",chs4);
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);


            phrase = new Phrase(dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"),chs4);
            cell.setBorder(Rectangle.BOX);
            cell = new PdfPCell(phrase);
            cell.setColspan(2);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);


            phrase = new Phrase("孕周",chs4);
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            /**add方法有坑*/
            phrase = new Phrase("周",chs4);
            phrase.add(0, new Chunk("3"));
            cell = new PdfPCell(phrase);
            cell.setColspan(2);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            // 第二行
            phrase = new Phrase("孕妇年龄",chs4);
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            phrase = new Phrase();
            phrase.add(new Chunk("25岁", chs4));
            cell = new PdfPCell(phrase);
            cell.setColspan(5);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            // 第三行
            phrase = new Phrase("丈夫姓名",chs4);
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            phrase = new Phrase();
            phrase.add(new Chunk("泰达米尔", chs4));
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);


            phrase = new Phrase("丈夫年龄",chs4);
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            phrase = new Phrase();
            phrase.add(new Chunk("25岁", chs4));
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            phrase = new Phrase("丈夫电话",chs4);
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            phrase = new Phrase();
            phrase.add(new Chunk("1234567890", chs4));
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            // 第四行
            phrase = new Phrase("孕次",chs4);
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            phrase = new Phrase();
            phrase.add(new Chunk("1次", chs4));
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            phrase = new Phrase("产次",chs4);
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);


            phrase = new Phrase("阴道分娩次 ",chs4);
            phrase.add("1");
            phrase.add(new Chunk("      剖宫产次 ",chs4));
            phrase.add("1");
            cell = new PdfPCell(phrase);
            cell.setColspan(3);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            // 第五行
            phrase = new Phrase("末次月经",chs4);
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            phrase = new Phrase(dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"),chs4);
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            phrase = new Phrase("预产期",chs4);
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);


            phrase = new Phrase(dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"),chs4);
            cell = new PdfPCell(phrase);
            cell.setColspan(3);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            // 第六行
            phrase = new Phrase("既往史",chs4);
            cell = new PdfPCell(phrase);
            cell.setRowspan(2);
            cell.setFixedHeight(35f);
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            phrase = new Phrase("1无 2心脏病 3肾脏疾病 4肝脏疾病 5高血压 6贫血 7糖尿病 8其他",chs4);
            cell = new PdfPCell(phrase);
            cell.setColspan(5);
            cell.setFixedHeight(18f);
            cell.setBorder(Rectangle.RIGHT);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);  // 0
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);


            phrase = new Phrase("□/□/□/□/□/□/□",chs4);
            cell = new PdfPCell(phrase);
            cell.setColspan(5);
            cell.setBorder(Rectangle.BOX);
            cell.disableBorderSide(Rectangle.LEFT);
            cell.disableBorderSide(Rectangle.TOP);
            // 高度不能太低，否则不显示
            cell.setFixedHeight(12f);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);  // 0
            cell.setVerticalAlignment(PdfPCell.ALIGN_TOP); // 5
            table.addCell(cell);

            /**第七行*/
            phrase = new Phrase("家族史",chs4);
            cell = new PdfPCell(phrase);
            cell.setFixedHeight(20f);
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);


            /**先后增加：段落会把短语覆盖*/
            phrase = new Phrase("1无 2遗传性疾病史 3精神疾病史 4其他",chs4);
            cell = new PdfPCell(phrase);
            cell.setColspan(4);
            cell.setBorder(Rectangle.TOP);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);  // 0
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            // 方块
            phrase = new Phrase("□/□/□", chs4);
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.RIGHT);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);  // 0
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);
            /**第八行*/
            phrase = new Phrase("个人史",chs4);
            cell = new PdfPCell(phrase);
            cell.setRowspan(2);
            cell.setFixedHeight(35f);
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            phrase = new Phrase("1无特殊 2吸烟 3饮酒 4服用药物 5接触有毒有害物质 6接触放射线 7其他",chs4);
            cell = new PdfPCell(phrase);
            cell.setColspan(5);
            cell.setFixedHeight(18f);
            cell.setBorder(Rectangle.BOX);
            cell.disableBorderSide(Rectangle.BOTTOM);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);  // 0
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            phrase = new Phrase("□/□/□/□/□/□",chs4);
            cell = new PdfPCell(phrase);
            cell.setColspan(5);
            cell.setBorder(Rectangle.RIGHT);
            // 高度不能太低，否则不显示
            cell.setFixedHeight(12f);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);  // 0
            cell.setVerticalAlignment(PdfPCell.ALIGN_TOP); // 5
            table.addCell(cell);

            /**第九行*/
            // 设置标签
            phrase = new Phrase("妇产科手术史",chs4);
            cell = new PdfPCell(phrase);
            cell.setFixedHeight(20f);
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);
            // 设置值
            phrase = new Phrase("1有 2无",chs4);
            cell = new PdfPCell(phrase);
            cell.setColspan(4);
            cell.setBorder(Rectangle.TOP);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);  // 0
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);
            // 复选框
            phrase = new Phrase("□", chs4);
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.BOX);
            cell.disableBorderSide(Rectangle.LEFT);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);  // 0
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);
            // 第十行
            // 设置标签
            phrase = new Phrase("孕产史",chs4);
            cell = new PdfPCell(phrase);
            cell.setFixedHeight(20f);
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);
            // 设置值
            phrase = new Phrase("1自然流产 2人工流产 3死胎 4死产 5新生儿死亡 6出生缺陷儿",chs4);
            cell = new PdfPCell(phrase);
            cell.setColspan(4);
            cell.setBorder(Rectangle.TOP);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);  // 0
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);
            // 复选框
            phrase = new Phrase("□/□/□/□/□", chs4);
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.RIGHT);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);  // 0
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);
            /**第十一行*/
            phrase = new Phrase("身高",chs4);
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            phrase = new Phrase("cm",chs4);
            phrase.add(0, new Chunk("180", chs4));
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            phrase = new Phrase("体重",chs4);
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            phrase = new Phrase("kg",chs4);
            phrase.add(0, new Chunk("180", chs4));
            cell = new PdfPCell(phrase);
            cell.setColspan(3);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);
            // 第十二行
            phrase = new Phrase("体质指数",chs4);
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            phrase = new Phrase("kg/m2",chs4);
            phrase.add(0, new Chunk("180", chs4));
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            phrase = new Phrase("血压",chs4);
            cell = new PdfPCell(phrase);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            phrase = new Phrase("/mmHg",chs4);
            phrase.add(0, new Chunk("180", chs4));
            cell = new PdfPCell(phrase);

            System.out.println(cell.getWidth());

            cell.setColspan(3);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);
            System.out.println(cell.getWidth());

            /**第十三行*/
            // 标签
            phrase = new Phrase("听诊",chs4);
            createSimpleCell(table, 1,1,  phrase);
            // 心脏跨两列
            List<Phrase> phraseList = new ArrayList<>();
            phraseList.add(new Phrase("心脏：1未见异常 2异常",chs4));
            phraseList.add(new Phrase("□",chs4));
            createNestTable(table, 2,WIDTH1_OF_NEST_TABLE_, phraseList);
            // 肺部跨三列
            phraseList = new ArrayList<>();
            phraseList.add(new Phrase("肺部：1未见异常 2异常",chs4));
            phraseList.add(new Phrase("□",chs4));
            createNestTable(table, 3,WIDTH2_OF_NEST_TABLE_, phraseList);
            // 第十四行:
            // 标签
            createSimpleCell(table,3,1, new Phrase("妇科检查",chs4));
            // 外阴：
            phraseList = new ArrayList<>();
            phraseList.add(new Phrase("外阴：1未见异常 2异常",chs4));
            phraseList.add(new Phrase("□",chs4));
            createNestTable(table, 2, WIDTH1_OF_NEST_TABLE_,phraseList );
            // 阴道：
            phraseList = new ArrayList<>();
            phraseList.add(new Phrase("阴道：1未见异常 2异常",chs4));
            phraseList.add(new Phrase("□",chs4));
            createNestTable(table, 3, WIDTH1_OF_NEST_TABLE_,phraseList );
            // 宫颈
            phraseList = new ArrayList<>();
            phraseList.add(new Phrase("宫颈：1未见异常 2异常",chs4));
            phraseList.add(new Phrase("□",chs4));
            createNestTable(table, 2, WIDTH1_OF_NEST_TABLE_,phraseList );
            // 子宫
            phraseList = new ArrayList<>();
            phraseList.add(new Phrase("子宫：1未见异常 2异常",chs4));
            phraseList.add(new Phrase("□",chs4));
            createNestTable(table, 3, WIDTH1_OF_NEST_TABLE_,phraseList );
            // 附件
            phraseList = new ArrayList<>();
            phraseList.add(new Phrase("附件：1未见异常 2异常",chs4));
            phraseList.add(new Phrase("□",chs4));
            createNestTable(table, 5, WIDTH1_OF_NEST_TABLE_,phraseList );
            // 第十五行
            // 标签：
            createSimpleCell(table, 6,1,  new Phrase("辅助检查",chs4));
            // 血常规标签
            /**这里因为生成值的时候，直接把两行当做一张子表生成，所以rolspan为1，而不是2
             *
             * 为2时，血常规单元格占两行，它右边的默认是两行单元格，生成嵌套表格时，使用的是右上的单元格，
             * */

            createSimpleCell(table, 2,1,  new Phrase("血常规",chs4));
            // 血红蛋白值和白细胞计数值：
            phraseList = new ArrayList<>();
            phraseList.add(new Phrase("血红蛋白值",chs4));
            phraseList.add(new Phrase(" 500g/L  ",chs4));
            phraseList.add(new Phrase("白细胞计数值",chs4));
            phraseList.add(new Phrase(" 100/L  ",chs4));
            createNestTable(table, 4, WIDTH3_OF_NEST_TABLE_,phraseList );
            phraseList = new ArrayList<>();
            // 血小板计数值和其他
            phraseList.add(new Phrase("血小板计数值",chs4));
            phraseList.add(new Phrase(" 500/L  ",chs4));
            phraseList.add(new Phrase("其他",chs4));
            phraseList.add(new Phrase("1",chs4));
            createNestTable(table, 4, WIDTH3_OF_NEST_TABLE_,phraseList );
            // 尿常规标签：
            createSimpleCell(table, 1,1, new Phrase("尿常规",chs4));
            // 尿常规值：
            phraseList = new ArrayList<>();
            phraseList.add(new Phrase("尿蛋白尿糖尿酮体尿潜血",chs4));
            phraseList.add(new Phrase(" 500g/L  ",chs4));
            phraseList.add(new Phrase("其他",chs4));
            phraseList.add(new Phrase(" 100/L  ",chs4));
            createNestTable(table, 4, WIDTH3_OF_NEST_TABLE_,phraseList );

            // 血型标签：
            /**换方法了：前面的需要修改*/
            Map<String,Object> tableAttributeMap = new HashMap<>();
            tableAttributeMap.put("columns", WIDTH4_OF_NEST_TABLE_);
            List<TableElement> elementList = new ArrayList<>();
            TableElement tableElement = new TableElement();
            tableElement.setAlignCenter(true);
            tableElement.setCheckBox(false);
            tableElement.setRowspan(2);
            tableElement.setElement(new Phrase("血型", chs4));
            elementList.add(tableElement);

            tableElement = new TableElement();
            tableElement.setAlignCenter(true);
            tableElement.setCheckBox(false);
            tableElement.setElement(new Phrase("ABO", chs4));
            elementList.add(tableElement);

            tableElement = new TableElement();
            tableElement.setAlignCenter(true);
            tableElement.setCheckBox(false);
            tableElement.setElement(new Phrase("Rh", chs4));
            elementList.add(tableElement);

            tableAttributeMap.put("elements",elementList);
//
            createComplexCell(table, 1,2,tableAttributeMap);
            createSimpleCell(table, 2, 4, new Phrase("我也不知道是什么",chs4));
            // 血糖：
            createSimpleCell(table, 1, 1, new Phrase("血糖",chs4));
            phrase =  new Phrase("mmol/L",chs4);
            phrase.add(0, new Chunk("100" ));
            createSimpleCell(table, 1, 4,phrase );
            // 肝功能：
            createSimpleCell(table, 3, 1, new Phrase("肝功能",chs4));

            cell = null;
            phrase = null;
            phraseList = null;
            /**关闭document*/
            doc.add(table);
            // 设置备注文本
            Paragraph remarksParagraph = new Paragraph();
            remarksParagraph.add(new Phrase("填表说明:",chs3));
            remarksParagraph.setAlignment(Element.ALIGN_LEFT);
            remarksParagraph.setIndentationLeft(60f);
            doc.add(remarksParagraph);

            remarksParagraph = new Paragraph();
            remarksParagraph.add(new Phrase("1．本表由医生在第一次接诊孕妇（尽量在孕13周前）时填写。若未建立居民健康档案，需同时建立。随访时填写各项目对应情况的数字。",chs4));
            remarksParagraph.setAlignment(Element.ALIGN_LEFT);
            remarksParagraph.setIndentationLeft(70); // 左缩进
            remarksParagraph.setIndentationRight(70); // 右缩进
            remarksParagraph.setFirstLineIndent(12f); // 设置第一行缩进
            //段落2与段落2的间距加大50个单位
//            remarksParagraph.SpacingAfter = 50;
//            //段落2与段落1的间距加大100个单位
//            remarksParagraph.SpacingBefore = 100;
            doc.add(remarksParagraph);
            remarksParagraph = null;

            // 关闭
            doc.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    /**
     * 通过嵌套表的方式，生成复杂的表格标签
     *  问题：
     *      1.参数过多
     *      2.尚未自定义子表的列宽度
     *      3.跨行跨列有问题： 应该和值在一起封装为一个对象
     *  1   2
     */
    @SuppressWarnings("unchecked")
    public void createComplexCell(PdfPTable fatherTable, int fatherColspan, int fatherRowspan, Map<String,Object> tableAttributeMap) throws DocumentException {
        float[] columns = (float[]) tableAttributeMap.get("columns");
        // 子表
        PdfPTable sonTable = new PdfPTable(columns.length);
        sonTable.setWidths(columns);
        // 子表中的单元格
        PdfPCell sonCell = null;
        List<TableElement> elements = (List<TableElement>) tableAttributeMap.get("elements");
        for (int i = 0; i < elements.size(); i++) {
            TableElement tableElement = elements.get(i);
            // 该属性目前用不到
//            int colspan = tableElement.getColspan();
            int rowspan = tableElement.getRowspan();
            boolean isCheckBox = tableElement.isCheckBox(); // 判断是否是普通标签还是复选框
            // 判断对齐方式：普通标签：水平垂直居中，复选框：靠右居中：值标签：靠左，值：居中
            boolean isAlignCenter = tableElement.isAlignCenter();
            Element element = tableElement.getElement();
            if (element instanceof Phrase){
                Phrase phrase = (Phrase)element;
                sonCell = new PdfPCell(phrase);
            }else if (element instanceof PdfPCell){
                PdfPCell cell = (PdfPCell) element;
                sonCell = new PdfPCell(cell);
            }else if (element instanceof PdfPTable){
                PdfPTable table = (PdfPTable) element;
                sonCell = new PdfPCell(table);
            }else {
                return;
            }
            if (rowspan>1){
                sonCell.setRowspan(rowspan);
                sonCell.setFixedHeight(20f*rowspan);
            }else {
                sonCell.setFixedHeight(20f);
            }
            if (isCheckBox){
                // 是复选框:靠右
                sonCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                sonCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            }else {
                // 不是复选框，进一步判断居中
                if (isAlignCenter){
                    // 居中
                    sonCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    sonCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                }else {
                    // 不居中,靠左
                    sonCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    sonCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                }
            }
            // 填充元素
            sonTable.addCell(sonCell);
        }
        // 根据子表创建父单元格
        PdfPCell fatherCell = new PdfPCell(sonTable);
        fatherCell.setColspan(fatherColspan);
        fatherCell.setRowspan(fatherRowspan);
        fatherTable.addCell(fatherCell);

    }

    /**
     * 用于生成简单的水平垂直居中的表格标签：
     * @param table 表格
     * @param rowspan 跨行
     * @param colspan 跨列
     */
    private void createSimpleCell(PdfPTable table,int rowspan,int colspan,Phrase phrase) {
        PdfPCell cell = new PdfPCell(phrase);
        // add的对齐不会生效
//        cell.addElement(phrase);
        if (rowspan>1){
            cell.setRowspan(rowspan);
            cell.setFixedHeight(20f*rowspan);
        }else {
            cell.setFixedHeight(20f);
        }

        if (colspan>1){
            cell.setColspan(colspan);
        }
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
        table.addCell(cell);
    }

    /**
     * 生成简单的嵌套表格：
     *          适用于嵌套表格不需要额外的跨行跨列
     * @param sonTableWidth 子表列宽数组
     * @param phraseList 子表单元格值
     * @throws DocumentException
     */
    private void createNestTable(PdfPTable fatherTable, int colspan, float[] sonTableWidth, List<Phrase> phraseList) throws DocumentException {

        PdfPTable nestTable = new PdfPTable(sonTableWidth.length);
        nestTable.setWidths(sonTableWidth);

        PdfPCell smallCell = null;
        for (int i = 0; i < phraseList.size(); i++) {
            if (i==phraseList.size()-1){
                // 最后一个单元格
                smallCell = new PdfPCell(phraseList.get(i));
                smallCell.setHorizontalAlignment(Element.ALIGN_RIGHT);  // 1
                smallCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
                smallCell.setFixedHeight(20f);
                smallCell.setBorder(Rectangle.BOX);
                smallCell.disableBorderSide(Rectangle.LEFT);
                nestTable.addCell(smallCell);
            }else {
                // 其他单元格
                smallCell = new PdfPCell(phraseList.get(i));
                smallCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                smallCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                smallCell.setFixedHeight(20f);
                smallCell.setBorder(Rectangle.BOX);
                smallCell.disableBorderSide(Rectangle.RIGHT);
                nestTable.addCell(smallCell);
            }
        }

        PdfPCell fatherCell = new PdfPCell(nestTable);
        fatherCell.setColspan(colspan);
        fatherTable.addCell(fatherCell);
    }

    public static String dateToString(Date date,String pattern){
        assert date != null && pattern!=null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }


    @Test
    public void test11() throws Exception{
        Document document = new Document();
        File file = new File("C:\\Users\\Administrator\\Desktop\\临时文件夹\\22.pdf");

        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();

        PdfPTable table = new PdfPTable(3);

        PdfPCell cell = new PdfPCell();
        cell.addElement(new Phrase("11", chs4));
        cell.setRowspan(2);
        table.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(new Phrase("11", chs4));
        table.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(new Phrase("11", chs4));
        table.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(new Phrase("12", chs4));
        table.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(new Phrase("12", chs4));
        table.addCell(cell);

        // 第二行：
        cell = new PdfPCell();
        cell.addElement(new Phrase("2", chs4));
        cell.setRowspan(3);
        table.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(new Phrase("21", chs4));
        table.addCell(cell);
        cell = new PdfPCell();
        cell.addElement(new Phrase("21", chs4));
        table.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(new Phrase("22", chs4));
        table.addCell(cell);
        cell = new PdfPCell();
        cell.addElement(new Phrase("22", chs4));
        table.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(new Phrase("23", chs4));
        table.addCell(cell);
        cell = new PdfPCell();
        cell.addElement(new Phrase("23", chs4));
        table.addCell(cell);

        // 第三行：
        /**有跨行的话：如果第一个跨5行，第一个儿子，跨2行，后面也只有第一个儿子的元素，那么总体还是跨2行。而不是5行*/
        cell = new PdfPCell();
        cell.addElement(new Phrase("3", chs4));
        cell.setRowspan(5);
        table.addCell(cell);

        cell = new PdfPCell();
        cell.setRowspan(2);
        cell.addElement(new Phrase("31", chs4));
        table.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(new Phrase("311", chs4));
        table.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(new Phrase("312", chs4));
        table.addCell(cell);

        // 应该是第二行
        cell = new PdfPCell();
        cell.addElement(new Phrase("32", chs4));
        table.addCell(cell);
        cell = new PdfPCell();
        cell.addElement(new Phrase("321", chs4));
        table.addCell(cell);

        // 第三个儿子
        PdfPTable t = new PdfPTable(2);
        PdfPCell c = new PdfPCell();
        c.setRowspan(2);
        c.addElement(new Phrase("1", chs4));
        c.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
        c.setHorizontalAlignment(Element.ALIGN_CENTER); // 1
        t.addCell(c);
        c = new PdfPCell(new Phrase("2",chs4));
        c.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
        c.setHorizontalAlignment(Element.ALIGN_CENTER); // 1
        t.addCell(c);

        c = new PdfPCell();
        c.addElement(new Phrase("3", chs4));
        c.setVerticalAlignment(Element.ALIGN_MIDDLE); // 5
        c.setHorizontalAlignment(Element.ALIGN_CENTER); // 1
        t.addCell(c);


        cell = new PdfPCell(t);

        table.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(new Phrase("333", chs4));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 5
        cell.setHorizontalAlignment(Element.ALIGN_CENTER); // 1
        table.addCell(cell);


        document.add(table);
        document.close();
    }

        /**
         * 根據數據生成PDF
         * 數據格式尚未確定
         * @param
         */
    @Test
    public void generatePdf1() throws Exception {
        Document doc = new Document();

        File file = new File("C:\\Users\\Administrator\\Desktop\\临时文件夹\\112.pdf");
        PdfWriter.getInstance(doc, new FileOutputStream(file));
        doc.open();
        PdfPTable table = new PdfPTable(4);
        table.getDefaultCell().setBorder(1);

        PdfPCell cell = null;

        Phrase p = new Phrase("HR(bpm):", chs8);
        p.add(new Chunk("aa", chs9));
        cell = new PdfPCell(p);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderWidth(0.5f);
        cell.setFixedHeight(25);
        cell.setBorderColor(BaseColor.GRAY);
        table.addCell(cell);

        p = new Phrase("PR(ms):", chs8);
        p.add(new Chunk("aa", chs9));
        cell = new PdfPCell(p);
        cell.setHorizontalAlignment(PdfPCell.LEFT);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderWidth(0.5f);
        cell.setFixedHeight(25);
        cell.setBorderColor(BaseColor.GRAY);
        table.addCell(cell);

        p = new Phrase("QRS(ms):", chs8);
        p.add(new Chunk("aa", chs9));
        cell = new PdfPCell(p);
        cell.setHorizontalAlignment(PdfPCell.LEFT);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderWidth(0.5f);
        cell.setFixedHeight(25);
        cell.setBorderColor(BaseColor.GRAY);
        table.addCell(cell);

        p = new Phrase("QT/QTc(ms):", chs8);
        p.add(new Chunk("aa" + "/" + "aa", chs9));
        cell = new PdfPCell(p);
        cell.setHorizontalAlignment(PdfPCell.LEFT);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderWidth(0.5f);
        cell.setFixedHeight(25);
        cell.setBorderColor(BaseColor.GRAY);
        table.addCell(cell);
        doc.add(table);
//        PdfPTable table = new PdfPTable(2);// 17列
//        // 临时写法
//        table.getDefaultCell().setBorder(1); // 設置邊框
//        // 单元格
//        PdfPCell cell = new PdfPCell();
////        cell.setColspan(2);
//
//
//        Phrase elements = new Phrase("SS", chs2);
//        cell.setUseAscender(true);
//        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell.addElement(elements);
//
//        table.addCell(cell);
//
//        cell = new PdfPCell();
//        cell.addElement(new Phrase("s谁说的", chs2));
//        cell.setHorizontalAlignment(PdfPCell.LEFT);
//        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//        cell.setBorder(Rectangle.BOTTOM);
//        cell.setBorderWidth(0.5f);
//        cell.setFixedHeight(25);
//        cell.setBorderColor(BaseColor.GRAY);
//
//        table.addCell(cell);
//
//        doc.add(table);
        doc.close();
    }

}
