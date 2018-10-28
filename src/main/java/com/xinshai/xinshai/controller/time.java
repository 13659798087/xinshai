package com.xinshai.xinshai.controller;

import com.xinshai.xinshai.entiry.TemplateData;
import com.xinshai.xinshai.entiry.WechatTemplate;
import com.xinshai.xinshai.entiry.menu.Button;
import com.xinshai.xinshai.entiry.menu.ClickButton;
import com.xinshai.xinshai.entiry.menu.Menu;
import com.xinshai.xinshai.entiry.menu.ViewButton;
import com.xinshai.xinshai.model.CheckinfoTest;
import com.xinshai.xinshai.model.WeixinMenu;
import com.xinshai.xinshai.model.WeixinMsg;
import com.xinshai.xinshai.services.AccessTokenServices;
import com.xinshai.xinshai.services.TagServices;
import com.xinshai.xinshai.services.WeixinMenuServices;
import com.xinshai.xinshai.services.WeixinUserInfoServices;
import com.xinshai.xinshai.servlet.TokenThread;
import com.xinshai.xinshai.util.DomainUrl;
import com.xinshai.xinshai.util.WeixinUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/abc")
public class time {
    @Resource
    private AccessTokenServices accessTokenServices;
    @Resource
    private WeixinUserInfoServices weixinUserInfoServices;
    @Resource
    private TagServices tagServices;

    @Resource
    private WeixinMenuServices weixinMenuServices;


