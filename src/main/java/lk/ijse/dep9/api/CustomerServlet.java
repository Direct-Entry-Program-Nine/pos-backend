package lk.ijse.dep9.api;

import jakarta.annotation.Resource;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.ijse.dep9.dto.CustomerDTO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

@WebServlet(name = "CustomerServlet", value = "/customers/*")
public class CustomerServlet extends HttpServlet {
    @Resource(lookup = "java:/comp/env/jdbc/dep9-pos")
    private DataSource pool;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getPathInfo() == null || request.getPathInfo() == "/"){
            fetchAllCustomers(response);
        }else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid");
        }

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
