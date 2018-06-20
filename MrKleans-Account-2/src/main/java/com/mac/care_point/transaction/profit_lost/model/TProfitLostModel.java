/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.profit_lost.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author kasun
 */
@Entity
@Table(name = "t_pl_main")
public class TProfitLostModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo;

    @Column(name = "name")
    private String name;

    @Column(name = "sub_of")
    private Integer subOf;

    @Column(name = "`checked`")
    private boolean checked;

    @Column(name = "`show`")
    private boolean show;

    @Column(name = "is_bold")
    private boolean isBold;

    @Column(name = "is_italic")
    private boolean isItalic;

    @Column(name = "is_underline")
    private boolean isUnderline;

    @Column(name = "debit")
    private BigDecimal debit;

    @Column(name = "credit")
    private BigDecimal credit;
   
    @Column(name = "order_no")
    private Integer orderNo;

   
    public TProfitLostModel() {
    }

    public TProfitLostModel(Integer indexNo, String name, Integer subOf, boolean checked, boolean show, boolean isBold, boolean isItalic, boolean isUnderline, BigDecimal debit, BigDecimal credit, Integer orderNo) {
        this.indexNo = indexNo;
        this.name = name;
        this.subOf = subOf;
        this.checked = checked;
        this.show = show;
        this.isBold = isBold;
        this.isItalic = isItalic;
        this.isUnderline = isUnderline;
        this.debit = debit;
        this.credit = credit;
        this.orderNo = orderNo;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getSubOf() {
        return subOf;
    }

    public void setSubOf(Integer subOf) {
        this.subOf = subOf;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public boolean isIsBold() {
        return isBold;
    }

    public void setIsBold(boolean isBold) {
        this.isBold = isBold;
    }

    public boolean isIsItalic() {
        return isItalic;
    }

    public void setIsItalic(boolean isItalic) {
        this.isItalic = isItalic;
    }

    public boolean isIsUnderline() {
        return isUnderline;
    }

    public void setIsUnderline(boolean isUnderline) {
        this.isUnderline = isUnderline;
    }

}
