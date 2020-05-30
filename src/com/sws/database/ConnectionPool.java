package com.sws.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sws.base.Log;

/**
 * ���ӳ�
 * @author Ben
 *
 */
public class ConnectionPool {

	List<Connection> cs = new ArrayList<Connection>();

    int size;

    public ConnectionPool(int size) {
        this.size = size;
        init();
    }

    public void init() {
        //����ǡǡ����ʹ��try-with-resource�ķ�ʽ����Ϊ��Щ���Ӷ���Ҫ��"��"�ģ���Ҫ���Զ��ر���
        try {
            Class.forName("com.mysql.jdbc.Driver");
            for (int i = 0; i < size; i++) {
                Connection c = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/user?characterEncoding=UTF-8",
                "root","admin");
                System.out.println("��ɵ�"+ ( i + 1 ) +"������");
                cs.add(c);
            }
        } catch (ClassNotFoundException e) {
            Log.exception(e);
        } catch (SQLException e) {
            Log.exception(e);
        }
    }
	//synchronized �̰߳�ȫ��
    public synchronized Connection getConnection() {
        while (cs.isEmpty()) {
            try {
            //������ӳ���û�����ӿ�����ȴ�
                this.wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
            	Log.exception(e);
            }
        }
        //�Ƴ���һ�����ӳ�
        Connection c = cs.remove(0);
        return c;
    }

    public synchronized void returnConnection(Connection c) {
    	//ʹ����������ӳ���
        cs.add(c);
        //����wait���������µ����ӳؿ���
        this.notifyAll();
    }
}
