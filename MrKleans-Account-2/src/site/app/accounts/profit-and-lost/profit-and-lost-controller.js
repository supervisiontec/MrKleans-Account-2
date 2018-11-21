
angular.module('profitAndLostModule', ['ui.bootstrap']);

angular.module("profitAndLostModule")
        .factory("profitAndLostFactory", function ($http, systemConfig) {
            var factory = {};

            factory.loadMainData = function (financialYear, fromDate, toDate, callback) {
                console.log(financialYear);
                console.log(fromDate);
                console.log(toDate);
                var url = systemConfig.apiUrl + "/api/care-point/transaction/profit-lost/load-profit-lost-main/" + financialYear + "/" + fromDate + "/" + toDate;

                $http.get(url)
                        .success(function (data, status, headers) {
                            callback(data);
                        })
                        .error(function (data, status, headers) {
                            callback(data);
                        });
            };
            factory.getSubList = function (index, financialYear, fromDate, toDate, callback) {
                var url = systemConfig.apiUrl + "/api/care-point/transaction/profit-lost/get-sub-list/" + index + "/" + financialYear + "/" + fromDate + "/" + toDate;

                $http.get(url)
                        .success(function (data, status, headers) {
                            callback(data);
                        })
                        .error(function (data, status, headers) {
                            callback(data);
                        });
            };
            factory.loadFinancialYearList = function (callback) {
                var url = systemConfig.apiUrl + "/api/care-point/master/financial-year";

                $http.get(url)
                        .success(function (data, status, headers) {
                            callback(data);
                        })
                        .error(function (data, status, headers) {
                            callback(data);
                        });
            };
            return factory;
        });

angular.module("profitAndLostModule")
        .controller('profitAndLostController', function ($scope, $route, PrintPane, printService, $filter, profitAndLostFactory) {
            $scope.printService = new printService();
            $scope.selectedNode = "";
            $scope.clickList = [];
            $scope.mainList = [];
            $scope.financialYearList = [];

            $scope.ui = {};
            $scope.model = {};
            $scope.model.fromDate = null;
            $scope.model.toDate = null;
            $scope.model.financialYear = null;


            $scope.ui.print = function () {
                PrintPane.printConfirm("Print As..")
                        .confirm(function () {
                            $scope.printService.printPdf('printDiv');
                        })
                        .discard(function () {
                            $scope.printService.printExcel('printDiv', 'profit and lost');
                        });
            };
            $scope.ui.frmoveFinancialYear = function () {
                $scope.model.financialYear = null;
            };
            $scope.ui.removeFromToDates = function () {
                $scope.model.fromDate = null;
                $scope.model.toDate = null;
            };
            $scope.ui.refresh = function () {
                $route.reload();
            };

            $scope.ui.unitClick = function (data) {
                var indexNo = data.indexNo;

                var check = false;

                angular.forEach($scope.clickList, function (number) {
                    if (indexNo === number) {
                        check = true;
                    }
                });
                console.log('check ' + check);
                if (check) {
                    angular.forEach($scope.mainList, function (sub) {
                        if (sub.subOf === data.indexNo) {
                            sub.show = sub.show === true ? false : true;
                        }
                    });
                } else {
                    $scope.clickList.push(data.indexNo);
                    var financialYear = $scope.model.financialYear;
                    var fromDate = $filter('date')($scope.model.fromDate, 'yyyy-MM-dd');
                    var toDate = $filter('date')($scope.model.toDate, 'yyyy-MM-dd');
                    profitAndLostFactory.getSubList(parseInt(indexNo), financialYear, fromDate, toDate, function (dataList) {
                        angular.forEach(dataList, function (data1) {
                            $scope.mainList.push(data1);
                        });
                    }, function () {
                        console.log('Empty Sub List');
                    });
                }
            };
            $scope.ui.modifierClass = function (data) {
                var classLable = "";
                if (data.isBold) {
                    classLable = ' bold';
                }
                if (data.isItalic) {
                    classLable = ' italic';
                }
                if (data.isUnderline) {
                    classLable = ' underline';
                }
                return classLable;
            };
            $scope.ui.findElmByIndex = function (index) {
                var returnData;
                angular.forEach($scope.mainList, function (data) {
                    if (data.indexNo === parseInt(index)) {
                        returnData = data;
                    }
                });
                return returnData;
            };
            $scope.ui.setDividend = function () {

                $scope.ui.findElmByIndex(10).credit = $scope.model.dividends;
                $scope.profitCalc();
            };
            $scope.profitCalc = function () {
                $scope.ui.findElmByIndex(11).credit = $scope.ui.findElmByIndex(8).credit - parseFloat($scope.model.dividends);
            };
            $scope.model.financialYearLable = function (model) {
                var label;
                angular.forEach($scope.financialYearList, function (value) {
                    if (value.indexNo === model) {
                        label = value.name;
                        return;
                    }
                });
                return label;
            };
            $scope.ui.setFinancialYear = function () {
                var financialYear = $scope.model.financialYear;
                var fromDate = $filter('date')($scope.model.fromDate, 'yyyy-MM-dd');
                var toDate = $filter('date')($scope.model.toDate, 'yyyy-MM-dd');
                profitAndLostFactory.loadMainData(financialYear, fromDate, toDate, function (data) {
                    $scope.mainList = data;
                });
            };
            $scope.ui.init = function () {
                profitAndLostFactory.loadFinancialYearList(function (data) {
                    $scope.financialYearList = data;
                });
            };

            $scope.ui.init();

        });

