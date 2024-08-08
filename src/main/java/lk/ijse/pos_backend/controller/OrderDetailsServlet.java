package lk.ijse.pos_backend.controller;


import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.pos_backend.bo.BOFactory;
import lk.ijse.pos_backend.bo.OrderDetailsBO;
import lk.ijse.pos_backend.dto.OrdersDTO;
import lk.ijse.pos_backend.entity.OrderDetails;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(urlPatterns = "/orderDetails")
public class OrderDetailsServlet extends HttpServlet {

    OrderDetailsBO orderDetailsBO = BOFactory.getBoFactory().getBO(BOFactory.BoTypes.ORDER_DETAILS_BO);
    DataSource connectionPool;
    @Override
    public void init() throws ServletException {

        try {
            var ctx = new InitialContext(); //get connection to connection pool
            Context envContext = (Context) ctx.lookup("java:/comp/env");
            DataSource dataSource = (DataSource) envContext.lookup("jdbc/pos_system_new");
//            DataSource pool = (DataSource) ctx.lookup("java/comp/env/jdbc/pos_system"); // cast and create datasource and get lookup set url path
            this.connectionPool = dataSource;
        } catch (NamingException e) {
            throw new ServletException("Cannot find JNDI resource", e);
//            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String function = req.getParameter("function");
        String orderId = req.getParameter("order_id");

        if ("getById".equals(function) && orderId != null) {
            try (Connection connection = connectionPool.getConnection()) {
                OrdersDTO orderDTO = orderDetailsBO.getOrderDetailsById(connection, orderId);
                System.out.println(orderDTO);
                if (orderDTO != null) {
                    Jsonb jsonb = JsonbBuilder.create();
                    String json = jsonb.toJson(orderDTO);
                    resp.setContentType("application/json");
                    resp.getWriter().write(json);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found");
                }
            } catch (Exception e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request parameters");
        }
    }

}
