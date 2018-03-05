(function () {
    var factory = function (dashBoardService, dashBoardFactory, $q, $filter) {
        function dashBoard() {
            this.constructor();
        }

        dashBoard.prototype = {
            //data model
            data: {},
            constructor: function () {
                this.data = dashBoardFactory.newData();
            }
            
        };
        return dashBoard;
    };
    angular.module("appModule")
            .factory("dashBoardModel", factory);
}());