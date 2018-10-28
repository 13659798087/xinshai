package com.xinshai.xinshai.entiry.groupMessage;


import java.util.Map;

/**
 * 文本消息
 */
public class TextMessage extends BasetMessage {

    private Map<String,Object> text ;//文本内容

    public Map<String, Object> getText() {
        return text;
    }

    public void setText(Map<String, Object> text) {
        this.text = text;
    }


}
