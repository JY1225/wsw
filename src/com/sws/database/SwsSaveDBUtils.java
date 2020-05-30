package com.sws.database;

import java.sql.*;

import com.sws.entity.Material;
import com.sws.entity.Record;
import com.sws.entity.Team;

/**
 * ���ݿ���뱣�湤����
 * @author Ben
 *
 */
public class SwsSaveDBUtils {

	/**
	 * ������־
	 * @param event
	 * @param content
	 * @param operator
	 * @param time
	 * @param ip
	 */
    public static void saveLog(String event, String content, String operator, String time, String ip)
    {
        String sql = "insert into "+DB.LOG_TABLE_NAME+"("
                +DB.LOG_EVENT+","
                +DB.LOG_CONTENT+","
                +DB.LOG_OPERATOR+","
                +DB.DEVICE_IP+","
                +DB.LOG_TIME+") values ("
                +"'"+event+"',"
                +"'"+content+"',"
                +"'"+operator+"',"
                +"'"+ip+"',"
                +"'"+time+"')";
        Connection connection = null;
        Statement statement = null;

        try {
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(connection, statement,null);
        }
    }
    
    /**
     * ����û�
     * @param username
     * @param password
     * @param power
     * @param remark
     * @return
     */
    public static boolean addUser(String username, String password, int power, String remark)
    {
    	 String sql = "insert into "+DB.USER_TABLE_NAME+"("
                 +DB.USERNAME+","
                 +DB.PASSWORD+","
                 +DB.POWER+","
                 +DB.REMARK+") values ("
                 +"'"+username+"',"
                 +"'"+password+"',"
                 +""+power+","
                 +"'"+remark+"')";
    	 
    	 System.out.println("����û� sql="+sql);
    	 
         Connection connection = null;
         Statement statement = null;

         try {
             connection = JdbcUtils.getConnection();
             statement = connection.createStatement();
             int result = statement.executeUpdate(sql);
             return result == 1?true:false;
         } catch (SQLException e) {
             e.printStackTrace();
         } finally {
             JdbcUtils.closeResource(connection, statement,null);
         }
         
         return false;
    }
    
    /**
     * ����豸
     * @param deviceNumber �豸���
     * @param deviceName   �豸����
     * @param deviceState  �豸״̬
     * @return
     */
    public static boolean addDevice(String deviceNumber, String deviceName, String deviceState)
    {
    	 String sql = "insert into "+DB.DEVICE_TABLE_NAME+"("
                 +DB.DEVICE_NUMBER+","
                 +DB.DEVICE_NAME+","
                 +DB.DEVICE_STATE+") values ("
                 +"'"+deviceNumber+"',"
                 +"'"+deviceName+"',"
                 +"'"+deviceState+"')";
    	 
    	 System.out.println("����豸 sql="+sql);
    	 
         Connection connection = null;
         Statement statement = null;

         try {
             connection = JdbcUtils.getConnection();
             statement = connection.createStatement();
             int result = statement.executeUpdate(sql);
             return result == 1?true:false;
         } catch (SQLException e) {
             e.printStackTrace();
         } finally {
             JdbcUtils.closeResource(connection, statement,null);
         }
         
         return false;
    }
    
    /**
     * ��Ӱ���
     * @param deviceNumber �豸���
     * @param deviceName   �豸����
     * @param deviceState  �豸״̬
     * @return
     */
    public static boolean addTeam(Team team)
    {
    	 String sql = "insert into "+DB.TEAM_TABLE_NAME+"("
                 +DB.TEAM_CODE+","
                 +DB.TEAM_NAME+","
                 +DB.TEAM_ALIAS+","
                 +DB.TEAM_CLASS+","
                 +DB.PRODUCE_UNIT+","
                 +DB.MODIFY_TIME+") values ("
                 +"'"+team.getTeamCode()+"',"
                 +"'"+team.getTeamName()+"',"
                 +"'"+team.getTeamAlias()+"',"
                 +"'"+team.getTeamClass()+"',"
                 +"'"+team.getProduceUnit()+"',"
                 +"'"+team.getModifyTime()+"')";
    	 
    	 System.out.println("����豸 sql="+sql);
    	 
         Connection connection = null;
         Statement statement = null;

         try {
             connection = JdbcUtils.getConnection();
             statement = connection.createStatement();
             int result = statement.executeUpdate(sql);
             return result == 1?true:false;
         } catch (SQLException e) {
             e.printStackTrace();
         } finally {
             JdbcUtils.closeResource(connection, statement,null);
         }
         
         return false;
    }
    
