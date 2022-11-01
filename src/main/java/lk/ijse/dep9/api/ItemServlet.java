package lk.ijse.dep9.api;

import jakarta.annotation.Resource;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.ijse.dep9.dto.CustomerDTO;
import lk.ijse.dep9.dto.ItemDTO;
import lk.ijse.dep9.util.HttpServlet2;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "ItemServlet", value = "/items/*")
public class ItemServlet extends HttpServlet2 {
    @Resource(lookup ="java:/comp/env/jdbc/dep9-pos")
    private DataSource pool;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("q");
        String size = request.getParameter("size");
        String page = request.getParameter("page");

        if (request.getPathInfo() == null || request.getPathInfo() == "/"){
            loadAllItems(response);
        } else if (query != null || size != null || page != null) {
            //search paginate items
            //search paginate items
        } else {
            Matcher matcher = Pattern.compile("^/[a-fA-F-0-9]{8}(-[a-fA-F0-9]{4}){3}-[a-fA-F0-9]{12}/?$").matcher(request.getPathInfo());
            if (matcher.matches()){
               //get item details
            }
        }
    }

    private void loadAllItems(HttpServletResponse response) {
        try {
            Connection connection = pool.getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM item");

            ArrayList<ItemDTO> items = new ArrayList<>();
            while(rst.next()){
                String code = rst.getString("code");
                String description = rst.getString("description");
                double unitPrice = rst.getDouble("unit_price");
                int stock= rst.getInt("stock");
                ItemDTO dto = new ItemDTO(code, description, unitPrice,stock);
                items.add(dto);
            }

            connection.close();

            Jsonb jsonb = JsonbBuilder.create();
            response.setContentType("application/json");
            jsonb.toJson(items, response.getWriter());

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPatch(req, resp);
    }
}
