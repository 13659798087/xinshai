package com.xinshai.xinshai.model;

public class TimeTask {

    private String timeTaskId;
    private String taskDescription;
    private String cronExpressions;
    private String taskClassName;

    public String getTimeTaskId() {
        return timeTaskId;
    }

    public void setTimeTaskId(String timeTaskId) {
        this.timeTaskId = timeTaskId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getCronExpressions() {
        return cronExpressions;
    }

    public void setCronExpressions(String cronExpressions) {
        this.cronExpressions = cronExpressions;
    }

    public String getTaskClassName() {
        return taskClassName;
    }

    public void setTaskClassName(String taskClassName) {
        this.taskClassName = taskClassName;
    }

}
