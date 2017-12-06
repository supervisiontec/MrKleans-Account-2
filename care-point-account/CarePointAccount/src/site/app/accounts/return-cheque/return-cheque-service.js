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
        this.getCheques = function (type) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/return-cheque/get-cheques/"+type);
        };
        this.getUsers = function () {
            return $http.get(systemConfig.apiUrl + "/security/user-list");
        };
        this.getSelectedChequeDetails = function (deleteIndex) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/return-cheque/get-selected-cheque-details/"+deleteIndex);
        };
        this.save = function (data) {
            return $http.post(systemConfig.apiUrl + "/api/care-point/transaction/return-cheque/save", data);
        };
        
    };
    angular.module("appModule")
            .service("returnChequeService", service);
}());

