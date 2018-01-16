(function () {
    'use strict';

    var service = function (systemConfig, $http) {

        this.loadClient = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/client");
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

        this.loadBank = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/service/bank-branch/bank");
        };

        this.loadBranch = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/service/bank-branch/branch");
        };

        this.loadCardType = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/service/bank-branch/card_type");
        };
        //invoice save
        this.savePaymentVoucher = function (data) {
            return $http.post(systemConfig.apiUrl + "/api/care-point/transaction/customer-payment-voucher/save-payment-voucher", data);
        };
       
        this.saveBalancePaymentVoucher = function (data) {
            return $http.post(systemConfig.apiUrl + "/api/care-point/transaction/customer-payment-voucher/save-balance-payment-voucher", data);
        };

        this.loadBranchByBank = function (bank) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/service/bank-branch/find-by-branch/" + bank);
        };
        this.getPermission = function (form) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/account-setting/user-permission/by-form/"+form);
        };

    };

    angular.module("appModule")
            .service("customerPaymentVoucherService", service);
}());