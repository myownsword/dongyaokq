import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * POI解析Excel
 */
public class ExcelReaderUtil {

    /**
     * 根据fileType不同读取excel文件
     *
     * @param path
     * @param path
     * @throws IOException
     */
    public static List<Kq_Record> readExcel(String path) {
        String fileType = path.substring(path.lastIndexOf(".") + 1);
        // return a list contains many list
        List<Kq_Record> lists = new ArrayList<Kq_Record>();
        //读取excel文件
        InputStream is = null;
        try {
            is = new FileInputStream(path);
            //获取工作薄
            Workbook wb = null;
            if (fileType.equals("xls")) {
                wb = new HSSFWorkbook(is);
            } else if (fileType.equals("xlsx")) {
                wb = new XSSFWorkbook(is);
            } else {
                return null;
            }

            //读取第一个工作页sheet
            Sheet sheet = wb.getSheetAt(0);

            if(sheet==null){
                System.out.println("没有sheet页叫搬迁");
                return null;
            }

            //第一行为标题
            // 从第二行开始读取数据
            for (int rownum=1;rownum<=sheet.getLastRowNum();rownum++){
                Row row = sheet.getRow(rownum);
                //遍历列cell
                //只取excel前一二四列 第一列：工号，第二列：姓名，第四列：打开时间
                Kq_Record kr = new Kq_Record();
                if(row.getCell(0)!= null) {
                    kr.setId(row.getCell(0).getStringCellValue());
                }else{
                    System.out.println("第"+rownum+"行人员编号是空！！！");
                }

                if(row.getCell(1)!= null) {
                    kr.setName(row.getCell(1).getStringCellValue());
                }else{
                    System.out.println("第"+rownum+"行姓名是空！！！");
                }
                if(row.getCell(3)!= null) {
                    kr.setKq_time(row.getCell(3).getStringCellValue());
                }else{
                    System.out.println("第"+rownum+"行考勤时间是空！！！");
                }
                lists.add(kr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lists;
    }


    /**
     * 创建Excel.xls
     * @param lists 需要写入xls的数据
     * @return
     * @throws IOException
     */
    public static Workbook creatExcel(List<Kq_Record_Person> lists,String yyyy_mm) throws IOException, ParseException {

        Calendar calendar=Calendar.getInstance();

        String DATE_PATTERN_YYYYMMDD = "yyyy-MM-dd";

        SimpleDateFormat timeFt = new SimpleDateFormat(DATE_PATTERN_YYYYMMDD);
        Date kq_time = timeFt.parse(yyyy_mm+"-01");

//            calendar.setTime(new Date());
        calendar.setTime(kq_time);

        String year=String.valueOf(calendar.get(Calendar.YEAR));
        String month=String.valueOf(calendar.get(Calendar.MONTH)+1);
        String day=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String week=String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)-1);
        System.out.println("现在时间是："+year+"年"+month+"月"+day+"日，星期"+week);

        int   maxDate   =   calendar.getActualMaximum(Calendar.DATE);
        System.out.println(lists);
        //创建新的工作薄
        Workbook wb = new XSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet(month+"月");
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        int length = 3+maxDate;
        for(int i=0;i<length;i++){
            if(i<3) {
                sheet.setColumnWidth((short) i, (short) (35.7 * 100));
            }else{
                sheet.setColumnWidth((short) i, (short) (35.7 * 25));
            }

        }

        // 创建第一行
        Row row1 = sheet.createRow((short) 0);

        // 创建两种字体
        Font f = wb.createFont();
        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        CellStyle cs3 = wb.createCellStyle();
        // 设置第一种单元格的样式（用于列名）
        cs3.setFont(f);
        cs3.setBorderLeft(CellStyle.BORDER_THIN);
        cs3.setBorderRight(CellStyle.BORDER_THIN);
        cs3.setBorderTop(CellStyle.BORDER_THIN);
        cs3.setBorderBottom(CellStyle.BORDER_THIN);
        cs3.setAlignment(CellStyle.ALIGN_CENTER);
        Cell cell1 = row1.createCell(3);
        cell1.setCellValue(year+"年"+month+"月考勤记录表");
        cell1.setCellStyle(cs3);

        Row row2 = sheet.createRow((short) 1);

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();


        Font f2 = wb.createFont();

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);

        // 夜班蓝色
        CellStyle cs_blue = wb.createCellStyle();
        cs_blue.setFont(f2);
        cs_blue.setBorderLeft(CellStyle.BORDER_THIN);
        cs_blue.setBorderRight(CellStyle.BORDER_THIN);
        cs_blue.setBorderTop(CellStyle.BORDER_THIN);
        cs_blue.setBorderBottom(CellStyle.BORDER_THIN);
        cs_blue.setAlignment(CellStyle.ALIGN_CENTER);
        cs_blue.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        cs_blue.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // 白夜休休 白-红色
        CellStyle cs_red = wb.createCellStyle();
        cs_red.setFont(f2);
        cs_red.setBorderLeft(CellStyle.BORDER_THIN);
        cs_red.setBorderRight(CellStyle.BORDER_THIN);
        cs_red.setBorderTop(CellStyle.BORDER_THIN);
        cs_red.setBorderBottom(CellStyle.BORDER_THIN);
        cs_red.setAlignment(CellStyle.ALIGN_CENTER);
        cs_red.setFillForegroundColor(IndexedColors.RED.getIndex());
        cs_red.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // 空白-黑色
        CellStyle cs_black = wb.createCellStyle();
        cs_black.setFont(f2);
        cs_black.setBorderLeft(CellStyle.BORDER_THIN);
        cs_black.setBorderRight(CellStyle.BORDER_THIN);
        cs_black.setBorderTop(CellStyle.BORDER_THIN);
        cs_black.setBorderBottom(CellStyle.BORDER_THIN);
        cs_black.setAlignment(CellStyle.ALIGN_CENTER);
        cs_black.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        cs_black.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //设置列名
        for(int i=0;i<length;i++){
            Cell cell = row2.createCell(i);

            if(i==0) {
                cell.setCellValue("序号");
            }else if(i==1) {
                cell.setCellValue("人员编号");
            }else if(i==2) {
                cell.setCellValue("姓名");
            }else{
                cell.setCellValue(i-2);
            }

            cell.setCellStyle(cs);
        }
        if(lists == null || lists.size() == 0){
            return wb;
        }
        //设置每行每列的值
        for (short i = 0; i < lists.size(); i++) {
            Kq_Record_Person kq_record_person = lists.get(i);
            List<Map<String,Object>> kq_date_list = kq_record_person.getKq_date_list();
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row3 = sheet.createRow((short)i+2);
            for(short j=0;j<length;j++){
//                cs2.setFillForegroundColor(IndexedColors.AUTOMATIC.index);
                // 在row行上创建一个方格
                Cell cell = row3.createCell(j);
                if(j==0) {
                    cell.setCellValue(i+1);
                    cell.setCellStyle(cs2);
                }else if(j==1) {
                    cell.setCellValue(kq_record_person.getId());
                    cell.setCellStyle(cs2);
                }else if(j==2) {
                    cell.setCellValue(kq_record_person.getName());
                    cell.setCellStyle(cs2);
                }else{

//                    if(kq_date_list.get(j-3).get("kq_start_time")!=null || kq_date_list.get(j-3).get("kq_end_time")!=null) {
//                        //创建绘图对象
//                        HSSFPatriarch p = (HSSFPatriarch) sheet.createDrawingPatriarch();
//                        //插入单元格内容
//                        String kq_start_time = kq_date_list.get(j - 3).get("kq_start_time")==null?"":kq_date_list.get(j - 3).get("kq_start_time").toString().substring(11);
//                        String kq_end_time = kq_date_list.get(j - 3).get("kq_end_time")==null?"":kq_date_list.get(j - 3).get("kq_end_time").toString().substring(11);
//                        //获取批注对象
//                        //(int dx1, int dy1, int dx2, int dy2, short col1, int row1, short col2, int row2)
//                        //前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
//                        HSSFComment comment = p.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 15, 6));
//                        //输入批注信息
//                        comment.setString(new HSSFRichTextString(kq_start_time+"-"+kq_end_time));
//                        //添加作者,选中B5单元格,看状态栏
//                        comment.setAuthor("toad");
//                        //将批注添加到单元格对象中
//                        cell.setCellComment(comment);
//                    }

                    if(kq_date_list.get(j-3).get("kq_start_time")!=null || kq_date_list.get(j-3).get("kq_end_time")!=null) {
                        String kq_start_time = kq_date_list.get(j - 3).get("kq_start_time")==null?"":kq_date_list.get(j - 3).get("kq_start_time").toString().substring(11);
                        String kq_end_time = kq_date_list.get(j - 3).get("kq_end_time")==null?"":kq_date_list.get(j - 3).get("kq_end_time").toString().substring(11);

                        CreationHelper factory = wb.getCreationHelper();
                        Drawing drawing = sheet.createDrawingPatriarch();
                        ClientAnchor anchor = factory.createClientAnchor();
                        anchor.setCol1(cell.getColumnIndex());
                        anchor.setCol2(cell.getColumnIndex()+15);
                        anchor.setRow1(row3.getRowNum());
                        anchor.setRow2(row3.getRowNum()+3);
                        Comment comment = drawing.createCellComment(anchor);
                        RichTextString str = factory.createRichTextString(kq_start_time+"-"+kq_end_time);
                        comment.setString(str);
                        comment.setAuthor("myownsword");
                        cell.setCellComment(comment);
                    }
                    if(kq_date_list.get(j-3)!=null&&kq_date_list.get(j-3).get("type")!=null) {
                        cell.setCellValue(kq_date_list.get(j-3).get("type").toString());
                        cell.setCellStyle(cs2);
                    }else{
                        if(kq_date_list.get(j-3)!=null&&kq_date_list.get(j-3).get("type")==null) {
                            cell.setCellStyle(cs_black);
                            continue;
                        }
                    }
                    if("夜".equals(kq_date_list.get(j-3).get("type"))) {
                        cell.setCellStyle(cs_blue);
                        if(j-3!=0) {
                            Map<String,Object> kq_date_map_before =  kq_date_list.get(j-4);
                            if(kq_date_map_before.get("kq_start_time")!=null&& kq_date_map_before.get("kq_end_time")!=null) {
                                String DATE_PATTERN_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

                                SimpleDateFormat timeFt2 = new SimpleDateFormat(DATE_PATTERN_YYYYMMDDHHMMSS);

                                Date kq_start_time = timeFt2.parse(kq_date_map_before.get("kq_start_time").toString());

                                Date kq_end_time = timeFt2.parse(kq_date_map_before.get("kq_end_time").toString());

                                Calendar kq_start_time_cal=Calendar.getInstance();
                                kq_start_time_cal.setTime(kq_start_time);
                                Calendar kq_end_time_cal=(Calendar)calendar.clone();
                                kq_end_time_cal.setTime(kq_end_time);

                                if(kq_start_time_cal.get(Calendar.HOUR_OF_DAY) < 8) {
                                    if(!kq_end_time_cal.equals(calendar)) {
                                        if(kq_end_time_cal.get(Calendar.HOUR_OF_DAY)>=18 ) {
                                            Cell cell_before = row3.getCell(j-1);
                                            cell_before.setCellStyle(cs_red);
                                        }
                                    }
                                }
                            }

                        }
                    }else {
                        String date = kq_date_list.get(j - 3).get("kq_time").toString();
                        Date _kq_time = timeFt.parse(date);
                        Calendar _calendar = Calendar.getInstance();
                        _calendar.setTime(_kq_time);
                        int _week = _calendar.get(Calendar.DAY_OF_WEEK) ;
                        if (_week == Calendar.SUNDAY || _week == Calendar.SATURDAY) {
                            // 创建第二种字体样式（用于值）
                            CellStyle _cs2 = wb.createCellStyle();
                            _cs2.setFont(f2);
                            _cs2.setBorderLeft(CellStyle.BORDER_THIN);
                            _cs2.setBorderRight(CellStyle.BORDER_THIN);
                            _cs2.setBorderTop(CellStyle.BORDER_THIN);
                            _cs2.setBorderBottom(CellStyle.BORDER_THIN);
                            _cs2.setAlignment(CellStyle.ALIGN_CENTER);
                            _cs2.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                            _cs2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            cell.setCellStyle(_cs2);
                        }
                    }
                }


            }
        }

        FileOutputStream fileOut = new FileOutputStream("E:\\IDEA\\workspace\\kaoqin\\src\\"+year+"年"+month+"月考勤.xlsx");

        wb.write(fileOut);

        fileOut.close();

        return wb;
    }

