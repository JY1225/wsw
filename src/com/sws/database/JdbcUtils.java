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
	
	//初始化数据库
	public static void initDataBase()
	{
		//创建数据库
		createDatabase(USER, PASSWORD, DATABASE_NAME);
		//创建数据表
		create_table(USER, PASSWORD, DATABASE_NAME, DB.USER_TABLE_NAME, DB.createUserSQL);
		//创建日志表
		create_table(USER, PASSWORD, DATABASE_NAME, DB.LOG_TABLE_NAME, DB.createLogSQL);
		//创建设备表
		create_table(USER, PASSWORD, DATABASE_NAME, DB.DEVICE_TABLE_NAME, DB.createDeviceSQL);
		//创建班组表
		create_table(USER, PASSWORD, DATABASE_NAME, DB.TEAM_TABLE_NAME, DB.createTeamSQL);
		//创建物料表
		create_table(USER, PASSWORD, DATABASE_NAME, DB.MATERIAL_TABLE_NAME, DB.createMaterialSQL);
		//创建记录表
		create_table(USER, PASSWORD, DATABASE_NAME, DB.RECORD_TABLE_NAME, DB.createRecordSQL);
		
		//插入管理员用户
		if(!SwsQueryDBUtils.userExist("admin"))
		{
			SwsSaveDBUtils.addUser("admin", "admin", 1, "该用户是管理员");
		}
		
		//插入一号设备
		if(!SwsQueryDBUtils.deviceExist("202005130857"))
		{
			SwsSaveDBUtils.addDevice("202005130857", "一号设备", "可用");
		}
	}
	
	/**
	 * 创建数据库
	 * @param user
	 * @param pass
	 * @return
	 */
	public static void createDatabase(String user, String pass,String dataBaseName){
		Statement statement = null;
		Connection conn = null;//声明连接对象
		String driver = "com.mysql.jdbc.Driver";// 驱动程序类名
		String url = "jdbc:mysql://127.0.0.1:3306";
		try
		{
			Class.forName(driver);// 注册(加载)驱动程序
			conn = DriverManager.getConnection(url, user, pass);// 获取数据库连接
			while(conn == null)
			{
				conn = DriverManager.getConnection(url, user, pass);// 获取数据库连接
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
	 * 创建表
	
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
			//创建TABLE
			if(bHasTable == false && createTableSQL != null)
			{
				stmt = conncet_conn.createStatement(); 
				
				String createSql = "create table "+tableName+createTableSQL;
				
				stmt.executeUpdate(createSql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功 
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
	
	//数据库连接
	public static Connection getConnection(String user, String password)
	{

		Connection conn = null;//声明连接对象
		String driver = "com.mysql.jdbc.Driver";// 驱动程序类名
		String url = "jdbc:mysql://127.0.0.1:3306/"+DATABASE_NAME+"?useUnicode=true&characterEncoding=UTF8";// 防止乱码
		try
		{
			Class.forName(driver);// 注册(加载)驱动程序
			conn = DriverManager.getConnection(url, user, password);// 获取数据库连接
		}
		catch (Exception e)
		{
			String errorSTR = "创建数据库表失败:"+url;
			Log.exceptionDialog(e, errorSTR);
		}
		return conn;
	}
	
	/**
     * 释放资源
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
     * 释放连接 Connection
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
        //等待垃圾回收
        conn = null;
    }
    
    /**
     * 释放语句执行者 Statement
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
        //等待垃圾回收
        st = null;
    }
    
    /**
     * 释放结果集 ResultSet
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
        //等待垃圾回收
        rs = null;
    }

}
