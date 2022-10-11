package dao;

import db.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {
    private static PreparedStatement getPreparedStatement(String sql, Object... args) throws SQLException, ClassNotFoundException {
        Connection con = DBUtil.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            pstm.setObject(i+1, args[i]);
        }

        return pstm;
    }

    public static boolean executeUpdate(String sql, Object... args) throws SQLException, ClassNotFoundException {
        return getPreparedStatement(sql, args).executeUpdate()>0;
    }

    public static ResultSet executeQuery(String sql, Object... args) throws SQLException, ClassNotFoundException {
        return getPreparedStatement(sql, args).executeQuery();

    }

}