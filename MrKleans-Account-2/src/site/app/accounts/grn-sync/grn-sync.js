(function () {
    //module
    angular.module("grnSyncModule", ['ui.bootstrap', 'ui-notification']);

    //http factory
    angular.module("grnSyncModule")
            .factory("grnSyncFactory", function ($http, systemConfig) {
                var factory = {};

                factory.loadData = function (param, callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/transaction/grn-sync/get-data";

                    $http.post(url, param)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                callback(data);
                            });
                };
                factory.saveData = function (data, callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/transaction/grn-sync/save-data";

                    $http.post(url, data)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                callback(data);
                            });
                };
                factory.loadBranch = function (callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/branch";

                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                callback(data);
                            });
                };
                factory.loadSupplier = function (callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/supplier/get-suppliers-grn";

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

    //controller
    angular.module("grnSyncModule")
            .controller("grnSyncController", function ($scope, $filter, $timeout, $uibModal, $uibModalStack, grnSyncFactory, ConfirmPane, Notification) {
                //data models 
                $scope.model = {};
                $scope.dataList = [];
                $scope.branchList = [];
                $scope.supplierList = [];
                $scope.imagemodelX = [];

                $scope.param = {
                    "branch": null,
                    "supplier": null,
                    "fromDate": null,
                    "toDate": null,
                    "grnNo": '',
                    "referenceNo": '',
                    "fDate": null,
                    "tDate": null
                };

                $scope.loadData = function () {
                    $scope.param.fDate = $filter('date')($scope.param.fromDate, 'yyyy-MM-dd');
                    $scope.param.tDate = $filter('date')($scope.param.toDate, 'yyyy-MM-dd');

                    if (!$scope.param.branch) {
                        $scope.param.branch = null;
                    }
                    if (!$scope.param.supplier) {
                        $scope.param.supplier = null;
                    }
                    if (!$scope.param.fDate) {
                        $scope.param.fDate = null;
                    }
                    if (!$scope.param.tDate) {
                        $scope.param.tDate = null;
                    }
                    if (!$scope.param.grnNo) {
                        $scope.param.grnNo = '';
                    }
                    if (!$scope.param.referenceNo) {
                        $scope.param.referenceNo = '';
                    }
                    grnSyncFactory.loadData($scope.param, function (data) {
                        $scope.dataList = data;
                        console.log(data);
                    });
                };

                $scope.loadBranch = function () {
                    grnSyncFactory.loadBranch(function (list) {
                        $scope.branchList = list;
                    });
                };
                $scope.loadSupplier = function () {
                    grnSyncFactory.loadSupplier(function (list) {
                        $scope.supplierList = list;
                    });
                };
                $scope.branchLable = function (regNo) {
                    var label;
                    angular.forEach($scope.branchList, function (data) {
                        if (regNo === data.regNumber) {
                            label = data.name + ' - ' + data.regNumber;
                            return;
                        }
                    });
                    return label;
                };
                $scope.supplierLable = function (suppCode) {
                    var label;
                    angular.forEach($scope.supplierList, function (data) {
                        if (suppCode === data[1]) {
                            label = data[0] + ' - ' + data[1];
                            return;
                        }
                    });
                    return label;
                };
                $scope.sync = function (data) {
                    ConfirmPane.primaryConfirm("DO YOU WANT TO SYNC THIS GRN TO ACCOUNT SYSTEM ?")
                            .confirm(function () {
                                grnSyncFactory.saveData(data,
                                        function (respond) {
                                            Notification.success("GRN (" + data[4] + ") sync successfully ");
                                            $scope.loadData();
                                        },
                                        function () {
                                            console.log("respond 2");
                                        });
                            });

                };
                $scope.viewImage = function (data) {
//                    $scope.imagemodelX[0] = "http://localhost:8087/master/image/download-image/" + data[4] + "-" + data[14] + "-0.jpg";
//                    $scope.imagemodelX[1] = "http://localhost:8087/master/image/download-image/" + data[4] + "-" + data[14] + "-1.jpg";
//                    $scope.imagemodelX[2] = "http://localhost:8087/master/image/download-image/" + data[4] + "-" + data[14] + "-2.jpg";
                    
                    $scope.imagemodelX[0] = "http://123.231.14.155:8087/master/image/download-image/" + data[4] + "-" + data[14] + "-0.jpg";
                    $scope.imagemodelX[1] = "http://123.231.14.155:8087/master/image/download-image/" + data[4] + "-" + data[14] + "-1.jpg";
                    $scope.imagemodelX[2] = "http://123.231.14.155:8087/master/image/download-image/" + data[4] + "-" + data[14] + "-2.jpg";

                    console.log($scope.imagemodelX[0]);
                    console.log($scope.imagemodelX[1]);
                    console.log($scope.imagemodelX[2]);

                    $uibModal.open({
                        animation: true,
                        ariaLabelledBy: 'modal-title',
                        ariaDescribedBy: 'modal-body',
                        templateUrl: 'imagePopup.html',
                        scope: $scope,
                        size: 'md'
                    });
                };
                $scope.ui.modelCancel = function () {
                    $uibModalStack.dismissAll();
                };

                $scope.init = function () {
                    $scope.mode = "IDEAL";
                    $scope.loadBranch();
                    $scope.loadSupplier();
                    $scope.loadData();
                };

                $scope.init();
            });
}());