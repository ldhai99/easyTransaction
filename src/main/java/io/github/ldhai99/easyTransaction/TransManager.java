package io.github.ldhai99.easyTransaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TransManager {


    public String title = ""; //标题--例子：人员管理


    public String operation = ""; //操作---例子：删除
    //数据------------------------------------------
    //保存参数
    public Map<String, Object> para = new HashMap<>();

    //保存结果
    public Map<String, Object> result = new HashMap<>();

    //处理结果-------------------
    public String message = "";
    public boolean success = true;

    //数据源---------------------------------------
    public DataSource ds;
    public Connection con;


    protected LinkedList<EventListener> eventListenerList = new LinkedList<EventListener>();

    public TransManager(DataSource ds) {
        this.ds = ds;
    }

    public TransManager(DataSource ds, String title, String operation) {
        this.ds = ds;
        this.title = title;
        this.operation = operation;

    }

    // 触发事件处理
    public boolean publishEvent() {

        return handle(eventListenerList);

    }

    // 处理服务开始


    // plc事务流程------------------------------------------------
    public boolean handle(List<EventListener> eventHandlerList) {

        // 在此打开数据库，因为后面有关闭动作，放在实例化时候，不一定有关闭动作，即用即关
        try {
            con = ds.getConnection();
            //连接信息，通过event事件传递
            con = con;
            if (con != null)
                con.setAutoCommit(false);

            if (!this.handleDetail(eventHandlerList)) {
                success = false;
                if (message == null || message.trim().length() == 0)
                    message = title + operation + "操作失败!";
                return false;
            }

            if (message == null || message.trim().length() == 0)
                message = title + operation + "操作成功!";

            if (con != null)
                con.commit();

            return true;

        } catch (SQLException ex) {
            try {
                if (con != null)
                    con.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            ex.printStackTrace();
            message = title + operation + "操作失败!" + ex.getMessage();
            success = false;
            return false;

        } catch (Exception ex) {
            try {
                if (con != null)
                    con.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            ex.printStackTrace();
            message = title + operation + "操作失败!" + ex.getMessage();
            success = false;
            return false;

        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // pcl事件处理--参数
    public boolean handleDetail(List<EventListener> eventHandlerList) throws Exception {

        // 处理事件
        for (int i = 0; i < eventHandlerList.size(); i++) {
            EventListener listener = eventHandlerList.get(i);

            success = listener.callback(this);

            // 出错，返回，不在执行
            if (!success) {
                return false;
            }
        }

        return true;
    }


    // 注册事件处理
    public TransManager add(EventListener listener) {

        eventListenerList.add(listener);
        return this;
    }

    // 注册事件处理为第一个
    public TransManager addFirst(EventListener listener) {


        eventListenerList.addFirst(listener);
        return this;
    }

    // 注册事件处理为最后一个
    public TransManager addLast(EventListener listener) {

        eventListenerList.addLast(listener);
        return this;
    }


    //get，set------结果------------------------------
    public TransManager saveResult(String name, Object value) {
        result.put(name, value);
        return this;
    }

    public Object getResult(String name) {
        return result.get(name);
    }

    //get，set------参数------------------------------
    public TransManager savePara(String name, Object value) {
        para.put(name, value);
        return this;
    }

    public Object getPara(String name) {
        return para.get(name);
    }

    //get，set------------------------------------
    public String getMessage() {
        return this.message;

    }

    public TransManager setMessage(String message) {
        this.message = message;
        return this;
    }


    public String getTitle() {
        return title;
    }

    public TransManager setTitle(String title) {
        this.title = title;
        return this;
    }
    public String getOperation() {
        return operation;
    }

    public TransManager setOperation(String operation) {
        this.operation = operation;
        return  this;
    }

    public Map<String, Object> getPara() {
        return para;
    }

    public TransManager setPara(Map<String, Object> para) {
        this.para = para;
        return this;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public TransManager setResult(Map<String, Object> result) {
        this.result = result;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public TransManager setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public DataSource getDs() {
        return ds;

    }

    public TransManager setDs(DataSource ds) {
        this.ds = ds;
        return this;
    }

    public Connection getCon() {
        return con;
    }

    public TransManager setCon(Connection con) {
        this.con = con;
        return this;
    }

}