(function () {
    var service = function ($http, systemConfig) {

        this.getDashBoardMain = function (toDate) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/dash-board/dash-board-main/" + toDate);
        };
        this.getDashBoard2 = function (fDate, tDate) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/dash-board/dash-board-2/" + fDate + "/" + tDate);
        };
        this.getDashBoard3 = function (fDate,tDate) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/dash-board/dash-board-3/" + fDate + "/" + tDate);
        };
        this.getDashBoard4 = function (tDate) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/dash-board/dash-board-4/"+ tDate);
        };
        this.getDashBoard5 = function (tDate) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/dash-board/dash-board-5/"+ tDate);
        };

    };
    angular.module("appModule")
            .service("dashBoardService", service);
}());
