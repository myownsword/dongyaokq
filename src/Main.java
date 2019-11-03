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
//        List<Kq_Record> list = ExcelReaderUtil.readExcel("E:\\IDEA\\workspace\\kaoqin\\src\\201910考勤一工段.xls");
//        MysqlUtil.saveKqTable(list,"2019-10");

        //法定节假日
        String[] three_salay = new String[]{"01","02","03","04","05","06","07"};
//        String[] three_salay = new String[0];

        //法定节假日调班
        String[] exclude_day = new String[]{"12"};

        //考勤表生成
        MysqlUtil.toExcelKqTable("2019-10",three_salay,exclude_day);


    }

}
