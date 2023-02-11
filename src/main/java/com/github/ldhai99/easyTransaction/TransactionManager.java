package com.github.ldhai99.easyTransaction;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TransactionManager extends EventManager {
    private DataSource ds;
    private Connection con;

    // 构成函数
    public TransactionManager() {

    }

    public TransactionManager(DataSource ds) {
        this.ds = ds;
    }

    // plc事务流程------------------------------------------------
    public boolean handle(List<EventListener> eventHandlerList) {

        // 在此打开数据库，因为后面有关闭动作，放在实例化时候，不一定有关闭动作，即用即关
        try {
            con = ds.getConnection();
            //连接信息，通过event事件传递
            event.con = con;
            if (con != null)
                con.setAutoCommit(false);

            if (!this.handleDetail(eventHandlerList)) {
                event.success = false;
                if (event.message == null || event.message.trim().length() == 0)
                    event.message =event.title + event.operation+ "操作失败!";
                return false;
            }

            if (event.message == null || event.message.trim().length() == 0)
                event.message =event.title + event.operation+ "操作成功!";

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
            event.message =event.title + event.operation+ "操作失败!"+ex.getMessage();
            event.success = false;
            return false;

        } catch (Exception ex) {
            try {
                if (con != null)
                    con.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            ex.printStackTrace();
            event.message =event.title + event.operation+ "操作失败!"+ex.getMessage();
            event.success = false;
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

            event.success = listener.callback(event);

            // 出错，返回，不在执行
            if (!event.success) {
                return false;
            }
        }

        return true;
    }


}
