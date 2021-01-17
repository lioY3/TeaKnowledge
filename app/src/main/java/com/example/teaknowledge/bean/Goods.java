package com.example.teaknowledge.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "goods")
public class Goods extends TeaInfo {

    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "year")
    private int year;

    @Column(name = "desc")
    private String desc;

    @Column(name = "favorite")
    private int isFavorite;

    public Goods() {
        this.type = 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    public int getType() {
        return this.type;
    }
}
