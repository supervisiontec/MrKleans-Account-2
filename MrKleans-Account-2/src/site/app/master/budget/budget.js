(function () {
    //module
    angular.module("budgetModule", ['ui.bootstrap', 'ui-notification']);

    //http factory
    angular.module("budgetModule")
            .factory("budgetFactory", function ($http, systemConfig) {
                var factory = {};

                factory.loadData = function (year, month, department, callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/budget/get-filtered-data-list/" + year + "/" + month + "/" + department;

                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                callback(data);
                            });
                };
                factory.loadActiveCostCenter = function (callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/cost-center/get-active-list";

                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                callback(data);
                            });
                };
                factory.loadActiveCostDepartment = function (callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/cost-department/get-active-list";

                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                callback(data);
                            });
                };
                factory.activeFinancialYear = function (callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/financial-year";

                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                callback(data);
                            });
                };

                factory.getPermission = function (form, callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/account/account-setting/user-permission/by-form/" + form;

                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                callback(data);
                            });
                };

                factory.save = function (summary, callback, errorCallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/budget/save";

                    $http.post(url, summary)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                if (errorCallback) {
                                    errorCallback(data);
                                }
                            });
                };

                //delete
                factory.deleteFactory = function (indexNo, callback, errorCallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/cost-center/delete/" + indexNo;
                    $http.delete(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                if (errorCallback) {
                                    errorCallback(data);
                                }
                            });
                };

                return factory;
            });

    //controller
    angular.module("budgetModule")
            .controller("budgetController", function ($scope, $rootScope, $filter, $log, ConfirmPane, $routeParams, budgetFactory, Notification, $timeout) {
                //data models 
                $scope.model = {};
                $scope.model.budget = {};

                //ui models
                $scope.ui = {};

                //http models
                $scope.http = {};

                //current ui mode IDEAL, SELECTED, NEW, EDIT
                $scope.ui.mode = null;

                //key event
                $rootScope.ctrlDown = false;

                //------------------ model functions ---------------------------
                //reset model
                $scope.model.reset = function () {
                    $scope.model.budget = {};
                    $scope.resetValue();
                };

                //<-----------------http funtiion------------------->
                $scope.http.save = function () {

                    var check = true;

                    if (!$scope.model.userPermission.add) {
                        check = false;
                        Notification.error('you have no permission !');
                    }
                    if (!$scope.model.budget.financialYear) {
                        check = false;
                        Notification.error('Financila Year is empty !');
                    }
                    if (!$scope.model.budget.budgetMonth) {
                        check = false;
                        Notification.error('Budget Month is empty !');
                    }
                    if (!$scope.model.budget.costDepartment) {
                        check = false;
                        Notification.error('Cost Department is empty !');
                    }

                    if (check) {
                        var detailList=[];

                        angular.forEach($scope.model.activeCostCenterList, function (value) {
                            var detail={};
                            detail.financialYear = $scope.model.budget.financialYear;
                            detail.budgetMonth = parseInt($filter('date')(new Date($scope.model.budget.budgetMonth), 'MM'));
                            detail.costDepartment = $scope.model.budget.costDepartment;
                            detail.costCenter = value.indexNo;
                            detail.budget = parseFloat(value.budget);
                            detailList.push(detail);
                        });
                        console.log(detailList);
                        var detailJSON = JSON.stringify(detailList);
                        budgetFactory.save(
                                detailJSON,
                                function (data) {
                                    Notification.success("Successfully Updated");
                                    $scope.model.reset();
//                                    $scope.ui.focus();
                                },
                                function (data) {
                                    Notification.error(data.message);
                                }
                        );
                    }
                };


                //<-----------------ui funtiion--------------------->
                //save function
                $scope.ui.save = function () {
                    $scope.http.save();
                };

                $scope.ui.changeTab = function () {
                    $scope.ui.mode = 'IDEAL';
                    $scope.model.client = {};
                };

                //focus
                $scope.ui.focus = function () {
                    $timeout(function () {
                        document.querySelectorAll("#name")[0].focus();
                    }, 10);
                };

                //new function
                $scope.ui.new = function () {
                    $scope.ui.mode = "NEW";
                    $scope.ui.focus();
                };

                //edit funtion
                $scope.ui.edit = function (data, index) {
                    $scope.ui.mode = "NEW";
                    $scope.model.data = data;
                    $scope.model.dataList.splice(index, 1);

                    $scope.ui.focus();
                };
                $scope.model.financialYearLable = function (indexNo) {
                    var data;
                    angular.forEach($scope.model.financialYearList, function (value) {
                        if (value.indexNo === parseInt(indexNo)) {
                            data = value.name;
                            return;
                        }
                    });
                    return data;
                };
                $scope.model.activeCostDepartmentLable = function (indexNo) {
                    var data;
                    angular.forEach($scope.model.activeCostDepartmentList, function (value) {
                        if (value.indexNo === parseInt(indexNo)) {
                            data = value.indexNo + " - " + value.name;
                            return;
                        }
                    });
                    return data;
                };
                $scope.onChange = function () {
                    console.log("start");
                    var year = $scope.model.budget.financialYear;
                    var month = parseInt($filter('date')(new Date($scope.model.budget.budgetMonth), 'MM'));
                    var department = $scope.model.budget.costDepartment;

                    console.log(year, month, department);
                    if (year && month && department) {

                        $scope.resetValue();
                        budgetFactory.loadData(year, month, department,
                                function (data) {
                                    $scope.model.tempDataList = data;
                                    $scope.loadValue();
                                });
                    }
                };
                $scope.loadValue = function () {
                    angular.forEach($scope.model.tempDataList, function (data) {
                        angular.forEach($scope.model.activeCostCenterList, function (value) {
                            if (parseInt(value.indexNo) === parseInt(data.costCenter)) {
                                console.log(value.name + " - " + data.budget);
                                value.budget = parseFloat(data.budget);
                                return;
                            }
                        });

                    });
                };
                $scope.resetValue = function () {
                    angular.forEach($scope.model.activeCostCenterList, function (value) {
                        value.budget = 0.00;
                    });
                };

                $scope.ui.init = function () {
                    //set ideal mode
                    $scope.ui.mode = "IDEAL";

                    //reset mdel
                    $scope.model.reset();

                    budgetFactory.loadActiveCostCenter(function (data) {
                        $scope.model.activeCostCenterList = data;
                    });
                    budgetFactory.loadActiveCostDepartment(function (data) {
                        $scope.model.activeCostDepartmentList = data;
                    });
                    budgetFactory.activeFinancialYear(function (data) {
                        $scope.model.financialYearList = data;
                    });
                    budgetFactory.getPermission('Budget', function (data) {
                        $scope.model.userPermission = data;
                    });

                };

                $scope.ui.init();
            });
}());