    /**
     * 创建Excel.xls
     * @param lists 需要写入xls的数据
     * @return
     * @throws IOException
     */
    public static Workbook creatJiaBanExcel(List<Jia_Ban_Bean> lists,String yyyy_mm) throws IOException, ParseException {

        Calendar calendar=Calendar.getInstance();

        String DATE_PATTERN_YYYYMMDD = "yyyy-MM-dd";

        SimpleDateFormat timeFt = new SimpleDateFormat(DATE_PATTERN_YYYYMMDD);
        Date kq_time = timeFt.parse(yyyy_mm+"-01");

        calendar.setTime(kq_time);

        String year=String.valueOf(calendar.get(Calendar.YEAR));
        String month=String.valueOf(calendar.get(Calendar.MONTH)+1);

        //创建新的工作薄
        Workbook wb = new XSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet(month+"月");
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        String[] titles = new String[]{"序号","岗位名称","加班人数","人员编码","加班人员名单","加班类型","加班日期","人均加班时间（天）","加班事由","补偿方式","加班日工资","加班费"};
        int[] titles_length = new int[]{40,    100,      40,        100,       100,           100,       200       ,60                  ,200       ,60        ,60          ,80};
        for(int i=0;i<titles_length.length;i++){
            sheet.setColumnWidth((short) i, (short) (35.7 * titles_length[i]));
        }

        // 创建第一行
        Row row1 = sheet.createRow((short) 0);

        // 创建两种字体
        Font f = wb.createFont();
        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        CellStyle cs3 = wb.createCellStyle();
        // 设置第一种单元格的样式（用于列名）
        cs3.setFont(f);
        cs3.setBorderLeft(CellStyle.BORDER_THIN);
        cs3.setBorderRight(CellStyle.BORDER_THIN);
        cs3.setBorderTop(CellStyle.BORDER_THIN);
        cs3.setBorderBottom(CellStyle.BORDER_THIN);
        cs3.setAlignment(CellStyle.ALIGN_CENTER);
        Cell cell1 = row1.createCell(3);
        cell1.setCellValue("加班费申请表（207分厂搬迁"+year+"年"+month+"月份）");
        cell1.setCellStyle(cs3);

        Row row2 = sheet.createRow((short) 1);
        row2.setHeight((short) 800);//目的是想把行高设置成25px


        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();


        Font f2 = wb.createFont();

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
//        cs.setAlignment(CellStyle.ALIGN_CENTER);
        cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
        cs.setWrapText(true);

        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);

