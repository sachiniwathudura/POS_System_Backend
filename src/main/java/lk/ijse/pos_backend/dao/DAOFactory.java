package lk.ijse.pos_backend.dao;

import lk.ijse.pos_backend.dao.impl.CustomerDAOIMPL;
import lk.ijse.pos_backend.dao.impl.ItemDAOIMPL;
import lk.ijse.pos_backend.dao.impl.OrderDAOIMPL;
import lk.ijse.pos_backend.dao.impl.OrderDetailsDAOIMPL;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory(){

    }

    public static DAOFactory getDaoFactory(){
        return (daoFactory==null) ? daoFactory = new DAOFactory() : daoFactory;

    }

    public enum DAOTypes{
        CUSTOMER_DAO, ITEM_DAO, ORDER_DAO, ORDER_DETAILS_DAO
    }

    public <T extends SuperDAO> T getDAO (DAOTypes types){
        switch (types){
            case CUSTOMER_DAO:
                return (T) new CustomerDAOIMPL();
            case ITEM_DAO:
                return (T) new ItemDAOIMPL();
            case ORDER_DAO:
                return (T) new OrderDAOIMPL();
            case ORDER_DETAILS_DAO:
                return (T) new OrderDetailsDAOIMPL();
            default:
                return null;
        }
    }
}
