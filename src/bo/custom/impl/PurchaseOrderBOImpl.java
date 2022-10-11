package bo.custom.impl;

import bo.custom.PurchaseOrderBO;
import dao.DAOFactory;
import dao.custom.*;
import db.DBUtil;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import entity.customer;
import entity.item;
import entity.orderdetails;
import entity.orders;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {
    private CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);
    private QueryDAO queryDAO = (QueryDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.QUERYDAO);


    @Override
    public boolean purchaseOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {

        /*Transaction*/
        Connection connection = null;
            connection = DBUtil.getInstance().getConnection();
            boolean orderAvailable = orderDAO.ifOrderExist(dto.getOrderId());
            /*if order id already exist*/
            if (orderAvailable) {
                return false;

            }

            connection.setAutoCommit(false);
            /*add order*/


        orders order = new orders(dto.getOrderId(), dto.getOrderDate(), dto.getCustomerId());
        boolean orderAdded = orderDAO.add(order);

            if (!orderAdded) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }




            for (OrderDetailDTO detail : dto.getOrderDetail()) {

                orderdetails orderDetailDTO = new orderdetails(dto.getOrderId(), detail.getItemCode(), detail.getQty(), detail.getUnitPrice());
                boolean orderDetailsAdded = orderDetailsDAO.add(orderDetailDTO);

                if (!orderDetailsAdded) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }

//                //Search & Update Item
                item search = itemDAO.search(detail.getItemCode());
                search.setQtyOnHand(search.getQtyOnHand() - detail.getQty());

                boolean update = itemDAO.update(search);

                if (!update) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }

            connection.commit();
            connection.setAutoCommit(true);
            return true;

    }

    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
       return orderDAO.generateNewOrderId();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        ArrayList<customer> all = customerDAO.getAll();
        for (customer c : all) {
            allCustomers.add(new CustomerDTO(c.getId(),c.getName(),c.getAddress()));
        }
        return allCustomers;
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        ArrayList<item> all = itemDAO.getAll();
        for (item item : all) {
            allItems.add(new ItemDTO(item.getCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand()));
        }
        return allItems;
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        item item = itemDAO.search(code);
        return new ItemDTO(item.getCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand());
    }

    @Override
    public boolean ifItemExist(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.ifItemExist(code);
    }

    @Override
    public boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.ifCustomerExist(id);
    }

    @Override
    public CustomerDTO searchCustomer(String s) throws SQLException, ClassNotFoundException {
        customer c = customerDAO.search(s);
        return new CustomerDTO(c.getId(),c.getName(),c.getAddress());
    }
}
