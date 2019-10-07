/*
考勤按人分每月bean
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Kq_Record_Person {

    String id = "";

    String name ="";

    List<Map<String,Object>> kq_date_list = new ArrayList<Map<String,Object>>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKq_date_list(List<Map<String, Object>> kq_date_list) {
        this.kq_date_list = kq_date_list;
    }

    public String getName() {
        return name;
    }

    public List<Map<String, Object>> getKq_date_list() {
        return kq_date_list;
    }

}
