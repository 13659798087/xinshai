package com.xinshai.xinshai.util;

import com.xinshai.xinshai.entiry.AccessToken;
import com.xinshai.xinshai.entiry.JsapiTicket;
import com.xinshai.xinshai.entiry.User_list;
import com.xinshai.xinshai.entiry.groupMessage.ImageTextMessage;
import com.xinshai.xinshai.entiry.groupMessage.PreviewMessage;
import com.xinshai.xinshai.entiry.groupMessage.TagMessage;
import com.xinshai.xinshai.entiry.groupMessage.TextMessage;
import com.xinshai.xinshai.entiry.menu.Button;
import com.xinshai.xinshai.entiry.menu.ClickButton;
import com.xinshai.xinshai.entiry.menu.Menu;
import com.xinshai.xinshai.entiry.menu.ViewButton;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeixinUtil {

    //测试号
    //private static final String APPID = "wxe81e2b9596f72534";
    //private static final String APPSECRET = "07ed82e301b278362d73de49d9672794";

    //联兆母婴健康
    public static final String APPID = "wx362203c9bf705039";
    private static final String APPSECRET = "d03fb25dda90c511d6becde4bf9a3cbf";

    //token
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    //jsapi_ticket
    private static final String JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    //上传临时素材
    private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
    //获取临时素材
    private static final String GET_TEMP_MEDIA = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

    private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

    private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

    //开发者可通过OpenID来获取用户基本信息,获取1条
    private static final String GET_USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    //批量获取用户基本信息。最多支持一次拉取100条。
    private static final String GET_BATCH_USER = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";


    private static final String GET_OPENID_URL =  "https://api.weixin.qq.com/sns/oauth2/access_token?appid=AppId&secret=AppSecret&code=CODE&grant_type=authorization_code";

    //获取用户信息
    private static final String GET_USER_LIST1 = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN";//10000条以下
    private static final String GET_USER_LIST2 = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";//10000条以上

    //发送模板消息
    private static final String TEMPLATE_SEND = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    //根据OpenID列表群发
    private static final String GROUPURL_OPENID = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
    //根据标签进行群发
    private static final String GROUPURL_TAGNID = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";

    private static final String PREVIEWp_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=ACCESS_TOKEN";

    // 获取公众号已创建的标签
    private static final String GET_TAG_LIST = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=ACCESS_TOKEN";

    //创建标签
    private static final String CREATE_TAG = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token=ACCESS_TOKEN";

    //编辑标签
    private static final String UPDATE_TAG = "https://api.weixin.qq.com/cgi-bin/tags/update?access_token=ACCESS_TOKEN";

    //删除标签
    private static final String DELETE_TAG = "https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=ACCESS_TOKEN";

    //编辑备注
    private static final String UPDATE_REMARK = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=ACCESS_TOKEN";

    //给用户打标签
    private static final String BATCH_TAG = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=ACCESS_TOKEN";

    //给用户取消标签
    private static final String CANCEL_TAG = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=ACCESS_TOKEN";

    //添加客服账号
    private static final String ADD_CUSTOMER = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN";

    //发送语义理解请求
    private static final String SEARCH_PARAM = "https://api.weixin.qq.com/semantic/semproxy/search?access_token=ACCESS_TOKEN";




    /**
     * get请求
     * @param url
     * @return
     */
    public static JSONObject doGetStr(String url){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entiy = response.getEntity();
            if(entiy != null){
                String result = EntityUtils.toString(entiy,"UTF-8");
                jsonObject = JSONObject.fromObject(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * post请求
     * @param url
     * @param outStr
     * @return
     */
    public static JSONObject doPostStr(String url,String outStr){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonObject = null;
        try {
            httpPost.setEntity(new StringEntity(outStr,"UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(),"UTF-8");

            jsonObject = JSONObject.fromObject(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    /**
     * 文件上传
     * @param filePath
     * @param accessToken
     * @param type
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws KeyManagementException
     */
    public static String upload(String filePath, String accessToken,String type) throws IOException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在");
        }

        String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE",type);

        URL urlObj = new URL(url);
        //连接
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false);

        //设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");

        //设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");

        //获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        //输出表头
        out.write(head);

        //文件正文部分
        //把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();

        //结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//定义最后数据分隔线

        out.write(foot);

        out.flush();
        out.close();

        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        String result = null;
        try {
            //定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        JSONObject jsonObj = JSONObject.fromObject(result);
        System.out.println(jsonObj);
        String typeName = "media_id";
        if(!"image".equals(type)){
            typeName = type + "_media_id";
        }
        String mediaId = jsonObj.getString(typeName);
        return mediaId;
    }

    public static JSONObject upload1(String filePath, String accessToken,String type) throws IOException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在");
        }

        String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE",type);

        URL urlObj = new URL(url);
        //连接
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false);

        //设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");

        //设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");

        //获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        //输出表头
        out.write(head);

        //文件正文部分
        //把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();

        //结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//定义最后数据分隔线

        out.write(foot);

        out.flush();
        out.close();

        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        String result = null;
        try {
            //定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        JSONObject jsonObj = JSONObject.fromObject(result);

        return jsonObj;
    }

    /**
     * 获取access_token
     * @return
     */
    public static AccessToken getAccessToken(){
        AccessToken token = new AccessToken();
        String url = ACCESS_TOKEN_URL.replace("APPID",APPID).replace("APPSECRET",APPSECRET);
        JSONObject jsonObject = doGetStr(url);
        if(jsonObject!=null){
            token.setToken(jsonObject.getString("access_token"));
            token.setExpires_in(jsonObject.getInt("expires_in"));
        }
        return token;
    }


    /**
     * 获取jsapi_ticket
     * @return
     */
    public static JsapiTicket getTsapiTicket(String token){
        JsapiTicket ticket = new JsapiTicket();
        String url = JSAPI_TICKET.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = doGetStr(url);
        if(jsonObject!=null){
            ticket.setTicket(jsonObject.getString("ticket"));
            ticket.setExpires_in(jsonObject.getInt("expires_in"));
        }
        return ticket;
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
        button13.setUrl(DomainUrl.getUrl() + "combine/listCombine");

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
        button22.setName("位置导航");
        button22.setType("view");
        button22.setUrl("http://map.baidu.com/mobile");

        ViewButton button23 = new ViewButton();
        button23.setName("注意事项");
        button23.setType("view");
        button23.setUrl("http://www.mama.cn/z/art/8736/");

        ClickButton button24 = new ClickButton();
        button24.setName("地理位置");
        button24.setType("location_select");
        button24.setKey("32");

        List<Button> sub_button2 = new ArrayList<Button>();
        sub_button2.add(button21);
        sub_button2.add(button22);
        sub_button2.add(button23);
        sub_button2.add(button24);

        Button button2 = new Button();
        button2.setName("服务咨询");
        button2.setSub_button(sub_button2);


        ViewButton button31 = new ViewButton();
        button31.setName("报告寄送");
        button31.setType("view");
        button31.setUrl(DomainUrl.getUrl() + "combine/listCombine");

        ViewButton button32 = new ViewButton();
        button32.setName("我的信息");
        button32.setType("view");
        button32.setUrl(DomainUrl.getUrl() + "personalData/personal");

        ViewButton button33 = new ViewButton();
        button33.setName("筛查报告");
        button33.setType("view");
        button33.setUrl(DomainUrl.getUrl() + "personalData/queryReport");

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
/*    public static Menu initMenu(){
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
        button13.setUrl(DomainUrl.getUrl() + "combine/listCombine");

        Button button1 = new Button();
        button1.setName("筛查中心");
        button1.setSub_button(new Button[]{button11,button12,button13});


        ViewButton button21 = new ViewButton();
        button21.setName("最新资讯");
        button21.setType("view");
        button21.setUrl("http://www.lznsn.com/");

        ViewButton button22 = new ViewButton();
      *//*  button22.setName("地理位置");
        button22.setType("location_select");
        button22.setKey("32");*//*
        button22.setName("位置导航");
        button22.setType("view");
        button22.setUrl("http://map.baidu.com/mobile");

        ViewButton button23 = new ViewButton();
        button23.setName("注意事项");
        button23.setType("view");
        button23.setUrl("http://www.mama.cn/z/art/8736/");

        Button button2 = new Button();
        button2.setName("服务咨询");
        button2.setSub_button(new Button[]{button21,button22,button23});


        ViewButton button31 = new ViewButton();
        button31.setName("报告寄送");
        button31.setType("view");
        button31.setUrl(DomainUrl.getUrl() + "combine/listCombine");

        ViewButton button32 = new ViewButton();
        button32.setName("我的信息");
        button32.setType("view");
        button32.setUrl(DomainUrl.getUrl() + "personalData/personal");

        ViewButton button33 = new ViewButton();
        button33.setName("筛查报告");
        button33.setType("view");
        button33.setUrl(DomainUrl.getUrl() + "personalData/queryReport");

        ClickButton button34 = new ClickButton();
        button34.setName("扫码事件");
        button34.setType("scancode_push");
        button34.setKey("31");

        Button button = new Button();
        button.setName("筛查结果");
        button.setSub_button(new Button[]{button31,button32,button33,button34});

        menu.setButton(new Button[]{button1,button2,button});
        return menu;
    }*/

    /**
     *群发文本消息
     * @return
     */
    public static TextMessage initTextGroupMessage(){

        TextMessage textMessage = new TextMessage();

        List<String> list = new ArrayList<String>();
        list.add("ofhhXxOY8b05I4uZ9UazShDaaDsM");
        list.add("ofhhXxFRKiAg5NJgPCSe1qlbirS8");
        textMessage.setTouser(list);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("content", "测试链接，<a href='https://www.baidu.com/'>点我去百度</a>");//格式是键值对
        textMessage.setText(map);

        textMessage.setMsgtype("text");

        return textMessage;

        //错误码：45065
        //群发消息ID重复，微信返回错误信息:"clientmsgid exist"
        //常见原因:
        //1、发送太频繁，建议发送间隔长一点,至少15秒

    }

    public static ImageTextMessage initImageTextGroupMessage(String media){
        ImageTextMessage imageTextMessage = new ImageTextMessage();
        Map<String,Object> mpnews = new HashMap<String,Object>();
        mpnews.put("media_id",media);
        List<String> list = new ArrayList<String>();
        list.add("ofhhXxFRKiAg5NJgPCSe1qlbirS8");
        list.add("ofhhXxOY8b05I4uZ9UazShDaaDsM");
        imageTextMessage.setTouser(list);

        imageTextMessage.setMpnews(mpnews);
        imageTextMessage.setMsgtype("mpnews");
        imageTextMessage.setSend_ignore_reprint(0);

        return imageTextMessage;
    }

    public static TagMessage initTagMessage(String media){
        TagMessage tagMessage = new TagMessage();
        Map<String,Object> mpnews = new HashMap<String,Object>();
        mpnews.put("media_id",media);

        Map<String,Object> filter = new HashMap<String,Object>();
        filter.put("is_to_all",false);
        filter.put("tag_id","星标用户");
        tagMessage.setFilter(filter);

        tagMessage.setMpnews(mpnews);
        tagMessage.setMsgtype("mpnews");
        tagMessage.setSend_ignore_reprint(0);

        return tagMessage;
    }


    public static User_list initUserMessage(String openidList){

        User_list userList = new User_list();

        String [] stringArr= openidList.replace("[","").
                replace("]","").replace("\"","").split(",");

        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();

        for(int i=0;i<stringArr.length;i++){
            Map<String,Object> user_list = new HashMap<String,Object>();

            user_list.put("openid",stringArr[i]);
            user_list.put("lang","zh_CN");
            listMap.add(user_list);

        }

        userList.setUser_list(listMap);

        return userList;

    }


    public static PreviewMessage initPreviewMessage(String media){
        PreviewMessage tagMessage = new PreviewMessage();
        Map<String,Object> mpnews = new HashMap<String,Object>();
        mpnews.put("media_id",media);

        tagMessage.setTouser("ofhhXxOY8b05I4uZ9UazShDaaDsM");
        tagMessage.setMpnews(mpnews);
        tagMessage.setMsgtype("mpnews");

        return tagMessage;
    }

    /*public static Menu initMenu(){
        Menu menu = new Menu();
        ClickButton button11 = new ClickButton();
        button11.setName("click菜单");
        button11.setType("click");
        button11.setKey("11");

        ViewButton button21 = new ViewButton();
        button21.setName("view菜单");
        button21.setType("view");
        button21.setUrl("http://www.imooc.com");

        ClickButton button31 = new ClickButton();
        button31.setName("扫码事件");
        button31.setType("scancode_push");
        button31.setKey("31");

        ClickButton button32 = new ClickButton();
        button32.setName("地理位置");
        button32.setType("location_select");
        button32.setKey("32");

        Button button = new Button();
        button.setName("菜单");
        button.setSub_button(new Button[]{button31,button32});

        menu.setButton(new Button[]{button11,button21,button});
        return menu;
    }*/

    /*public static TagMessage initTagMessage(String media){
        TagMessage tagMessage = new TagMessage();
        Map<String,Object> mpnews = new HashMap<String,Object>();
        mpnews.put("media_id",media);
        //private String[] touser;
        *//*String string = "ofhhXxOY8b05I4uZ9UazShDaaDsM,ofhhXxFRKiAg5NJgPCSe1qlbirS8";
        String [] stringArr= string.split(",");
        ImageTextMessage.setTouser(stringArr);*//*
        List<String> list = new ArrayList<String>();
        list.add("ofhhXxOY8b05I4uZ9UazShDaaDsM");
        list.add("ofhhXxFRKiAg5NJgPCSe1qlbirS8");
        tagMessage.setTouser(list);

        tagMessage.setMpnews(mpnews);
        tagMessage.setMsgtype("mpnews");
        tagMessage.setSend_ignore_reprint(0);

        return tagMessage;
    }*/



    public static int createMenu(String token,String menu) throws ParseException {
        int result = 0;
        String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = doPostStr(url, menu);
        if(jsonObject != null){
            result = jsonObject.getInt("errcode");
        }
        return result;
    }

    public static JSONObject queryMenu(String token) throws ParseException {
        String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = doGetStr(url);
        return jsonObject;
    }

    public static int deleteMenu(String token) throws ParseException {
        String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = doGetStr(url);
        int result = 0;
        if(jsonObject != null){
            result = jsonObject.getInt("errcode");
        }
        return result;
    }


    /**  通过OpenID来获取用户基本信息
     *  * @param opendID
     *  * @return
     *  */
    public static JSONObject getUserInfo(String token,String opendID){
        String url = GET_USERINFO_URL.replace("ACCESS_TOKEN" , token).replace("OPENID" ,opendID);
        JSONObject jsonObject = doGetStr(url);

        if(jsonObject != null){
            if ( jsonObject.has("errcode") ) {//包含错误码
                System.out.println("错误 errcode:{} errmsg:{},"+ jsonObject.getInt("errcode")+","+jsonObject.get("errmsg").toString());
            }else{
                return jsonObject;
            }
        }
        return jsonObject;
    }

    /**
     * 批量获取用户列表
     * @param token
     * @param groupMessage
     */
    public static String getBatchUser(String token,String groupMessage) {
        String msg = null;
        String url = GET_BATCH_USER.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = doPostStr(url, groupMessage);
        if(jsonObject != null){
            if ( jsonObject.has("user_info_list") ) {
                msg = jsonObject.getString("user_info_list");
            }else {
                System.out.println("错误 errcode:{} errmsg:{},"+ jsonObject.getInt("errcode")+","+jsonObject.get("errmsg").toString());
            }
        }

        return msg;
    }




    /**
     * 获取openid
     * @return
     */
    public static String getOpendId(String code) throws ParseException {
        String url = GET_OPENID_URL.replace("AppId", APPID).replace("AppSecret", APPSECRET).replace("CODE", code);
        JSONObject jsonObject = doGetStr(url);
        String openid = "";
        if (jsonObject.get("openid")!=null) {
            openid = jsonObject.get("openid").toString();
        }
        return openid;
    }

    /**
     * 获取微信关注列表
     * @return
     */
    public static String getUserList(String access_token,String nextOpenid) throws ParseException {

        String openidList = "";

        int total=0;
        int count=0;
        do{
            String url = null;
            if(StringUtils.isBlank(nextOpenid)){
                url = GET_USER_LIST1.replace("ACCESS_TOKEN", access_token);//10000条一下
            }else {
                url = GET_USER_LIST2.replace("ACCESS_TOKEN", access_token).replace("NEXT_OPENID", nextOpenid);//10000条以上
            }
            JSONObject jsonObject = doGetStr(url);
            if(jsonObject != null){
                total = jsonObject.getInt("total");
                count = jsonObject.getInt("count");
                nextOpenid = jsonObject.getString("next_openid");
                jsonObject.get("data");
                openidList = JSONObject.fromObject(jsonObject.get("data")).get("openid").toString();
            }

        }
        while (total>10000);

        return openidList;

    }

    /**
     * 发送模板信息
     * @param token
     * @param wechatTemplate
     * @return
     * @throws ParseException
     */
    public static int sendTemplatesMsg(String token,String wechatTemplate) throws ParseException{
        int result = 0;
        String url = TEMPLATE_SEND.replace("ACCESS_TOKEN", token);

        JSONObject jsonObject = doPostStr(url, wechatTemplate);
        if(jsonObject != null){
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                System.out.println("错误 errcode:{} errmsg:{},"+ jsonObject.getInt("errcode")+","+jsonObject.get("errmsg").toString());
            }
        }
        return result;
    }

    /**
     * 根据OpenID列表群发
     * @param token
     * @param groupMessage
     * @return
     * @throws ParseException
     */
    public static int SendTextMessage (String token,String groupMessage) throws ParseException{
        int result = 0;
        String url = GROUPURL_OPENID.replace("ACCESS_TOKEN", token);
        //groupMessage = "{\"touser\":[\"ofhhXxOY8b05I4uZ9UazShDaaDsM\",\"ofhhXxFRKiAg5NJgPCSe1qlbirS8\"],\"mpnews\":{\"media_id\":\"2JGpiEqa34NdlC31iQkbJwn2FUaeVSuey2NX47Az7kbrDMEJBgBE1c6LpI2ckFVw\"},\"send_ignore_reprint\":0,\"msgtype\":\"mpnews\"}";
        JSONObject jsonObject = doPostStr(url, groupMessage);
        if(jsonObject != null){
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                System.out.println("错误 errcode:{} errmsg:{},"+ jsonObject.getInt("errcode")+","+jsonObject.get("errmsg").toString());
            }
        }

        return result;
    }

    /**
     * 根据标签群发
     * @param token
     * @param groupMessage
     * @return
     * @throws ParseException
     */
    public static int SendTagMessage (String token,String groupMessage) throws ParseException{
        int result = 0;
        String url = GROUPURL_TAGNID.replace("ACCESS_TOKEN", token);

        JSONObject jsonObject = doPostStr(url, groupMessage);

        if(jsonObject != null){
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                System.out.println("错误 errcode:{} errmsg:{},"+ jsonObject.getInt("errcode")+","+jsonObject.get("errmsg").toString());
            }
        }

        return result;
    }


    public static int previewMessage (String token,String groupMessage) throws ParseException{
        int result = 0;
        String url = PREVIEWp_URL.replace("ACCESS_TOKEN", token);

        JSONObject jsonObject = doPostStr(url, groupMessage);

        if(jsonObject != null){
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                System.out.println("错误 errcode:{} errmsg:{},"+ jsonObject.getInt("errcode")+","+jsonObject.get("errmsg").toString());
            }
        }

        return result;
    }


    /**
     * 获取公众号已创建的标签
     * @param token
     * @return
     * @throws ParseException
     */
    public static JSONObject getTagList (String token) throws ParseException{
        String url = GET_TAG_LIST.replace("ACCESS_TOKEN", token);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entiy = response.getEntity();
            if(entiy != null){
                String result = EntityUtils.toString(entiy,"UTF-8");
                jsonObject = JSONObject.fromObject(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**创建标签
     */
    public static String createTag (String token,String tags){
        String result = null;
        String url = CREATE_TAG.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = doPostStr(url, tags);
        if (jsonObject != null) {
            if (jsonObject.has("tag")) {
                result = jsonObject.getString("tag");
            } else {
                System.out.println("错误 errcode:{} errmsg:{}," + jsonObject.getInt("errcode") + "," + jsonObject.get("errmsg").toString());
            }
        }
        return result;
    }

    /**编辑标签
     */
    public static int updateTag (String token,String tags){
        int result = 0;
        String url = UPDATE_TAG.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = doPostStr(url, tags);

        if(jsonObject != null){
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                System.out.println("错误 errcode:{} errmsg:{},"+ jsonObject.getInt("errcode")+","+jsonObject.get("errmsg").toString());
            }
        }
        return result;
    }


    /**删除标签
     */
    public static int deleteTag (String token,String tags){
        int result = 0;
        String url = DELETE_TAG.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = doPostStr(url, tags);

        if(jsonObject != null){
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                System.out.println("错误 errcode:{} errmsg:{},"+ jsonObject.getInt("errcode")+","+jsonObject.get("errmsg").toString());
            }
        }
        return result;
    }

    /**编辑备注
     */
    public static int updateFarRemark (String token,String remark){
        int result = 0;
        String url = UPDATE_REMARK.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = doPostStr(url, remark);

        if(jsonObject != null){
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                System.out.println("错误 errcode:{} errmsg:{},"+ jsonObject.getInt("errcode")+","+jsonObject.get("errmsg").toString());
            }
        }
        return result;
    }


    /**给用户打标签*/
    public static int batchtagging (String token,String ta){
        int result = 0;
        String url = BATCH_TAG.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = doPostStr(url, ta);

        if(jsonObject != null){
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                System.out.println("错误 errcode:{} errmsg:{},"+ jsonObject.getInt("errcode")+","+jsonObject.get("errmsg").toString());
            }
        }
        return result;
    }


    /**给用户取消标签*/
    public static int canceltag (String token,String ta){
        int result = 0;
        String url = CANCEL_TAG.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = doPostStr(url, ta);

        if(jsonObject != null){
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                System.out.println("错误 errcode:{} errmsg:{},"+ jsonObject.getInt("errcode")+","+jsonObject.get("errmsg").toString());
            }
        }
        return result;
    }



    /**添加客服*/
    public static int addCustomer (String token,String ta){
        int result = 0;
        String url = ADD_CUSTOMER.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = doPostStr(url, ta);

        if(jsonObject != null){
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                System.out.println("错误 errcode:{} errmsg:{},"+ jsonObject.getInt("errcode")+","+jsonObject.get("errmsg").toString());
            }
        }
        return result;
    }

    /**获取临时素材*/
    public static int getTempMedia(String token,String media_id) throws IOException {
        int result = 0;
        String url = GET_TEMP_MEDIA.replace("ACCESS_TOKEN", token).replace("MEDIA_ID",media_id);

        JSONObject jsonObject = doGetStr(url);

        if(jsonObject != null){
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                System.out.println("错误 errcode:{} errmsg:{},"+ jsonObject.getInt("errcode")+","+jsonObject.get("errmsg").toString());
            }
        }
        return result;
    }



    /**发送语义理解请求*/
    public static String searchParam (String token,String ta){
        String url = SEARCH_PARAM.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = doPostStr(url, ta);

        /*if(jsonObject != null){
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                System.out.println("错误 errcode:{} errmsg:{},"+ jsonObject.getInt("errcode")+","+jsonObject.get("errmsg").toString());
            }
        }*/
        return jsonObject.toString();
    }




    public static String getWeatherSemInfo(String accessToken,String reqJson){
        String requestUrl="https://api.weixin.qq.com/semantic/semproxy/search?access_token=YOUR_ACCESS_TOKEN";
        requestUrl=requestUrl.replace("ACCESS_TOKEN", accessToken);
        JSONObject json= doPostStr(requestUrl, reqJson);

        String city="广州";
        int errcode=json.getInt("errcode");
        if(0==errcode){
            JSONObject semantic=json.getJSONObject("semantic");
            JSONObject details=semantic.getJSONObject("details");
            JSONObject location=details.getJSONObject("location");
            city=location.getString("city");
        }else{
            System.out.println("语义理解失败");
        }

        return city;
    }


}
