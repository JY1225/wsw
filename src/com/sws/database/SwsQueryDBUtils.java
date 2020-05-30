package com.sws.database;

import com.sws.base.Log;
import com.sws.entity.Material;
import com.sws.entity.Record;
import com.sws.entity.SwsLog;
import com.sws.entity.Team;
import com.sws.entity.User;
import com.sws.utils.CommonUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库查询工具类
 * @author Ben
 *
 */
public class SwsQueryDBUtils {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
    public static boolean login(String username, String password)
    {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String SQL = "select ifnull((select 'Y' from "+ DB.USER_TABLE_NAME+" where "+DB.USERNAME+" = '"+username+"' and "+DB.PASSWORD+" = '"+password+"' limit 1), 'N')";
            System.out.println("SqL="+SQL);
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);
            if(resultSet.next())
            {
                String returnValue = resultSet.getString(1);
                System.out.println("returnValue="+returnValue);
                return "Y".equals(returnValue)?true:false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(connection,statement,resultSet);
        }
        return false;
    }

    public static List<SwsLog> queryLog(String startTime, String endTime)
    {
        List<SwsLog> list = new ArrayList<SwsLog>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String SQL = "select * from "+ DB.LOG_TABLE_NAME+" where "+DB.LOG_TIME+" between '"+startTime+"' and '"+endTime+"'";
            System.out.println("SqL="+SQL);
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);
            while(resultSet.next())
            {
                String logEvent = resultSet.getString(DB.LOG_EVENT);
                String logContent = resultSet.getString(DB.LOG_CONTENT);
                String logOperator = resultSet.getString(DB.LOG_OPERATOR);
                String deviceIP = resultSet.getString(DB.DEVICE_IP);
                String logTime = CommonUtils.getTime(resultSet.getTimestamp(DB.LOG_TIME), "yyyy-MM-dd HH:mm:ss");
                SwsLog log = new SwsLog();
                log.setLogEvent(logEvent);
                log.setLogContent(logContent);
                log.setLogOperator(logOperator);
                log.setDeviceIP(deviceIP);
                log.setLogTime(logTime);
                list.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(connection,statement,resultSet);
        }

        return list;
    }

    /**
     * 查询用户是否已存在
     * @param username 用户名
     * @return
     */
    public static boolean userExist(String username)
    {
    	Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String SQL = "select ifnull((select 'Y' from "+ DB.USER_TABLE_NAME+" where "+DB.USERNAME+" = '"+username+"' limit 1), 'N')";
            System.out.println("SqL="+SQL);
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);
            if(resultSet.next())
            {
                String returnValue = resultSet.getString(1);
                System.out.println("returnValue="+returnValue);
                return "Y".equals(returnValue)?true:false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(connection,statement,resultSet);
        }
        return false;
    }
    
    /**
     * 查询设备是否已存在
     * @param username 用户名
     * @return
     */
    public static boolean deviceExist(String deviceNumber)
    {
    	Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String SQL = "select ifnull((select 'Y' from "+ DB.DEVICE_TABLE_NAME+" where "+DB.DEVICE_NUMBER+" = '"+deviceNumber+"' limit 1), 'N')";
            System.out.println("查询设备是否存在 sql="+SQL);
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);
            if(resultSet.next())
            {
                String returnValue = resultSet.getString(1);
                System.out.println("returnValue="+returnValue);
                return "Y".equals(returnValue)?true:false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(connection,statement,resultSet);
        }
        return false;
    }
    
    public static List<User> queryAllUser()
    {
    	List<User> list = new ArrayList<User>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String SQL = "select * from "+ DB.USER_TABLE_NAME;
            System.out.println("sqL="+SQL);
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);
            while(resultSet.next())
            {
            	int id = resultSet.getInt("id");
                String username = resultSet.getString(DB.USERNAME);
                String password = resultSet.getString(DB.PASSWORD);
                int power = resultSet.getInt(DB.POWER);
                String remark = resultSet.getString(DB.REMARK);
               
                User user = new User();
                user.setId(id);
                user.setUsername(username);
                user.setPassword(password);
                user.setPower(power);
                user.setRemark(remark);
               System.out.println(user);
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(connection,statement,resultSet);
        }

        return list;
    }
    
    /**
     * 根据ID来查询用户
     * @param id
     * @return
     */
    public static User queryUserByID(int id)
    {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String SQL = "select * from "+ DB.USER_TABLE_NAME +" where id="+id;
            System.out.println("sqL="+SQL);
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);
            while(resultSet.next())
            {
                String username = resultSet.getString(DB.USERNAME);
                String password = resultSet.getString(DB.PASSWORD);
                int power = resultSet.getInt(DB.POWER);
                String remark = resultSet.getString(DB.REMARK);
               
                User user = new User();
                user.setId(id);
                user.setUsername(username);
                user.setPassword(password);
                user.setPower(power);
                user.setRemark(remark);
               
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(connection,statement,resultSet);
        }

        return null;
    }
    
    /**
     * 查询班组是否已存在
     * @param username 用户名
     * @return
     */
    public static boolean teamExist(String teamCode)
    {
    	Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String SQL = "select ifnull((select 'Y' from "+ DB.TEAM_TABLE_NAME+" where "+DB.TEAM_CODE+" = '"+teamCode+"' limit 1), 'N')";
            System.out.println("查询班组是否存在 sql="+SQL);
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);
            if(resultSet.next())
            {
                String returnValue = resultSet.getString(1);
                System.out.println("returnValue="+returnValue);
                return "Y".equals(returnValue)?true:false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(connection,statement,resultSet);
        }
        return false;
    }
    
    /**
     * 查询物料是否已存在
     * @param materialCode 物料代码
     * @return
     */
    public static boolean materialExist(String materialCode)
    {
    	Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String SQL = "select ifnull((select 'Y' from "+ DB.MATERIAL_TABLE_NAME+" where "+DB.MATERIAL_CODE+" = "+materialCode+" limit 1), 'N')";
            System.out.println("查询班组是否存在 sql="+SQL);
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);
            if(resultSet.next())
            {
                String returnValue = resultSet.getString(1);
                System.out.println("returnValue="+returnValue);
                return "Y".equals(returnValue)?true:false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(connection,statement,resultSet);
        }
        return false;
    }
    
    public static List<Team> queryAllTeam()
    {
    	List<Team> list = new ArrayList<Team>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String SQL = "select * from "+ DB.TEAM_TABLE_NAME;
            System.out.println("查询所有班组信息sql="+SQL);
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);
            while(resultSet.next())
            {
            	int id = resultSet.getInt("id");
                String teamCode = resultSet.getString(DB.TEAM_CODE);
                String teamName = resultSet.getString(DB.TEAM_NAME);
                String teamAlias = resultSet.getString(DB.TEAM_ALIAS);
                String teamClass = resultSet.getString(DB.TEAM_CLASS);
                String produceUnit = resultSet.getString(DB.PRODUCE_UNIT);
                //String modifyTime = resultSet.getString(DB.MODIFY_TIME);
                String modifyTime = CommonUtils.getTime(resultSet.getTimestamp(DB.MODIFY_TIME), "yyyy-MM-dd HH:mm:ss");
                
                Team team = new Team();
                team.setTeamId(id);
                team.setTeamCode(teamCode);
                team.setTeamName(teamName);
                team.setTeamAlias(teamAlias);
                team.setTeamClass(teamClass);
                team.setProduceUnit(produceUnit);
                team.setModifyTime(modifyTime);
                list.add(team);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(connection,statement,resultSet);
        }

        return list;
    }
    
    public static List<Material> queryAllMaterial()
    {
    	List<Material> list = new ArrayList<Material>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String SQL = "select * from "+ DB.MATERIAL_TABLE_NAME;
            System.out.println("查询所有物料信息sql="+SQL);
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);
            while(resultSet.next())
            {
                int materialCode = resultSet.getInt(DB.MATERIAL_CODE);//物料代码
                String materialName = resultSet.getString(DB.MATERIAL_NAME); //物料名称
                String materialAlias = resultSet.getString(DB.MATERIAL_ALIAS); //物料别名
                //String materialSpecifition = resultSet.getString(DB.MATERIAL_SPECIFITION);//物料规格
                String materialDimension = resultSet.getString(DB.MATERIAL_DIMENSION);//物料寸别
                float outerDiameter = resultSet.getFloat(DB.OUTER_DIAMETER);//外径
                float wallThickness = resultSet.getFloat(DB.WALL_THICKNESS);//壁厚
                float length = resultSet.getFloat(DB.MATERIAL_LENGTH);//长度
                float dxxs = resultSet.getFloat(DB.GALVANIZATION_COEFFICIENT);//镀锌系数
                float zz = resultSet.getFloat(DB.BRANCH_WEIGHT);//支重
                int zs = resultSet.getInt(DB.BRANCH_COUNT);//支数
                float jz = resultSet.getFloat(DB.MATERIAL_PIECE_WEIGHT);//件重
                float minWeight = resultSet.getFloat(DB.MINIMAL_WEIGHT);//最小重量
                float maxWeight = resultSet.getFloat(DB.MAXIMUM_WEIGHT);//最大重量
                String isMatch = resultSet.getString(DB.IS_MATCH);//是否匹配
                //String produceDate = resultSet.getString(DB.PRODUCE_DATE);//生产日期
                //String modifyTime = resultSet.getString(DB.MODIFY_TIME);//修改时间
                String produceDate = CommonUtils.getTime(resultSet.getDate(DB.PRODUCE_DATE), "yyyy-MM-dd");//生产日期
                String modifyTime = CommonUtils.getTime(resultSet.getTimestamp(DB.MODIFY_TIME), "yyyy-MM-dd HH:mm:ss");//修改时间
                
                Material material = new Material();
                material.setMaterialCode(materialCode);
                material.setMaterialName(materialName);
                material.setMaterialAlias(materialAlias);
                //material.setMaterialSpecifition(materialSpecifition);
                material.setMaterialDimension(materialDimension);
                material.setOuterDiameter(outerDiameter);
                material.setWallThickness(wallThickness);
                material.setLength(length);
                material.setDxxs(dxxs);
                material.setZz(zz);
                material.setZs(zs);
                material.setPieceWeight(jz);
                material.setMinWeight(minWeight);
                material.setMaxWeight(maxWeight);
                material.setIsMatch(isMatch);
                material.setProduceDate(produceDate);
                material.setModifyTime(modifyTime);
                list.add(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(connection,statement,resultSet);
        }

        return list;
    }
    
    /**
     * 根据班组代码查询班组信息
     * @param teamCode 班组代码
     * @return
     */
    public static Team queryTeamByCode(String teamCode)
    {
    	List<Team> list = new ArrayList<Team>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String SQL = "select * from "+ DB.TEAM_TABLE_NAME+" where "+DB.TEAM_CODE+" = "+teamCode;
            System.out.println("查询班组信息sql="+SQL);
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);
            while(resultSet.next())
            {
                //String teamCode = resultSet.getString(DB.TEAM_CODE);
                String teamName = resultSet.getString(DB.TEAM_NAME);
                String teamAlias = resultSet.getString(DB.TEAM_ALIAS);
                String teamClass = resultSet.getString(DB.TEAM_CLASS);
                String produceUnit = resultSet.getString(DB.PRODUCE_UNIT);
                //String modifyTime = resultSet.getString(DB.MODIFY_TIME);
                String modifyTime = CommonUtils.getTime(resultSet.getTimestamp(DB.MODIFY_TIME), "yyyy-MM-dd HH:mm:ss");
                
                Team team = new Team();
                //team.setTeamId(id);
                team.setTeamCode(teamCode);
                team.setTeamName(teamName);
                team.setTeamAlias(teamAlias);
                team.setTeamClass(teamClass);
                team.setProduceUnit(produceUnit);
                team.setModifyTime(modifyTime);

                return team;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(connection,statement,resultSet);
        }

        return null;
    }
    
    public static Material queryMaterialByCode(int materialCode)
    {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String SQL = "select * from "+ DB.MATERIAL_TABLE_NAME+" where "+DB.MATERIAL_CODE+" = "+materialCode;
            System.out.println("查询物料信息sql="+SQL);
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);
            while(resultSet.next())
            {
                String materialName = resultSet.getString(DB.MATERIAL_NAME); //物料名称
                String materialAlias = resultSet.getString(DB.MATERIAL_ALIAS); //物料别名
                //String materialSpecifition = resultSet.getString(DB.MATERIAL_SPECIFITION);//物料规格
                String materialDimension = resultSet.getString(DB.MATERIAL_DIMENSION);//物料寸别
                float outerDiameter = resultSet.getFloat(DB.OUTER_DIAMETER);//外径
                float wallThickness = resultSet.getFloat(DB.WALL_THICKNESS);//壁厚
                float length = resultSet.getFloat(DB.MATERIAL_LENGTH);//长度
                float dxxs = resultSet.getFloat(DB.GALVANIZATION_COEFFICIENT);//镀锌系数
                float zz = resultSet.getFloat(DB.BRANCH_WEIGHT);//支重
                int zs = resultSet.getInt(DB.BRANCH_COUNT);//支数
                float jz = resultSet.getFloat(DB.MATERIAL_PIECE_WEIGHT);//件重
                float minWeight = resultSet.getFloat(DB.MINIMAL_WEIGHT);//最小重量
                float maxWeight = resultSet.getFloat(DB.MAXIMUM_WEIGHT);//最大重量
                String isMatch = resultSet.getString(DB.IS_MATCH);//是否匹配
                String produceDate = CommonUtils.getTime(resultSet.getDate(DB.PRODUCE_DATE), "yyyy-MM-dd");//生产日期
               // String modifyTime = resultSet.getString(DB.MODIFY_TIME);//修改时间
                String modifyTime = CommonUtils.getTime(resultSet.getTimestamp(DB.MODIFY_TIME), "yyyy-MM-dd HH:mm:ss");//修改时间
                
                Material material = new Material();
                material.setMaterialCode(materialCode);
                material.setMaterialName(materialName);
                material.setMaterialAlias(materialAlias);
               // material.setMaterialSpecifition(materialSpecifition);
                material.setMaterialDimension(materialDimension);
                material.setOuterDiameter(outerDiameter);
                material.setWallThickness(wallThickness);
                material.setLength(length);
                material.setDxxs(dxxs);
                material.setZz(zz);
                material.setZs(zs);
                material.setPieceWeight(jz);
                material.setMinWeight(minWeight);
                material.setMaxWeight(maxWeight);
                material.setIsMatch(isMatch);
                material.setProduceDate(produceDate);
                material.setModifyTime(modifyTime);

                return material;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(connection,statement,resultSet);
        }

        return null;
    }
    
    public static Material queryMaterialByName(String materialName)
    {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String SQL = "select * from "+ DB.MATERIAL_TABLE_NAME+" where "+DB.MATERIAL_NAME+" = '"+materialName+"'";
            System.out.println("根据物料名称查询物料信息sql="+SQL);
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);
            while(resultSet.next())
            {
                int materialCode = resultSet.getInt(DB.MATERIAL_CODE); //物料名称
                String materialAlias = resultSet.getString(DB.MATERIAL_ALIAS); //物料别名
                //String materialSpecifition = resultSet.getString(DB.MATERIAL_SPECIFITION);//物料规格
                String materialDimension = resultSet.getString(DB.MATERIAL_DIMENSION);//物料寸别
                float outerDiameter = resultSet.getFloat(DB.OUTER_DIAMETER);//外径
                float wallThickness = resultSet.getFloat(DB.WALL_THICKNESS);//壁厚
                float length = resultSet.getFloat(DB.MATERIAL_LENGTH);//长度
                float dxxs = resultSet.getFloat(DB.GALVANIZATION_COEFFICIENT);//镀锌系数
                float zz = resultSet.getFloat(DB.BRANCH_WEIGHT);//支重
                int zs = resultSet.getInt(DB.BRANCH_COUNT);//支数
                float jz = resultSet.getFloat(DB.MATERIAL_PIECE_WEIGHT);//件重
                float minWeight = resultSet.getFloat(DB.MINIMAL_WEIGHT);//最小重量
                float maxWeight = resultSet.getFloat(DB.MAXIMUM_WEIGHT);//最大重量
                String isMatch = resultSet.getString(DB.IS_MATCH);//是否匹配
                String produceDate = CommonUtils.getTime(resultSet.getDate(DB.PRODUCE_DATE), "yyyy-MM-dd");//生产日期
               // String modifyTime = resultSet.getString(DB.MODIFY_TIME);//修改时间
                String modifyTime = CommonUtils.getTime(resultSet.getTimestamp(DB.MODIFY_TIME), "yyyy-MM-dd HH:mm:ss");//修改时间
                
                Material material = new Material();
                material.setMaterialCode(materialCode);
                material.setMaterialName(materialName);
                material.setMaterialAlias(materialAlias);
               // material.setMaterialSpecifition(materialSpecifition);
                material.setMaterialDimension(materialDimension);
                material.setOuterDiameter(outerDiameter);
                material.setWallThickness(wallThickness);
                material.setLength(length);
                material.setDxxs(dxxs);
                material.setZz(zz);
                material.setZs(zs);
                material.setPieceWeight(jz);
                material.setMinWeight(minWeight);
                material.setMaxWeight(maxWeight);
                material.setIsMatch(isMatch);
                material.setProduceDate(produceDate);
                material.setModifyTime(modifyTime);

                return material;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(connection,statement,resultSet);
        }

        return null;
    }
    
    public static Material queryMaterialByCun(String materialCun)
    {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String SQL = "select * from "+ DB.MATERIAL_TABLE_NAME+" where "+DB.MATERIAL_DIMENSION+" = '"+materialCun+"'";
            System.out.println("根据物料寸别查询物料信息sql="+SQL);
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);
            while(resultSet.next())
            {
                int materialCode = resultSet.getInt(DB.MATERIAL_CODE); //物料代码
                String materialAlias = resultSet.getString(DB.MATERIAL_ALIAS); //物料别名
                //String materialSpecifition = resultSet.getString(DB.MATERIAL_SPECIFITION);//物料规格
                String materialName= resultSet.getString(DB.MATERIAL_NAME);//物料名称
                float outerDiameter = resultSet.getFloat(DB.OUTER_DIAMETER);//外径
                float wallThickness = resultSet.getFloat(DB.WALL_THICKNESS);//壁厚
                float length = resultSet.getFloat(DB.MATERIAL_LENGTH);//长度
                float dxxs = resultSet.getFloat(DB.GALVANIZATION_COEFFICIENT);//镀锌系数
                float zz = resultSet.getFloat(DB.BRANCH_WEIGHT);//支重
                int zs = resultSet.getInt(DB.BRANCH_COUNT);//支数
                float jz = resultSet.getFloat(DB.MATERIAL_PIECE_WEIGHT);//件重
                float minWeight = resultSet.getFloat(DB.MINIMAL_WEIGHT);//最小重量
                float maxWeight = resultSet.getFloat(DB.MAXIMUM_WEIGHT);//最大重量
                String isMatch = resultSet.getString(DB.IS_MATCH);//是否匹配
                String produceDate = CommonUtils.getTime(resultSet.getDate(DB.PRODUCE_DATE), "yyyy-MM-dd");//生产日期
               // String modifyTime = resultSet.getString(DB.MODIFY_TIME);//修改时间
                String modifyTime = CommonUtils.getTime(resultSet.getTimestamp(DB.MODIFY_TIME), "yyyy-MM-dd HH:mm:ss");//修改时间
                
                Material material = new Material();
                material.setMaterialCode(materialCode);
                material.setMaterialName(materialName);
                material.setMaterialAlias(materialAlias);
               // material.setMaterialSpecifition(materialSpecifition);
                material.setMaterialDimension(materialCun);
                material.setOuterDiameter(outerDiameter);
                material.setWallThickness(wallThickness);
                material.setLength(length);
                material.setDxxs(dxxs);
                material.setZz(zz);
                material.setZs(zs);
                material.setPieceWeight(jz);
                material.setMinWeight(minWeight);
                material.setMaxWeight(maxWeight);
                material.setIsMatch(isMatch);
                material.setProduceDate(produceDate);
                material.setModifyTime(modifyTime);

                return material;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(connection,statement,resultSet);
        }

        return null;
    }
    
    public static List<Record> queryRecordByCondition(String querySQL)
    {
    	List<Record> list = new ArrayList<Record>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String SQL = "select * from "+ DB.RECORD_TABLE_NAME+querySQL;
            System.out.println("查询班组信息sql="+SQL);
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);
            while(resultSet.next())
            {
            	System.out.println("查询到数据====");
                String teamCode = resultSet.getString(DB.TEAM_CODE);//班组代码
                String teamName = resultSet.getString(DB.TEAM_NAME);//班组名称
                //String teamAlias = resultSet.getString(DB.TEAM_ALIAS);//班组别名
                String teamClass = resultSet.getString(DB.TEAM_CLASS);//班别
                String produceUnit = resultSet.getString(DB.PRODUCE_UNIT);//生产机组
                //String modifyTime = resultSet.getString(DB.MODIFY_TIME);
                
                int materialCode = resultSet.getInt(DB.MATERIAL_CODE);//物料代码
                String materialName = resultSet.getString(DB.MATERIAL_NAME);//物料名称
                //String materialAlias = resultSet.getString(DB.MATERIAL_ALIAS);//物料别名
                //String materialSpecifition = resultSet.getString(DB.MATERIAL_SPECIFITION);
                String materialDimension = resultSet.getString(DB.MATERIAL_DIMENSION);//寸别
                //String weightTime = resultSet.getString(DB.WEIGHING_TIME);
                String weightTime = CommonUtils.getTime(resultSet.getTimestamp(DB.WEIGHING_TIME), "yyyy-MM-dd HH:mm:ss");//称重时间
                
                float outerDiameter = resultSet.getFloat(DB.OUTER_DIAMETER);//外径
                float wallThickness = resultSet.getFloat(DB.WALL_THICKNESS);//壁厚
                float length = resultSet.getFloat(DB.MATERIAL_LENGTH);//长度
                float dxxs = resultSet.getFloat(DB.GALVANIZATION_COEFFICIENT);//镀锌系数
                //float zz = resultSet.getFloat(DB.BRANCH_WEIGHT);//支重
                //float zs = resultSet.getFloat(DB.MATERIAL_LENGTH);
                int zs = resultSet.getInt(DB.BRANCH_COUNT);
                //float pieceWeight = resultSet.getFloat(DB.MATERIAL_PIECE_WEIGHT);
                float materialWeight = resultSet.getFloat(DB.MATERIAL_WEIGHT);//物料重量
                float minWeight = resultSet.getFloat(DB.MINIMAL_WEIGHT);
                float maxWeight = resultSet.getFloat(DB.MAXIMUM_WEIGHT);
                //码单序号
                String listSerialNumber = resultSet.getString(DB.CODE_LIST_SERIAL_NUMBER);
                //记录编号
                String recordNumber = resultSet.getString(DB.RECORD_NUMBER);
                String deviceName = resultSet.getString(DB.DEVICE_NAME);
                String deviceNumber = resultSet.getString(DB.DEVICE_NUMBER);
                //String produceDate = resultSet.getString(DB.PRODUCE_DATE);
                String produceDate = CommonUtils.getTime(resultSet.getDate(DB.PRODUCE_DATE), "yyyy-MM-dd");//生产日期
                //称重人
                String weigher = resultSet.getString(DB.WEIGHER);
                //操作员
                String operator = resultSet.getString(DB.OPERATOR);
                //备注
                String remark = resultSet.getString(DB.REMARK);
                
                Record record = new Record();
                
                Team team = new Team();
                team.setTeamCode(teamCode);
                team.setTeamName(teamName);
                //team.setTeamAlias(teamAlias);
                team.setTeamClass(teamClass);
                team.setProduceUnit(produceUnit);
                
                record.setTeam(team);
                
                Material material = new Material();
                material.setMaterialCode(materialCode);
                material.setMaterialName(materialName);
                //material.setMaterialAlias(materialAlias);
                //material.setMaterialSpecifition(materialSpecifition);
                material.setMaterialDimension(materialDimension);
                material.setOuterDiameter(outerDiameter);
                material.setWallThickness(wallThickness);
                material.setLength(length);
                material.setDxxs(dxxs);
                //material.setZz(zz);
                material.setZs(zs);
               // material.setPieceWeight(pieceWeight);
                material.setWeight(materialWeight);
                material.setMinWeight(minWeight);
                material.setMaxWeight(maxWeight);
                record.setMaterial(material);
                
                record.setWeighingTime(weightTime);
                record.setCodeListSerialNumber(listSerialNumber);
                record.setDeviceName(deviceName);
                record.setDeviceNumber(deviceNumber);
                record.setRecordNumber(recordNumber);
                record.setProduceDate(produceDate);
                record.setWeigher(weigher);
                record.setOperator(operator);
                record.setRemark(remark);
                
                
                list.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.exception(e);
        } finally {
            JdbcUtils.closeResource(connection,statement,resultSet);
        }

        return list;
    }
    
    public static List<String> queryMaterialName()
    {
    	List<String> list = new ArrayList<String>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String SQL = "select Distinct("+DB.MATERIAL_NAME+") from "+ DB.MATERIAL_TABLE_NAME;
            System.out.println("查询物料名称 sql="+SQL);
            connection = JdbcUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);
            while(resultSet.next())
            {
                String name = resultSet.getString(1);
                System.out.println("name="+name);
                list.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(connection,statement,resultSet);
        }

        return list;
    }
}
