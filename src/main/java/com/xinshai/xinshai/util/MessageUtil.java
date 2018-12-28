package com.xinshai.xinshai.util;

import com.thoughtworks.xstream.XStream;
import com.xinshai.xinshai.entiry.*;
import com.xinshai.xinshai.model.AttentionReply;
import com.xinshai.xinshai.services.AttentionServices;
import com.xinshai.xinshai.services.HospitalServices;
import com.xinshai.xinshai.services.WeixinUserInfoServices;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
public class MessageUtil {

	private static HospitalServices hospitalServices;
	@Resource
	public void setVerificDao(HospitalServices hospitalServices) {
		this.hospitalServices = hospitalServices;
	}

	public static String MESSAGE_TEXT = "text";
	public static String MESSAGE_NEWS = "news";
	public static String MESSAGE_IMAGE = "image";
	public static String MESSAGE_VOICE = "voice";
	public static String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_MUSIC = "music";
	public static String MESSAGE_LINK = "link";
	public static String MESSAGE_LOCATION = "location";
	public static String MESSAGE_EVENT = "event";
	public static String MESSAGE_SUBSCRIBE = "subscribe";//消息关注
	public static String MESSAGE_UNSUBSCRIBE = "unsubscribe";//取消关注
	public static String MESSAGE_CLICK = "CLICK";
	public static String MESSAGE_VIEW = "VIEW";
	public static final String MESSAGE_SCANCODE= "scancode_push";

	/**
	 * 将文本消息对象转为xml
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());//将根节点替换成xml
		return xstream.toXML(textMessage);
	}


	/**
	 * 将图文消息转为xml
	 */
	public static String NewsMessageToXml(NewsMessage newsMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", newsMessage.getClass());//将根节点替换成xml
		xstream.alias("item", new News().getClass());//将根节点替换成xml
		return xstream.toXML(newsMessage);
	}

	/**
	 * 图片消息转为xml
	 * @param imageMessage
	 * @return
	 */
	public static String imageMessageToXml(ImageMessage imageMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}

	/**
	 * 音乐消息转为xml
	 * @param musicMessage
	 * @return
	 */
	public static String musicMessageToXml(MusicMessage musicMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	/*
	 * xml转map集合
	 */
	public static Map<String,String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
		Map<String,String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();

		InputStream ins = request.getInputStream();//从request获取输入流
		Document doc = reader.read(ins); //从reader对象中取出流

		Element root = doc.getRootElement();//获取xml的根元素

		List<Element> list = root.elements();//将根元素的所有节点放入list集合中

		for(Element e:list){
			map.put(e.getName(), e.getText());
		}
		ins.close();

		return map;

	}


    public static String initText(String toUserName,String fromUserName,String content){
        TextMessage text = new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(MessageUtil.MESSAGE_TEXT);
        text.setCreateTime(new Date().getTime());
        text.setContent(content);
        return textMessageToXml(text);
    }

	public static String initServer(String toUserName,String fromUserName,String msgType){
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(msgType);
		text.setCreateTime(new Date().getTime());
		//text.setContent(content);
		return textMessageToXml(text);
	}



