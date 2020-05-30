package com.sws.database;

import java.sql.*;

/**
 * 数据库的删除工具类
 * @author Ben
 *
 */
public class SwsDeleteDBUtils {

    /**
     * 根据用户名删除
     * @param username
     * @return
     */
    public static boolean deleteUser(String username)
    {
    	String sql = "delete from "+DB.USER_TABLE_NAME+" where "+DB.USERNAME+" ='"+username+"'";
		
		System.out.println("删除用户sql="+sql);
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
     * 根据班组代码删除班组信息
     * @param teamCode 班组代码
     * @return
     */
    public static boolean deleteTeam(String teamCode)
    {
    	String sql = "delete from "+DB.TEAM_TABLE_NAME+" where "+DB.TEAM_CODE+" = "+teamCode;
		
		System.out.println("删除班组sql="+sql);
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
     * 根据物料代码删除物料信息
     * @param materialCode 物料代码
     * @return
     */
    public static boolean deleteMaterial(String materialCode)
    {
    	String sql = "delete from "+DB.MATERIAL_TABLE_NAME+" where "+DB.MATERIAL_CODE+" = "+materialCode;
		
		System.out.println("删除物料信息sql="+sql);
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
