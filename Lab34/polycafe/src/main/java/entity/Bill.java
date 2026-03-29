package entity;

import java.util.Date;

public class Bill {
    private int id;
    private String code;
    private Date createdAt;
    private double total;
    private int status;
    private int userId;

    public Bill() {}

    public Bill(int id, String code, Date createdAt, double total, int status, int userId) {
        this.id = id;
        this.code = code;
        this.createdAt = createdAt;
        this.total = total;
        this.status = status;
        this.userId = userId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}
