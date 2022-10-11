package dao.custom;

import dao.CrudDAO;
import dto.OrderDTO;
import entity.orders;

import java.sql.SQLException;

public interface OrderDAO extends CrudDAO<orders, String> {
    boolean ifOrderExist(String oid) throws SQLException, ClassNotFoundException;
    String generateNewOrderId() throws SQLException, ClassNotFoundException;
}
