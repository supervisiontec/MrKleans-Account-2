(function () {
    //module
    angular.module("userPermissionModule", ['ui.bootstrap', 'ui-notification']);

    //http factory
    angular.module("userPermissionModule")
            .factory("userPermissionFactory", function ($http, systemConfig) {
                var factory = {};

                factory.loadUsers = function (callback) {
                    var url = systemConfig.apiUrl + "/security/user-list";
                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                callback(data);
                            });
                };
                factory.loadForm = function (callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/form-name";
                    $http.get(url)
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
                factory.loadUserPermissionDetails = function (indexNo, callback, errorCallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/account/account-setting/user-permission/by-user/" + indexNo;
                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                if (errorCallback) {
                                    errorCallback(data);
                                }
                            });
                };
                factory.save = function (details, callback, errorCallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/account/account-setting/user-permission/save";

                    $http.post(url, details)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                if (errorCallback) {
                                    errorCallback(data);
                                }
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

                return factory;
            });


    //controller
    angular.module("userPermissionModule")
            .controller("userPermissionController", function ($scope, $filter, $log, ConfirmPane, $routeParams,
                    userPermissionFactory, Notification, $timeout) {
                //data models 
                $scope.model = {};
                $scope.model.userList = [];
                $scope.model.detailList = [];
                $scope.model.formList = [];
                $scope.model.branchList = [];
                $scope.model.permission = {};

                //ui models
                $scope.ui = {};

                //http models
                $scope.http = {};

                //current ui mode IDEAL, SELECTED
                $scope.ui.mode = null;


//                //focus
                $scope.ui.focus = function (id) {
                    $timeout(function () {
                        document.querySelectorAll(id)[0].focus();
                    }, 10);
                };
//
//                //new function
                $scope.ui.new = function () {
                    $scope.ui.mode = "NEW";
                    $scope.ui.focus('#user');
                };

                $scope.ui.selectUser = function (user) {
                    userPermissionFactory.loadUserPermissionDetails(user, function (data) {
                        $scope.model.detailList = [];
                        angular.forEach(data, function (value) {
                            value.name = $scope.model.formLabel(value.formName);
                            $scope.model.detailList.push(value);
                        });
                    });
                };
                $scope.ui.refresh = function () {
                    if ($scope.model.selectedUser) {
                        $scope.ui.selectUser($scope.model.selectedUser);
                    }
                };
                $scope.ui.permissionTypeClick = function (type, selected) {
                    console.log(selected);
                    if (type === 'all') {
                        $scope.ui.setAllPermission(selected);
                    }
                    if (type === 'view') {
                        $scope.ui.setViewPermission(selected);
                    }
                    if (type === 'add') {
                        $scope.ui.setAddPermission(selected);
                    }
                    if (type === 'update') {
                        $scope.ui.setEditPermission(selected);
                    }
                    if (type === 'delete') {
                        $scope.ui.setDeletePermission(selected);
                    }
                };
                $scope.ui.setAllPermission = function (selected) {
                    angular.forEach($scope.model.detailList, function (detail) {
                        detail.view = selected;
                        detail.add = selected;
                        detail.update = selected;
                        detail.delete = selected;
                    });
                    $scope.model.permission.view = selected;
                    $scope.model.permission.add = selected;
                    $scope.model.permission.update = selected;
                    $scope.model.permission.delete = selected;
                };
                $scope.ui.setViewPermission = function (selected) {
                    angular.forEach($scope.model.detailList, function (detail) {
                        detail.view = selected;
                    });
                };
                $scope.ui.setAddPermission = function (selected) {
                    angular.forEach($scope.model.detailList, function (detail) {
                        detail.add = selected;
                    });
                };
                $scope.ui.setEditPermission = function (selected) {
                    angular.forEach($scope.model.detailList, function (detail) {
                        detail.update = selected;
                    });
                };
                $scope.ui.setDeletePermission = function (selected) {
                    angular.forEach($scope.model.detailList, function (detail) {
                        detail.delete = selected;
                    });
                };
                $scope.ui.save = function () {
                    console.log($scope.model.userPermission);
                    var check = true;
                    if (!$scope.model.userPermission.add) {
                        check = false;
                        Notification.error('you have no permission !');
                    }
                    if (check) {
                        ConfirmPane.successConfirm("Do you want to save user permissions !")
                                .confirm(function () {
                                    $scope.http.save();
                                });
                    }

                };
                $scope.model.userLabel = function (user) {
                    var lable = "";
                    angular.forEach($scope.model.userList, function (value) {
                        if (value.indexNo === parseInt(user)) {
                            lable = value.indexNo + " - " + value.name;
                            return;
                        }
                    });
                    return lable;
                };
                $scope.model.getUser = function (user) {
                    var lable = {};
                    angular.forEach($scope.model.userList, function (value) {
                        if (value.indexNo === parseInt(user)) {
                            lable = value;
                            return;
                        }
                    });
                    return lable;
                };
                $scope.model.formLabel = function (form) {
                    var lable = "";
                    angular.forEach($scope.model.formList, function (value) {
                        if (value.indexNo === parseInt(form)) {
                            lable = value.name;
                            return;
                        }
                    });
                    return lable;
                };
                $scope.model.branchLable = function (user) {
                    var branch = $scope.model.getUser(user).branch;
                    var lable = "";
                    angular.forEach($scope.model.branchList, function (value) {
                        if (value.indexNo === parseInt(branch)) {
                            lable = value.branchCode + ' - ' + value.name;
                            return;
                        }
                    });
                    return lable;
                };
                $scope.http.save = function () {
                    var mix = {
                        userPermissionList: $scope.model.detailList
                    };
                    console.log(mix);
                    userPermissionFactory.save(
                            JSON.stringify(mix),
                            function (data) {
                                Notification.success('user permission save success !');
                            }),
                            function (data) {
                                Notification.error(data.message);
                            };
                };

                $scope.ui.init = function () {
                    //set ideal mode
                    $scope.ui.mode = "IDEAL";

                    userPermissionFactory.loadUsers(function (data) {
                        $scope.model.userList = data;
                    });
                    userPermissionFactory.loadForm(function (data) {
                        $scope.model.formList = data;
                    });
                    userPermissionFactory.loadBranch(function (data) {
                        $scope.model.branchList = data;
                    });
                    userPermissionFactory.getPermission('User Permission', function (data) {
                        $scope.model.userPermission = data;
                    });

                };

                $scope.ui.init();
            });
}());

