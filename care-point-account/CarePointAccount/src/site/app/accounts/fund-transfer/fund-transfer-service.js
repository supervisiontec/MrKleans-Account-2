(function () {
    var service = function ($http, systemConfig) {
        
        this.loadAccAccounts = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/master/acc-account/find-only-cahs-bank");
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
        this.loadAccTypes = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/account-type");
        };
        this.saveFundTransfer = function (data) {
            return $http.post(systemConfig.apiUrl + "/api/care-point/transaction/fund-transfer/save", data);
        };
        
    };
    angular.module("appModule")
            .service("fundTransferService", service);
}());

