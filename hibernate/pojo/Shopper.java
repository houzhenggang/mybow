package com.taobao.mybow.hibernate.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/12.
 */
@Entity
public class Shopper implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "credit")
    private Integer credit;

    @Column(name = "favour")
    private Integer favour;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "dateTime")
    private Date date;

    @Override
    public String toString() {
        return "Shopper{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", credit=" + credit +
                ", favour=" + favour +
                ", quantity=" + quantity +
                ", date='" + date + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getFavour() {
        return favour;
    }

    public void setFavour(Integer favour) {
        this.favour = favour;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
