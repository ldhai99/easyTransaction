package com.github.ldhai99.easyTransaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class EventObject {

    public String eventName=Constant.DEFAULT_Event; // 事件名

    private Object source;  // 事件源

    public String title=""; //标题--例子：人员管理
    public String operation=""; //操作---例子：删除
    //数据------------------------------------------
    //保存参数
    public Map<String, Object> para = new HashMap<>();

    //保存结果
    public Map<String, Object> result = new HashMap<>();

    //处理结果-------------------
    public String message ="";
    public boolean  success =true;

    //数据源---------------------------------------
    public DataSource ds;
    public Connection con;

    //get，set------结果------------------------------
    public EventObject saveResult(String name,Object value){
       result.put(name,value);
       return  this;
    }
    public Object getResult(String name){
        return result.get(name);
    }
    //get，set------参数------------------------------
    public EventObject savePara(String name,Object value){
        para.put(name,value);
        return  this;
    }
    public Object getPara(String name){
        return para.get(name);
    }

    //get，set------------------------------------
    public String getMessage(){
        return this.message;

    }
    public EventObject setMessage(String message){
        this.message=message;
        return  this;
    }

    public String getEventName() {
        return eventName;
    }

    public EventObject setEventName(String eventName) {
        this.eventName = eventName;
        return  this;
    }

    public Object getSource() {
        return source;
    }

    public EventObject setSource(Object source) {
        this.source = source;
        return  this;
    }

    public String getTitle() {
        return title;
    }

    public EventObject setTitle(String title) {
        this.title = title;
        return  this;
    }

    public Map<String, Object> getPara() {
        return para;
    }

    public EventObject setPara(Map<String, Object> para) {
        this.para = para;
        return  this;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public EventObject setResult(Map<String, Object> result) {
        this.result = result;
        return  this;
    }

    public boolean isSuccess() {
        return success;
    }

    public EventObject setSuccess(boolean success) {
        this.success = success;
        return  this;
    }

    public DataSource getDs() {
        return ds;

    }

    public EventObject setDs(DataSource ds) {
        this.ds = ds;
        return  this;
    }

    public Connection getCon() {
        return con;
    }

    public EventObject setCon(Connection con) {
        this.con = con;
        return  this;
    }

}