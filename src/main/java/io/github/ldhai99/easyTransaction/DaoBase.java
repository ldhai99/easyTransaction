package io.github.ldhai99.easyTransaction;



import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public abstract class DaoBase implements EventListener {

    public DataSource ds;
    public  Connection con;
    public TransManager trans;


    //处理需要的参数para及处理结果result
    public Map<String, Object> para;
    public Map<String, Object> result;
    public DaoBase() {
        // TODO Auto-generated constructor stub
    }

    //回调处理分为三个步骤
    public boolean callback(TransManager event) throws Exception {

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
    public  boolean initHandle(TransManager trans) throws SQLException, Exception {
        // 变成服务事件参数
        this.trans =  trans;

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
        this.ds = trans.ds;
        this.con = trans.con;

        //event事件传过来的入参与返回结果
        this.para = trans.para;
        this.result = trans.result;

    }
    public void saveResult(String name,Object value){
        trans.saveResult(name,value);
    }

    public Object getResult(String name){
        return trans.getResult(name);
    }
    public void savePara(String name,Object value){
        trans.savePara(name,value);
    }

    public Object getPara(String name){
        return trans.getPara(name);
    }

}