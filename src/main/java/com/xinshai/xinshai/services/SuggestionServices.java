package com.xinshai.xinshai.services;

import com.xinshai.xinshai.dao.SuggestionDao;
import com.xinshai.xinshai.model.Suggestion;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class SuggestionServices {

    @Resource
    private SuggestionDao suggestionDao;

    public void wxsubmitSuggestion(String id,String openid,String name, String phone, String mail, String suggestion) {
        suggestionDao.wxsubmitSuggestion(id,openid,name,phone,mail,suggestion);
    }

    public List<Map> wxgetSuggestion(String openid) {
        return suggestionDao.wxgetSuggestion(openid);
    }

    public void deleteSug(String id) {
        suggestionDao.deleteSug(id);
    }

    public List<Suggestion> getSuggestionList(Timestamp time1, Timestamp time2, int pageNo, int pageSize) {
        return suggestionDao.getSuggestionList(time1, time2, pageNo, pageSize);
    }

    public long getSuggestionCount(Timestamp time1, Timestamp time2) {
        return suggestionDao.getSuggestionCount(time1, time2);
    }
}
