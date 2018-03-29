(function () {
    var service = function ($http, systemConfig) {
        
        this.getDashBoardMain = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/dash-board/dash-board-main");
        };
    
    };
    angular.module("appModule")
            .service("dashBoardService", service);
}());
