(function () {
    var factory = function (dashBoardService, dashBoardFactory, $q, $filter) {
        function dashBoard() {
            this.constructor();
        }

        dashBoard.prototype = {
            //data model
            dashBoardMainList: [],
            dashBoardMainLable: [],
            dashBoardMainData: [],
            constructor: function () {
                var that = this;
                this.data = dashBoardFactory.newData();

                dashBoardService.getDashBoardMain()
                        .success(function (data) {
                            that.dashBoardMainList = data;
                            console.log(data);
                            that.getDashBoardMainLable();
                            that.getDashBoardMainData();
                        });
            }
            ,
            getDashBoardMainLable: function () {
                var labelList = [];
                var that = this;
                angular.forEach(this.dashBoardMainList, function (value) {
                    labelList.push(value[1]);
                });
                that.dashBoardMainLable = labelList;
                
            }
            ,
            getDashBoardMainData: function () {
                var that = this;
                var dataList = [];
                angular.forEach(this.dashBoardMainList, function (value) {
                    dataList.push(value[0]);
                });
                that.dashBoardMainData = dataList;
            }

        };
        return dashBoard;
    };
    angular.module("appModule")
            .factory("dashBoardModel", factory);
}());