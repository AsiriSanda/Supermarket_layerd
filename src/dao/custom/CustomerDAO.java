package dao.custom;

import dao.CrudDAO;
import dto.CustomerDTO;
import entity.customer;

import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<customer, String> {

    boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException;

    String generateNewId() throws SQLException, ClassNotFoundException;

}
