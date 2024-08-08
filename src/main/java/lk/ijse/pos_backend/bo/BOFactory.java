package lk.ijse.pos_backend.bo;

import lk.ijse.pos_backend.bo.impl.CustomerBOIMPL;
import lk.ijse.pos_backend.bo.impl.ItemBOIMPL;
import lk.ijse.pos_backend.bo.impl.OrderBOIMPL;
import lk.ijse.pos_backend.bo.impl.OrderDetailsBOIMPL;

public class BOFactory {

    private static  BOFactory boFactory;
    private BOFactory() {
    }

    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory():boFactory;
    }

    public enum BoTypes{
        CUSTOMER_BO,ITEM_BO,ORDER_BO,ORDER_DETAILS_BO
    }

    public <T extends  SuperBO> T getBO(BoTypes types){
        switch (types){
            case CUSTOMER_BO :
                return (T) new CustomerBOIMPL();
            case ITEM_BO:
                return (T) new ItemBOIMPL();
            case ORDER_BO:
                return (T) new OrderBOIMPL();
            case ORDER_DETAILS_BO:
                return (T) new OrderDetailsBOIMPL();

            default:
                return null;
        }
    }
}
