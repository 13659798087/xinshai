/*
package com.xinshai.xinshai.entiry;

import java.sql.Timestamp;

public class Applyinfo {

    private Integer a_id;
    private String  a_name;// 姓名
    private Timestamp a_bithday;//出生日期
    private String  a_sex;//性别
    private String  a_hospital;//送检单位
    private String  a_dept;//送检科室
    private String  a_doctor;//送检医生
    private String  a_in_no;//病历号
    private String  a_bed_no;//床号
    private String  a_tel1;//联系电话1 ,//电话加密
    private String  a_tel2;//联系电话2
    private String  a_place;//户籍地点
    private Integer a_hk_city;// 户口类型城市为1、农村为2,
    private Integer a_hk_bd;//户口类型本地为1，本省为2，省外为3 ,
    private String  a_address;//住址

    private Timestamp a_lr_date;//录入日期
    private String  a_lr_name;//录入人
    private Integer a_flag;//1为录入，2为送检，4为签收，5为报告， ,
    private String  a_setmeal_name;//所选套餐
    private String  a_id_number;//身份证号
    private String  a_system_bbh;//保存录入时软件版本号
    private String  a_check_yq;//孕期，孕期、孕前、孕中
    private Integer a_free_flag;// 收  费,1收费、2免费、9其它
    private String  a_age;//年龄
    private String  a_age_unit;//年龄单位，年、月、日
    private String  a_lczd;//临床诊断
    private String  a_stature;//身高
    private String  a_weigh;//体重
    private String  a_blycs;//不良孕产史
    private Timestamp a_lmp_date;//末次月经
    private Timestamp a_sample_date;//采样日期
    private String a_sample_name;//采样人


    public Integer getA_id() {
        return a_id;
    }

    public void setA_id(Integer a_id) {
        this.a_id = a_id;
    }

    public String getA_name() {
        return a_name;
    }

    public void setA_name(String a_name) {
        this.a_name = a_name;
    }

    public Timestamp getA_bithday() {
        return a_bithday;
    }

    public void setA_bithday(Timestamp a_bithday) {
        this.a_bithday = a_bithday;
    }

    public String getA_sex() {
        return a_sex;
    }

    public void setA_sex(String a_sex) {
        this.a_sex = a_sex;
    }

    public String getA_hospital() {
        return a_hospital;
    }

    public void setA_hospital(String a_hospital) {
        this.a_hospital = a_hospital;
    }

    public String getA_dept() {
        return a_dept;
    }

    public void setA_dept(String a_dept) {
        this.a_dept = a_dept;
    }

    public String getA_doctor() {
        return a_doctor;
    }

    public void setA_doctor(String a_doctor) {
        this.a_doctor = a_doctor;
    }

    public String getA_in_no() {
        return a_in_no;
    }

    public void setA_in_no(String a_in_no) {
        this.a_in_no = a_in_no;
    }

    public String getA_bed_no() {
        return a_bed_no;
    }

    public void setA_bed_no(String a_bed_no) {
        this.a_bed_no = a_bed_no;
    }

    public String getA_tel1() {
        return a_tel1;
    }

    public void setA_tel1(String a_tel1) {
        this.a_tel1 = a_tel1;
    }

    public String getA_tel2() {
        return a_tel2;
    }

    public void setA_tel2(String a_tel2) {
        this.a_tel2 = a_tel2;
    }

    public String getA_place() {
        return a_place;
    }

    public void setA_place(String a_place) {
        this.a_place = a_place;
    }

    public Integer getA_hk_city() {
        return a_hk_city;
    }

    public void setA_hk_city(Integer a_hk_city) {
        this.a_hk_city = a_hk_city;
    }

    public Integer getA_hk_bd() {
        return a_hk_bd;
    }

    public void setA_hk_bd(Integer a_hk_bd) {
        this.a_hk_bd = a_hk_bd;
    }

    public String getA_address() {
        return a_address;
    }

    public void setA_address(String a_address) {
        this.a_address = a_address;
    }

    public Timestamp getA_lr_date() {
        return a_lr_date;
    }

    public void setA_lr_date(Timestamp a_lr_date) {
        this.a_lr_date = a_lr_date;
    }

    public String getA_lr_name() {
        return a_lr_name;
    }

    public void setA_lr_name(String a_lr_name) {
        this.a_lr_name = a_lr_name;
    }

    public Integer getA_flag() {
        return a_flag;
    }

    public void setA_flag(Integer a_flag) {
        this.a_flag = a_flag;
    }

    public String getA_setmeal_name() {
        return a_setmeal_name;
    }

    public void setA_setmeal_name(String a_setmeal_name) {
        this.a_setmeal_name = a_setmeal_name;
    }

    public String getA_id_number() {
        return a_id_number;
    }

    public void setA_id_number(String a_id_number) {
        this.a_id_number = a_id_number;
    }

    public String getA_system_bbh() {
        return a_system_bbh;
    }

    public void setA_system_bbh(String a_system_bbh) {
        this.a_system_bbh = a_system_bbh;
    }

    public String getA_check_yq() {
        return a_check_yq;
    }

    public void setA_check_yq(String a_check_yq) {
        this.a_check_yq = a_check_yq;
    }

    public Integer getA_free_flag() {
        return a_free_flag;
    }

    public void setA_free_flag(Integer a_free_flag) {
        this.a_free_flag = a_free_flag;
    }

    public String getA_age() {
        return a_age;
    }

    public void setA_age(String a_age) {
        this.a_age = a_age;
    }

    public String getA_age_unit() {
        return a_age_unit;
    }

    public void setA_age_unit(String a_age_unit) {
        this.a_age_unit = a_age_unit;
    }

    public String getA_lczd() {
        return a_lczd;
    }

    public void setA_lczd(String a_lczd) {
        this.a_lczd = a_lczd;
    }

    public String getA_stature() {
        return a_stature;
    }

    public void setA_stature(String a_stature) {
        this.a_stature = a_stature;
    }

    public String getA_weigh() {
        return a_weigh;
    }

    public void setA_weigh(String a_weigh) {
        this.a_weigh = a_weigh;
    }

    public String getA_blycs() {
        return a_blycs;
    }

    public void setA_blycs(String a_blycs) {
        this.a_blycs = a_blycs;
    }

    public Timestamp getA_lmp_date() {
        return a_lmp_date;
    }

    public void setA_lmp_date(Timestamp a_lmp_date) {
        this.a_lmp_date = a_lmp_date;
    }

    public Timestamp getA_sample_date() {
        return a_sample_date;
    }

    public void setA_sample_date(Timestamp a_sample_date) {
        this.a_sample_date = a_sample_date;
    }

    public String getA_sample_name() {
        return a_sample_name;
    }

    public void setA_sample_name(String a_sample_name) {
        this.a_sample_name = a_sample_name;
    }
}
*/
