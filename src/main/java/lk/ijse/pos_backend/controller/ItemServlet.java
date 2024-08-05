package lk.ijse.pos_backend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.pos_backend.bo.ItemBO;
import lk.ijse.pos_backend.bo.impl.ItemBOIMPL;
import lk.ijse.pos_backend.dto.ItemDTO;
import lombok.SneakyThrows;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

@WebServlet(name = "item",urlPatterns = "/item",loadOnStartup = 3)
public class ItemServlet extends HttpServlet {
    ItemBO itemBO =(ItemBO) new ItemBOIMPL();
    DataSource connectionPool;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try{
            var ctx = new InitialContext();
            Context envContext = (Context) ctx.lookup("java:/comp/env");
            DataSource dataSource = (DataSource) envContext.lookup("jdbc/pos_system_new");
            this.connectionPool = dataSource;
        } catch (NamingException e) {
            throw new ServletException("Can not find JNDI resource",e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String function = req.getParameter("function");

        if (function.equals("getAll")) {
            try (Connection connection = connectionPool.getConnection()) {
                ArrayList<ItemDTO> itemDTOList = itemBO.getAlItem(connection);

                Jsonb jsonb = JsonbBuilder.create();
                String json = jsonb.toJson(itemDTOList);
                resp.getWriter().write(json);
            } catch (JsonbException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            } catch (IOException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            } catch (SQLException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (function.equals("getById")) {
            String id = req.getParameter("id");
            try (Connection connection = connectionPool.getConnection()) {
                ItemDTO itemDTO = itemBO.getItemById(connection,id);

                Jsonb jsonb = JsonbBuilder.create();
                String json = jsonb.toJson(itemDTO);
                resp.getWriter().write(json);
            } catch (JsonbException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            } catch (IOException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            } catch (SQLException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        try (Connection connection = connectionPool.getConnection()){
            boolean isDeleted = itemBO.deleteItem(connection,id);
            if (isDeleted){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else{
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to delete item!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(Connection connection = connectionPool.getConnection()){
            Jsonb jsonb = JsonbBuilder.create();

            ItemDTO itemDTO = jsonb.fromJson(req.getReader(),ItemDTO.class);
            System.out.println(itemDTO);

            if (itemDTO.getItemId() == null || !itemDTO.getItemId().matches("^(I00-)[0-9]{3}$")) {
                resp.getWriter().write("itemId is empty or invalid!!");
                return;
            } else if (itemDTO.getItemName() == null || !(itemDTO.getItemName().matches("^[A-Za-z ]{4,}$"))) {
                resp.getWriter().write("itemName is empty or invalid!!");
                return;
            } else if (itemDTO.getItemQty() <= 0) {
                resp.getWriter().write("itemQty is empty or invalid!!");
                return;
            } else if (itemDTO.getItemPrice() <= 0) {
                resp.getWriter().write("itemPrice is empty or invalid!!");
                return;
            }
            boolean isSaved = itemBO.saveItem(connection, itemDTO);
            if (isSaved) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "failed to save item");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            resp.sendError(HttpServletResponse.SC_CONFLICT, "Duplicate values. Please check again");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
        }
    }
    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = connectionPool.getConnection()) {
            Jsonb jsonb = JsonbBuilder.create();

            ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);
            System.out.println(itemDTO);

            if (itemDTO.getItemId() == null || !itemDTO.getItemId().matches("^(I00-)[0-9]{3}$")) {
                resp.getWriter().write("itemId is empty or invalid!!");
                return;
            } else if (itemDTO.getItemName() == null || !(itemDTO.getItemName().matches("^[A-Za-z ]{4,}$"))) {
                resp.getWriter().write("itemName is empty or invalid!!");
                return;
            } else if (itemDTO.getItemQty() <= 0) {
                resp.getWriter().write("itemQty is empty or invalid!!");
                return;
            } else if (itemDTO.getItemPrice() <= 0) {
                resp.getWriter().write("itemPrice is empty or invalid!!");
                return;
            }
            boolean isUpdated = itemBO.updateItem(connection, itemDTO);
            if (isUpdated) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);

            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "failed to update item");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            resp.sendError(HttpServletResponse.SC_CONFLICT, "Duplicate values. Please check again");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
        }
    }
}
