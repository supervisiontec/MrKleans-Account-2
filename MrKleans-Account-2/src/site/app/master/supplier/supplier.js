(function () {
    //module
    angular.module("supplierModule", ['ui.bootstrap', 'ui-notification']);

    //http factory
    angular.module("supplierModule")
            .factory("supplierFactory", function ($http, systemConfig) {
                var factory = {};

                factory.lordSupplierFactory = function (callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/supplier";

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

                factory.saveSupplierFactory = function (summary, callback, errorCallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/supplier/save";

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
                factory.deleteSupplierFactory = function (indexNo, callback, errorCallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/supplier/delete/" + indexNo;
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
    angular.module("supplierModule")
            .controller("supplierController", function ($scope, $filter, $log, $routeParams, supplierFactory, Notification, $timeout) {
                //data models 
                $scope.model = {};

                //ui models
                $scope.ui = {};

                //http models
                $scope.http = {};

                //current ui mode IDEAL, SELECTED, NEW, EDIT
                $scope.ui.mode = null;

                //------------------ model functions ---------------------------
                //reset model
                $scope.model.reset = function () {
                    $scope.model.supplier = {
                        "indexNo": null,
                        "name": null,
                        "contactName": null,
                        "contactNo": null,
                        "address1": null,
                        "address2": null,
                        "address3": null,
                        "branch": null,
                        "type": null,
                        "creditPeriod": null,
                        "accAccount": null
                    };
                };

                //------------------ validation functions ------------------------------
                $scope.validateInput = function () {
                    if ($scope.model.supplier.name | $scope.model.supplier.contactNo | $scope.model.supplier.type !== null) {
                        return true;
                    } else {
                        return false;
                    }
                };


                //<-----------------http funtiion------------------->
                $scope.http.saveSupplier = function () {
                    var detail = $scope.model.supplier;

                    var detailJSON = JSON.stringify(detail);
                    supplierFactory.saveSupplierFactory(
                            detailJSON,
                            function (data) {
                                $scope.model.supplierList.splice($scope.model.supplierPlace(data.indexNo), 1);
                                $scope.model.supplierList.push(data);
                                Notification.success(data.indexNo + " - " + data.name + " Save Successfully");
                                $scope.model.reset();
                            },
                            function (data) {
                                Notification.error(data.message);
                            }
                    );
                };

                $scope.http.deleteSupplier = function (indexNo, index) {
                    var check = true;
                    if (!$scope.model.userPermission.delete) {
                        ch = false;
                        Notification.error('you have no permission  !');
                    }
                    if (check) {
                        supplierFactory.deleteSupplierFactory(indexNo
                                , function () {
                                    $scope.model.supplierList.splice(index, 1);
                                    Notification.success(indexNo + " - " + "Supplier Delete Successfully");
                                }
                        , function (data) {
                            Notification.error(data);
                        });
                    }
                };

                //<-----------------ui funtiion--------------------->
                //save function
                $scope.ui.save = function () {
                    var check = true;
                    if (!$scope.model.userPermission.add) {
                        ch = false;
                        Notification.error('you have no permission !');
                    }

                    if (check) {
                        if ($scope.validateInput()) {
                            $scope.http.saveSupplier();
                        } else {
                            Notification.error("Please input detail");
                        }
                    }
                };
                $scope.ui.editNewSupplier = function (supplier, index) {
                    $scope.ui.mode = "EDIT";
                    $scope.model.supplier = supplier;

                };
                $scope.ui.supplierTypeLable = function (supplierType) {
                    var lable = "";
                    angular.forEach($scope.model.supplierTypeList, function (value) {
                        if (value.indexNo === supplierType) {
                            lable = value.indexNo + " - " + value.name;
                            return;
                        }
                    });
                    return lable;
                };
                $scope.ui.changeTab = function () {
                    $scope.ui.mode = 'IDEAL';
                    $scope.model.supplier = {};
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
                $scope.ui.edit = function (supplier, index) {
                    var check = true;
                    if (!$scope.model.userPermission.update) {
                        check = false;
                        Notification.error('you have no permission  !');
                    }
                    if (check) {
                        $scope.ui.mode = "EDIT";
                        $scope.model.supplier = supplier;
                        $scope.model.supplierList.splice($scope.model.supplierPlace(supplier.indexNo), 1);

                        $scope.ui.focus();
                    }
                };
                $scope.model.findSupplier = function (supplier) {
                    for (var i = 0; i < $scope.model.supplierList.length; i++) {
                        if ($scope.model.supplierList[i].indexNo === parseInt(supplier)) {
                            return $scope.model.supplierList[i];
                        }
                    }
                    $scope.ui.mode = "EDIT";
                };
                $scope.model.supplierPlace = function (supplierId) {
                    for (var i = 0; i < $scope.model.supplierList.length; i++) {
                        if ($scope.model.supplierList[i].indexNo === parseInt(supplierId)) {
                            return i;
                        }
                    }
                };

                $scope.ui.init = function () {
                    //set ideal mode
                    $scope.ui.mode = "IDEAL";

                    //reset mdel
                    $scope.model.reset();

                    supplierFactory.lordSupplierFactory(function (data) {
                        $scope.model.supplierList = data;
                    });
                    supplierFactory.getPermission('Supplier Registration', function (data) {
                        $scope.model.userPermission = data;
                        console.log(data);
                    });

                };

                $scope.ui.init();
            });
}());