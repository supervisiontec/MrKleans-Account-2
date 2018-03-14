(function () {
    //module
    angular.module("clientModule", ['ui.bootstrap', 'ui-notification']);

    //http factory
    angular.module("clientModule")
            .factory("clientFactory", function ($http, systemConfig) {
                var factory = {};

                factory.lordClientFactory = function (callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/client";

                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                callback(data);
                            });
                };
                factory.getNewClientList = function (callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/client/get-new-client-list";

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

                factory.saveClientFactory = function (summary, callback, errorCallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/client/insert-client";

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
                factory.deleteClientFactory = function (indexNo, callback, errorCallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/client/delete-client/" + indexNo;
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
    angular.module("clientModule")
            .controller("clientController", function ($scope, $rootScope, $filter, $log, $routeParams, clientFactory, Notification, $timeout) {
                //data models 
                $scope.model = {};
                $scope.model.newClientList = [];

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
                    $scope.model.client = {
                        "indexNo": null,
                        "name": null,
                        "address1": null,
                        "address2": null,
                        "address3": null,
                        "mobile": null,
                        "branch": null,
                        "nic": null,
                        "resident": null,
                        "date": null,
                        "isNew": false

                    };
                };




                //<-----------------http funtiion------------------->
                $scope.http.saveClient = function () {
                    $scope.model.client.branch = 1;
                    if (!$scope.model.client.date) {
                        $scope.model.client.date = $filter('date')(new Date(), 'yyyy-MM-dd');
                    }
                    $scope.model.client.isNew = false;

                    var detail = $scope.model.client;

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
                    if (!$scope.model.client.name) {
                        check = false;
                        Notification.error('name is empty !');
                    }
                    if (!$scope.model.client.resident) {
                        check = false;
                        Notification.error('resident is empty !');
                    }
                    if (check) {

                        var detailJSON = JSON.stringify(detail);
                        clientFactory.saveClientFactory(
                                detailJSON,
                                function (data) {
                                    $scope.model.clientList.splice($scope.model.customerPlace(data.indexNo), 1);
                                    $scope.model.clientList.push(data);
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

                $scope.http.deleteClient = function (indexNo, index) {
                    var check = true;
                    if (!$scope.model.userPermission.delete) {
                        check = false;
                        Notification.error('you have no permission  !');
                    }
                    if (check) {
                        clientFactory.deleteClientFactory(indexNo
                                , function () {
                                    $scope.model.clientList.splice(index, 1);
                                    Notification.success(indexNo + " - " + "Client Delete Successfully");
                                }
                        , function (data) {
                            Notification.error(data);
                        });
                    }
                };

                //<-----------------ui funtiion--------------------->
                //save function
                $scope.ui.save = function () {
                    $scope.http.saveClient();
                };
                $scope.ui.editNewClient = function (client, index) {
                    var check = true;
                    if (!$scope.model.userPermission.update) {
                        check = false;
                        Notification.error('you have no permission  !');
                    }
                    if (check) {
                        $scope.ui.mode = "EDIT";
                        $scope.model.client = client;
                        $scope.model.newClientList.splice($scope.model.customerPlace(client.indexNo), 1);
                    }
                };

                $scope.ui.changeTab = function () {
                    $scope.ui.mode = 'IDEAL';
                    $scope.model.client = {};
                };

                //focus
                $scope.ui.focus = function () {
                    $timeout(function () {
                        document.querySelectorAll("#resident")[0].focus();
                    }, 10);
                };

                //new function
                $scope.ui.new = function () {
                    $scope.ui.mode = "NEW";

                    $scope.ui.focus();
                };

                //edit funtion
                $scope.ui.edit = function (client, index) {
                    $scope.ui.mode = "EDIT";
                    $scope.model.client = client;
                    $scope.model.clientList.splice(index, 1);

                    $scope.ui.focus();
                };
                $scope.model.findCustomer = function (client) {
                    for (var i = 0; i < $scope.model.clientList.length; i++) {
                        if ($scope.model.clientList[i].indexNo === parseInt(client)) {
                            return $scope.model.clientList[i];
                        }
                    }
                    $scope.ui.mode = "EDIT";
                };
                $scope.model.customerPlace = function (clientId) {
                    for (var i = 0; i < $scope.model.clientList.length; i++) {
                        if ($scope.model.clientList[i].indexNo === parseInt(clientId)) {
                            return i;
                        }
                    }
                };
                $scope.ui.keyPressFunction = function ($event) {
                    if ($rootScope.ctrlDown && ($event.keyCode === 83)) {
                        $scope.ui.save();
                    } else if ($rootScope.ctrlDown && ($event.keyCode === 78)) {
                        $scope.ui.new();
                    } else if ($rootScope.ctrlDown && ($event.keyCode === 70)) {
                        $scope.ui.focus();
                    }
                };

                $scope.ui.init = function () {
                    //set ideal mode
                    $scope.ui.mode = "IDEAL";

                    //reset mdel
                    $scope.model.reset();
                    $scope.ui.new();

                    clientFactory.lordClientFactory(function (data) {
                        $scope.model.clientList = data;
                    });
                    clientFactory.getPermission('Client Registration', function (data) {
                        $scope.model.userPermission = data;
                    });
                    clientFactory.getNewClientList(function (data) {
                        $scope.model.newClientList = data;
                    });
                };

                $scope.ui.init();
            });
}());