        //设置列名
        for(int i=0;i<titles.length;i++){
            Cell cell = row2.createCell(i);

            cell.setCellValue(titles[i]);

            cell.setCellStyle(cs);
        }
        if(lists == null || lists.size() == 0){
            return wb;
        }
        //设置每行每列的值
        for (short i = 0; i < lists.size(); i++) {
            Jia_Ban_Bean jia_Ban_Bean = lists.get(i);
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row3 = sheet.createRow((short)i+2);
            Cell row3_cell0 = row3.createCell(0);
            row3_cell0.setCellValue(i+1);
            row3_cell0.setCellStyle(cs2);

            Cell row3_cell1 = row3.createCell(1);
            row3_cell1.setCellValue(jia_Ban_Bean.getPosm_name());
            row3_cell1.setCellStyle(cs2);

            Cell row3_cell2 = row3.createCell(2);
            row3_cell2.setCellValue(jia_Ban_Bean.getJia_ban_ren_shu());
            row3_cell2.setCellStyle(cs2);

            Cell row3_cell3 = row3.createCell(3);
            row3_cell3.setCellValue(jia_Ban_Bean.getId());
            row3_cell3.setCellStyle(cs2);

            Cell row3_cell4 = row3.createCell(4);
            row3_cell4.setCellValue(jia_Ban_Bean.getName());
            row3_cell4.setCellStyle(cs2);

            Cell row3_cell5 = row3.createCell(5);
            row3_cell5.setCellValue(jia_Ban_Bean.getJia_ban_type());
            row3_cell5.setCellStyle(cs2);

            Cell row3_cell6 = row3.createCell(6);
            row3_cell6.setCellValue(jia_Ban_Bean.getJia_ban_time());
            row3_cell6.setCellStyle(cs2);

            Cell row3_cell7 = row3.createCell(7);
            DecimalFormat df = new DecimalFormat("0.00");
            row3_cell7.setCellValue(df.format(jia_Ban_Bean.getJia_ban_days()));
            row3_cell7.setCellStyle(cs2);

            Cell row3_cell8 = row3.createCell(8);
            row3_cell8.setCellValue(jia_Ban_Bean.getJia_ban_reason());
            row3_cell8.setCellStyle(cs2);

            Cell row3_cell9 = row3.createCell(9);
            row3_cell9.setCellValue(jia_Ban_Bean.getBuchangfangshi());
            row3_cell9.setCellStyle(cs2);

            Cell row3_cell10 = row3.createCell(10);
            row3_cell10.setCellValue(jia_Ban_Bean.getSalay_days());
            row3_cell10.setCellStyle(cs2);

            Cell row3_cell11 = row3.createCell(11);
            row3_cell11.setCellValue(df.format(jia_Ban_Bean.getSalay()));
            row3_cell11.setCellStyle(cs2);
        }

        FileOutputStream fileOut = new FileOutputStream("E:\\IDEA\\workspace\\kaoqin\\src\\"+year+"年"+month+"月加班.xlsx");

        wb.write(fileOut);

        fileOut.close();

        return wb;
    }


}