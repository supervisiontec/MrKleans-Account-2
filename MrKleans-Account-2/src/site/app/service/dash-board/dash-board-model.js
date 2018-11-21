(function () {
    var factory = function (dashBoardService, dashBoardFactory, $q, $filter) {
        function dashBoard() {
            this.constructor();
        }

        dashBoard.prototype = {
            //data model
            data: {},
            filter: {},
            dashBoardMainList: [],
            dashBoardMainLable: [],
            dashBoardMainData: [],
            ledgerTypeLabels: [],
            ledgerTypeValues: [],
            db2Labels: [],
            db2Values: [],
            dashboardLabels4: [],
            dashboardValues4: [],
            dashboardLabels5: [],
            dashboardValues5: [],
            
            constructor: function () {
                var that = this;
                this.data = dashBoardFactory.newData();
                this.filter = dashBoardFactory.filter();
            },
            getDashboard5: function (tDate) {
                var that = this;
                dashBoardService.getDashBoard5(tDate)
                        .success(function (data) {
                            that.setDashboardData5(data);
                        });
            },
            getDashboard4: function (tDate) {
                var that = this;
                dashBoardService.getDashBoard4(tDate)
                        .success(function (data) {
                            that.setDashboardData4(data);
                        });

            },
            getDashboard3: function (fDate, tDate) {
                var that = this;
                dashBoardService.getDashBoard3(fDate, tDate)
                        .success(function (data) {
                            that.setDashboardData3(data);
                        });

            },

            getDashboard2: function (fDate, tDate) {
                var that = this;
                console.log(fDate);
                console.log(tDate);
                dashBoardService.getDashBoard2(fDate, tDate)
                        .success(function (data) {
                            console.log(data);
                            that.setDashboardData2(data);
                        });

            }
            , getDashboardMain: function (tDate) {
                var that = this;
                dashBoardService.getDashBoardMain(tDate)
                        .success(function (data) {
                            that.dashBoardMainList = data;
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
            , setDashboardData2: function (data) {
                var that = this;
                var dataList = [];
                var labelList = [];
                angular.forEach(data[1], function (value) {
                    dataList.push(value);
                });
                angular.forEach(data[0], function (value) {
                    labelList.push(value);
                });
                that.ledgerTypeLabels = labelList;
                that.ledgerTypeValues = dataList;
            }
            , setDashboardData3: function (data) {
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
                that.db2Labels = labelList;
                that.db2Values = mainDataList;
            }
            , setDashboardData4: function (data) {
                var that = this;
                var dashboardLabels4 = [];
                var dashboardValues4 = [];
                angular.forEach(data, function (value) {
                    dashboardLabels4.push(value[0]);
                    dashboardValues4.push(value[1]);
                });

                that.dashboardLabels4 = dashboardLabels4;
                that.dashboardValues4 = dashboardValues4;
            }
            , setDashboardData5: function (data) {
                var that = this;
                var dashboardLabels5 = [];
                var dashboardValues5 = [];
                angular.forEach(data, function (value) {
                    dashboardLabels5.push(value[0]);
                    dashboardValues5.push(value[1]);
                });

                that.dashboardLabels5 = dashboardLabels5;
                that.dashboardValues5 = dashboardValues5;
            }
        };
        return dashBoard;
    };
    angular.module("appModule")
            .factory("dashBoardModel", factory);
}());