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
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/master/acc-account/find-account-value-all/"+index);
        };
        this.getReconciliationDetail = function (acc,date) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/bank-reconciliation/get-reconciliation-detail/"+date+"/"+acc);
        };
        this.getStartBalance = function (fDate,accAccount) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/bank-reconciliation/get-start-balance/"+fDate+"/"+accAccount);
        };
        this.loadTransactions = function (year,month,accAccount) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/bank-reconciliation/load-transactions/"+year+"/"+month+"/"+accAccount);
        };
        this.saveBankReconciliation = function (data) {
            return $http.post(systemConfig.apiUrl + "/api/care-point/transaction/bank-reconciliation/save", data);
        };
        this.savePopupData = function (data) {
            return $http.post(systemConfig.apiUrl + "/api/care-point/transaction/bank-reconciliation/savePopup", data);
        };
        this.getDeleteReconciliationCheque = function (index) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/bank-reconciliation/get-delete-reconciliation-cheque/"+index);
        };
        this.delete = function (index) {
            return $http.delete(systemConfig.apiUrl + "/api/care-point/transaction/bank-reconciliation/delete/"+index);
        };
        this.getPermission = function (form) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/account-setting/user-permission/by-form/"+form);
        };
        
    };
    angular.module("appModule")
            .service("bankReconciliationService", service);
}());

