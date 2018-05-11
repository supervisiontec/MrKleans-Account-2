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
        this.loadSupplier = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/supplier");
        };
        this.loadAccBalance = function (index) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/master/acc-account/find-account-value/"+index);
        };
        this.loadAccTypes = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/account-type");
        };
        this.saveAccruedBill = function (data) {
            return $http.post(systemConfig.apiUrl + "/api/care-point/transaction/accred-bill/save", data);
        };
        this.setAccFlow = function (acc) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/master/acc-account/get-account-flow/"+acc);
        };
        this.getPermission = function (form) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/account-setting/user-permission/by-form/"+form);
        };
        this.findAccruedBillByNumberAndBranch = function (number) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/accred-bill/find-accrued-bills-by-number-and-branch/"+number);
        };
        this.activeCostCenterList = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/cost-center/get-active-list");
        };
        this.activeCostDepartmentList = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/cost-department/get-active-list");
        };
        
    };
    angular.module("appModule")
            .service("accruedBillService", service);
}());

