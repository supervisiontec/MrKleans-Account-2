
angular.module('profitAndLostModule', ['ui.bootstrap']);

angular.module("profitAndLostModule")
        .factory("profitAndLostFactory", function ($http, systemConfig) {
            var factory = {};

            factory.loadMainData = function (financialYear,callback) {
                console.log(financialYear);
                var url = systemConfig.apiUrl + "/api/care-point/transaction/profit-lost/load-profit-lost-main/"+financialYear;

                $http.get(url)
                        .success(function (data, status, headers) {
                            callback(data);
                        })
                        .error(function (data, status, headers) {
                            callback(data);
                        });
            };
            factory.getSubList = function (index, callback) {
                var url = systemConfig.apiUrl + "/api/care-point/transaction/profit-lost/get-sub-list/" + index;

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
        .controller('profitAndLostController', function ($scope, PrintPane, printService, profitAndLostFactory) {
            $scope.printService = new printService();
            $scope.selectedNode = "";
            $scope.clickList = [];
            $scope.mainList = [];
            $scope.financialYearList = [];

            $scope.ui = {};
            $scope.model = {};


            $scope.ui.print = function () {
                PrintPane.printConfirm("Print As..")
                        .confirm(function () {
                            $scope.printService.printPdf('printDiv');
                        })
                        .discard(function () {
                            $scope.printService.printExcel('printDiv', 'profit and lost');
                        });
            };

            $scope.ui.init = function () {
                profitAndLostFactory.loadFinancialYearList(function (data) {
                    $scope.financialYearList = data;
                });
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
                    profitAndLostFactory.getSubList(parseInt(indexNo), function (dataList) {
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
            $scope.ui.setFinancialYear=function () {
                var financialYear=$scope.model.financialYear;
                 profitAndLostFactory.loadMainData(financialYear,function (data) {
                    $scope.mainList = data;
                });
            };

            $scope.ui.init();

        });

