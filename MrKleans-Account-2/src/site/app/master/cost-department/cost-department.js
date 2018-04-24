(function () {
    //module
    angular.module("costDepartmentModule", ['ui.bootstrap', 'ui-notification']);

    //http factory
    angular.module("costDepartmentModule")
            .factory("costDepartmentFactory", function ($http, systemConfig) {
                var factory = {};

                factory.loadData = function (callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/cost-department";
                    console.log("factory");
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
                    var url = systemConfig.apiUrl + "/api/care-point/master/cost-department/save";

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
                    var url = systemConfig.apiUrl + "/api/care-point/master/cost-department/delete/" + indexNo;
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
    angular.module("costDepartmentModule")
            .controller("costDepartmentController", function ($scope, $rootScope, $filter, $log, ConfirmPane, $routeParams, costDepartmentFactory, Notification, $timeout) {
                //data models 
                $scope.model = {};

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
                    $scope.model.data = {
                        "indexNo": null,
                        "name": null,
                        "isActive": true
                    };
                };




                //<-----------------http funtiion------------------->
                $scope.http.save = function () {
                    var detail = $scope.model.data;

                    var check = true;
                    if (!detail.indexNo) {
                        if (!$scope.model.userPermission.add) {
                            check = false;
                            Notification.error('you have no permission !');
                        }
                    } else {
                        if (!$scope.model.userPermission.update) {
                            check = false;
                            Notification.error('you have no permission !');
                        }
                    }
                    if (!$scope.model.data.name) {
                        check = false;
                        Notification.error('name is empty !');
                    }

                    if (check) {

                        var detailJSON = JSON.stringify(detail);
                        costDepartmentFactory.save(
                                detailJSON,
                                function (data) {
//                                    $scope.model.dataList.splice($scope.model.customerPlace(data.indexNo), 1);
                                    $scope.model.dataList.push(data);
                                    Notification.success(data.indexNo + " - " + data.name + " Save Successfully");
                                    $scope.model.reset();
                                    $scope.ui.focus();
                                },
                                function (data) {
                                    Notification.error(data.message);
                                }
                        );
                    }
                };

                $scope.http.delete = function (indexNo, index) {
                    var check = true;
                    if (!$scope.model.userPermission.delete) {
                        check = false;
                        Notification.error('you have no permission  !');
                    }
                    if (check) {
                        ConfirmPane.dangerConfirm("do you want to delete !")
                                .confirm(function () {
                                    costDepartmentFactory.deleteFactory(indexNo
                                            , function () {
                                                $scope.model.dataList.splice(index, 1);
                                                Notification.success(indexNo + " - " + "Cost Center Delete Successfully");
                                            }
                                    , function (data) {
                                        Notification.error(data);
                                    });

                                })
                                .discard(function () {
                                    console.log('discard');
                                });


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
                    console.log("new");
                    $scope.ui.focus();
                };

                //edit funtion
                $scope.ui.edit = function (data, index) {
                    $scope.ui.mode = "NEW";
                    $scope.model.data = data;
                    $scope.model.dataList.splice(index, 1);

                    $scope.ui.focus();
                };


                $scope.ui.init = function () {
                    //set ideal mode
                    $scope.ui.mode = "IDEAL";
                    console.log("init");

                    //reset mdel
                    $scope.model.reset();

                    costDepartmentFactory.loadData(function (data) {
                        console.log(data);
                        $scope.model.dataList = data;
                    });
                    costDepartmentFactory.getPermission('Cost Department', function (data) {
                        $scope.model.userPermission = data;
                    });

                };

                $scope.ui.init();
            });
}());