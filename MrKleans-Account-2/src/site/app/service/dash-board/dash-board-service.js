(function () {
    var service = function ($http, systemConfig) {
        
        this.getDashBoardMain = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/dash-board/dash-board-main");
        };
        this.getDashBoard1 = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/dash-board/dash-board-1");
        };
        this.getDashBoard2 = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/dash-board/dash-board-2");
        };
    
    };
    angular.module("appModule")
            .service("dashBoardService", service);
}());
