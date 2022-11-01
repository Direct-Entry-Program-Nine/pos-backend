package lk.ijse.dep9.dto;

import java.io.Serializable;
import java.util.Date;

public class OrderDTO implements Serializable {
    private String id;
    private Date date;
    private String Customer_id;

    public OrderDTO() {
    }

    public OrderDTO(String id, Date date, String customer_id) {
        this.id = id;
        this.date = date;
        Customer_id = customer_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomer_id() {
        return Customer_id;
    }

    public void setCustomer_id(String customer_id) {
        Customer_id = customer_id;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", Customer_id='" + Customer_id + '\'' +
                '}';
    }
}
