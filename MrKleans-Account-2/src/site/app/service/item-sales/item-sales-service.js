(function () {
    'use strict';

    var service = function (systemConfig, $http) {

        this.loadClient = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/client");
        };
       
        this.loadEmployee = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/employee");
        };

        this.loadItemUnits = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/item-unit");
        };

        this.getClientBalance = function (index) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/customer-payment-voucher/get-client-balance/" + index);
        };

        this.getClientOverPayment = function (index) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/customer-payment-voucher/get-client-over-payment/" + index);
        };

        this.getBalanceInvoiceCount = function (index) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/customer-payment-voucher/get-balance-invoice-count/" + index);
        };

        this.getBalanceInvoiceList = function (index) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/customer-payment-voucher/get-balance-invoice/" + index);
        };
        
        this.loadBranch = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/service/bank-branch/branch");
        };

        this.loadCardType = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/service/bank-branch/card_type");
        };
        
        this.loadBranchByBank = function (bank) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/service/bank-branch/find-by-branch/" + bank);
        };

        this.loadBank = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/service/bank-branch/bank");
        };
        
        this.getCardReaders = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/card-reader/by-branch");
        };

        this.getNonPackageItemItem = function (itemKey) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/job-item/get-non-package-item/" + itemKey);
        };

        this.findByAvailableStockQty = function (itemIndexNo) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/job-item/get-item-qty-by-stocks/" + itemIndexNo);
        };

        //save
        this.saveItemSale = function (data) {
            return $http.post(systemConfig.apiUrl + "/api/care-point/service/item-sale/save-item-sale", data);
        };

        this.registerCustomer = function (client) {
            return $http.post(systemConfig.apiUrl + "/api/care-point/master/client/insert-client",client);
        };
        this.getPermission = function (form) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/account-setting/user-permission/by-form/"+form);
        };
    };

    angular.module("appModule")
            .service("itemSalesService", service);
}());