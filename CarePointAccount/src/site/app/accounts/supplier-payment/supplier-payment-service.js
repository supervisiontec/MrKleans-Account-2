(function () {
    var service = function ($http, systemConfig) {
        
        this.loadAccAccounts = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/master/acc-account/find-only-account");
        };
        this.currentBranch = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/branch/current-branch");
        };
        this.loadBranch = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/branch");
        };
        this.loadAccBalance = function (index) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/master/acc-account/find-account-value/"+index);
        };
        this.loadSupplier = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/supplier");
        };
        this.getOverPaymentIssueAccount = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/master/acc-account/find-over-payment-issue-account");
        };
        this.getPayableBills = function (account) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/supplier-payment/get-payable-bills/"+account);
        };
        this.getOverPaymentAmount = function (supplier) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/supplier-payment/get-over-payment-amount/"+supplier);
        };
        this.saveSupplierPayment = function (data) {
            return $http.post(systemConfig.apiUrl + "/api/care-point/transaction/supplier-payment/save", data);
        };
        this.getPermission = function (form) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/account-setting/user-permission/by-form/"+form);
        };

        
    };
    angular.module("appModule")
            .service("supplierPaymentService", service);
}());

