package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDAO;
import dto.OrderDTO;
import entity.orders;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderDAOImpl  implements OrderDAO {

    @Override
    public boolean add(orders dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `Orders` (oid, date, customerID) VALUES (?,?,?)", dto.getOid(),dto.getDate(),dto.getCustomerID());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported");
    }

    @Override
    public boolean update(orders orderDTO) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported");
    }

    @Override
    public orders search(String oid) throws SQLException, ClassNotFoundException {

        ResultSet rst = CrudUtil.executeQuery("SELECT oid FROM `Orders` WHERE oid=?", oid);
        rst.next();
        return new orders(rst.getString("oid"), LocalDate.parse(rst.getString("date")), rst.getString("customerID"));
    }

    @Override
    public ArrayList<orders> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean ifOrderExist(String oid) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT oid FROM `Orders` WHERE oid=?", oid);
        /*if order id already exist*/
        return rst.next();
    }

    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {

        ResultSet rst = CrudUtil.executeQuery("SELECT oid FROM `Orders` ORDER BY oid DESC LIMIT 1;");
        return rst.next() ? String.format("OD%03d", (Integer.parseInt(rst.getString("oid").replace("OD", "")) + 1)) : "OD001";
    }
}