    /**
     * 发送模板消息
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    @ResponseBody
    @RequestMapping("/test1")
    public String test1(HttpServletRequest request, HttpServletResponse response) {

        //模拟查询出结果的报告
        List<CheckinfoTest> listCheckinfo = weixinUserInfoServices.getTest();

        //weixinMsg 查询已绑定的个人信息的表的信息，再和今天出报告的记录对应，将消息推送给绑定的微信用户
        List<WeixinMsg> listWeixinMsg = weixinUserInfoServices.getAllMsg();

        //WeixinMsg wmsg = new WeixinMsg();

        for(CheckinfoTest a : listCheckinfo){
            String m_name = null;
            String openid = null;
            String combine = null;
            String title = null;

            //此处避免多次查询数据库
            //wmsg = weixinUserInfoServices.getByApplyinfo(a.getM_name(),a.getTel());
            for(WeixinMsg l:listWeixinMsg){
                if(a.getM_name().equals(l.getP_name()) && a.getTel().equals(l.getP_tel()) ){
                    m_name = l.getP_name();
                    openid = l.getOpenId();
                    combine = a.getCombine();
                    title = a.getTitle();
                }
            }

            // 封装基础数据
            WechatTemplate wechatTemplate = new WechatTemplate();
            wechatTemplate.setTemplate_id(DomainUrl.getTemplate_id1());
            wechatTemplate.setTouser( openid );
            wechatTemplate.setUrl("https://www.baidu.com/");
            Map<String,TemplateData> mapdata = new HashMap<>();
            // 封装模板数据
            TemplateData first = new TemplateData();
            first.setValue("您好，您收到一份报告结果");
            first.setColor("#173177");
            mapdata.put("first", first);

            TemplateData keyword1 = new TemplateData();
            keyword1.setValue( m_name );
            first.setColor("#173177");
            mapdata.put("keyword1", keyword1);

            TemplateData keyword2 = new TemplateData();
            keyword2.setValue(combine);
            first.setColor("#173177");
            mapdata.put("keyword2", keyword2);

            TemplateData keyword3 = new TemplateData();
            keyword3.setValue(title);
            first.setColor("#173177");
            mapdata.put("keyword3", keyword3);

            TemplateData keyword4 = new TemplateData();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String format = formatter.format(new Date());
            keyword4.setValue(format);
            first.setColor("#173177");
            mapdata.put("keyword4", keyword4);

            wechatTemplate.setData(mapdata);
            String msg  = JSONObject.fromObject(wechatTemplate).toString();
            WeixinUtil.sendTemplatesMsg(TokenThread.accessToken.getToken(),msg);

        }

        return "ok！";

    }



    @RequestMapping("/test")
    public void test() {
        String nextOpenid="";
        String openidList = null;

        openidList = WeixinUtil.getUserList( TokenThread.accessToken.getToken() ,nextOpenid);

        String [] stringArr= openidList.replace("[","").replace("]","").split(",");

       /* for(int i=0;i<stringArr.length;i++){
            weixinUserInfoServices.insertUserOpenid(openid, nickname, sex, language, city, province, country, stringArr[i].replace("\"",""));
        }*/
    }


    //批量拉取用户信息，即更新数据，将关注的用户信息拉去到数据库，当取消关注时，也将此表对应的openid的用户删除掉
    @ResponseBody
    @RequestMapping("/batch")
    public String batch() {

        //先删掉weixinUserinfo表所有数据，在插入新数据
        weixinUserInfoServices.deletaAll();

        String nextOpenid="";
        String openidList = null;
        openidList = WeixinUtil.getUserList( TokenThread.accessToken.getToken() , nextOpenid );
        String groupMessage = JSONObject.fromObject(WeixinUtil.initUserMessage(openidList)).toString();
        String result = WeixinUtil.getBatchUser(TokenThread.accessToken.getToken(),groupMessage);
        JSONArray array=JSONArray.fromObject(result);

        String openid = null;//用户的标识，对当前公众号唯一
        String nickname = null;//用户的昵称
        String sex = null;//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
        String language = null;//用户的语言，简体中文为zh_CN
        String city = null;//用户所在城市
        String province = null;//用户所在省份
        String country = null;//用户所在国家
        String groupid = null;//用户所在的分组ID（暂时兼容用户分组旧接口）
        String tagid_list = null;//标签id列
        String remark = null;//备注

        for(int i=0;i<array.size();i++){
            JSONObject job = array.getJSONObject(i);
            switch (job.getInt("sex")){
                case 1:
                    sex = "男";
                    break;
                case 2:
                    sex = "女";
                case 0:
                    sex = "未知";
                    break;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long time = Long.parseLong(job.getString("subscribe_time"));
            long lt = new Long(time);
            Date date = new Date(lt * 1000L);
            Timestamp t = Timestamp.valueOf( sdf.format(date)  );//关注的时间
            openid = job.getString("openid");
            nickname = job.getString("nickname");
            //处理昵称中有空格的
            if(nickname.contains(" ")){
                nickname = nickname.replace(" ","&nbsp;");
            }
            language = job.getString("language");
            city = job.getString("city");
            province = job.getString("province");
            country = job.getString("country");
            groupid = job.getString("groupid");
            remark = job.getString("remark");

            tagid_list = job.getString("tagid_list");
            if("[]".equals(tagid_list)){
                tagid_list = null;
            }

            weixinUserInfoServices.insertUserOpenid(openid,nickname,sex,language,city,province,country,groupid,t,tagid_list,remark);

        }
        return "拉取用户列表成功，改接口最多拉去100条，可多次拉去，考录到前期关注的人少，先这样处理";
    }


    //批量标签
    @ResponseBody
    @RequestMapping("/tag")
    public String tag() {

        tagServices.deleteAllTag();

        JSONObject jsonObject = WeixinUtil.getTagList( TokenThread.accessToken.getToken() );

        String data= jsonObject.getString("tags");
        JSONArray array=JSONArray.fromObject(data);

        for (int i =0;i<array.size();i++){
            JSONObject job = array.getJSONObject(i);
            tagServices.insertTag(job.getString("id"),job.getString("name"),job.getInt("count") );
        }
        return "标签插入数据库成功";
    }
    /**
     * 组装菜单
     * @return
     */
    public static Menu initMenu(){
        Menu menu = new Menu();

        ViewButton button11 = new ViewButton();
        button11.setName("中心简介");
        button11.setType("view");
        button11.setUrl("http://yc.jxcdc.cn/show.aspx?id=25&cid=20");

        ViewButton button12 = new ViewButton();
        button12.setName("筛查告知");
        button12.setType("view");
        button12.setUrl("https://www.fsfy.com/Content-34013.html");

        ViewButton button13 = new ViewButton();
        button13.setName("筛查项目");
        button13.setType("view");
        button13.setUrl(DomainUrl.getUrl() + "/combine/listCombine");

        List<Button> sub_button1 = new ArrayList<Button>();
        sub_button1.add(button11);
        sub_button1.add(button12);
        sub_button1.add(button13);

        Button button1 = new Button();
        button1.setName("筛查中心");
        button1.setSub_button(sub_button1);


        ViewButton button21 = new ViewButton();
        button21.setName("最新资讯");
        button21.setType("view");
        button21.setUrl("http://www.lznsn.com/");

        ViewButton button22 = new ViewButton();
       /* button22.setName("地理位置");
        button22.setType("location_select");
        button22.setKey("32");*/
        button22.setName("位置导航");
        button22.setType("view");
        button22.setUrl("http://map.baidu.com/mobile");

        ViewButton button23 = new ViewButton();
        button23.setName("注意事项");
        button23.setType("view");
        button23.setUrl("http://www.mama.cn/z/art/8736/");

        List<Button> sub_button2 = new ArrayList<Button>();
        sub_button2.add(button21);
        sub_button2.add(button22);
        sub_button2.add(button23);

        Button button2 = new Button();
        button2.setName("服务咨询");
        button2.setSub_button(sub_button2);


        ViewButton button31 = new ViewButton();
        button31.setName("报告寄送");
        button31.setType("view");
        button31.setUrl(DomainUrl.getUrl() + "/combine/listCombine");

        ViewButton button32 = new ViewButton();
        button32.setName("我的信息");
        button32.setType("view");
        button32.setUrl(DomainUrl.getUrl() + "/personalData/personal");

        ViewButton button33 = new ViewButton();
        button33.setName("筛查报告");
        button33.setType("view");
        button33.setUrl(DomainUrl.getUrl() + "/personalData/queryReport");

        ClickButton button34 = new ClickButton();
        button34.setName("扫码事件");
        button34.setType("scancode_push");
        button34.setKey("31");

        List<Button> sub_button3 = new ArrayList<Button>();
        sub_button3.add(button31);
        sub_button3.add(button32);
        sub_button3.add(button33);
        sub_button3.add(button34);

        Button button = new Button();
        button.setName("筛查结果");
        button.setSub_button(sub_button3);

        List<Button> list=new ArrayList<Button>();
        list.add(button1);
        list.add(button2);
        list.add(button);

        menu.setButton(list);
        return menu;
    }


    //批量标签
    @ResponseBody
    @RequestMapping("/weixinMenu")
    public String weixinMenu() {

        Menu menu = new Menu();

        List<WeixinMenu> weixin = weixinMenuServices.getWeixinMenu();

        //一级菜单
        List<WeixinMenu> weixin1 = new ArrayList<WeixinMenu>();

        for(WeixinMenu w : weixin ){
            if( StringUtils.isEmpty(w.getPid()) ){
                weixin1.add(w); //.....一级菜单
            }
        }

        List<Button> list=new ArrayList<Button>();
        for(WeixinMenu w : weixin1 ){//一级菜单
            Button button1 = new Button();
            List<Button> sub_button1 = new ArrayList<Button>();
            for(WeixinMenu w2 : weixin ){
                if( w.getId().equals(w2.getPid()) ){ //如果pid等于id，说明该记录w2是二级菜单

                    if("view".equals( w2.getType() ) ){
                        ViewButton button = new ViewButton();
                        button.setName(w2.getName());
                        button.setType(w2.getType());
                        button.setUrl(DomainUrl.getUrl() + w2.getUrl());
                        sub_button1.add(button);
                    }
                    if("scancode_push".equals( w2.getType() ) ){//扫码事件
                        ClickButton button = new ClickButton();
                        button.setName(w2.getName());
                        button.setType(w2.getType());
                        button.setKey(w2.getMenukey());
                        sub_button1.add(button);
                    }

                }
            }
            button1.setName(w.getName());
            button1.setSub_button(sub_button1);

            list.add(button1);
        }

        menu.setButton(list);

        String menuStr  = JSONObject.fromObject(menu).toString();
        int result = 0;

        result = WeixinUtil.createMenu(WeixinUtil.getAccessToken().getToken(),menuStr);//创建菜单

        if(result==0){
            System.out.println("创建菜单成功");
        }else{
            System.out.println("错误码"+result);
        }

        return "创建菜单成功";

    }

}








