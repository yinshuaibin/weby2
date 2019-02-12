package com.ferret.dao;

import com.ferret.bean.ImageFaceInfo;
import com.ferret.bean.FeatureData;
import com.ferret.utils.sqlite.SqliteDataSourceUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.sqlite.SQLiteConnection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cc;
 * @since 2018/4/15;
 */
@Repository
public class SqliteDao {


    private static String paramString = "featureId,faceNumber,confidence,eyeL,eyeR,mouth,nose,pitch,quality,rect,roll,yaw,imagePath," +
            "featurePosition";
    private static String[] params = paramString.split(",");


    private SqliteDataSourceUtils sqliteDataSourceUtils = new SqliteDataSourceUtils();

    public void insertBatch(List<ImageFaceInfo> faceInfo, String dbPath) throws NoSuchFieldException, IllegalAccessException {
        if (StringUtils.isBlank(dbPath)) {
            return;
        }
        SQLiteConnection conn = (SQLiteConnection) sqliteDataSourceUtils.connect(dbPath);
        PreparedStatement ps = null;
        String createSql = "DROP TABLE IF EXISTS header;" +
                "DROP TABLE IF EXISTS body;" +
                "CREATE TABLE IF NOT EXISTS header(fileType varchar(8), version varchar(8), count int(8), directory varchar(150));" +
                "CREATE TABLE body (" +
                "  featureId int(16) DEFAULT NULL ," +
                "  faceNumber int(2) DEFAULT NULL ," +

                "  confidence int(3) DEFAULT NULL," +
                "  eyeL varchar(10) DEFAULT NULL," +
                "  eyeR varchar(10) DEFAULT NULL," +
                "  mouth varchar(10) DEFAULT NULL," +
                "  nose varchar(10) DEFAULT NULL," +

                "  pitch varchar(10) DEFAULT NULL," +
                "  quality int(3) DEFAULT NULL," +
                "  rect varchar(100) DEFAULT NULL," +

                "  roll int(3) DEFAULT NULL ," +
                "  yaw int(3) DEFAULT NULL," +
                "  imagePath varchar(255) DEFAULT NULL," +
                "  featurePosition int(5) DEFAULT NULL" +
                "  );";
        try {
            conn.createStatement().executeUpdate(createSql);

            conn.setAutoCommit(false);
            String header = "INSERT INTO header(fileType,version,count,directory) VALUES (?,?,?,?)";
            ps = conn.prepareStatement(header);
            ps.setString(1, FeatureData.DB_TYPE);
            ps.setString(2, FeatureData.VERSION);
            ps.setInt(3, faceInfo.size());
            ps.setString(4, null);
            ps.executeUpdate();

            String body = "INSERT INTO body(" + paramString + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(body);
            for (int i = 0; i < faceInfo.size(); i++) {
                ImageFaceInfo f = faceInfo.get(i);
                for (int j = 0; j < params.length; j++) {
                    Field field = ImageFaceInfo.class.getDeclaredField(params[j]);
                    field.setAccessible(true);
                    ps.setObject(j + 1, field.get(f));
                    field.setAccessible(false);
                }
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<ImageFaceInfo> getFaceInfos(String path) throws InvocationTargetException, IllegalAccessException {
        if (StringUtils.isBlank(path)) {
            return null;
        }
        List<ImageFaceInfo> list = new ArrayList<>();
        SQLiteConnection conn = (SQLiteConnection) sqliteDataSourceUtils.connect(path);
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT "+paramString+" FROM body";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ImageFaceInfo info = new ImageFaceInfo();
                for (int i = 0; i < params.length ; i++) {
                    BeanUtils.setProperty(info, params[i], rs.getObject(i+1));
                }
                list.add(info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 是否满足1w条记录一个包
     * @return 有1万条记录则返回true,否则false
     */
    public boolean onePackWithinTenThousandRecords(String path){
        if (StringUtils.isBlank(path)) {
            return false;
        }
        boolean flag = false;
        SQLiteConnection conn = (SQLiteConnection) sqliteDataSourceUtils.connect(path);
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM header");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                if (rs.getInt("count") == 10000){
                    flag = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
