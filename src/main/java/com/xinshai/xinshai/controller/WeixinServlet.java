package com.xinshai.xinshai.controller;

import com.alibaba.fastjson.JSON;
import com.xinshai.xinshai.entiry.SemParams;
import com.xinshai.xinshai.services.HospitalServices;
import com.xinshai.xinshai.services.WeixinUserInfoServices;
import com.xinshai.xinshai.servlet.TokenThread;
import com.xinshai.xinshai.util.MessageUtil;
import com.xinshai.xinshai.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.dom4j.DocumentException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/Webchat")
public class WeixinServlet extends HttpServlet {

	@Resource
	private WeixinUserInfoServices weixinUserInfoServices;

	@Resource
	private AttentionController attentionController;

	@Resource
	private HospitalServices hospitalServices;

	@RequestMapping("/Index")
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		//修改配置url和token,接入校验，提交成功后，说明开发环境与微信和后台的对接已经成功。微信配置启用开发模式
		/*String signature = req.getParameter("signature");//签名
		String timestamp = req.getParameter("timestamp");//时间戳
		String nonce = req.getParameter("nonce");//随机数
		String echostr = req.getParameter("echostr");//随机字符串

		PrintWriter out = resp.getWriter();
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
		System.out.println("签名"+signature);
		System.out.println("时间戳"+timestamp);
		System.out.println("随机数"+nonce);
		System.out.println("随机字符串"+echostr);*/



		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		try{
			Map<String,String> map = MessageUtil.xmlToMap(req);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
			String createTime = map.get("CreateTime");

			String message = null;
			if("text".equals(msgType)){
				if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
					if("1".equals(content)){
						message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
					}else if("2".equals(content)){
						//message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
						message = MessageUtil.initNewsMessage(toUserName,fromUserName);
					}else if("3".equals(content)){
						//message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
						message = MessageUtil.initNewsMessage1(toUserName,fromUserName);
					}else if("4".equals(content)){
						//message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
						message = MessageUtil.initImageMessage(toUserName,fromUserName);
					}else if("5".equals(content)){
						message = MessageUtil.initMusicMessage(toUserName,fromUserName);
					}
					else if("我的信息".equals(content)){
						message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.menuText());
					}
					else if("?".equals(content) || "？".equals(content)){
						//message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.secondMenu());
						message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
					}/*else if("人工服务".equals(content)) { //其他都进入微信客服模式
						msgType = "transfer_customer_service";
						message = MessageUtil.initServer(toUserName,fromUserName,msgType);
					}*/
					else if( content.length()>0 ) { //其他都进入微信客服模式
						msgType = "transfer_customer_service";
						message = MessageUtil.initServer(toUserName,fromUserName,msgType);
					}
					//大家如果没有报错,但是微信公众号上显示"无法提供服务,请稍后再试",原因是,
					//我们是使用开发测试号来进行开发的,需要登陆测试号，然后扫码关注在测试公众号上进行检验,
					//原公众号没有认证无法调用接口
				}
			}else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
				String eventType = map.get("Event");
				//关注
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){

					//从数据库取数据
					String replyMenu = attentionController.replyMenu();

					//message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText1()); //静态数据
					message = MessageUtil.initText(toUserName, fromUserName, replyMenu);//动态数据

					JSONObject job = WeixinUtil.getUserInfo(TokenThread.accessToken.getToken(),fromUserName);
					String sex = null;//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
					int i = job.getInt("sex");
						switch (i){
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
						Timestamp attentionTime = Timestamp.valueOf( sdf.format(date)  );//关注的时间

						String openid = job.getString("openid");
						String nickname = job.getString("nickname");
						String language = job.getString("language");
						String city = job.getString("city");//用户所在城市
						String province = job.getString("province");//用户所在省份
						String country = job.getString("country");//用户所在国家
						String groupid = job.getString("groupid");//用户所在的分组ID（暂时兼容用户分组旧接口）
						String remark =  job.getString("remark");//备注

					int m = weixinUserInfoServices.getByOpenid(fromUserName);

					if(m==1){ //说明用户关注过，再次关注的时候更改关注时间等信息
						weixinUserInfoServices.UpdateConcerns(fromUserName,0,nickname,language,city,province,country,groupid,attentionTime,remark);
					}else{//如果查不到该用户的记录，说明用户之前没关注过该公众号，就往数据库插入一条记录
						weixinUserInfoServices.insertUserOpenid(openid,nickname,sex,language,city,province,country,groupid,attentionTime,remark);
					}

				}else if(MessageUtil.MESSAGE_UNSUBSCRIBE.equals(eventType)){ //取消关注

					weixinUserInfoServices.UpdateConcerns1(fromUserName,1);

					//weixinUserInfoServices.deleteWeixinMsg(fromUserName);

				}else if(MessageUtil.MESSAGE_CLICK.equals(eventType)){

					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());

					String key = map.get("EventKey");
					if (key.equals("33")) {
						message = "天气预报菜单项被点击！";
					}
				}else if(MessageUtil.MESSAGE_VIEW.equals(msgType)){
					String url = map.get("EventKey");
					message = MessageUtil.initText(toUserName, fromUserName, url);
				}else if(MessageUtil.MESSAGE_SCANCODE.equals(msgType)){
					String key = map.get("EventKey");
					message = MessageUtil.initText(toUserName, fromUserName, key);
				}
			}else if(MessageUtil.MESSAGE_LOCATION.equals(msgType)){
				String label = map.get("Label");
				message = MessageUtil.initText(toUserName, fromUserName, label);
			}else if(MessageUtil.MESSAGE_VOICE.equals(msgType)){
				//String label = map.get("Label");
				String recognition=map.get("Recognition");

				SemParams p=new SemParams();
				p.setAppid( hospitalServices.getAppid() );
				p.setCategory("weather");
				p.setCity("广州");
				p.setQuery(recognition);
				p.setUid(fromUserName);

				String msg = JSON.toJSONString(p);
				String city=WeixinUtil.getWeatherSemInfo(TokenThread.accessToken.getToken(),msg);
				String a = WeixinUtil.searchParam(TokenThread.accessToken.getToken(),msg);

				message = MessageUtil.initText(toUserName, fromUserName, a);
			}

			//System.out.println(message);
			out.print(message);//向微信公众号发送


		}catch(DocumentException e){
			e.printStackTrace();
		}finally{
			out.close();
		}





	}
					/*TextMessage text = new TextMessage();
					text.setFromUserName(toUserName);
					text.setToUserName(fromUserName);
					text.setMsgType("text");
					text.setCreateTime(new Date().getTime());
					text.setContent("您发送的消息是："+content);
					message = MessageUtil.textMessageToXml(text);*/








}
