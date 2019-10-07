/*
考勤bean
 */

public class Jia_Ban_Bean {

    String posm_name = "";

    String jia_ban_ren_shu = "";

    String jia_ban_type = "";

    String id = "";

    String name ="";

    String jia_ban_time="";

    String jia_ban_start_time="";

    String jia_ban_end_time="";

    double jia_ban_days =0;

    String jia_ban_reason = "";

    String buchangfangshi="支付加班费";

    double salay_days = 91.95;

    double salay = 0;

    public String getJia_ban_kind() {
        return jia_ban_kind;
    }

    public void setJia_ban_kind(String jia_ban_kind) {
        this.jia_ban_kind = jia_ban_kind;
    }

    /***
     * 1：白夜休休
     *     11：白，12：夜，13：休，14：休
     * 2：常白
     *
     * 3：夜休
     *      31：夜，32：休
     * @return
     */
    String jia_ban_kind="";

    public String getPosm_name() {
        return posm_name;
    }

    public void setPosm_name(String posm_name) {
        this.posm_name = posm_name;
    }

    public String getJia_ban_ren_shu() {
        return jia_ban_ren_shu;
    }

    public void setJia_ban_ren_shu(String jia_ban_ren_shu) {
        this.jia_ban_ren_shu = jia_ban_ren_shu;
    }

    public String getJia_ban_type() {
        return jia_ban_type;
    }

    public void setJia_ban_type(String jia_ban_type) {
        this.jia_ban_type = jia_ban_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJia_ban_time() {
        return jia_ban_time;
    }

    public void setJia_ban_time(String jia_ban_time) {
        this.jia_ban_time = jia_ban_time;
    }

    public String getJia_ban_start_time() {
        return jia_ban_start_time;
    }

    public void setJia_ban_start_time(String jia_ban_start_time) {
        this.jia_ban_start_time = jia_ban_start_time;
    }

    public String getJia_ban_end_time() {
        return jia_ban_end_time;
    }

    public void setJia_ban_end_time(String jia_ban_end_time) {
        this.jia_ban_end_time = jia_ban_end_time;
    }

    public double getJia_ban_days() {
        return jia_ban_days;
    }

    public void setJia_ban_days(double jia_ban_days) {
        this.jia_ban_days = jia_ban_days;
    }

    public String getJia_ban_reason() {
        return jia_ban_reason;
    }

    public void setJia_ban_reason(String jia_ban_reason) {
        this.jia_ban_reason = jia_ban_reason;
    }

    public String getBuchangfangshi() {
        return buchangfangshi;
    }

    public void setBuchangfangshi(String buchangfangshi) {
        this.buchangfangshi = buchangfangshi;
    }

    public double getSalay_days() {
        return salay_days;
    }

    public void setSalay_days(double salay_days) {
        this.salay_days = salay_days;
    }

    public double getSalay() {
        return salay;
    }

    public void setSalay(double salay) {
        this.salay = salay;
    }
}
