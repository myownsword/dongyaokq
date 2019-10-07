import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        // 打卡数据导入
        List<Kq_Record> list = ExcelReaderUtil.readExcel("E:\\IDEA\\workspace\\kaoqin\\src\\考勤数据_201908.xls");
        MysqlUtil.saveKqTable(list);

        //法定节假日
//        String[] three_salay = new String[]{"13"};
        String[] three_salay = new String[0];

        //考勤表生成
        MysqlUtil.toExcelKqTable("2019-08",three_salay);


    }

}
