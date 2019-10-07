import org.apache.poi.xwpf.converter.core.utils.StringUtils;

import java.io.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class MysqlUtil {
    public static void main(String[] args) throws Exception {
        Connection conn = null;
        String sql;
        // MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值
        // 避免中文乱码要指定useUnicode和characterEncoding
        // 执行数据库操作之前要在数据库管理系统上创建一个数据库，名字自己定，
        // 下面语句之前就要先创建javademo数据库
        String url = "jdbc:mysql://localhost:3306/apolloconfigdb?"
                + "user=root&password=Pwd_2019&useUnicode=true&characterEncoding=UTF8&serverTimezone=GMT%2B8";

        url ="jdbc:mysql://localhost:3306/dongyao?"
                + "user=root&password=Pwd_2019&useUnicode=true&characterEncoding=UTF8&serverTimezone=GMT%2B8";
        try {
            // 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
            // 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
            Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
            // or:
            // com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
            // or：
            // new com.mysql.jdbc.Driver();
 
            System.out.println("成功加载MySQL驱动程序");
            // 一个Connection代表一个数据库连接
            conn = DriverManager.getConnection(url);
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            Statement stmt = conn.createStatement();
            sql = "create table student(NO char(20),name varchar(20),primary key(NO))";
            int result = stmt.executeUpdate(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
            if (result != -1) {
                System.out.println("创建数据表成功");
                sql = "insert into student(NO,name) values('2012001','陶伟基')";
                result = stmt.executeUpdate(sql);
                sql = "insert into student(NO,name) values('2012002','周小俊')";
                result = stmt.executeUpdate(sql);
                sql = "select * from student";
                ResultSet rs = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
                System.out.println("学号\t姓名");
                while (rs.next()) {
                    System.out
                            .println(rs.getString(1) + "\t" + rs.getString(2));// 入如果返回的是int类型可以用getInt()
                }
            }
        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
 
    }

    /**
     * 保存考勤信息
     *
     */
    public static void saveKqTable(List<Kq_Record> list_rc) throws SQLException {
        Connection conn = null;
        String sql;
        // MySQL的JDBC URL编写方式：jdbc:♠://主机名称：连接端口/数据库的名称?参数=值
        // 避免中文乱码要指定useUnicode和characterEncoding
        // 执行数据库操作之前要在数据库管理系统上创建一个数据库，名字自己定，
        // 下面语句之前就要先创建javademo数据库
        String url = "jdbc:mysql://localhost:3306/apolloconfigdb?"
                + "user=root&password=Pwd_2019&useUnicode=true&characterEncoding=UTF8&serverTimezone=GMT%2B8";

        url ="jdbc:mysql://localhost:3306/dongyao?"
                + "user=root&password=Pwd_2019&useUnicode=true&characterEncoding=UTF8&serverTimezone=GMT%2B8";
        try {
            // 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
            // 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
            Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
            // or:
            // com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
            // or：
            // new com.mysql.jdbc.Driver();

            System.out.println("成功加载MySQL驱动程序");
            // 一个Connection代表一个数据库连接
            conn = DriverManager.getConnection(url);
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            Statement stmt = conn.createStatement();
            sql = "delete from kq_table";
            stmt.executeUpdate(sql);
            for (Kq_Record kr : list_rc) {
                if(StringUtils.isNotEmpty(kr.getId()) && StringUtils.isNotEmpty(kr.getName()) && StringUtils.isNotEmpty(kr.getKq_time())) {
                    sql = "insert into kq_table(id,name,kq_time) values('"+kr.getId()+"','"+kr.getName()+"',DATE_FORMAT('"+kr.getKq_time()+"', '%Y-%m-%d %H:%i:%s'))";
                    System.out.println(sql);
                    stmt.executeUpdate(sql);
                }
            }

        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }

    /**
     * 分析并导出考勤信息
     *
     */
    public static void toExcelKqTable(String yyyy_mm,String[] three_salay) throws SQLException {
        Connection conn = null;
        String sql;
        // MySQL的JDBC URL编写方式：jdbc:♠://主机名称：连接端口/数据库的名称?参数=值
        // 避免中文乱码要指定useUnicode和characterEncoding
        // 执行数据库操作之前要在数据库管理系统上创建一个数据库，名字自己定，
        // 下面语句之前就要先创建javademo数据库
        String url = "jdbc:mysql://localhost:3306/apolloconfigdb?"
                + "user=root&password=Pwd_2019&useUnicode=true&characterEncoding=UTF8&serverTimezone=GMT%2B8";

        url ="jdbc:mysql://localhost:3306/dongyao?"
                + "user=root&password=Pwd_2019&useUnicode=true&characterEncoding=UTF8&serverTimezone=GMT%2B8";
        try {
            // 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
            // 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
            Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
            // or:
            // com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
            // or：
            // new com.mysql.jdbc.Driver();

            System.out.println("成功加载MySQL驱动程序");
            // 一个Connection代表一个数据库连接
            conn = DriverManager.getConnection(url);
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            Statement stmt = conn.createStatement();
            sql = "SELECT t.id,t.name,date_format(t.kq_time,'%Y-%m-%d') kq_date,min(t.kq_time) min_date,max(t.kq_time) max_date FROM `kq_table` t,`user_info` t2 where t.id=t2.id and t.name='刘贞' group by t.id,t.name,date_format(t.kq_time,'%Y-%m-%d') order by t.id asc,t.name asc,date_format(t.kq_time,'%Y-%m-%d') asc ";
            ResultSet rs = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值

            List<Kq_Record> list_rc = new ArrayList<Kq_Record>() ;

            while (rs.next()) {

                Kq_Record kr=new Kq_Record();
                kr.setId(rs.getString("id"));
                kr.setName(rs.getString("name"));
                kr.setKq_time(rs.getString("kq_date"));
                kr.setKq_start_time(rs.getString("min_date"));
                kr.setKq_end_time(rs.getString("max_date"));
                list_rc.add(kr);

            }

            //按人调整后list
            List<Kq_Record_Person> person_list = new ArrayList<Kq_Record_Person>();
            //每人考勤日期list
            List<Map<String,Object>> kq_date_list = new ArrayList<Map<String,Object>>();
            //按人调整后bean
            Kq_Record_Person kq_person = new Kq_Record_Person();

            for (int i = 0; i < list_rc.size(); i++) {

                Kq_Record kr = list_rc.get(i);

                if(i==0) {
                    kq_person.setId(kr.getId().trim());
                    kq_person.setName(kr.getName().trim());
                }

                //每人考勤日期list
                //日期MAP
                Map<String,Object> kq_time_map = new HashMap<String,Object>();
                kq_time_map.put("kq_time",kr.getKq_time());
                kq_time_map.put("kq_start_time",kr.getKq_start_time());
                kq_time_map.put("kq_end_time",kr.getKq_end_time());
                kq_date_list.add(kq_time_map);

                if(i!=list_rc.size()-1) {
                    Kq_Record kr_next = list_rc.get(i+1);
                    if(kr.getId().trim().equals(kr_next.getId().trim())
                    && kr.getName().trim().equals(kr_next.getName().trim())) {

                    }else{
                        kq_person.setKq_date_list(depCopy(kq_date_list));
                        person_list.add(kq_person);

                        //下个人统计
                        kq_person = new Kq_Record_Person();
//                        kq_person.setId();
                        kq_person.setId(kr_next.getId().trim());
                        kq_person.setName(kr_next.getName().trim());
                        kq_date_list = new ArrayList<Map<String,Object>>();
                    }
                }else{
                    kq_person.setId(kr.getId().trim());
                    kq_person.setName(kr.getName().trim());
                    kq_person.setKq_date_list(depCopy(kq_date_list));
                    person_list.add(kq_person);
                }
            }

            Calendar calendar=Calendar.getInstance();


            String DATE_PATTERN_YYYYMMDD = "yyyy-MM-dd";

            SimpleDateFormat timeFt = new SimpleDateFormat(DATE_PATTERN_YYYYMMDD);
            Date kq_time = timeFt.parse(yyyy_mm+"-01");

//            calendar.setTime(new Date());
            calendar.setTime(kq_time);

            String year=String.valueOf(calendar.get(Calendar.YEAR));
            String month=String.valueOf(calendar.get(Calendar.MONTH)+1);
            int   maxDate   =   calendar.getActualMaximum(Calendar.DATE);
            for (Kq_Record_Person kq_record_person : person_list) {
                List<Map<String, Object>> kq_date_list_tmp = kq_record_person.getKq_date_list();
                List<Map<String, Object>> kq_date_list_new = new ArrayList<Map<String,Object>>();
                for(int j=1;j<=maxDate;j++) {
                    boolean is_exsit = false;
                    int _index =0;
                    for(int i=0;i<kq_date_list_tmp.size();i++) {
                        if((year+"-"+String.format("%02d", Integer.valueOf(month))+"-"+String.format("%02d", j)).equals(kq_date_list_tmp.get(i).get("kq_time"))) {
                            is_exsit = true;
                            _index = i;
                            break;
                        }
                    }
                    if(!is_exsit) {
                        Map<String,Object> map = new HashMap<String,Object>();
                        map.put("kq_time",year+"-"+String.format("%02d", Integer.valueOf(month))+"-"+String.format("%02d", j));
                        map.put("type","休");
                        kq_date_list_new.add(map);
                    }else{
                        kq_date_list_new.add(kq_date_list_tmp.get(_index));
                    }
                }
                kq_record_person.setKq_date_list(kq_date_list_new);
            }

            //导出考勤excel
            toExcel(person_list);

            ExcelReaderUtil.creatExcel(person_list,yyyy_mm);

            for (Kq_Record_Person kq_record_person : person_list) {
                System.out.println(kq_record_person.getId());
                System.out.println(kq_record_person.getName());
                for (Map<String, Object> stringObjectMap : kq_record_person.getKq_date_list()) {
                    System.out.println(stringObjectMap);
                }
            }

            List<Jia_Ban_Bean> jia_ban_list = toJiaBanExcel(person_list,yyyy_mm,three_salay);

            //导出加班excel
            ExcelReaderUtil.creatJiaBanExcel(jia_ban_list,yyyy_mm);

        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }

    /***
     * 方法一对集合进行深拷贝 注意需要对泛型类进行序列化(实现Serializable)
     *
     * @param srcList
     * @param <T>
     * @return
     */
    public static <T> List<T> depCopy(List<T> srcList) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(srcList);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream inStream = new ObjectInputStream(byteIn);
            List<T> destList = (List<T>) inStream.readObject();
            return destList;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 导出excel
     *
     * @return
     */
    public static void toExcel(List<Kq_Record_Person> person_list) throws ParseException {
//            person_list数据
//        000000522
//        穆鑫
//        {kq_start_time=2019-09-01 06:56:06, kq_time=2019-09-01, kq_end_time=2019-09-01 18:33:20}
//        {kq_start_time=2019-09-02 17:55:18, kq_time=2019-09-02, kq_end_time=2019-09-02 17:55:20}
//        {kq_start_time=2019-09-03 07:32:09, kq_time=2019-09-03, kq_end_time=2019-09-03 07:32:11}
//        {kq_time=2019-09-04, type=休}
//        {kq_start_time=2019-09-05 07:01:51, kq_time=2019-09-05, kq_end_time=2019-09-05 18:30:33}
        String DATE_PATTERN_YYYYMMDD = "yyyy-MM-dd";
        String DATE_PATTERN_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

        SimpleDateFormat timeFt = new SimpleDateFormat(DATE_PATTERN_YYYYMMDD);
        SimpleDateFormat timeFt2 = new SimpleDateFormat(DATE_PATTERN_YYYYMMDDHHMMSS);

        //当前时间
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());

        for (int i = 0; i < person_list.size(); i++) {
            Kq_Record_Person kq_record_person = person_list.get(i);

            for (int i1 = 0; i1 < kq_record_person.getKq_date_list().size(); i1++) {
                Map<String, Object> stringObjectMap= kq_record_person.getKq_date_list().get(i1);

                if("休".equals(stringObjectMap.get("type"))){
                    continue;
                }

                Date kq_time = timeFt.parse(stringObjectMap.get("kq_time").toString());
                Date kq_start_time = timeFt2.parse(stringObjectMap.get("kq_start_time").toString());

                Date kq_end_time = null;
                if(stringObjectMap.get("kq_end_time")!=null) {
                    kq_end_time = timeFt2.parse(stringObjectMap.get("kq_end_time").toString());
                }

                Calendar kq_time_cal=Calendar.getInstance();
                kq_time_cal.setTime(kq_time);
                Calendar kq_start_time_cal=Calendar.getInstance();
                kq_start_time_cal.setTime(kq_start_time);
                Calendar kq_end_time_cal=(Calendar)calendar.clone();
                if(kq_end_time!=null) {
                    kq_end_time_cal.setTime(kq_end_time);
                    //判断最早和最晚打卡时间大约4小时 否则为无效打卡
                    if (kq_end_time_cal.getTimeInMillis()-kq_start_time_cal.getTimeInMillis()<4*60*60*1000) {
                        kq_end_time_cal=(Calendar)calendar.clone();
                    }
                }

                if(i1!=kq_record_person.getKq_date_list().size()-1) {
                    Map<String, Object> stringObjectMapNext= kq_record_person.getKq_date_list().get(i1+1);

                    if("休".equals(stringObjectMapNext.get("type"))){

                        if(kq_start_time_cal.get(Calendar.HOUR_OF_DAY) < 8
                                || (kq_start_time_cal.get(Calendar.HOUR_OF_DAY)==8&&kq_start_time_cal.get(Calendar.MINUTE)<30)) {
                            if(!kq_end_time_cal.equals(calendar)) {
                                if(kq_end_time_cal.get(Calendar.HOUR_OF_DAY)>=17 ) {
                                    stringObjectMap.put("type","白");
                                }
                            }else{
                                stringObjectMap.put("type","可能休");
                            }
                        }

                        continue;
                    }

                    Date kq_time_next = timeFt.parse(stringObjectMapNext.get("kq_time").toString());
                    Date kq_start_time_next = timeFt2.parse(stringObjectMapNext.get("kq_start_time").toString());
                    Date kq_end_time_next = null;
                    if(stringObjectMapNext.get("kq_end_time")!=null) {
                        kq_end_time_next = timeFt2.parse(stringObjectMapNext.get("kq_end_time").toString());
                    }

                    Calendar kq_time_cal_next=Calendar.getInstance();
                    kq_time_cal_next.setTime(kq_time_next);
                    Calendar kq_start_time_cal_next=Calendar.getInstance();
                    kq_start_time_cal_next.setTime(kq_start_time_next);
                    Calendar kq_end_time_cal_next=(Calendar)calendar.clone();;
                    if(kq_end_time_next!=null) {
                        kq_end_time_cal_next.setTime(kq_end_time_next);
                        //判断最早和最晚打卡时间大约4小时 否则为无效打卡
                        if (kq_end_time_cal_next.getTimeInMillis()-kq_start_time_cal_next.getTimeInMillis()<4*60*60*1000) {
                            kq_end_time_cal_next = (Calendar)calendar.clone();
                        }
                    }

                    if(kq_start_time_cal.get(Calendar.HOUR_OF_DAY) < 8
                            || (kq_start_time_cal.get(Calendar.HOUR_OF_DAY)==8&&kq_start_time_cal.get(Calendar.MINUTE)<30)) {
                        if(!kq_end_time_cal.equals(calendar)) {
                            if(kq_end_time_cal.get(Calendar.HOUR_OF_DAY)>=17 ) {
                                stringObjectMap.put("type","白");
                            }
                        }
                    }
                    //打卡时间在13-18之前算夜班
                    if(kq_start_time_cal.get(Calendar.HOUR_OF_DAY)<18 && kq_start_time_cal.get(Calendar.HOUR_OF_DAY) >12 && kq_end_time_cal.equals(calendar)) {
                        if(kq_start_time_cal_next.get(Calendar.HOUR_OF_DAY)>=8 && kq_end_time_cal_next.equals(calendar)) {
                            stringObjectMap.put("type","夜");
                            stringObjectMapNext.put("type","休");
                            i1++;
                        }
                    }
                    if(kq_start_time_cal.get(Calendar.HOUR_OF_DAY)>=8 && kq_start_time_cal.get(Calendar.DAY_OF_MONTH) ==1 && kq_end_time_cal.equals(calendar)) {
                        stringObjectMap.put("type","可能休");
                    }
                }else{
                    if(kq_start_time_cal.get(Calendar.HOUR_OF_DAY) < 8
                            || (kq_start_time_cal.get(Calendar.HOUR_OF_DAY)==8&&kq_start_time_cal.get(Calendar.MINUTE)<30)) {
                        if(!kq_end_time_cal.equals(calendar)) {
                            if(kq_end_time_cal.get(Calendar.HOUR_OF_DAY)>=17 ) {
                                stringObjectMap.put("type","白");
                            }
                        }else{
                            stringObjectMap.put("type","可能休");
                        }
                    }
                }
            }
        }
    }

    /***
     * 导出加班excel
     *
     * @return
     */
    public static List<Jia_Ban_Bean> toJiaBanExcel(List<Kq_Record_Person> person_list,String yyyy_mm,String[] three_salay) throws ParseException {
//            person_list数据
////        000000522
////        穆鑫
////        {kq_start_time=2019-09-01 06:56:06, kq_time=2019-09-01, kq_end_time=2019-09-01 18:33:20}
////        {kq_start_time=2019-09-02 17:55:18, kq_time=2019-09-02, kq_end_time=2019-09-02 17:55:20}
////        {kq_start_time=2019-09-03 07:32:09, kq_time=2019-09-03, kq_end_time=2019-09-03 07:32:11}
////        {kq_time=2019-09-04, type=休}
////        {kq_start_time=2019-09-05 07:01:51, kq_time=2019-09-05, kq_end_time=2019-09-05 18:30:33}
        String DATE_PATTERN_YYYYMMDD = "yyyy-MM-dd";
        String DATE_PATTERN_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

        SimpleDateFormat timeFt = new SimpleDateFormat(DATE_PATTERN_YYYYMMDD);
        SimpleDateFormat timeFt2 = new SimpleDateFormat(DATE_PATTERN_YYYYMMDDHHMMSS);

        List<Jia_Ban_Bean> listResult = new ArrayList<Jia_Ban_Bean>();

        //当前时间
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());

        for (int i = 0; i < person_list.size(); i++) {
            Kq_Record_Person kq_record_person = person_list.get(i);

            for (int i1 = 0; i1 < kq_record_person.getKq_date_list().size(); i1++) {
                Map<String, Object> stringObjectMap= kq_record_person.getKq_date_list().get(i1);

                String jiaban_kind = get_kq_type(kq_record_person.getKq_date_list(),i1);
                //白夜休休
                if(jiaban_kind.startsWith("1")) {

                    if("11".equals(jiaban_kind)) {
                        for (String s : three_salay) {
                            if((yyyy_mm+"-"+s).equals(stringObjectMap.get("kq_time"))) {
                                Jia_Ban_Bean jia_ban_bean = new Jia_Ban_Bean();
                                //岗位名称
                                jia_ban_bean.setPosm_name("");
                                //加班人数
                                jia_ban_bean.setJia_ban_ren_shu("1");
                                //人员编码
                                jia_ban_bean.setId(kq_record_person.getId());
                                //加班人员名单
                                jia_ban_bean.setName(kq_record_person.getName());
                                //加班类型
                                jia_ban_bean.setJia_ban_type("法定(300%)");
                                //加班日期
//                                jia_ban_bean.setJia_ban_start_time(stringObjectMap.get("kq_start_time").toString());
//                                jia_ban_bean.setJia_ban_end_time(stringObjectMap.get("kq_end_time").toString());

                                Date kq_time = timeFt.parse(stringObjectMap.get("kq_time").toString());

                                Calendar kq_time_cal=Calendar.getInstance();
                                kq_time_cal.setTime(kq_time);

                                int month = kq_time_cal.get(Calendar.MONTH) + 1;
                                int day = kq_time_cal.get(Calendar.DAY_OF_MONTH);

                                String jia_ban_time_2bei = month+"月"+day+"月08:30-17:00";
                                jia_ban_bean.setJia_ban_time(jia_ban_time_2bei);
                                jia_ban_bean.setJia_ban_days(3);
                                jia_ban_bean.setSalay(jia_ban_bean.getSalay_days()*jia_ban_bean.getJia_ban_days());
                                jia_ban_bean.setBuchangfangshi("支付加班费");

                                listResult.add(jia_ban_bean);
                            }
                            //白夜休休-夜
                            if(i1<kq_record_person.getKq_date_list().size()-1) {
                                Map<String, Object> stringObjectMap_next= kq_record_person.getKq_date_list().get(i1+1);
                                if((yyyy_mm+"-"+s).equals(stringObjectMap_next.get("kq_time"))) {
                                    Jia_Ban_Bean jia_ban_bean = new Jia_Ban_Bean();
                                    //岗位名称
                                    jia_ban_bean.setPosm_name("");
                                    //加班人数
                                    jia_ban_bean.setJia_ban_ren_shu("1");
                                    //人员编码
                                    jia_ban_bean.setId(kq_record_person.getId());
                                    //加班人员名单
                                    jia_ban_bean.setName(kq_record_person.getName());
                                    //加班类型
                                    jia_ban_bean.setJia_ban_type("法定(300%)");
                                    //加班日期
//                                    jia_ban_bean.setJia_ban_start_time(stringObjectMap_next.get("kq_start_time").toString());
//                                    jia_ban_bean.setJia_ban_end_time(stringObjectMap_next.get("kq_end_time").toString());

                                    Date kq_time = timeFt.parse(stringObjectMap_next.get("kq_time").toString());

                                    Calendar kq_time_cal_next=Calendar.getInstance();
                                    kq_time_cal_next.setTime(kq_time);

                                    int month = kq_time_cal_next.get(Calendar.MONTH) + 1;
                                    int day = kq_time_cal_next.get(Calendar.DAY_OF_MONTH);

                                    String jia_ban_time_3bei = month+"月"+day+"月08:30-17:00";
                                    jia_ban_bean.setJia_ban_time(jia_ban_time_3bei);
                                    jia_ban_bean.setJia_ban_days(3);
                                    jia_ban_bean.setSalay(jia_ban_bean.getSalay_days()*jia_ban_bean.getJia_ban_days());
                                    jia_ban_bean.setBuchangfangshi("支付加班费");

                                    listResult.add(jia_ban_bean);
                                }

                            }
                            //白夜休休-休1
                            if(i1<kq_record_person.getKq_date_list().size()-2) {
                                Map<String, Object> stringObjectMap_next2= kq_record_person.getKq_date_list().get(i1+2);
                                if((yyyy_mm+"-"+s).equals(stringObjectMap_next2.get("kq_time"))) {
                                    Jia_Ban_Bean jia_ban_bean = new Jia_Ban_Bean();
                                    //岗位名称
                                    jia_ban_bean.setPosm_name("");
                                    //加班人数
                                    jia_ban_bean.setJia_ban_ren_shu("1");
                                    //人员编码
                                    jia_ban_bean.setId(kq_record_person.getId());
                                    //加班人员名单
                                    jia_ban_bean.setName(kq_record_person.getName());
                                    //加班类型
                                    jia_ban_bean.setJia_ban_type("法定(300%)");
                                    //加班日期
//                                    jia_ban_bean.setJia_ban_start_time(stringObjectMap_next2.get("kq_start_time").toString());
//                                    jia_ban_bean.setJia_ban_end_time(stringObjectMap_next2.get("kq_end_time").toString());

                                    Date kq_time = timeFt.parse(stringObjectMap_next2.get("kq_time").toString());

                                    Calendar kq_time_cal_next=Calendar.getInstance();
                                    kq_time_cal_next.setTime(kq_time);

                                    int month = kq_time_cal_next.get(Calendar.MONTH) + 1;
                                    int day = kq_time_cal_next.get(Calendar.DAY_OF_MONTH);

                                    String jia_ban_time_3bei = month+"月"+day+"月08:30-17:00";
                                    jia_ban_bean.setJia_ban_time(jia_ban_time_3bei);
                                    jia_ban_bean.setJia_ban_days(3);
                                    jia_ban_bean.setSalay(jia_ban_bean.getSalay_days()*jia_ban_bean.getJia_ban_days());
                                    jia_ban_bean.setBuchangfangshi("支付加班费");

                                    listResult.add(jia_ban_bean);
                                }
                            }
                            //白夜休休-休2
                            if(i1<kq_record_person.getKq_date_list().size()-3) {
                                Map<String, Object> stringObjectMap_next3= kq_record_person.getKq_date_list().get(i1+3);
                                if((yyyy_mm+"-"+s).equals(stringObjectMap_next3.get("kq_time"))) {
                                    Jia_Ban_Bean jia_ban_bean = new Jia_Ban_Bean();
                                    //岗位名称
                                    jia_ban_bean.setPosm_name("");
                                    //加班人数
                                    jia_ban_bean.setJia_ban_ren_shu("1");
                                    //人员编码
                                    jia_ban_bean.setId(kq_record_person.getId());
                                    //加班人员名单
                                    jia_ban_bean.setName(kq_record_person.getName());
                                    //加班类型
                                    jia_ban_bean.setJia_ban_type("法定(300%)");
                                    //加班日期
//                                    jia_ban_bean.setJia_ban_start_time(stringObjectMap_next3.get("kq_start_time").toString());
//                                    jia_ban_bean.setJia_ban_end_time(stringObjectMap_next3.get("kq_end_time").toString());

                                    Date kq_time = timeFt.parse(stringObjectMap_next3.get("kq_time").toString());

                                    Calendar kq_time_cal_next=Calendar.getInstance();
                                    kq_time_cal_next.setTime(kq_time);

                                    int month = kq_time_cal_next.get(Calendar.MONTH) + 1;
                                    int day = kq_time_cal_next.get(Calendar.DAY_OF_MONTH);

                                    String jia_ban_time_3bei = month+"月"+day+"月08:30-17:00";
                                    jia_ban_bean.setJia_ban_time(jia_ban_time_3bei);
                                    jia_ban_bean.setJia_ban_days(3);
                                    jia_ban_bean.setSalay(jia_ban_bean.getSalay_days()*jia_ban_bean.getJia_ban_days());
                                    jia_ban_bean.setBuchangfangshi("支付加班费");

                                    listResult.add(jia_ban_bean);
                                }
                            }
                        }
                        if(i1<kq_record_person.getKq_date_list().size()-3) {
                            Map<String, Object> stringObjectMapNext3 = kq_record_person.getKq_date_list().get(i1 + 3);
                            if("休".equals(stringObjectMapNext3.get("type"))) {
                                continue;
                            }
                            Date kq_start_time_next = timeFt2.parse(stringObjectMapNext3.get("kq_start_time").toString());
                            Date kq_end_time_next = null;
                            if(stringObjectMapNext3.get("kq_end_time")!=null) {
                                kq_end_time_next = timeFt2.parse(stringObjectMapNext3.get("kq_end_time").toString());
                            }

                            Calendar kq_start_time_cal_next=Calendar.getInstance();
                            kq_start_time_cal_next.setTime(kq_start_time_next);
                            Calendar kq_end_time_cal_next=(Calendar)calendar.clone();;
                            if(kq_end_time_next!=null) {
                                kq_end_time_cal_next.setTime(kq_end_time_next);
                                //判断最早和最晚打卡时间大约4小时 否则为无效打卡
                                if (kq_end_time_cal_next.getTimeInMillis()-kq_start_time_cal_next.getTimeInMillis()<4*60*60*1000) {
                                    kq_end_time_cal_next = (Calendar)calendar.clone();
                                }
                            }
                            if(kq_start_time_cal_next.get(Calendar.HOUR_OF_DAY) < 8
                                    || (kq_start_time_cal_next.get(Calendar.HOUR_OF_DAY) == 8 && kq_start_time_cal_next.get(Calendar.MINUTE)<30)) {
                                if(!kq_end_time_cal_next.equals(calendar)) {
                                    if (kq_end_time_cal_next.get(Calendar.HOUR_OF_DAY) >= 17) {

                                        //7点半之前6点十分之后算加班
                                        String base_jiaban_start = " 08:30:00";

                                        String base_jiaban_end = " 17:00:00";
                                        Date base_jiaban_start_time = timeFt2.parse(stringObjectMapNext3.get("kq_time").toString()+base_jiaban_start);
                                        Calendar base_jiaban_start_time_cal=Calendar.getInstance();
                                        base_jiaban_start_time_cal.setTime(base_jiaban_start_time);

                                        Date base_jiaban_end_time = timeFt2.parse(stringObjectMapNext3.get("kq_time").toString()+base_jiaban_end);
                                        Calendar base_jiaban_end_time_cal=Calendar.getInstance();
                                        base_jiaban_end_time_cal.setTime(base_jiaban_end_time);

                                        Jia_Ban_Bean jia_ban_bean = new Jia_Ban_Bean();
                                        //岗位名称
                                        jia_ban_bean.setPosm_name("");
                                        //加班人数
                                        jia_ban_bean.setJia_ban_ren_shu("1");
                                        //人员编码
                                        jia_ban_bean.setId(kq_record_person.getId());
                                        //加班人员名单
                                        jia_ban_bean.setName(kq_record_person.getName());
                                        //加班类型
                                        jia_ban_bean.setJia_ban_type("延时(150%)");
                                        //加班日期
                                        jia_ban_bean.setJia_ban_start_time(stringObjectMapNext3.get("kq_start_time").toString());
                                        jia_ban_bean.setJia_ban_end_time(stringObjectMapNext3.get("kq_end_time").toString());
                                        int month = kq_end_time_cal_next.get(Calendar.MONTH) + 1;
                                        int day = kq_end_time_cal_next.get(Calendar.DAY_OF_MONTH);
                                        int hour_start = kq_start_time_cal_next.get(Calendar.HOUR_OF_DAY);
                                        int minute_start = kq_start_time_cal_next.get(Calendar.MINUTE);
                                        int hour_end = kq_end_time_cal_next.get(Calendar.HOUR_OF_DAY);
                                        int minute_end = kq_end_time_cal_next.get(Calendar.MINUTE);

                                        String jia_ban_time_2bei = month+"月"+day+"月08:30-17:00";
                                        jia_ban_bean.setJia_ban_time(jia_ban_time_2bei);
                                        jia_ban_bean.setJia_ban_days(1.5);
                                        jia_ban_bean.setSalay(jia_ban_bean.getSalay_days()*jia_ban_bean.getJia_ban_days());
                                        jia_ban_bean.setBuchangfangshi("支付加班费");

                                        listResult.add(jia_ban_bean);

                                        long wan_jiaban = kq_end_time_cal_next.getTimeInMillis()-base_jiaban_end_time_cal.getTimeInMillis();
                                        long zao_jiaban = base_jiaban_start_time_cal.getTimeInMillis()-kq_start_time_cal_next.getTimeInMillis();
                                        //早上加班超过一小时
                                        if(zao_jiaban>59*60*1000) {
                                            Jia_Ban_Bean jia_ban_bean2 = new Jia_Ban_Bean();
                                            //岗位名称
                                            jia_ban_bean2.setPosm_name("");
                                            //加班人数
                                            jia_ban_bean2.setJia_ban_ren_shu("1");
                                            //人员编码
                                            jia_ban_bean2.setId(kq_record_person.getId());
                                            //加班人员名单
                                            jia_ban_bean2.setName(kq_record_person.getName());
                                            //加班类型
                                            jia_ban_bean2.setJia_ban_type("延时(150%)");
                                            //加班日期
                                            jia_ban_bean2.setJia_ban_start_time(stringObjectMapNext3.get("kq_start_time").toString());
                                            jia_ban_bean2.setJia_ban_end_time(stringObjectMapNext3.get("kq_end_time").toString());

                                            String jia_ban_time = month+"月"+day+"月"+hour_start+":"+minute_start+"-8:30";

                                            jia_ban_bean2.setJia_ban_time(jia_ban_time);
                                            jia_ban_bean2.setJia_ban_days(zao_jiaban*1.5/(8*60*60*1000));
                                            jia_ban_bean2.setSalay(jia_ban_bean2.getSalay_days()*jia_ban_bean2.getJia_ban_days());
                                            jia_ban_bean2.setBuchangfangshi("支付加班费");

                                            listResult.add(jia_ban_bean2);
                                        }
                                        //晚上加班超过18:10
                                        if(wan_jiaban>70*60*1000) {
                                            Jia_Ban_Bean jia_ban_bean3 = new Jia_Ban_Bean();
                                            //岗位名称
                                            jia_ban_bean3.setPosm_name("");
                                            //加班人数
                                            jia_ban_bean3.setJia_ban_ren_shu("1");
                                            //人员编码
                                            jia_ban_bean3.setId(kq_record_person.getId());
                                            //加班人员名单
                                            jia_ban_bean3.setName(kq_record_person.getName());
                                            //加班类型
                                            jia_ban_bean3.setJia_ban_type("延时(150%)");
                                            //加班日期
                                            jia_ban_bean3.setJia_ban_start_time(stringObjectMapNext3.get("kq_start_time").toString());
                                            jia_ban_bean3.setJia_ban_end_time(stringObjectMapNext3.get("kq_end_time").toString());

                                            String jia_ban_time = month+"月"+day+"月17:00-"+hour_end+":"+String.format("%02d", minute_end);

                                            jia_ban_bean3.setJia_ban_time(jia_ban_time);
                                            jia_ban_bean3.setJia_ban_days(wan_jiaban*1.5/(8*60*60*1000));
                                            jia_ban_bean3.setSalay(jia_ban_bean3.getSalay_days()*jia_ban_bean3.getJia_ban_days());
                                            jia_ban_bean3.setBuchangfangshi("支付加班费");

                                            listResult.add(jia_ban_bean3);
                                        }


                                    }
                                }
                            }
                        }
                    }
                // 常白
                }else if("2".equals(jiaban_kind)) {
                    Jia_Ban_Bean jia_ban_bean = new Jia_Ban_Bean();

                    Date kq_time = timeFt.parse(stringObjectMap.get("kq_time").toString());
                    Date kq_start_time = timeFt2.parse(stringObjectMap.get("kq_start_time").toString());

                    Date kq_end_time = null;
                    if(stringObjectMap.get("kq_end_time")!=null) {
                        kq_end_time = timeFt2.parse(stringObjectMap.get("kq_end_time").toString());
                    }

                    Calendar kq_time_cal=Calendar.getInstance();
                    kq_time_cal.setTime(kq_time);
                    Calendar kq_start_time_cal=Calendar.getInstance();
                    kq_start_time_cal.setTime(kq_start_time);
                    Calendar kq_end_time_cal=(Calendar)calendar.clone();
                    if(kq_end_time!=null) {
                        kq_end_time_cal.setTime(kq_end_time);
                        //判断最早和最晚打卡时间大约4小时 否则为无效打卡
                        if (kq_end_time_cal.getTimeInMillis()-kq_start_time_cal.getTimeInMillis()<4*60*60*1000) {
                            kq_end_time_cal=(Calendar)calendar.clone();
                        }
                    }


                    //7点半之前6点十分之后算加班
                    String base_jiaban_start = " 08:30:00";

                    String base_jiaban_end = " 17:00:00";
                    Date base_jiaban_start_time = timeFt2.parse(stringObjectMap.get("kq_time").toString()+base_jiaban_start);
                    Calendar base_jiaban_start_time_cal=Calendar.getInstance();
                    base_jiaban_start_time_cal.setTime(base_jiaban_start_time);

                    Date base_jiaban_end_time = timeFt2.parse(stringObjectMap.get("kq_time").toString()+base_jiaban_end);
                    Calendar base_jiaban_end_time_cal=Calendar.getInstance();
                    base_jiaban_end_time_cal.setTime(base_jiaban_end_time);
                    //
                    if("1".equals(is_holiday(yyyy_mm,three_salay,stringObjectMap.get("kq_time").toString()))
                        || "2".equals(is_holiday(yyyy_mm,three_salay,stringObjectMap.get("kq_time").toString()))) {

                        //岗位名称
                        jia_ban_bean.setPosm_name("");
                        //加班人数
                        jia_ban_bean.setJia_ban_ren_shu("1");
                        //人员编码
                        jia_ban_bean.setId(kq_record_person.getId());
                        //加班人员名单
                        jia_ban_bean.setName(kq_record_person.getName());
                        //加班类型
                        if("1".equals(is_holiday(yyyy_mm,three_salay,stringObjectMap.get("kq_time").toString()))) {
                            jia_ban_bean.setJia_ban_type("双休日(200%)");
                        }else{
                            jia_ban_bean.setJia_ban_type("法定(300%)");
                        }

                        //加班日期
                        jia_ban_bean.setJia_ban_start_time(stringObjectMap.get("kq_start_time").toString());
                        jia_ban_bean.setJia_ban_end_time(stringObjectMap.get("kq_end_time").toString());
                        int month = kq_time_cal.get(Calendar.MONTH) + 1;
                        int day = kq_time_cal.get(Calendar.DAY_OF_MONTH);
                        int hour_start = kq_start_time_cal.get(Calendar.HOUR_OF_DAY);
                        int minute_start = kq_start_time_cal.get(Calendar.MINUTE);
                        int hour_end = kq_end_time_cal.get(Calendar.HOUR_OF_DAY);
                        int minute_end = kq_end_time_cal.get(Calendar.MINUTE);

                        String jia_ban_time_2bei = month+"月"+day+"月08:30-17:00";
                        jia_ban_bean.setJia_ban_time(jia_ban_time_2bei);
                        if("1".equals(is_holiday(yyyy_mm,three_salay,stringObjectMap.get("kq_time").toString()))) {
                            jia_ban_bean.setJia_ban_days(2);
                        }else{
                            jia_ban_bean.setJia_ban_days(3);
                        }
                        jia_ban_bean.setSalay(jia_ban_bean.getSalay_days()*jia_ban_bean.getJia_ban_days());
                        jia_ban_bean.setBuchangfangshi("支付加班费");

                        listResult.add(jia_ban_bean);

                        long wan_jiaban = kq_end_time_cal.getTimeInMillis()-base_jiaban_end_time_cal.getTimeInMillis();
                        long zao_jiaban = base_jiaban_start_time_cal.getTimeInMillis()-kq_start_time_cal.getTimeInMillis();
                        //早上加班超过一小时
                        if(zao_jiaban>59*60*1000) {
                            Jia_Ban_Bean jia_ban_bean2 = new Jia_Ban_Bean();
                            //岗位名称
                            jia_ban_bean2.setPosm_name("");
                            //加班人数
                            jia_ban_bean2.setJia_ban_ren_shu("1");
                            //人员编码
                            jia_ban_bean2.setId(kq_record_person.getId());
                            //加班人员名单
                            jia_ban_bean2.setName(kq_record_person.getName());
                            //加班类型
                            jia_ban_bean2.setJia_ban_type("延时(150%)");
                            //加班日期
                            jia_ban_bean2.setJia_ban_start_time(stringObjectMap.get("kq_start_time").toString());
                            jia_ban_bean2.setJia_ban_end_time(stringObjectMap.get("kq_end_time").toString());

                            String jia_ban_time = month+"月"+day+"月"+hour_start+":"+minute_start+"-8:30";

                            jia_ban_bean2.setJia_ban_time(jia_ban_time);
                            jia_ban_bean2.setJia_ban_days(zao_jiaban*1.5/(8*60*60*1000));
                            jia_ban_bean2.setSalay(jia_ban_bean2.getSalay_days()*jia_ban_bean2.getJia_ban_days());
                            jia_ban_bean2.setBuchangfangshi("支付加班费");

                            listResult.add(jia_ban_bean2);
                        }
                        //晚上加班超过18:10
                        if(wan_jiaban>70*60*1000) {
                            Jia_Ban_Bean jia_ban_bean3 = new Jia_Ban_Bean();
                            //岗位名称
                            jia_ban_bean3.setPosm_name("");
                            //加班人数
                            jia_ban_bean3.setJia_ban_ren_shu("1");
                            //人员编码
                            jia_ban_bean3.setId(kq_record_person.getId());
                            //加班人员名单
                            jia_ban_bean3.setName(kq_record_person.getName());
                            //加班类型
                            jia_ban_bean3.setJia_ban_type("延时(150%)");
                            //加班日期
                            jia_ban_bean3.setJia_ban_start_time(stringObjectMap.get("kq_start_time").toString());
                            jia_ban_bean3.setJia_ban_end_time(stringObjectMap.get("kq_end_time").toString());

                            String jia_ban_time = month+"月"+day+"月17:00-"+hour_end+":"+String.format("%02d", minute_end);

                            jia_ban_bean3.setJia_ban_time(jia_ban_time);
                            jia_ban_bean3.setJia_ban_days(wan_jiaban*1.5/(8*60*60*1000));
                            jia_ban_bean3.setSalay(jia_ban_bean3.getSalay_days()*jia_ban_bean3.getJia_ban_days());
                            jia_ban_bean3.setBuchangfangshi("支付加班费");

                            listResult.add(jia_ban_bean3);
                        }

                    }else{
                        long wan_jiaban = kq_end_time_cal.getTimeInMillis()-base_jiaban_end_time_cal.getTimeInMillis();
                        long zao_jiaban = base_jiaban_start_time_cal.getTimeInMillis()-kq_start_time_cal.getTimeInMillis();

                        int month = kq_time_cal.get(Calendar.MONTH) + 1;
                        int day = kq_time_cal.get(Calendar.DAY_OF_MONTH);
                        int hour_start = kq_start_time_cal.get(Calendar.HOUR_OF_DAY);
                        int minute_start = kq_start_time_cal.get(Calendar.MINUTE);
                        int hour_end = kq_end_time_cal.get(Calendar.HOUR_OF_DAY);
                        int minute_end = kq_end_time_cal.get(Calendar.MINUTE);

                        //早上加班超过一小时
                        if(zao_jiaban>59*60*1000) {
                            //岗位名称
                            jia_ban_bean.setPosm_name("");
                            //加班人数
                            jia_ban_bean.setJia_ban_ren_shu("1");
                            //人员编码
                            jia_ban_bean.setId(kq_record_person.getId());
                            //加班人员名单
                            jia_ban_bean.setName(kq_record_person.getName());
                            //加班类型
                            jia_ban_bean.setJia_ban_type("延时(150%)");
                            //加班日期
                            jia_ban_bean.setJia_ban_start_time(stringObjectMap.get("kq_start_time").toString());
                            jia_ban_bean.setJia_ban_end_time(stringObjectMap.get("kq_end_time").toString());

                            String jia_ban_time = month+"月"+day+"月"+hour_start+":"+minute_start+"-8:30";

                            jia_ban_bean.setJia_ban_time(jia_ban_time);
                            jia_ban_bean.setJia_ban_days(zao_jiaban*1.5/(8*60*60*1000));
                            jia_ban_bean.setSalay(jia_ban_bean.getSalay_days()*jia_ban_bean.getJia_ban_days());
                            jia_ban_bean.setBuchangfangshi("支付加班费");

                            listResult.add(jia_ban_bean);
                        }
                        //晚上加班超过18:10
                        if(wan_jiaban>70*60*1000) {
                            Jia_Ban_Bean jia_ban_bean2 = new Jia_Ban_Bean();
                            //岗位名称
                            jia_ban_bean2.setPosm_name("");
                            //加班人数
                            jia_ban_bean2.setJia_ban_ren_shu("1");
                            //人员编码
                            jia_ban_bean2.setId(kq_record_person.getId());
                            //加班人员名单
                            jia_ban_bean2.setName(kq_record_person.getName());
                            //加班类型
                            jia_ban_bean2.setJia_ban_type("延时(150%)");
                            //加班日期
                            jia_ban_bean2.setJia_ban_start_time(stringObjectMap.get("kq_start_time").toString());
                            jia_ban_bean2.setJia_ban_end_time(stringObjectMap.get("kq_end_time").toString());

                            String jia_ban_time = month+"月"+day+"月17:00-"+hour_end+":"+String.format("%02d", minute_end);

                            jia_ban_bean2.setJia_ban_time(jia_ban_time);
                            jia_ban_bean2.setJia_ban_days(wan_jiaban*1.5/(8*60*60*1000));
                            jia_ban_bean2.setSalay(jia_ban_bean2.getSalay_days()*jia_ban_bean2.getJia_ban_days());
                            jia_ban_bean2.setBuchangfangshi("支付加班费");

                            listResult.add(jia_ban_bean2);
                        }
                    }


                //夜休
                }else if("31".equals(jiaban_kind)) {

                    Jia_Ban_Bean jia_ban_bean = new Jia_Ban_Bean();

                    Date kq_time = timeFt.parse(stringObjectMap.get("kq_time").toString());
                    Date kq_start_time = timeFt2.parse(stringObjectMap.get("kq_start_time").toString());

                    Date kq_end_time = null;
                    if(stringObjectMap.get("kq_end_time")!=null) {
                        kq_end_time = timeFt2.parse(stringObjectMap.get("kq_end_time").toString());
                    }

                    Calendar kq_time_cal=Calendar.getInstance();
                    kq_time_cal.setTime(kq_time);
                    Calendar kq_start_time_cal=Calendar.getInstance();
                    kq_start_time_cal.setTime(kq_start_time);
                    Calendar kq_end_time_cal=(Calendar)calendar.clone();
                    if(kq_end_time!=null) {
                        kq_end_time_cal.setTime(kq_end_time);
                        //判断最早和最晚打卡时间大约4小时 否则为无效打卡
                        if (kq_end_time_cal.getTimeInMillis()-kq_start_time_cal.getTimeInMillis()<4*60*60*1000) {
                            kq_end_time_cal=(Calendar)calendar.clone();
                        }
                    }

                    //岗位名称
                    jia_ban_bean.setPosm_name("");
                    //加班人数
                    jia_ban_bean.setJia_ban_ren_shu("1");
                    //人员编码
                    jia_ban_bean.setId(kq_record_person.getId());
                    //加班人员名单
                    jia_ban_bean.setName(kq_record_person.getName());
                    //加班类型
                    jia_ban_bean.setJia_ban_type("延时(150%)");
                    //加班日期
                    jia_ban_bean.setJia_ban_start_time(stringObjectMap.get("kq_start_time").toString());
                    jia_ban_bean.setJia_ban_end_time(stringObjectMap.get("kq_end_time").toString());

                    int month = kq_time_cal.get(Calendar.MONTH) + 1;
                    int day = kq_time_cal.get(Calendar.DAY_OF_MONTH);

                    String jia_ban_time_2bei = month+"月"+day+"月17:00-08:30";
                    jia_ban_bean.setJia_ban_time(jia_ban_time_2bei);

                    jia_ban_bean.setJia_ban_days(0.66);
                    jia_ban_bean.setSalay(jia_ban_bean.getSalay_days()*jia_ban_bean.getJia_ban_days());
                    jia_ban_bean.setBuchangfangshi("支付加班费");

                    listResult.add(jia_ban_bean);

                    for (String s : three_salay) {
                        if ((yyyy_mm + "-" + s).equals(stringObjectMap.get("kq_time"))) {
                            Jia_Ban_Bean jia_ban_bean2 = new Jia_Ban_Bean();
                            //岗位名称
                            jia_ban_bean2.setPosm_name("");
                            //加班人数
                            jia_ban_bean2.setJia_ban_ren_shu("1");
                            //人员编码
                            jia_ban_bean2.setId(kq_record_person.getId());
                            //加班人员名单
                            jia_ban_bean2.setName(kq_record_person.getName());
                            //加班类型
                            jia_ban_bean2.setJia_ban_type("法定(300%)");
                            //加班日期
//                            jia_ban_bean.setJia_ban_start_time(stringObjectMap.get("kq_start_time").toString());
//                            jia_ban_bean.setJia_ban_end_time(stringObjectMap.get("kq_end_time").toString());

                            String jia_ban_time_3bei = month + "月" + day + "月08:30-17:00";
                            jia_ban_bean2.setJia_ban_time(jia_ban_time_3bei);
                            jia_ban_bean2.setJia_ban_days(3);
                            jia_ban_bean2.setSalay(jia_ban_bean2.getSalay_days() * jia_ban_bean2.getJia_ban_days());
                            jia_ban_bean2.setBuchangfangshi("支付加班费");

                            listResult.add(jia_ban_bean2);
                        }
                        //白夜休休-夜
                        if (i1 < kq_record_person.getKq_date_list().size() - 1) {
                            Map<String, Object> stringObjectMap_next = kq_record_person.getKq_date_list().get(i1 + 1);
                            if ((yyyy_mm + "-" + s).equals(stringObjectMap_next.get("kq_time"))) {
                                Jia_Ban_Bean jia_ban_bean3 = new Jia_Ban_Bean();
                                //岗位名称
                                jia_ban_bean3.setPosm_name("");
                                //加班人数
                                jia_ban_bean3.setJia_ban_ren_shu("1");
                                //人员编码
                                jia_ban_bean3.setId(kq_record_person.getId());
                                //加班人员名单
                                jia_ban_bean3.setName(kq_record_person.getName());
                                //加班类型
                                jia_ban_bean3.setJia_ban_type("法定(300%)");
                                //加班日期
//                                jia_ban_bean.setJia_ban_start_time(stringObjectMap_next.get("kq_start_time").toString());
//                                jia_ban_bean.setJia_ban_end_time(stringObjectMap_next.get("kq_end_time").toString());


                                Calendar kq_time_cal_next = Calendar.getInstance();
                                kq_time_cal_next.setTime(kq_time);

                                int month_next = kq_time_cal_next.get(Calendar.MONTH) + 1;
                                int day_next = kq_time_cal_next.get(Calendar.DAY_OF_MONTH);

                                String jia_ban_time_3bei = month_next + "月" + day_next + "月08:30-17:00";
                                jia_ban_bean3.setJia_ban_time(jia_ban_time_3bei);
                                jia_ban_bean3.setJia_ban_days(3);
                                jia_ban_bean3.setSalay(jia_ban_bean3.getSalay_days() * jia_ban_bean3.getJia_ban_days());
                                jia_ban_bean3.setBuchangfangshi("支付加班费");

                                listResult.add(jia_ban_bean3);
                            }

                        }
                    }
                //串休
                }else if("4".equals(jiaban_kind)) {

                //正常休
                }else  if("5".equals(jiaban_kind)) {

                //不确定
                }else{

                }
            }
        }
        return listResult;
    }



    /**
     * 是否周末或法定节假日
     *
     * 1周末，2法定,0不是
     */
    public  static  String is_holiday(String yyyy_mm,String[] three_salay,String day) throws ParseException {


        SimpleDateFormat timeFt = new SimpleDateFormat("yyyy-MM-dd");

        Date kq_time = timeFt.parse(day);

        Calendar kq_time_cal=Calendar.getInstance();
        kq_time_cal.setTime(kq_time);

        for (String s : three_salay) {
            if((yyyy_mm+"-"+s).equals(day)) {
                return "2";
            }
        }

        int _week = kq_time_cal.get(Calendar.DAY_OF_WEEK) ;
        if (_week == Calendar.SUNDAY || _week == Calendar.SATURDAY) {
            return "1";
        }else{
            return "0";
        }
    }

    /***
     * 1：白夜休休 白天（8:00-18：00，夜:18:00-8:00）
     *     11：白，12：夜，13：休，14：休
     *
     * 2：常白(8:30-17:00)
     *
     * 3：夜休(17:00-8:30)
     *      31：夜，32：休
     *
     * 4：串休
     *
     * 5：正常休
     * 0:不确定
     * @return
     */
    public static String get_kq_type(List<Map<String,Object>> kq_date_list,int index) throws ParseException {
////        {kq_start_time=2019-09-01 06:56:06, kq_time=2019-09-01, kq_end_time=2019-09-01 18:33:20}
////        {kq_start_time=2019-09-02 17:55:18, kq_time=2019-09-02, kq_end_time=2019-09-02 17:55:20}
////        {kq_start_time=2019-09-03 07:32:09, kq_time=2019-09-03, kq_end_time=2019-09-03 07:32:11}
////        {kq_time=2019-09-04, type=休}
////        {kq_start_time=2019-09-05 07:01:51, kq_time=2019-09-05, kq_end_time=2019-09-05 18:30:33}

        SimpleDateFormat timeFt = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Map<String, Object> stringObjectMap= kq_date_list.get(index);

        //判断休息（白夜休休，夜休，休息，串休）
        if("休".equals(stringObjectMap.get("type"))){
            //白夜休休的第一个休或者夜休的休
            if(stringObjectMap.containsKey("kq_start_time")) {

                Date kq_start_time = timeFt2.parse(stringObjectMap.get("kq_start_time").toString());

                Date kq_end_time = null;
                if(stringObjectMap.get("kq_end_time")!=null) {
                    kq_end_time = timeFt2.parse(stringObjectMap.get("kq_end_time").toString());
                }

                //当前时间
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(new Date());

                Calendar kq_start_time_cal=Calendar.getInstance();
                kq_start_time_cal.setTime(kq_start_time);
                Calendar kq_end_time_cal=(Calendar)calendar.clone();
                if(kq_end_time!=null) {
                    kq_end_time_cal.setTime(kq_end_time);
                    //判断最早和最晚打卡时间大约4小时 否则为无效打卡
                    if (kq_end_time_cal.getTimeInMillis()-kq_start_time_cal.getTimeInMillis()<4*60*60*1000) {
                        kq_end_time_cal=(Calendar)calendar.clone();
                    }
                }
                //8点半以后下夜班 肯能是夜休的休
                if((kq_start_time_cal.get(Calendar.HOUR_OF_DAY) == 8 && kq_start_time_cal.get(Calendar.MINUTE)>30
                || kq_start_time_cal.get(Calendar.HOUR_OF_DAY) > 8)&& kq_end_time_cal.equals(calendar)) {
                    int is_yexiu_xiu = 0;
                    int is_baiyexiuxiu_xiu1=0;
                    if(index!=0) {
                        Map<String, Object> stringObjectMapBefore= kq_date_list.get(index-1);

                        if("休".equals(stringObjectMapBefore.get("type"))){
                            return "0";
                        }else{

                            Date kq_time_before = timeFt.parse(stringObjectMapBefore.get("kq_time").toString());
                            Calendar kq_time_cal_before=Calendar.getInstance();
                            kq_time_cal_before.setTime(kq_time_before);

                            Date kq_start_time_before = timeFt2.parse(stringObjectMapBefore.get("kq_start_time").toString());
                            Date kq_end_time_before = null;
                            if(stringObjectMapBefore.get("kq_end_time")!=null) {
                                kq_end_time_before = timeFt2.parse(stringObjectMapBefore.get("kq_end_time").toString());
                            }

                            Calendar kq_start_time_cal_before=Calendar.getInstance();
                            kq_start_time_cal_before.setTime(kq_start_time_before);
                            Calendar kq_end_time_cal_before=(Calendar)calendar.clone();;
                            if(kq_end_time_before!=null) {
                                kq_end_time_cal_before.setTime(kq_end_time_before);
                                //判断最早和最晚打卡时间大约4小时 否则为无效打卡
                                if (kq_end_time_cal_before.getTimeInMillis()-kq_start_time_cal_before.getTimeInMillis()<4*60*60*1000) {
                                    kq_end_time_cal_before = (Calendar)calendar.clone();
                                }
                            }
                            if(kq_start_time_cal_before.get(Calendar.HOUR_OF_DAY) < 17
                                    && kq_start_time_cal_before.get(Calendar.HOUR_OF_DAY) > 12
                                    && kq_end_time_cal_before.equals(calendar)) {
                                is_yexiu_xiu++;
                            }else if(kq_start_time_cal_before.get(Calendar.HOUR_OF_DAY) < 18
                                    && kq_start_time_cal_before.get(Calendar.HOUR_OF_DAY) > 17
                                    && kq_end_time_cal_before.equals(calendar)) {
                                is_baiyexiuxiu_xiu1++;
                            }

                        }
                    }
                    //取前两天
                    if(index>1) {
                        Map<String, Object> stringObjectMapBefore2= kq_date_list.get(index-2);

                        if("休".equals(stringObjectMapBefore2.get("type"))){
                            is_yexiu_xiu++;
                        }else{

                            Date kq_time_before2 = timeFt.parse(stringObjectMapBefore2.get("kq_time").toString());
                            Calendar kq_time_cal_before2=Calendar.getInstance();
                            kq_time_cal_before2.setTime(kq_time_before2);

                            Date kq_start_time_before2 = timeFt2.parse(stringObjectMapBefore2.get("kq_start_time").toString());
                            Date kq_end_time_before2 = null;
                            if(stringObjectMapBefore2.get("kq_end_time")!=null) {
                                kq_end_time_before2 = timeFt2.parse(stringObjectMapBefore2.get("kq_end_time").toString());
                            }

                            Calendar kq_start_time_cal_before2=Calendar.getInstance();
                            kq_start_time_cal_before2.setTime(kq_start_time_before2);
                            Calendar kq_end_time_cal_before2=(Calendar)calendar.clone();;
                            if(kq_end_time_before2!=null) {
                                kq_end_time_cal_before2.setTime(kq_end_time_before2);
                                //判断最早和最晚打卡时间大约4小时 否则为无效打卡
                                if (kq_end_time_cal_before2.getTimeInMillis()-kq_start_time_cal_before2.getTimeInMillis()<4*60*60*1000) {
                                    kq_end_time_cal_before2 = (Calendar)calendar.clone();
                                }
                            }
                            if(kq_start_time_cal_before2.get(Calendar.HOUR_OF_DAY) < 8 && (!kq_end_time_cal_before2.equals(calendar))
                                    && kq_end_time_cal_before2.get(Calendar.HOUR_OF_DAY) >= 18) {
                                is_baiyexiuxiu_xiu1++;
                            }

                        }
                    }
                    //判断下一天
                    if(index!=kq_date_list.size()-1) {
                        Map<String, Object> stringObjectMapNext= kq_date_list.get(index+1);

                        Date kq_time_next = timeFt.parse(stringObjectMapNext.get("kq_time").toString());
                        Calendar kq_time_cal_next=Calendar.getInstance();
                        kq_time_cal_next.setTime(kq_time_next);

                        if("休".equals(stringObjectMapNext.get("type"))){
                            is_baiyexiuxiu_xiu1++;
                        }else{

                            Date kq_start_time_next = timeFt2.parse(stringObjectMapNext.get("kq_start_time").toString());
                            Date kq_end_time_next = null;
                            if(stringObjectMapNext.get("kq_end_time")!=null) {
                                kq_end_time_next = timeFt2.parse(stringObjectMapNext.get("kq_end_time").toString());
                            }

                            Calendar kq_start_time_cal_next=Calendar.getInstance();
                            kq_start_time_cal_next.setTime(kq_start_time_next);
                            Calendar kq_end_time_cal_next=(Calendar)calendar.clone();;
                            if(kq_end_time_next!=null) {
                                kq_end_time_cal_next.setTime(kq_end_time_next);
                                //判断最早和最晚打卡时间大约4小时 否则为无效打卡
                                if (kq_end_time_cal_next.getTimeInMillis()-kq_start_time_cal_next.getTimeInMillis()<4*60*60*1000) {
                                    kq_end_time_cal_next = (Calendar)calendar.clone();
                                }
                            }
                            if(kq_start_time_cal_next.get(Calendar.HOUR_OF_DAY) < 17
                                    && kq_start_time_cal_next.get(Calendar.HOUR_OF_DAY) > 12
                                    && kq_end_time_cal_next.equals(calendar)) {
                                is_yexiu_xiu++;
                            }

                        }
                    }

                    //夜休因子大于白夜休休1因子判定为夜休
                    if(is_yexiu_xiu>=is_baiyexiuxiu_xiu1) {
                        return "32";
                    }else{
                        return "13";
                    }
                }


                //白夜休休第二休或者正常休息或者串休
            }else{
                //当前时间
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(new Date());

                //白夜休休2休因子
                int baiyexiuxiu2 = 0;
                if(index!=0) {
                    Map<String, Object> stringObjectMapBefore= kq_date_list.get(index-1);

                    if("休".equals(stringObjectMapBefore.get("type"))){
                        baiyexiuxiu2++;
                    }else{
                        baiyexiuxiu2--;
                    }
                }
                //取前两天
                if(index>1) {
                    Map<String, Object> stringObjectMapBefore2= kq_date_list.get(index-2);

                    if("休".equals(stringObjectMapBefore2.get("type"))){
                        baiyexiuxiu2--;
                    }else{

                        Date kq_time_before2 = timeFt.parse(stringObjectMapBefore2.get("kq_time").toString());
                        Calendar kq_time_cal_before2=Calendar.getInstance();
                        kq_time_cal_before2.setTime(kq_time_before2);

                        Date kq_start_time_before2 = timeFt2.parse(stringObjectMapBefore2.get("kq_start_time").toString());
                        Date kq_end_time_before2 = null;
                        if(stringObjectMapBefore2.get("kq_end_time")!=null) {
                            kq_end_time_before2 = timeFt2.parse(stringObjectMapBefore2.get("kq_end_time").toString());
                        }

                        Calendar kq_start_time_cal_before2=Calendar.getInstance();
                        kq_start_time_cal_before2.setTime(kq_start_time_before2);
                        Calendar kq_end_time_cal_before2=(Calendar)calendar.clone();;
                        if(kq_end_time_before2!=null) {
                            kq_end_time_cal_before2.setTime(kq_end_time_before2);
                            //判断最早和最晚打卡时间大约4小时 否则为无效打卡
                            if (kq_end_time_cal_before2.getTimeInMillis()-kq_start_time_cal_before2.getTimeInMillis()<4*60*60*1000) {
                                kq_end_time_cal_before2 = (Calendar)calendar.clone();
                            }
                        }
                        if(kq_start_time_cal_before2.get(Calendar.HOUR_OF_DAY) < 18
                                && kq_start_time_cal_before2.get(Calendar.HOUR_OF_DAY) > 12
                                && kq_end_time_cal_before2.equals(calendar)) {
                            baiyexiuxiu2++;
                        }

                    }
                }
                //取前三天
                if(index>2) {
                    Map<String, Object> stringObjectMapBefore3= kq_date_list.get(index-3);

                    if("休".equals(stringObjectMapBefore3.get("type"))){
                        baiyexiuxiu2--;
                    }else{

                        Date kq_time_before3 = timeFt.parse(stringObjectMapBefore3.get("kq_time").toString());
                        Calendar kq_time_cal_before3=Calendar.getInstance();
                        kq_time_cal_before3.setTime(kq_time_before3);

                        Date kq_start_time_before3 = timeFt2.parse(stringObjectMapBefore3.get("kq_start_time").toString());
                        Date kq_end_time_before3 = null;
                        if(stringObjectMapBefore3.get("kq_end_time")!=null) {
                            kq_end_time_before3 = timeFt2.parse(stringObjectMapBefore3.get("kq_end_time").toString());
                        }

                        Calendar kq_start_time_cal_before3=Calendar.getInstance();
                        kq_start_time_cal_before3.setTime(kq_start_time_before3);
                        Calendar kq_end_time_cal_before3=(Calendar)calendar.clone();;
                        if(kq_end_time_before3!=null) {
                            kq_end_time_cal_before3.setTime(kq_end_time_before3);
                            //判断最早和最晚打卡时间大约4小时 否则为无效打卡
                            if (kq_end_time_cal_before3.getTimeInMillis()-kq_start_time_cal_before3.getTimeInMillis()<4*60*60*1000) {
                                kq_end_time_cal_before3 = (Calendar)calendar.clone();
                            }
                        }
                        if(kq_start_time_cal_before3.get(Calendar.HOUR_OF_DAY) < 8) {
                            if (!kq_end_time_cal_before3.equals(calendar)) {
                                //8:00-1800
                                if (kq_end_time_cal_before3.get(Calendar.HOUR_OF_DAY) >= 18) {
                                    baiyexiuxiu2++;
                                }
                            }
                        }

                    }
                }

                if(baiyexiuxiu2>0) {
                    return "14";
                }else{

                    Date kq_time = timeFt.parse(stringObjectMap.get("kq_time").toString());

                    Calendar kq_time_cal=Calendar.getInstance();
                    kq_time_cal.setTime(kq_time);


                    int _week = kq_time_cal.get(Calendar.DAY_OF_WEEK) ;
                    if (_week == Calendar.SUNDAY || _week == Calendar.SATURDAY) {
                        return "5";
                    }else{
                        return "0";
                    }
                }

            }

            return "0";
        }

        Date kq_start_time = timeFt2.parse(stringObjectMap.get("kq_start_time").toString());

        Date kq_end_time = null;
        if(stringObjectMap.get("kq_end_time")!=null) {
            kq_end_time = timeFt2.parse(stringObjectMap.get("kq_end_time").toString());
        }

        //当前时间
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());

        Calendar kq_start_time_cal=Calendar.getInstance();
        kq_start_time_cal.setTime(kq_start_time);
        Calendar kq_end_time_cal=(Calendar)calendar.clone();
        if(kq_end_time!=null) {
            kq_end_time_cal.setTime(kq_end_time);
            //判断最早和最晚打卡时间大约4小时 否则为无效打卡
            if (kq_end_time_cal.getTimeInMillis()-kq_start_time_cal.getTimeInMillis()<4*60*60*1000) {
                kq_end_time_cal=(Calendar)calendar.clone();
            }
        }

        int changbai=0;
        //判断常白
        if(kq_start_time_cal.get(Calendar.HOUR_OF_DAY) < 8
                || (kq_start_time_cal.get(Calendar.HOUR_OF_DAY) == 8 && kq_start_time_cal.get(Calendar.MINUTE)<30)) {
            if(!kq_end_time_cal.equals(calendar)) {
                if(kq_end_time_cal.get(Calendar.HOUR_OF_DAY)>=17 ) {

                    //判断前一天是否是白
                    if(index!=0) {
                        Map<String, Object> stringObjectMapBefore= kq_date_list.get(index-1);

                        Date kq_time_before = timeFt.parse(stringObjectMapBefore.get("kq_time").toString());
                        Calendar kq_time_cal_before=Calendar.getInstance();
                        kq_time_cal_before.setTime(kq_time_before);

                        if("休".equals(stringObjectMapBefore.get("type"))){
                            int _week = kq_time_cal_before.get(Calendar.DAY_OF_WEEK) ;
                            if (_week == Calendar.SUNDAY || _week == Calendar.SATURDAY) {
                            }else{
                                changbai--;
                            }
                        }else{

                            Date kq_start_time_before = timeFt2.parse(stringObjectMapBefore.get("kq_start_time").toString());
                            Date kq_end_time_before = null;
                            if(stringObjectMapBefore.get("kq_end_time")!=null) {
                                kq_end_time_before = timeFt2.parse(stringObjectMapBefore.get("kq_end_time").toString());
                            }

                            Calendar kq_start_time_cal_before=Calendar.getInstance();
                            kq_start_time_cal_before.setTime(kq_start_time_before);
                            Calendar kq_end_time_cal_before=(Calendar)calendar.clone();;
                            if(kq_end_time_before!=null) {
                                kq_end_time_cal_before.setTime(kq_end_time_before);
                                //判断最早和最晚打卡时间大约4小时 否则为无效打卡
                                if (kq_end_time_cal_before.getTimeInMillis()-kq_start_time_cal_before.getTimeInMillis()<4*60*60*1000) {
                                    kq_end_time_cal_before = (Calendar)calendar.clone();
                                }
                            }

                            if(kq_start_time_cal_before.get(Calendar.HOUR_OF_DAY) < 8
                                    || (kq_start_time_cal_before.get(Calendar.HOUR_OF_DAY) == 8 && kq_start_time_cal_before.get(Calendar.MINUTE)<30)) {
                                if(!kq_start_time_cal_before.equals(calendar)) {
                                    if (kq_end_time_cal_before.get(Calendar.HOUR_OF_DAY) >= 17) {
                                        changbai++;
                                    }
                                }
                            }

                        }
                    }

                    //判断如果第二天还是常白 怎当天为常白
                    if(index!=kq_date_list.size()-1) {
                        Map<String, Object> stringObjectMapNext= kq_date_list.get(index+1);

                        Date kq_time_next = timeFt.parse(stringObjectMapNext.get("kq_time").toString());
                        Calendar kq_time_cal_next=Calendar.getInstance();
                        kq_time_cal_next.setTime(kq_time_next);

                        if("休".equals(stringObjectMapNext.get("type"))){
                            int _week = kq_time_cal_next.get(Calendar.DAY_OF_WEEK) ;
                            if (_week == Calendar.SUNDAY || _week == Calendar.SATURDAY) {
                                changbai++;
                            }else{
                             //   changbai--;
                            }
                            if(index<kq_date_list.size()-2) {
                                Map<String, Object> stringObjectMapNext2 = kq_date_list.get(index + 2);
                                Date kq_time_next2 = timeFt.parse(stringObjectMapNext2.get("kq_time").toString());
                                Calendar kq_time_cal_next2 = Calendar.getInstance();
                                kq_time_cal_next2.setTime(kq_time_next2);
                                if ("休".equals(stringObjectMapNext2.get("type"))) {
                                    int _week2 = kq_time_cal_next2.get(Calendar.DAY_OF_WEEK);
                                    if (_week2 == Calendar.SUNDAY || _week2 == Calendar.SATURDAY) {
                                        changbai++;
                                    } else {
                                        changbai--;
                                    }
                                }else{

                                    Date kq_start_time_next2 = timeFt2.parse(stringObjectMapNext2.get("kq_start_time").toString());
                                    Date kq_end_time_next2 = null;
                                    if(stringObjectMapNext2.get("kq_end_time")!=null) {
                                        kq_end_time_next2 = timeFt2.parse(stringObjectMapNext2.get("kq_end_time").toString());
                                    }

                                    Calendar kq_start_time_cal_next2=Calendar.getInstance();
                                    kq_start_time_cal_next2.setTime(kq_start_time_next2);
                                    Calendar kq_end_time_cal_next2=(Calendar)calendar.clone();;
                                    if(kq_end_time_next2!=null) {
                                        kq_end_time_cal_next2.setTime(kq_end_time_next2);
                                        //判断最早和最晚打卡时间大约4小时 否则为无效打卡
                                        if (kq_end_time_cal_next2.getTimeInMillis() - kq_start_time_cal_next2.getTimeInMillis() < 4 * 60 * 60 * 1000) {
                                            kq_end_time_cal_next2 = (Calendar) calendar.clone();
                                        }

                                        if (kq_start_time_cal_next2.get(Calendar.HOUR_OF_DAY) < 8
                                                || (kq_start_time_cal_next2.get(Calendar.HOUR_OF_DAY) == 8 && kq_start_time_cal_next2.get(Calendar.MINUTE) < 30)) {
                                            if (!kq_end_time_cal_next2.equals(calendar)) {
                                                if (kq_end_time_cal_next2.get(Calendar.HOUR_OF_DAY) >= 17) {
                                                    changbai++;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }else{

                            Date kq_start_time_next = timeFt2.parse(stringObjectMapNext.get("kq_start_time").toString());
                            Date kq_end_time_next = null;
                            if(stringObjectMapNext.get("kq_end_time")!=null) {
                                kq_end_time_next = timeFt2.parse(stringObjectMapNext.get("kq_end_time").toString());
                            }

                            Calendar kq_start_time_cal_next=Calendar.getInstance();
                            kq_start_time_cal_next.setTime(kq_start_time_next);
                            Calendar kq_end_time_cal_next=(Calendar)calendar.clone();;
                            if(kq_end_time_next!=null) {
                                kq_end_time_cal_next.setTime(kq_end_time_next);
                                //判断最早和最晚打卡时间大约4小时 否则为无效打卡
                                if (kq_end_time_cal_next.getTimeInMillis()-kq_start_time_cal_next.getTimeInMillis()<4*60*60*1000) {
                                    kq_end_time_cal_next = (Calendar)calendar.clone();
                                }
                            }
                            if(kq_start_time_cal_next.get(Calendar.HOUR_OF_DAY) < 8
                                    || (kq_start_time_cal_next.get(Calendar.HOUR_OF_DAY) == 8 && kq_start_time_cal_next.get(Calendar.MINUTE)<30)) {
                                if(!kq_end_time_cal_next.equals(calendar)) {
                                    if (kq_end_time_cal_next.get(Calendar.HOUR_OF_DAY) >= 17) {
                                        //说明前一天也是白班 后一天还是白班 怎当天判断为白班
                                        if(changbai>0) {
                                            changbai=100;
                                        }else{
                                            changbai++;
                                        }
                                        //有可能第二天是白夜休休的白
                                        if(kq_start_time_cal_next.get(Calendar.HOUR_OF_DAY) < 8) {
                                            if (!kq_end_time_cal_next.equals(calendar)) {
                                                if (kq_end_time_cal_next.get(Calendar.HOUR_OF_DAY) > 17) {
                                                    changbai--;

                                                    //判断第三天是否是夜班 也是就是白夜休休的夜 白夜休白白夜休白
                                                    if(index<kq_date_list.size()-2) {
                                                        Map<String, Object> stringObjectMapNext2= kq_date_list.get(index+2);
                                                        Date kq_time_next2 = timeFt.parse(stringObjectMapNext2.get("kq_time").toString());
                                                        Calendar kq_time_cal_next2=Calendar.getInstance();
                                                        kq_time_cal_next2.setTime(kq_time_next2);
                                                        if("休".equals(stringObjectMapNext2.get("type"))) {
                                                            int _week = kq_time_cal_next2.get(Calendar.DAY_OF_WEEK) ;
                                                            if (_week == Calendar.SUNDAY || _week == Calendar.SATURDAY) {
                                                                changbai++;
                                                            }else{
                                                                changbai--;
                                                            }
                                                        }else{
                                                            //判断夜休第三天夜班
                                                            Date kq_start_time_next2 = timeFt2.parse(stringObjectMapNext2.get("kq_start_time").toString());
                                                            Date kq_end_time_next2 = null;
                                                            if(stringObjectMapNext2.get("kq_end_time")!=null) {
                                                                kq_end_time_next2 = timeFt2.parse(stringObjectMapNext2.get("kq_end_time").toString());
                                                            }

                                                            Calendar kq_start_time_cal_next2=Calendar.getInstance();
                                                            kq_start_time_cal_next2.setTime(kq_start_time_next2);
                                                            Calendar kq_end_time_cal_next2=(Calendar)calendar.clone();;
                                                            if(kq_end_time_next2!=null) {
                                                                kq_end_time_cal_next2.setTime(kq_end_time_next2);
                                                                //判断最早和最晚打卡时间大约4小时 否则为无效打卡
                                                                if (kq_end_time_cal_next2.getTimeInMillis()-kq_start_time_cal_next2.getTimeInMillis()<4*60*60*1000) {
                                                                    kq_end_time_cal_next2 = (Calendar)calendar.clone();
                                                                }
                                                                if(kq_start_time_cal_next2.get(Calendar.HOUR_OF_DAY)<18 && kq_start_time_cal_next2.get(Calendar.HOUR_OF_DAY) >12 && kq_end_time_cal_next2.equals(calendar)) {
                                                                    changbai--;

                                                                    //继续判断是否是白夜休白白夜休休 第三天是否是休
                                                                    if(index<kq_date_list.size()-3) {
                                                                        Map<String, Object> stringObjectMapNext3= kq_date_list.get(index+3);
                                                                        if ("休".equals(stringObjectMapNext3.get("type"))) {
                                                                            changbai--;
                                                                        }

                                                                    }
                                                                }

                                                            }


                                                        }
                                                    }
                                                }else{
                                                    changbai++;
                                                }
                                            }
                                        }else{
                                            changbai++;
                                        }

                                    }
                                }
                            }else{
                                //如果当天白班 第二天夜班则判定为白夜休休的白
                                if(kq_start_time_cal_next.get(Calendar.HOUR_OF_DAY)<18 && kq_start_time_cal_next.get(Calendar.HOUR_OF_DAY) >12 && kq_end_time_cal_next.equals(calendar)) {
                                    changbai=-100;
                                }
                            }

                        }

                        //如果是月末最后一天则判断如果前一天还是常白 怎当天为常白 不确定
                    }else{
                        Map<String, Object> stringObjectMapBefore= kq_date_list.get(index-1);

                        Date kq_time_before = timeFt.parse(stringObjectMapBefore.get("kq_time").toString());
                        Calendar kq_time_cal_before=Calendar.getInstance();
                        kq_time_cal_before.setTime(kq_time_before);

                        if("休".equals(stringObjectMapBefore.get("type"))){
                            int _week = kq_time_cal_before.get(Calendar.DAY_OF_WEEK) ;
                            if (_week == Calendar.SUNDAY || _week == Calendar.SATURDAY) {
                                if(index>1) {
                                    Map<String, Object> stringObjectMapBefore2= kq_date_list.get(index-2);

                                    if("休".equals(stringObjectMapBefore2.get("type"))){
                                        changbai--;
                                    }else{

                                        Date kq_time_before2 = timeFt.parse(stringObjectMapBefore2.get("kq_time").toString());
                                        Calendar kq_time_cal_before2=Calendar.getInstance();
                                        kq_time_cal_before2.setTime(kq_time_before2);

                                        Date kq_start_time_before2 = timeFt2.parse(stringObjectMapBefore2.get("kq_start_time").toString());
                                        Date kq_end_time_before2 = null;
                                        if(stringObjectMapBefore2.get("kq_end_time")!=null) {
                                            kq_end_time_before2 = timeFt2.parse(stringObjectMapBefore2.get("kq_end_time").toString());
                                        }

                                        Calendar kq_start_time_cal_before2=Calendar.getInstance();
                                        kq_start_time_cal_before2.setTime(kq_start_time_before2);
                                        Calendar kq_end_time_cal_before2=(Calendar)calendar.clone();;
                                        if(kq_end_time_before2!=null) {
                                            kq_end_time_cal_before2.setTime(kq_end_time_before2);
                                            //判断最早和最晚打卡时间大约4小时 否则为无效打卡
                                            if (kq_end_time_cal_before2.getTimeInMillis()-kq_start_time_cal_before2.getTimeInMillis()<4*60*60*1000) {
                                                kq_end_time_cal_before2 = (Calendar)calendar.clone();
                                            }
                                        }
                                        if(kq_start_time_cal_before2.get(Calendar.HOUR_OF_DAY) < 8
                                                || (kq_start_time_cal_before2.get(Calendar.HOUR_OF_DAY) == 8 && kq_start_time_cal_before2.get(Calendar.MINUTE)<30)) {
                                            if(!kq_end_time_cal_before2.equals(calendar)) {
                                                if (kq_end_time_cal_before2.get(Calendar.HOUR_OF_DAY) >= 17) {
                                                    changbai++;
                                                }
                                            }
                                        }

                                    }
                                }
                            }else{
                                changbai--;
                            }
                        }else{

                            Date kq_start_time_before = timeFt2.parse(stringObjectMapBefore.get("kq_start_time").toString());
                            Date kq_end_time_before = null;
                            if(stringObjectMapBefore.get("kq_end_time")!=null) {
                                kq_end_time_before = timeFt2.parse(stringObjectMapBefore.get("kq_end_time").toString());
                            }

                            Calendar kq_start_time_cal_before=Calendar.getInstance();
                            kq_start_time_cal_before.setTime(kq_start_time_before);
                            Calendar kq_end_time_cal_before=(Calendar)calendar.clone();;
                            if(kq_end_time_before!=null) {
                                kq_end_time_cal_before.setTime(kq_end_time_before);
                                //判断最早和最晚打卡时间大约4小时 否则为无效打卡
                                if (kq_end_time_cal_before.getTimeInMillis()-kq_start_time_cal_before.getTimeInMillis()<4*60*60*1000) {
                                    kq_end_time_cal_before = (Calendar)calendar.clone();
                                }
                            }
                            if(kq_start_time_cal_before.get(Calendar.HOUR_OF_DAY) < 8
                                    || (kq_start_time_cal_before.get(Calendar.HOUR_OF_DAY) == 8 && kq_start_time_cal_before.get(Calendar.MINUTE)<30)) {
                                if(!kq_end_time_cal_before.equals(calendar)) {
                                    if (kq_end_time_cal_before.get(Calendar.HOUR_OF_DAY) >= 17) {
                                        changbai++;
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }

        if(changbai>0) {
            return "2";
        }


        int baiyexiuxiu_bai = 0;
        //判断白夜休休-白
        if(kq_start_time_cal.get(Calendar.HOUR_OF_DAY) < 8) {
            if(!kq_end_time_cal.equals(calendar)) {
                //8:00-1800
                if(kq_end_time_cal.get(Calendar.HOUR_OF_DAY)>=18 ) {
                    if(index<kq_date_list.size()-1) {

                        Map<String, Object> stringObjectMapNext= kq_date_list.get(index+1);
                        //判断常白方法中已经判断了第一天白班第二天休得情况 所以现在这个休应该是串休
                        if("休".equals(stringObjectMapNext.get("type"))){
                            baiyexiuxiu_bai--;
                        }else {
                            //下一天记录
                            Date kq_start_time_next = timeFt2.parse(stringObjectMapNext.get("kq_start_time").toString());
                            Date kq_end_time_next = null;
                            if (stringObjectMapNext.get("kq_end_time") != null) {
                                kq_end_time_next = timeFt2.parse(stringObjectMapNext.get("kq_end_time").toString());
                            }

                            Calendar kq_start_time_cal_next = Calendar.getInstance();
                            kq_start_time_cal_next.setTime(kq_start_time_next);
                            Calendar kq_end_time_cal_next = (Calendar) calendar.clone();
                            ;
                            if (kq_end_time_next != null) {
                                kq_end_time_cal_next.setTime(kq_end_time_next);
                                //判断最早和最晚打卡时间大约4小时 否则为无效打卡
                                if (kq_end_time_cal_next.getTimeInMillis() - kq_start_time_cal_next.getTimeInMillis() < 4 * 60 * 60 * 1000) {
                                    kq_end_time_cal_next = (Calendar) calendar.clone();
                                }
                            }
                            //第二天12-18点之间来打卡 视为白夜休休的夜
                            if (kq_start_time_cal_next.get(Calendar.HOUR_OF_DAY) < 18
                                    && kq_start_time_cal_next.get(Calendar.HOUR_OF_DAY) > 12
                                    && kq_end_time_cal_next.equals(calendar)) {
                                baiyexiuxiu_bai++;
                                if (index < kq_date_list.size() - 2) {
                                    Map<String, Object> stringObjectMapNext2 = kq_date_list.get(index + 2);
                                    if ("休".equals(stringObjectMapNext2.get("type"))) {
                                        baiyexiuxiu_bai++;
                                        if (index < kq_date_list.size() - 3) {
                                            Map<String, Object> stringObjectMapNext3 = kq_date_list.get(index + 2);
                                            if ("休".equals(stringObjectMapNext3.get("type"))) {
                                                baiyexiuxiu_bai++;
                                            } else {
                                                baiyexiuxiu_bai--;
                                            }
                                        }
                                    } else {
                                        baiyexiuxiu_bai--;
                                    }
                                }
                            } else {
                                baiyexiuxiu_bai--;
                            }
                        }
                    }
                }
            }
        }

        if(baiyexiuxiu_bai>0)  {
            return "11";
        }


        //判断白夜休休-夜或者夜休
        if(kq_start_time_cal.get(Calendar.HOUR_OF_DAY) < 18
                && kq_start_time_cal.get(Calendar.HOUR_OF_DAY) > 12
                && kq_end_time_cal.equals(calendar)) {

            int baiyexiuxiu_ye = 0;
            int yexiu_ye=0;

            //判断前一天
            if(index!=0) {
                Map<String, Object> stringObjectMapBefore= kq_date_list.get(index-1);

                Date kq_time_next = timeFt.parse(stringObjectMapBefore.get("kq_time").toString());
                Calendar kq_time_cal_before=Calendar.getInstance();
                kq_time_cal_before.setTime(kq_time_next);

                //如果前一天休息判断为夜休的夜
                if("休".equals(stringObjectMapBefore.get("type"))){
                    //夜休
                    if(kq_start_time_cal.get(Calendar.HOUR_OF_DAY) < 17
                            && kq_start_time_cal.get(Calendar.HOUR_OF_DAY) > 12
                            && kq_end_time_cal.equals(calendar)) {
                        yexiu_ye++;
                    }
                }else {

                    Date kq_start_time_before = timeFt2.parse(stringObjectMapBefore.get("kq_start_time").toString());
                    Date kq_end_time_before = null;
                    if (stringObjectMapBefore.get("kq_end_time") != null) {
                        kq_end_time_before = timeFt2.parse(stringObjectMapBefore.get("kq_end_time").toString());
                    }


                    Calendar kq_start_time_cal_before = Calendar.getInstance();
                    kq_start_time_cal_before.setTime(kq_start_time_before);
                    Calendar kq_end_time_cal_before = (Calendar) calendar.clone();

                    if (kq_end_time_before != null) {
                        kq_end_time_cal_before.setTime(kq_end_time_before);
                        //判断最早和最晚打卡时间大约4小时 否则为无效打卡
                        if (kq_end_time_cal_before.getTimeInMillis() - kq_start_time_cal_before.getTimeInMillis() < 4 * 60 * 60 * 1000) {
                            kq_end_time_cal_before = (Calendar) calendar.clone();
                        }
                    }
                    //如果前一天8-18白班怎判断为白夜休休的夜
                    if (kq_start_time_cal_before.get(Calendar.HOUR_OF_DAY) < 8) {
                        if (!kq_end_time_cal_before.equals(calendar)) {
                            //8:00-1800
                            if (kq_end_time_cal_before.get(Calendar.HOUR_OF_DAY) >= 18) {
                                if(index<kq_date_list.size()-1) {
                                    Map<String, Object> stringObjectMapNext2= kq_date_list.get(index+1);
                                    if("休".equals(stringObjectMapNext2.get("type"))) {
                                        baiyexiuxiu_ye++;
                                        yexiu_ye++;
                                        if(index<kq_date_list.size()-2) {
                                            Map<String, Object> stringObjectMapNext3= kq_date_list.get(index+2);

                                            //如果连两天休说明是白夜休休
                                            if("休".equals(stringObjectMapNext3.get("type"))) {
                                                baiyexiuxiu_ye++;
                                                //如果只休一天说明是夜休
                                            }else{
                                                yexiu_ye--;
                                                //判断夜休第三天夜班
                                                Date kq_start_time_next = timeFt2.parse(stringObjectMapNext3.get("kq_start_time").toString());
                                                Date kq_end_time_next = null;
                                                if(stringObjectMapNext3.get("kq_end_time")!=null) {
                                                    kq_end_time_next = timeFt2.parse(stringObjectMapNext3.get("kq_end_time").toString());
                                                }

                                                Calendar kq_start_time_cal_next=Calendar.getInstance();
                                                kq_start_time_cal_next.setTime(kq_start_time_next);
                                                Calendar kq_end_time_cal_next=(Calendar)calendar.clone();;
                                                if(kq_end_time_next!=null) {
                                                    kq_end_time_cal_next.setTime(kq_end_time_next);
                                                    //判断最早和最晚打卡时间大约4小时 否则为无效打卡
                                                    if (kq_end_time_cal_next.getTimeInMillis()-kq_start_time_cal_next.getTimeInMillis()<4*60*60*1000) {
                                                        kq_end_time_cal_next = (Calendar)calendar.clone();
                                                    }
                                                    //夜休
                                                    if(kq_start_time_cal_next.get(Calendar.HOUR_OF_DAY) < 17
                                                            && kq_start_time_cal_next.get(Calendar.HOUR_OF_DAY) > 12
                                                            && kq_end_time_cal_next.equals(calendar)) {
                                                        yexiu_ye++;
                                                    }
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }else {
                        baiyexiuxiu_ye--;
                    }
                }
            }
            if(yexiu_ye>=baiyexiuxiu_ye) {
                if(kq_start_time_cal.get(Calendar.HOUR_OF_DAY) < 17
                        && kq_start_time_cal.get(Calendar.HOUR_OF_DAY) > 12
                        && kq_end_time_cal.equals(calendar)) {
                    return "31";
                }else{
                    return "0";
                }
            }else{
                return "12";
            }
        }

        return "0";
    }

}