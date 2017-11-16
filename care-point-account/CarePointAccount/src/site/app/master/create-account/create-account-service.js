(function () {
    var service = function ($http, systemConfig) {
        
        this.loadAccAccounts = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/master/acc-account");
        };
        this.saveNewAccount = function (accAccount) {
            return $http.post(systemConfig.apiUrl + "/api/care-point/account/master/acc-account/save-new-account", accAccount);
        };
        this.delete = function (indexNo) {
            return $http.delete(systemConfig.apiUrl + "/api/care-point/account/master/acc-account/delete-account/"+ indexNo);
        };
    };
    angular.module("appModule")
            .service("createAccountService", service);
}());