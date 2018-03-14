(function () {
    var service = function ($http, systemConfig) {
        
        this.loadMainAcc = function (date) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/view/trial-balance/get-main-acc-balance/"+date);
        };
        
    };
    angular.module("appModule")
            .service("trialBalanceService", service);
}());