    public static String firstMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("请输入您的问题，等待客服回复！");
		return sb.toString();
	}
	
	public static String secondMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("结果查询请先选中地区，在选择对应的筛查中心名称，感谢关注");
		return sb.toString();
	}
	
	/**
	 * 主菜单 动态取数据
	 * @return
	 */
	public static String menuText(){
		AttentionServices a = new AttentionServices();
		List<AttentionReply> reply = new ArrayList<AttentionReply>();
		StringBuffer sb = new StringBuffer();
		int i = 1;
		for(AttentionReply r : reply){
			sb.append( i+"、"+r.getContent());
			i++;
		}
		return sb.toString();
	}

	/**
	 * 主菜单 写死的数据
	 * @return
	 */
	public static String menuText1(){
		StringBuffer sb = new StringBuffer();
		//sb.append("欢迎您的关注！\n\n");
		//sb.append("点击"+"<a href=\""+DomainUrl.getUrl()+"/personalData/personal\""+">我的消息</a>，进行信息绑定。\n");
		//sb.append("个人信息绑定："+"<a href=\""+DomainUrl.getUrl()+"/personalData/personal\""+">http://wx.lznsn.com/personalData/personal</a>\n");
		sb.append("1、为了您能及时接收到报告结果，请您先绑定个人信息：http://wx.lznsn.com/personalData/personal\n\n");
		sb.append("3、回复关键字“人工服务”，即可进入人工服务模式。\n\n");
		sb.append("回复？调出此菜单。");

		return sb.toString();

	}


	/**
	 * 报告出结果之后，将此消息推送给用户（openid）
	 * @return
	 */
	public static String pushMsg(){
		StringBuffer sb = new StringBuffer();
		sb.append("您的报告已出结果啦！");
		return sb.toString();

	}



	/**
	 * 图文消息的组装
	 */
	public static String initNewsMessage(String toUserName,String fromUserName) {
		String message = null;
		List<News> newslist = new ArrayList<News>();
		NewsMessage newsMessage = new NewsMessage();

		//单图文消息才会展示图文消息的描述，多图文不会展示
		News news = new News();
		news.setTitle("码上敲响录");
		news.setDescription("码上敲响录是老王的技能学习网站，提供技术交流和软件开发");//单图文消息才会展
		news.setPicUrl( hospitalServices.getDomainUrl()  +"/img/house.png");
		news.setUrl("http://yayihouse.com/yayishuwu/");

		newslist.add(news);

		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticles(newslist);
		newsMessage.setArticleCount(newslist.size());

		message = NewsMessageToXml(newsMessage);
		return message;
	}

	/**
	 * 图文消息的组装
	 */
	public static String initNewsMessage1(String toUserName,String fromUserName) {
		String message = null;
		NewsMessage newsMessage = new NewsMessage();
		List<News> newslist = new ArrayList<News>();

		//单图文消息才会展示图文消息的描述，多图文不会展示
		News news = new News();
		news.setTitle("淘宝网");
		news.setDescription("淘宝网是大型的购物网站平台");//单图文消息才会展
		news.setPicUrl( hospitalServices.getDomainUrl()  +"/img/taobao.png");
		news.setUrl("https://www.taobao.com/");

		News news1 = new News();
		news1.setTitle("百度");
		news1.setDescription("百度网站是国内的高级搜索引擎，网站baidu.com，百度一下，你就知道");//单图文消息才会展
		news1.setPicUrl( hospitalServices.getDomainUrl() +"/img/baidu.png");
		news1.setUrl("https://www.hao123.com/");

		newslist.add(news);
		newslist.add(news1);

		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticles(newslist);
		newsMessage.setArticleCount(newslist.size());

		message = NewsMessageToXml(newsMessage);
		return message;
	}

	/**
	 * 组装图片消息
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initImageMessage(String toUserName,String fromUserName){
		String message = null;
		Image image = new Image();
		image.setMediaId("46LypSwugWrwGbPo_1bxVjIDNaP172tI1cdxf0oGZA14P_HX4mMFi87_-id0D725");
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setImage(image);
		message = imageMessageToXml(imageMessage);
		return message;
	}

	/**
	 * 组装音乐消息
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initMusicMessage(String toUserName,String fromUserName){
		String message = null;
		Music music = new Music();
		music.setThumbMediaId("onv19hLaL1xoBM4vjGRFZvyMhJRkESxw7ZFSGhQ-Kv5LmABWYKF-Em35ndm8RGZe");
		music.setTitle("see you again");
		music.setDescription("速7片尾曲");
		music.setMusicUrl( hospitalServices.getDomainUrl() +"/weixin/resources/static/img/beautiful.mp3");
		music.setHQMusicUrl( hospitalServices.getDomainUrl() +"/weixin/resources/static/img/beautiful.mp3");

		MusicMessage musicMessage = new MusicMessage();
		musicMessage.setFromUserName(toUserName);
		musicMessage.setToUserName(fromUserName);
		musicMessage.setMsgType(MESSAGE_MUSIC);
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setMusic(music);
		message = musicMessageToXml(musicMessage);
		return message;
	}


}