    /**
     * �������
     * @param deviceNumber �豸���
     * @param deviceName   �豸����
     * @param deviceState  �豸״̬
     * @return
     */
    public static boolean addMaterial(Material material)
    {
    	 String sql = "insert into "+DB.MATERIAL_TABLE_NAME+"("
                 +DB.MATERIAL_CODE+","
                 +DB.MATERIAL_NAME+","
                 +DB.MATERIAL_ALIAS+","
                 //+DB.MATERIAL_SPECIFITION+","
                 +DB.MATERIAL_DIMENSION+","
                 +DB.OUTER_DIAMETER+","
                 +DB.WALL_THICKNESS+","
                 +DB.MATERIAL_LENGTH+","
                 +DB.GALVANIZATION_COEFFICIENT+","
                 +DB.BRANCH_WEIGHT+","
                 +DB.BRANCH_COUNT+","
                 +DB.MATERIAL_PIECE_WEIGHT+","
                 +DB.MINIMAL_WEIGHT+","
                 +DB.MAXIMUM_WEIGHT+","
                 +DB.PRODUCE_DATE+","
                 +DB.IS_MATCH+","
                 +DB.MODIFY_TIME+") values ("
                 +"'"+material.getMaterialCode()+"',"
                 +"'"+material.getMaterialName()+"',"
                 +"'"+material.getMaterialAlias()+"',"
                 //+"'"+material.getMaterialSpecifition()+"',"
                 +"'"+material.getMaterialDimension()+"',"
                 +""+material.getOuterDiameter()+","
                 +""+material.getWallThickness()+","
                 +""+material.getLength()+","
                 +""+material.getDxxs()+","
                 +""+material.getZz()+","
                 +""+material.getZs()+","
                 +""+material.getPieceWeight()+","
                 +""+material.getMinWeight()+","
                 +""+material.getMaxWeight()+","
                 +"'"+material.getProduceDate()+"',"
                 +"'"+material.getIsMatch()+"',"
                 +"'"+material.getModifyTime()+"')";
    	 
    	 System.out.println("���������Ϣ sql="+sql);
    	 
         Connection connection = null;
         Statement statement = null;

         try {
             connection = JdbcUtils.getConnection();
             statement = connection.createStatement();
             int result = statement.executeUpdate(sql);
             return result == 1?true:false;
         } catch (SQLException e) {
             e.printStackTrace();
         } finally {
             JdbcUtils.closeResource(connection, statement,null);
         }
         
         return false;
    }
    
    /**
     * ���������¼
     * @param deviceNumber �豸���
     * @param deviceName   �豸����
     * @param deviceState  �豸״̬
     * @return
     */
    public static boolean addRecord(Record record)
    {
    	 String sql = "insert into "+DB.RECORD_TABLE_NAME+"("
                 +DB.RECORD_NUMBER+","
                 +DB.CODE_LIST_SERIAL_NUMBER+","
                 //+DB.CATEGORY+","
                 +DB.MATERIAL_QUALITY+","
                 +DB.PIECE_COUNT+","
                 +DB.BRANCH_COUNT+","
                 +DB.MATERIAL_WEIGHT+","
                 +DB.IN_OUT_STOCK+","
                 +DB.OPERATOR+","
                 +DB.WEIGHING_TIME+","
                 +DB.WEIGHER+","
                 +DB.MATERIAL_STATE+","
                 +DB.NOZZLE_STATE+","
                 +DB.MEASURE_WAY+") values ("
                 +"'"+record.getRecordNumber()+"',"
                 +"'"+record.getCodeListSerialNumber()+"',"
                 //+"'"+record.getc+"',"
                 +"'"+record.getMaterial().getMaterialQuality()+"',"
                 +"'"+record.getMaterial().getJs()+"',"
                 +"'"+record.getMaterial().getZs()+"',"
                 + "'"+record.getMaterial().getWeight()+"',"
                 + "'���',"
                 +"'"+record.getOperator()+"',"
                 +"'"+record.getWeighingTime()+"',"
                 + "'"+record.getWeigher()+"',"
                 +"'"+record.getMateralState()+"',"
                 +"'"+record.getMaterial().getNozzleState()+"',"
                 +"'"+record.getMaterial().getMeasureWay()+"')";
    	 
    	 System.out.println("����豸 sql="+sql);
    	 
         Connection connection = null;
         Statement statement = null;

         try {
             connection = JdbcUtils.getConnection();
             statement = connection.createStatement();
             int result = statement.executeUpdate(sql);
             return result == 1?true:false;
         } catch (SQLException e) {
             e.printStackTrace();
         } finally {
             JdbcUtils.closeResource(connection, statement,null);
         }
         
         return false;
    }
}
