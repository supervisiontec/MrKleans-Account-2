/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.profit_lost;

import com.mac.care_point.transaction.profit_lost.model.TProfitLostModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author kasun
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ProfitLostService {

    @Autowired
    private ProfitLostRepository profitLostRepository;

    BigDecimal openStock = new BigDecimal(0);
    BigDecimal purchase = new BigDecimal(0);
    BigDecimal closeStock = new BigDecimal(0);

    public List<TProfitLostModel> loadProfitLostMain(Integer financialYear) {

        List<TProfitLostModel> mainList = profitLostRepository.findAll();
        List<TProfitLostModel> returnList = new ArrayList<>();

        BigDecimal salesIncome = getRevenueTotal(financialYear);
        BigDecimal cost = new BigDecimal(0);
        BigDecimal otherIncome = getOtherIncome();
        BigDecimal otherExpense = getOtherExpense();
        BigDecimal taxExpense = getTaxExpense();

        for (TProfitLostModel model : mainList) {
            model.setSubOf(0);
            if (model.getIndexNo() == 1) {
                model.setCredit(salesIncome);
            }
            if (model.getIndexNo() == 2) {
                getOpenStock();
                getPurchaseOfYear();
                getCloseStock();
                cost=getCostOfSales();
                model.setCredit(cost);
            }
            if (model.getIndexNo() == 3) {
                model.setCredit(salesIncome.subtract(cost));
            }
            if (model.getIndexNo() == 4) {
                model.setCredit(otherIncome);
            }
            if (model.getIndexNo() == 5) {
                model.setDebit(otherExpense.subtract(taxExpense));
            }
            if (model.getIndexNo() == 6) {
                model.setCredit(salesIncome.subtract(cost).add(otherIncome).subtract(otherExpense.subtract(taxExpense)));
            }
            if (model.getIndexNo() == 7) {
                model.setDebit(taxExpense);
            }
            if (model.getIndexNo() == 8) {
                model.setCredit(salesIncome.subtract(cost).add(otherIncome).subtract(otherExpense));
            }
            if (model.getIndexNo() == 9) {
                model.setDebit(new BigDecimal(0));
            }

            returnList.add(model);

        }
        returnList.sort(Comparator.comparing(TProfitLostModel::getOrderNo));
        return returnList;

    }

    private BigDecimal getRevenueTotal(Integer financialYear) {
        return profitLostRepository.getRevenueTotal(financialYear);
    }

    private BigDecimal getOtherIncome() {
        return profitLostRepository.getOtherIncome();
    }

    private BigDecimal getOtherExpense() {
        return profitLostRepository.getOtherExpense();
    }

    private BigDecimal getTaxExpense() {
        return profitLostRepository.getTaxExpense();
    }

    private void getOpenStock() {
        openStock = profitLostRepository.getOpenStock();
    }

    private void getPurchaseOfYear() {
        purchase = profitLostRepository.getPurchaseOfYear();
    }

    private void getCloseStock() {
        closeStock = profitLostRepository.getCloseStock();
    }

    private BigDecimal getCostOfSales() {
        return openStock.add(purchase).subtract(closeStock);
    }

    public List<TProfitLostModel> getSubList(Integer indexNo) {
        List<TProfitLostModel> returnList = new ArrayList<>();
        TProfitLostModel model = profitLostRepository.findOne(indexNo);
        if (indexNo == 1) {
            //Revenue
            List<Object[]> list = profitLostRepository.getRevenueDetails();
            for (Object[] object : list) {
                TProfitLostModel plModel = new TProfitLostModel();

                plModel.setChecked(false);
                plModel.setDebit(new BigDecimal(object[2].toString()));
                plModel.setCredit(new BigDecimal(0));
                plModel.setName(object[1].toString());
                plModel.setShow(true);
                plModel.setOrderNo(model.getOrderNo() + Integer.parseInt(object[0].toString()));
                plModel.setSubOf(model.getIndexNo());
                returnList.add(plModel);
            }
        }
        if (indexNo == 2) {
            //cosst of sales

            TProfitLostModel openStockModel = new TProfitLostModel();
            openStockModel.setChecked(false);
            openStockModel.setDebit(this.openStock);
            openStockModel.setCredit(new BigDecimal(0));
            openStockModel.setName("Open Stock");
            openStockModel.setShow(true);
            openStockModel.setOrderNo(model.getOrderNo() + 100);
            openStockModel.setSubOf(model.getIndexNo());

            returnList.add(openStockModel);

            TProfitLostModel purchaseModel = new TProfitLostModel();
            purchaseModel.setChecked(false);
            purchaseModel.setDebit(this.purchase);
            purchaseModel.setCredit(new BigDecimal(0));
            purchaseModel.setName("Purchasing");
            purchaseModel.setShow(true);
            purchaseModel.setOrderNo(model.getOrderNo() + 200);
            purchaseModel.setSubOf(model.getIndexNo());
            returnList.add(purchaseModel);

            TProfitLostModel stockReturns = new TProfitLostModel();
            stockReturns.setChecked(false);
            stockReturns.setDebit(new BigDecimal(0));
            stockReturns.setCredit(new BigDecimal(0));
            stockReturns.setName("Stock Return (-)");
            stockReturns.setShow(true);
            stockReturns.setOrderNo(model.getOrderNo() + 300);
            stockReturns.setSubOf(model.getIndexNo());
            returnList.add(stockReturns);

            TProfitLostModel closeStock = new TProfitLostModel();
            closeStock.setChecked(false);
            closeStock.setDebit(this.closeStock);
            closeStock.setCredit(new BigDecimal(0));
            closeStock.setName("Close Stock (-)");
            closeStock.setShow(true);
            closeStock.setOrderNo(model.getOrderNo() + 400);
            closeStock.setSubOf(model.getIndexNo());
            returnList.add(closeStock);
        }
        if (indexNo==5) {
             //other expense
            List<Object[]> list = profitLostRepository.getOtherExpenseDetails();
            for (Object[] object : list) {
                TProfitLostModel plModel = new TProfitLostModel();

                plModel.setChecked(false);
                plModel.setDebit(new BigDecimal(object[2].toString()));
                plModel.setCredit(new BigDecimal(0));
                plModel.setName(object[1].toString());
                plModel.setShow(true);
                plModel.setOrderNo(model.getOrderNo() + Integer.parseInt(object[0].toString()));
                plModel.setSubOf(model.getIndexNo());
                returnList.add(plModel);
            }
            
        }
        return returnList;
    }

}
