(function () {
    var service = function ($http, systemConfig) {

        this.loadBranch = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/branch");
        };
        this.loadUsers = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/user");
        };
        this.getAccLedgerTypeList = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/acc-ledger-type/find-active");
        };
        this.getLedgerTypeDataList = function (paramModel) {
            return $http.post(systemConfig.apiUrl + "/api/care-point/transaction/journal/get-ledger-type-data-list",paramModel);
        };
        this.getDeleteRefDetails = function (number) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/journal/get-delete-ref-details/" + number);
        };
        this.loadAccounts = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/master/acc-account/find-only-account");
        };
        this.loadCostDepartment = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/cost-department/get-active-list");
        };
        this.loadCostCenter = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/cost-center/get-active-list");
        };
        this.loadfinancialYear = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/financial-year");
        };
        this.saveEditedData = function (data) {
            return $http.post(systemConfig.apiUrl + "/api/care-point/transaction/journal/save-edit-enteries", data);
        };

    };
    angular.module("appModule")
            .service("editTransactionService", service);
}());

