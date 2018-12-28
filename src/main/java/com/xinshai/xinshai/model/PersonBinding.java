package com.xinshai.xinshai.model;

public class PersonBinding {

    private String id;
    private String openId;//微信的openid，即在公众号中为疑似
    private String m_name;//母亲或受检人姓名
    private String bloodCard;//采血卡号
    private String bithday;//小孩出生日期
    private String tel;//采血卡上手机号
    private String name;//小孩姓名
    private Integer tes;//胎儿数
    private String hospitalNo;//住院号
    private String m_age;//母亲姓名
    private String f_name;//父亲姓名
    private String tel_2;//父亲电话
    private String address;//居住地址
    private String pregnanciesNum;//第几次怀孕
    private String deliverNum;//第几次分娩
    private String pregnancyWeek;//孕周
    private String pregnancyDay;//孕天
    private Integer sex;//婴儿性别
    private String weigh;//婴儿出生体重

    private String createTime;
    private Integer isPass;//逻辑标识，0-通过，1-待验证，2-验证不通过，即系统不存在对应的用户
    private String patientId; //病人id,关联结果表，推送消息
    private Integer resultType; //用户类型，初筛（1阴性、2阳性）、召回（1阴性、2阳性）、确诊（已确诊，待排）、随访（），归档（已归档，未归档，空）
    private Integer userType; //病人来源类型，1-筛查，2-门诊


    public String getM_age() {
        return m_age;
    }

    public void setM_age(String m_age) {
        this.m_age = m_age;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getTes() {
        return tes;
    }

    public void setTes(Integer tes) {
        this.tes = tes;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getTel_2() {
        return tel_2;
    }

    public void setTel_2(String tel_2) {
        this.tel_2 = tel_2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPregnanciesNum() {
        return pregnanciesNum;
    }

    public void setPregnanciesNum(String pregnanciesNum) {
        this.pregnanciesNum = pregnanciesNum;
    }

    public String getDeliverNum() {
        return deliverNum;
    }

    public void setDeliverNum(String deliverNum) {
        this.deliverNum = deliverNum;
    }

    public String getPregnancyWeek() {
        return pregnancyWeek;
    }

    public void setPregnancyWeek(String pregnancyWeek) {
        this.pregnancyWeek = pregnancyWeek;
    }

    public String getPregnancyDay() {
        return pregnancyDay;
    }

    public void setPregnancyDay(String pregnancyDay) {
        this.pregnancyDay = pregnancyDay;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getWeigh() {
        return weigh;
    }

    public void setWeigh(String weigh) {
        this.weigh = weigh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getBloodCard() {
        return bloodCard;
    }

    public void setBloodCard(String bloodCard) {
        this.bloodCard = bloodCard;
    }

    public String getBithday() {
        return bithday;
    }

    public void setBithday(String bithday) {
        this.bithday = bithday;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getIsPass() {
        return isPass;
    }

    public void setIsPass(Integer isPass) {
        this.isPass = isPass;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public Integer getResultType() {
        return resultType;
    }

    public void setResultType(Integer resultType) {
        this.resultType = resultType;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
