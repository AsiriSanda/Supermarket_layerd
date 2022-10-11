package dao.custom;

import dao.CrudDAO;
import dto.ItemDTO;
import entity.item;

import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<item, String> {
    boolean ifItemExist(String code) throws SQLException, ClassNotFoundException;

    String generateNewID() throws SQLException, ClassNotFoundException;
}
