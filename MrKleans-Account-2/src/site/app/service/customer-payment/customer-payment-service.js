(function () {
    'use strict';

    var service = function (systemConfig, $http) {

        this.loadClient = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/client");
        };

        this.getPayableBills = function (account) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/customer-payment-voucher/get-payable-bills/" + account);
        };

        this.getPermission = function (form) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/account-setting/user-permission/by-form/" + form);
        };

        this.getOverPaymentAmount = function (supplier) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/supplier-payment/get-over-payment-amount/" + supplier);
        };

        this.getOverPaymentReceviedAccount = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/master/acc-account/find-over-payment-received-account");
        };

        this.loadAccAccounts = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/master/acc-account/find-only-account");
        };

        this.loadAccBalance = function (index) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/master/acc-account/find-account-value/" + index);
        };
          this.save = function (data) {
            return $http.post(systemConfig.apiUrl + "/api/care-point/transaction/customer-payment-voucher/save", data);
        };
//        this.getClientBalance = function (index) {
//            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/customer-payment-voucher/get-client-balance/" + index);
//        };
//
//        this.getClientOverPayment = function (index) {
//            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/customer-payment-voucher/get-client-over-payment/" + index);
//        };
//       
//        this.getBalanceInvoiceCount = function (index) {
//            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/customer-payment-voucher/get-balance-invoice-count/" + index);
//        };
//       
//        this.getBalanceInvoiceList = function (index) {
//            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/customer-payment-voucher/get-balance-invoice/" + index);
//        };
//
//        this.loadBank = function () {
//            return $http.get(systemConfig.apiUrl + "/api/care-point/service/bank-branch/bank");
//        };
//
//        this.loadBranch = function () {
//            return $http.get(systemConfig.apiUrl + "/api/care-point/service/bank-branch/branch");
//        };
//
//        this.loadCardType = function () {
//            return $http.get(systemConfig.apiUrl + "/api/care-point/service/bank-branch/card_type");
//        };
//        //invoice save
//        this.savePaymentVoucher = function (data) {
//            return $http.post(systemConfig.apiUrl + "/api/care-point/transaction/customer-payment-voucher/save-payment-voucher", data);
//        };
//       
//        this.saveBalancePaymentVoucher = function (data) {
//            return $http.post(systemConfig.apiUrl + "/api/care-point/transaction/customer-payment-voucher/save-balance-payment-voucher", data);
//        };
//
//        this.loadBranchByBank = function (bank) {
//            return $http.get(systemConfig.apiUrl + "/api/care-point/service/bank-branch/find-by-branch/" + bank);
//        };

    };

    angular.module("appModule")
            .service("customerPaymentVoucherService", service);
}());