/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.grn_sync.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author kasun
 */
@Entity
@Table(name = "t_grn")
public class TGrnMaster implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer index_no;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "number")
    private String number;

    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    private String date;

    @Basic(optional = false)
    @NotNull
    @Column(name = "amount")
    private BigDecimal amount;

    @Size(max = 25)
    @Column(name = "ref_number")
    private String refNumber;

    @Basic(optional = false)
    @NotNull
    @Column(name = "branch")
    private int branch;

    @Column(name = "nbt")
    private BigDecimal nbt;

    @Column(name = "supplier")
    private Integer supplier;

    @Column(name = "nbt_value")
    private BigDecimal nbtValue;

    @Column(name = "vat")
    private BigDecimal vat;

    @Column(name = "vat_value")
    private BigDecimal vatValue;

    @Column(name = "grand_amount")
    private BigDecimal grandAmount;

    @Column(name = "pay_amount")
    private BigDecimal payAmount;

    @Column(name = "balance_amount")
    private BigDecimal balanceAmount;

    @Column(name = "return_value")
    private BigDecimal returnValue;

    @Size(max = 50)
    @Column(name = "status")
    private String status;

    @Size(max = 50)
    @Column(name = "type")
    private String type;

    @Column(name = "is_nbt")
    private Boolean isNbt;

    @Column(name = "is_vat")
    private Boolean isVat;

    @Column(name = "credit_period")
    private Integer creditPeriod;


    public TGrnMaster() {
    }

    public TGrnMaster(String number, String date, BigDecimal amount, String refNumber, int branch, BigDecimal nbt, Integer supplier, BigDecimal nbtValue, BigDecimal vat, BigDecimal vatValue, BigDecimal grandAmount, BigDecimal payAmount, BigDecimal balanceAmount, BigDecimal returnValue, String status, String type, Boolean isNbt, Boolean isVat, Integer creditPeriod, Integer index_no) {
        this.number = number;
        this.date = date;
        this.amount = amount;
        this.refNumber = refNumber;
        this.branch = branch;
        this.nbt = nbt;
        this.supplier = supplier;
        this.nbtValue = nbtValue;
        this.vat = vat;
        this.vatValue = vatValue;
        this.grandAmount = grandAmount;
        this.payAmount = payAmount;
        this.balanceAmount = balanceAmount;
        this.returnValue = returnValue;
        this.status = status;
        this.type = type;
        this.isNbt = isNbt;
        this.isVat = isVat;
        this.creditPeriod = creditPeriod;
        this.index_no = index_no;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getSupplier() {
        return supplier;
    }

    public void setSupplier(Integer supplier) {
        this.supplier = supplier;
    }

    public Integer getIndex_no() {
        return index_no;
    }

    public void setIndex_no(Integer index_no) {
        this.index_no = index_no;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public int getBranch() {
        return branch;
    }

    public void setBranch(int branch) {
        this.branch = branch;
    }

    public BigDecimal getNbt() {
        return nbt;
    }

    public void setNbt(BigDecimal nbt) {
        this.nbt = nbt;
    }

    public BigDecimal getNbtValue() {
        return nbtValue;
    }

    public void setNbtValue(BigDecimal nbtValue) {
        this.nbtValue = nbtValue;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public BigDecimal getVatValue() {
        return vatValue;
    }

    public void setVatValue(BigDecimal vatValue) {
        this.vatValue = vatValue;
    }

    public BigDecimal getGrandAmount() {
        return grandAmount;
    }

    public void setGrandAmount(BigDecimal grandAmount) {
        this.grandAmount = grandAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public BigDecimal getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(BigDecimal returnValue) {
        this.returnValue = returnValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsNbt() {
        return isNbt;
    }

    public void setIsNbt(Boolean isNbt) {
        this.isNbt = isNbt;
    }

    public Boolean getIsVat() {
        return isVat;
    }

    public void setIsVat(Boolean isVat) {
        this.isVat = isVat;
    }

    public Integer getCreditPeriod() {
        return creditPeriod;
    }

    public void setCreditPeriod(Integer creditPeriod) {
        this.creditPeriod = creditPeriod;
    }
}
