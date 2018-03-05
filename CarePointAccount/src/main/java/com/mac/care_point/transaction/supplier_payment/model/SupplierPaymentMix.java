/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.supplier_payment.model;

import java.util.List;

/**
 *
 * @author 'Kasun Chamara'
 */
public class SupplierPaymentMix {
    private DataMain data;
    private List<DataSub> dataList;

    public SupplierPaymentMix() {
    }

    public SupplierPaymentMix(DataMain data, List<DataSub> dataList) {
        this.data = data;
        this.dataList = dataList;
    }

    public DataMain getData() {
        return data;
    }

    public void setData(DataMain data) {
        this.data = data;
    }

    public List<DataSub> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataSub> dataList) {
        this.dataList = dataList;
    }
    
}
