(function () {
    var service = function ($http, systemConfig) {
        
        this.loadAccCategoryMain = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/master/categoryMain");
        };
        this.loadAccCategory1 = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/master/category1");
        };
        this.loadAccCategory2 = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/master/category2");
        };
        this.loadAccCategory3 = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/master/category3");
        };
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