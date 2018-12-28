package com.xinshai.xinshai.services;

import com.xinshai.xinshai.dao.TimeTaskDao;
import com.xinshai.xinshai.model.TimeTask;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TimeTaskServices {

    @Resource
    private TimeTaskDao timeTaskDao;

    public List<TimeTask> listTimeTask(int pageNo, int pageSize) {
        return timeTaskDao.listTimeTask(pageNo,pageSize);
    }

    public long getTimeTaskCount() {
        return timeTaskDao.getTimeTaskCount();
    }

    public String getCorn(String timetaskId) {
        return timeTaskDao.getCorn(timetaskId);
    }

    public void pcChangeState(String timeTaskId, String state) {
        timeTaskDao.pcChangeState(timeTaskId,state);
    }

    public void updateTimeTask(String taskDescription, String cronExpressions, String timeTaskId) {
        timeTaskDao.updateTimeTask(taskDescription, cronExpressions, timeTaskId);
    }
}
