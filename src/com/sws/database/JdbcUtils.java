package com.sws.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sws.base.Log;

public class JdbcUtils
{
	private final static String DATABASE_NAME = "SmartWeight";
	private final static String USER = "root";
	private final static String PASSWORD = "123456";
	
	//��ʼ�����ݿ�
	public static void initDataBase()
	{
		//�������ݿ�
		createDatabase(USER, PASSWORD, DATABASE_NAME);
		//�������ݱ�
		create_table(USER, PASSWORD, DATABASE_NAME, DB.USER_TABLE_NAME, DB.createUserSQL);
		//������־��
		create_table(USER, PASSWORD, DATABASE_NAME, DB.LOG_TABLE_NAME, DB.createLogSQL);
		//�����豸��
		create_table(USER, PASSWORD, DATABASE_NAME, DB.DEVICE_TABLE_NAME, DB.createDeviceSQL);
		//���������
		create_table(USER, PASSWORD, DATABASE_NAME, DB.TEAM_TABLE_NAME, DB.createTeamSQL);
		//�������ϱ�
		create_table(USER, PASSWORD, DATABASE_NAME, DB.MATERIAL_TABLE_NAME, DB.createMaterialSQL);
		//������¼��
		create_table(USER, PASSWORD, DATABASE_NAME, DB.RECORD_TABLE_NAME, DB.createRecordSQL);
		
		//�������Ա�û�
		if(!SwsQueryDBUtils.userExist("admin"))
		{
			SwsSaveDBUtils.addUser("admin", "admin", 1, "���û��ǹ���Ա");
		}
		
		//����һ���豸
		if(!SwsQueryDBUtils.deviceExist("202005130857"))
		{
			SwsSaveDBUtils.addDevice("202005130857", "һ���豸", "����");
		}
	}
	
	/**
	 * �������ݿ�
	 * @param user
	 * @param pass
	 * @return
	 */
	public static void createDatabase(String user, String pass,String dataBaseName){
		Statement statement = null;
		Connection conn = null;//�������Ӷ���
		String driver = "com.mysql.jdbc.Driver";// ������������
		String url = "jdbc:mysql://127.0.0.1:3306";
		try
		{
			Class.forName(driver);// ע��(����)��������
			conn = DriverManager.getConnection(url, user, pass);// ��ȡ���ݿ�����
			while(conn == null)
			{
				conn = DriverManager.getConnection(url, user, pass);// ��ȡ���ݿ�����
				Thread.sleep(2000);
			}
			
			statement = conn.createStatement();      
			String sql = "CREATE DATABASE "+dataBaseName + " DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci";   
			statement.executeUpdate(sql);
		}
		catch (Exception e)
		{
			Log.exception(e);
		}
		finally
		{
			closeResource(conn, statement, null);
		}
	}
	/**
	 * ������
	
	 */
	public static void create_table(String user, String pass, String dataBaseName, String tableName, String createTableSQL)
	{
		//=========================================================================================
		ResultSet rs = null;
		Connection conncet_conn = null;
		DatabaseMetaData dbmd;
		Statement stmt = null;
		try {
			conncet_conn = getConnection( user,  pass);
			while(conncet_conn == null)
			{
				conncet_conn = getConnection( user,  pass);
				if(conncet_conn == null){
					Thread.sleep(2000);
				}
			}

			dbmd = conncet_conn.getMetaData();  										
			rs = dbmd.getTables(dataBaseName, null, null, new String[]{"TABLE"});
			boolean bHasTable = false;
			while(rs.next())
			{  								
				if(rs.getString(3).equals(tableName))
				{
					bHasTable = true;
				}
	        }						
			//����TABLE
			if(bHasTable == false && createTableSQL != null)
			{
				stmt = conncet_conn.createStatement(); 
				
				String createSql = "create table "+tableName+createTableSQL;
				
				stmt.executeUpdate(createSql);// executeUpdate���᷵��һ����Ӱ����������������-1��û�гɹ� 
			}
			
		} 
		catch (SQLException e) 
		{
			Log.exception(e);
		} 
		catch (InterruptedException e) 
		{
			Log.exception(e);
		} 
		finally
		{
			closeResource(conncet_conn, stmt, rs);
		}
	}
	
	public static Connection getConnection()
	{
		return getConnection("root", "123456");
	}
	
	//���ݿ�����
	public static Connection getConnection(String user, String password)
	{

		Connection conn = null;//�������Ӷ���
		String driver = "com.mysql.jdbc.Driver";// ������������
		String url = "jdbc:mysql://127.0.0.1:3306/"+DATABASE_NAME+"?useUnicode=true&characterEncoding=UTF8";// ��ֹ����
		try
		{
			Class.forName(driver);// ע��(����)��������
			conn = DriverManager.getConnection(url, user, password);// ��ȡ���ݿ�����
		}
		catch (Exception e)
		{
			String errorSTR = "�������ݿ��ʧ��:"+url;
			Log.exceptionDialog(e, errorSTR);
		}
		return conn;
	}
	
	/**
     * �ͷ���Դ
     * @param conn
     * @param st
     * @param rs
     */
	 public static void closeResource(Connection conn,Statement st,ResultSet rs) 
	 {
	     closeResultSet(rs);
	     closeStatement(st);
	     closeConnection(conn);
	 }
	 
	 /**
     * �ͷ����� Connection
     * @param conn
     */
    public static void closeConnection(Connection conn) 
    {
        if(conn !=null) 
        {
            try 
            {
                conn.close();
            } 
            catch (SQLException e) 
            {
                Log.exception(e);
            }
        }
        //�ȴ���������
        conn = null;
    }
    
    /**
     * �ͷ����ִ���� Statement
     * @param st
     */
    public static void closeStatement(Statement st) 
    {
        if(st !=null) 
        {
            try 
            {
                st.close();
            } 
            catch (SQLException e) 
            {
                Log.exception(e);
            }
        }
        //�ȴ���������
        st = null;
    }
    
    /**
     * �ͷŽ���� ResultSet
     * @param rs
     */
    public static void closeResultSet(ResultSet rs) 
    {
        if(rs !=null) 
        {
            try 
            {
                rs.close();
            } 
            catch (SQLException e) 
            {
                Log.exception(e);
            }
        }
        //�ȴ���������
        rs = null;
    }

}
