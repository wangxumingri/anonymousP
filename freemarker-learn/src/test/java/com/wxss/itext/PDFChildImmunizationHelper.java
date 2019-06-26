package com.wxss.itext;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author:Created by wx on 2019/6/25
 * Desc:
 */
public class PDFChildImmunizationHelper {
    private static final float[] WIDTH1_OF_NEST_TABLE_ = {150f, 50f};
    private static final float[] WIDTH2_OF_NEST_TABLE_ = {200f, 50f};
    private static final float[] WIDTH3_OF_NEST_TABLE_ = {100f, 50f,100f,50f};

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
        /**
         * 要考虑：标签与值分离的情况，即一个加粗一个不加粗;以及文本的位置
         *  可放在table里，通过table设置位置
         *  样式相同：Phrase#add(String)
         *  样式不同：Phrase#add(Element)
         *   Phrase#add(int,Element) // 一个元素占一个索引位
         * phrase：超出一行会自动换行
         * Paragraph一个一行
         * 制表符无效
         * 设置字符间距：
         *      Chunk#setCharacterSpacing(20F);
         *  设置上下移位
         *              Chunk#setTextRise()
         *
         *    chunk.setWordSpacing(3F);有什么用
         *
         *      Chunk设置文本位置，需要放在ColummnText中，Phrase有个setColumn方法
         *
         *      PdfPcell# add(Element) 会替换掉已有的元素，而不是增加
         */



//        Chunk name = new Chunk("姓名：",chs3);
        try {
            PdfPTable table = new PdfPTable(6);
            // 不生效
//            firstTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            Phrase phrase = new Phrase("姓名：",chs3);
            phrase.add("测试人员A");
            PdfPCell cell = new PdfPCell(phrase);

            cell.setColspan(3);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT); // 2
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);


            phrase = new Phrase("编号",chs3);
            phrase.add("123456789");
            cell = new PdfPCell(phrase);
            cell.setColspan(3);
            cell.setBorder(Rectangle.BOX);
            cell.setFixedHeight(20f);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);  // 0
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
            table.addCell(cell);

            // 为了设置姓名和编号与表格的间距
            cell = new PdfPCell();
            cell.setFixedHeight(5f);
            cell.setColspan(6);
            /**暂时设为有边框*/
            cell.setBorder(Rectangle.BOX); // 15
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
            createTableLabel(table, 1, phrase);
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
            createTableLabel(table,3,new Phrase("妇科检查",chs4));
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
            phraseList.add(new Phrase("阴道：1未见异常 2异常",chs4));
            phraseList.add(new Phrase("□",chs4));
            createNestTable(table, 5, WIDTH1_OF_NEST_TABLE_,phraseList );
            // 第十五行
            // 标签：
            createTableLabel(table, 1, new Phrase("辅助检查",chs4));
            // 血常规标签：
            createTableLabel(table, 1, new Phrase("血常规",chs4));
            // 血常规值：
            phraseList = new ArrayList<>();
            phraseList.add(new Phrase("血红蛋白值",chs4));
            phraseList.add(new Phrase(" 500g/L  ",chs4));
            phraseList.add(new Phrase("白细胞计数值",chs4));
            phraseList.add(new Phrase(" 100/L  ",chs4));
            phraseList.add(new Phrase("血小板计数值",chs4));
            phraseList.add(new Phrase(" 500/L  ",chs4));
            phraseList.add(new Phrase("其他",chs4));
            phraseList.add(new Phrase("",chs4));
            createNestTable(table, 4, WIDTH3_OF_NEST_TABLE_,phraseList );

            /**关闭document*/
            doc.add(table);
            // 关闭
            doc.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用于生成表格基本的标签
     * 待完善：跨列，及复杂跨行跨列的宽度和长度问题
     *      增加一个标志来判断 是大元素还是小元素
     * @param table 表格
     * @param rowspan 跨行
     */
    private void createTableLabel(PdfPTable table,int rowspan,Phrase phrase) {
        PdfPCell cell = new PdfPCell(phrase);
        if (rowspan>1){
            cell.setRowspan(rowspan);
            cell.setFixedHeight(20f*rowspan);
        }else {
            cell.setFixedHeight(20f);

        }
//        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 1
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 5
        table.addCell(cell);
    }

    /**
     * 生成简单的嵌套表格
     * 待优化：
     *          区分普通文本和复选框
     *          根据奇偶设置单元格文本的对齐方式：奇数：右对齐，偶数（是复选框就右对齐，否则居中对齐）
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



        /**
         * 根據數據生成PDF
         * 數據格式尚未確定
         * @param
         */
    @Test
    public void generatePdf1() throws Exception {
        Document doc = new Document();

        File file = new File("C:\\Users\\Administrator\\Desktop\\临时文件夹\\12.pdf");
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
