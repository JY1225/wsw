package com.sws.database;

import java.sql.*;

/**
 * ���ݿ��ɾ��������
 * @author Ben
 *
 */
public class SwsDeleteDBUtils {

    /**
     * �����û���ɾ��
     * @param username
     * @return
     */
    public static boolean deleteUser(String username)
    {
    	String sql = "delete from "+DB.USER_TABLE_NAME+" where "+DB.USERNAME+" ='"+username+"'";
		
		System.out.println("ɾ���û�sql="+sql);
		Connection connection = null;
        Statement statement = null;

        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            int result = statement.executeUpdate(sql);
            return result==1?true:false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(connection, statement,null);
        }
        
        return false;
    }
    
    /**
     * ���ݰ������ɾ��������Ϣ
     * @param teamCode �������
     * @return
     */
    public static boolean deleteTeam(String teamCode)
    {
    	String sql = "delete from "+DB.TEAM_TABLE_NAME+" where "+DB.TEAM_CODE+" = "+teamCode;
		
		System.out.println("ɾ������sql="+sql);
		Connection connection = null;
        Statement statement = null;

        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            int result = statement.executeUpdate(sql);
            return result==1?true:false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(connection, statement,null);
        }
        
        return false;
    }
    
    /**
     * �������ϴ���ɾ��������Ϣ
     * @param materialCode ���ϴ���
     * @return
     */
    public static boolean deleteMaterial(String materialCode)
    {
    	String sql = "delete from "+DB.MATERIAL_TABLE_NAME+" where "+DB.MATERIAL_CODE+" = "+materialCode;
		
		System.out.println("ɾ��������Ϣsql="+sql);
		Connection connection = null;
        Statement statement = null;

        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            int result = statement.executeUpdate(sql);
            return result==1?true:false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(connection, statement,null);
        }
        
        return false;
    }
}
