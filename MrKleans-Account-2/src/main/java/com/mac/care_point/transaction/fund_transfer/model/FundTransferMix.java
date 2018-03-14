/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.fund_transfer.model;

import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import java.util.List;

/**
 *
 * @author 'Kasun Chamara'
 */
public class FundTransferMix {
    private TAccLedger data;
    private List<TAccLedger> dataList;

    public FundTransferMix() {
    }

    public FundTransferMix(TAccLedger data, List<TAccLedger> dataList) {
        this.data = data;
        this.dataList = dataList;
    }

    public TAccLedger getData() {
        return data;
    }

    public void setData(TAccLedger data) {
        this.data = data;
    }

    public List<TAccLedger> getDataList() {
        return dataList;
    }

    public void setDataList(List<TAccLedger> dataList) {
        this.dataList = dataList;
    }

    
    
    
}
