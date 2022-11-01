package lk.ijse.dep9.dto;

public class ItemDTO {
    private String code;
    private String description;
    private double unitPrice;
    private int stock;

    public ItemDTO() {
    }

    public ItemDTO(String code, String description, double unitPrice, int stock) {
        this.code = code;
        this.description = description;
        this.unitPrice = unitPrice;
        this.stock = stock;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
