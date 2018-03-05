/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.items.items.model;

import java.io.Serializable;

/**
 *
 * @author kasun
 */
public class MItemMix implements Serializable{

    private MItem item;
    
    private String department;

    private String brand;

    private String category;

    private String itemCategory;

    private String subCategory;

    public MItemMix() {
    }

    public MItemMix(MItem item, String department, String brand, String category, String itemCategory, String subCategory) {
        this.item = item;
        this.department = department;
        this.brand = brand;
        this.category = category;
        this.itemCategory = itemCategory;
        this.subCategory = subCategory;
    }

    public MItem getItem() {
        return item;
    }

    public void setItem(MItem item) {
        this.item = item;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }
    
    

}
