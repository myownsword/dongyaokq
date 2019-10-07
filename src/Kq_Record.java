/*
考勤bean
 */

public class Kq_Record {

    String id = "";

    String name ="";

    String kq_time="";

    String kq_start_time="";

    String kq_end_time="";

    public void setKq_start_time(String kq_start_time) {
        this.kq_start_time = kq_start_time;
    }

    public void setKq_end_time(String kq_end_time) {
        this.kq_end_time = kq_end_time;
    }

    public String getKq_start_time() {
        return kq_start_time;
    }

    public String getKq_end_time() {
        return kq_end_time;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKq_time() {
        return kq_time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKq_time(String kq_time) {
        this.kq_time = kq_time;
    }
}
