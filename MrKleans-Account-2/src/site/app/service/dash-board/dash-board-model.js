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
            ledgerTypeLabels: [],
            ledgerTypeValues: [],
            db2Labels: [],
            db2Values: [],
            constructor: function () {
                var that = this;
                this.data = dashBoardFactory.newData();

                dashBoardService.getDashBoardMain()
                        .success(function (data) {
                            that.dashBoardMainList = data;
                            that.getDashBoardMainLable();
                            that.getDashBoardMainData();
                        });
                dashBoardService.getDashBoard1()
                        .success(function (data) {
                            that.setDashboardData1(data);
                        });
                dashBoardService.getDashBoard2()
                        .success(function (data) {
                            that.setDashboardData2(data);
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
            , setDashboardData1: function (data) {
                var that = this;
                var dataList = [];
                var labelList = [];
                angular.forEach(data[1], function (value) {
                    dataList.push(value);
                });
                angular.forEach(data[0], function (value) {
                    labelList.push(value);
                });
                that.ledgerTypeLabels=labelList;
                that.ledgerTypeValues=dataList;
            }
            , setDashboardData2: function (data) {
                var that = this;
                var labelList = [];
                var mainDataList = [];
                var dataList1 = [];
                var dataList2 = [];
                angular.forEach(data, function (value) {
                    labelList.push(value[0]);
                    dataList1.push(value[1]);
                    dataList2.push(value[2]);
                });
                mainDataList.push(dataList1);
                mainDataList.push(dataList2);
                that.db2Labels=labelList;
                that.db2Values=mainDataList;
            }
        };
        return dashBoard;
    };
    angular.module("appModule")
            .factory("dashBoardModel", factory);
}());