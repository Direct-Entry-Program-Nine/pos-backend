package lk.ijse.dep9.api;

import jakarta.annotation.Resource;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.ijse.dep9.util.HttpServlet2;
import lk.ijse.dep9.dto.CustomerDTO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "CustomerServlet", value = "/customers/*",loadOnStartup = 0)
public class CustomerServlet extends HttpServlet2 {
    @Resource(lookup ="java:/comp/env/jdbc/dep9-pos")
    private DataSource pool;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("q");
        String size = request.getParameter("size");
        String page = request.getParameter("page");

        if (request.getPathInfo() == null || request.getPathInfo() == "/"){
            fetchAllCustomers(response);
        } else if (query != null || size != null || page != null) {
            searchPaginatedCustomers(query ,Integer.parseInt(size), Integer.parseInt(page), response);
        } else {
            Matcher matcher = Pattern.compile("^/[a-fA-F-0-9]{8}(-[a-fA-F0-9]{4}){3}-[a-fA-F0-9]{12}/?$").matcher(request.getPathInfo());
            if (matcher.matches()){
                getCustomerDetails(matcher.group(1), response);
            }
        }
    }

    private void getCustomerDetails(String customerId, HttpServletResponse response) throws IOException {
        response.getWriter().printf("Get customer details" + customerId);
    }

    private void searchPaginatedCustomers(String query, int size, int page, HttpServletResponse response) throws IOException {
        try (Connection connection = pool.getConnection()) {
            String sql="SELECT COUNT(id) AS count FROM customer WHERE id LIKE ? OR name LIKE ? OR address LIKE ? ";
            PreparedStatement countStm = connection.prepareStatement(sql);
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM customer WHERE id LIKE ? OR name LIKE ? OR address LIKE ?  LIMIT ? OFFSET ?");

            query="%"+query+"%";



            stm.setString(1,query);
            stm.setString(2,query);
            stm.setString(3,query);

            stm.setInt(4,size);
            stm.setInt(5,(page-1)*size);





            ResultSet countRST = countStm.executeQuery();
            countRST.next();
            int totalCustomers = countRST.getInt("count");
            response.setIntHeader("X-Total-Count", totalCustomers);




            ResultSet rst = stm.executeQuery();

            ArrayList<CustomerDTO> customers = new ArrayList<>();
            while(rst.next()){
                String id = rst.getString("id");
                String name = rst.getString("name");
                String address = rst.getString("address");
                customers.add(new CustomerDTO(id, name, address));
            }

            Jsonb jsonb = JsonbBuilder.create();
            response.setContentType("application/json");
            jsonb.toJson(customers, response.getWriter());

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed fetch customers");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().printf("Add a customer");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getPathInfo() == null || request.getPathInfo().equals("/")){
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
            return;
        }
        Matcher matcher = Pattern.compile("^/([A-Fa-f0-9]{8}(-[A-Fa-f0-9]{4}){3}-[A-Fa-f0-9]{12})$").matcher(request.getPathInfo());
        if (matcher.matches()){
            deleteCustomer(matcher.group(1), response);
        }else {
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Expected valid UUID");
        }
    }

    private void deleteCustomer(String customerId, HttpServletResponse response) {
        try {
            Connection connection = pool.getConnection();
            PreparedStatement stm = connection.prepareStatement("DELETE FROM customer WHERE id=?");
            stm.setString(1, customerId);
            int affectedRows = stm.executeUpdate();
            if (affectedRows == 0){
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid customer ID");
            }else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().printf("Update Customer");
    }

    private void fetchAllCustomers(HttpServletResponse response){
        try {
            Connection connection = pool.getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM customer");

            ArrayList<CustomerDTO> customers = new ArrayList<>();
            while(rst.next()){
                String id = rst.getString("id");
                String name = rst.getString("name");
                String address = rst.getString("address");
                CustomerDTO dto = new CustomerDTO(id, name, address);
                customers.add(dto);
            }

            connection.close();

            Jsonb jsonb = JsonbBuilder.create();
            response.setContentType("application/json");
            jsonb.toJson(customers, response.getWriter());

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
