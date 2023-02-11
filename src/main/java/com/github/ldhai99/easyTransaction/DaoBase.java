package com.github.ldhai99.easyTransaction;



import com.github.ldhai99.easyTransaction.EventListener;
import com.github.ldhai99.easyTransaction.EventObject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public abstract class DaoBase implements EventListener {

    public DataSource ds;
    public  Connection con;
    public EventObject event;


    //处理需要的参数para及处理结果result
    public Map<String, Object> para;
    public Map<String, Object> result;
    public DaoBase() {
        // TODO Auto-generated constructor stub
    }

    //回调处理分为三个步骤
    public boolean callback(EventObject event) throws Exception {

        if (!this.initHandle( event)) {
            return false;
        }
        if (!this.beforeHandle()) {
            return false;
        }
        if (!this.handle()) {
            return false;
        }
        if (!this.afterHandle()) {
            return false;
        }
        return true;
    }
    public  boolean initHandle(EventObject event) throws SQLException, Exception {
        // 变成服务事件参数
        this.event =  event;

        setParameters();
        return true;
    };
    public  boolean beforeHandle() throws SQLException, Exception {
        return true;
    };
    public abstract boolean handle() throws SQLException, Exception ;

    public  boolean afterHandle() throws SQLException, Exception {
        return true;
    };
    // 处理event-参数----------------------------------------------
    public void setParameters() {

        //event事件传过来的数据源与连接
        this.ds = event.ds;
        this.con = event.con;

        //event事件传过来的入参与返回结果
        this.para = event.para;
        this.result = event.result;

    }
    public void saveResult(String name,Object value){
        event.saveResult(name,value);
    }

    public Object getResult(String name){
        return event.getResult(name);
    }
    public void savePara(String name,Object value){
        event.savePara(name,value);
    }

    public Object getPara(String name){
        return event.getPara(name);
    }

}