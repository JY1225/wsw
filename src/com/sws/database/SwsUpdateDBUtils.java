package com.sws.database;

import java.sql.*;

import com.sws.entity.Material;
import com.sws.entity.Team;

/**
 * ���ݿ���޸Ĺ�����
 * @author Ben
 *
 */
public class SwsUpdateDBUtils {

	/**
	 * �޸��û�����
	 * @param username  �û���
	 * @param originalPassword  ԭ����
	 * @param newPassword  ������
	 * @return true��ʾ�ɹ���false��ʾʧ��
	 */
	public static boolean updatePassword(String username, String originalPassword, String newPassword)
	{
		String sql = "update "+DB.USER_TABLE_NAME+" set "
				+DB.PASSWORD+" ='"+newPassword+"' where "
				+DB.USERNAME+" ='"+username+"' and "
				+DB.PASSWORD+" ='"+originalPassword+"'";
		
		System.out.println("�޸��û�����sql="+sql);
		return updateDatabae(sql);
	}
   
	/**
	 * �޸��û�Ȩ��
	 * @param username
	 * @param power
	 * @return
	 */
	public static boolean updateUserPower(String username, int power)
	{
		String sql = "update "+DB.USER_TABLE_NAME+" set "
				+DB.POWER+" ='"+power+"' where "
				+DB.USERNAME+" ='"+username+"'";
		
		System.out.println("�޸��û�Ȩ��sql="+sql);
		return updateDatabae(sql);
	}
	
	public static boolean updateTeam(Team team)
	{
		String sql = "update "+DB.TEAM_TABLE_NAME+" set "
				//+DB.TEAM_CODE+" ='"+team.getTeamCode()+"' and "
				+DB.TEAM_NAME+" ='"+team.getTeamName()+"', "
				+DB.TEAM_ALIAS+" ='"+team.getTeamAlias()+"', "
				+DB.TEAM_CLASS+" ='"+team.getTeamClass()+"', "
				+DB.PRODUCE_UNIT+" ='"+team.getProduceUnit()+"', "
				+DB.MODIFY_TIME+" ='"+team.getModifyTime()+"' where "
				+DB.TEAM_CODE+" ="+team.getTeamCode();
		
		System.out.println("�޸��û�Ȩ��sql="+sql);
		return updateDatabae(sql);
	}
	
	public static boolean updateMaterial(Material material)
	{
		String sql = "update "+DB.MATERIAL_TABLE_NAME+" set "
				+DB.MATERIAL_NAME+" ='"+material.getMaterialName()+"', "
				+DB.MATERIAL_ALIAS+" ='"+material.getMaterialAlias()+"', "
				//+DB.MATERIAL_SPECIFITION+" ='"+material.getMaterialSpecifition()+"', "
				+DB.MATERIAL_DIMENSION+" ='"+material.getMaterialDimension()+"', "
				+DB.OUTER_DIAMETER+" ="+material.getOuterDiameter()+", "
				+DB.WALL_THICKNESS+" ="+material.getWallThickness()+", "
				+DB.MATERIAL_LENGTH+" ="+material.getLength()+", "
				+DB.GALVANIZATION_COEFFICIENT+" ="+material.getDxxs()+", "
		        +DB.BRANCH_WEIGHT+" ="+material.getZz()+", "
				+DB.BRANCH_COUNT+" ="+material.getZs()+", "
				+DB.MATERIAL_PIECE_WEIGHT+" ="+material.getPieceWeight()+", "
				+DB.MINIMAL_WEIGHT+" ="+material.getMinWeight()+", "
				+DB.MAXIMUM_WEIGHT+" ="+material.getMaxWeight()+", "
				+DB.PRODUCE_DATE+" ='"+material.getProduceDate()+"', "
				+DB.IS_MATCH+" ='"+material.getIsMatch()+"', "
				+DB.MODIFY_TIME+" ='"+material.getModifyTime()+"' where "
				+DB.MATERIAL_CODE+" ="+material.getMaterialCode();
		
		System.out.println("�޸�������Ϣsql="+sql);
		return updateDatabae(sql);
	}
	
	private static boolean updateDatabae(String sql)
	{
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
