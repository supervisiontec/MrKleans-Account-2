(function () {
    //module
    angular.module("accCodeSettingModule", ['ui.bootstrap', 'ui-notification']);

    //http factory
    angular.module("accCodeSettingModule")
            .factory("accCodeSettingFactory", function ($http, systemConfig) {
                var factory = {};

                factory.loadAccCodeSetting = function (callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/account/account-code-setting";
                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                callback(data);
                            });
                };
                factory.loadAllAccounts = function (callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/account/master/acc-account/find-only-account";
                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                callback(data);
                            });
                };
                factory.save = function (details, callback, errorCallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/account/account-code-setting/save";

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
                //delete
                factory.delete = function (indexNo, callback, errorCallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/account/account-code-setting/delete/" + indexNo;
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
    angular.module("accCodeSettingModule")
            .controller("accCodeSettingController", function ($scope, ConfirmPane, accCodeSettingFactory, Notification, $timeout) {
                //data models 
                $scope.model = {};
                $scope.model.accCodeSettingList = [];
                $scope.model.accAccountList = [];
                $scope.model.codeSetting = {};


                //ui models
                $scope.ui = {};

                //http models
                $scope.http = {};

                //current ui mode IDEAL, SELECTED
                $scope.ui.mode = null;


                //focus
                $scope.ui.focus = function (id) {
                    $timeout(function () {
                        document.querySelectorAll(id)[0].focus();
                    }, 10);
                };

                //new function
                $scope.ui.new = function () {
                    $scope.ui.mode = "NEW";
                    $scope.model.codeSetting.maxNo = 0;

                };
                $scope.ui.save = function () {
                    ConfirmPane.successConfirm("Do you want to save account code setting !")
                            .confirm(function () {
                                $scope.http.save();
                            });

                };
                $scope.ui.edit = function (setting, index) {
                    $scope.ui.mode = "NEW";
                    $scope.model.codeSetting = setting;
                    $scope.model.accCodeSettingList.splice(index, 1);
                };
                $scope.model.accAccountLabel = function (account) {
                    var lable = "";
                    angular.forEach($scope.model.accAccountList, function (value) {
                        if (value.indexNo === account) {
                            lable = value.accCode + " - " + value.name;
                            return;
                        }
                    });
                    return lable;
                };

                $scope.http.save = function () {
                    var detail = $scope.model.codeSetting;
                    var detailJSON = JSON.stringify(detail);
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
                    if (check) {
                        accCodeSettingFactory.save(
                                detailJSON,
                                function (data) {
                                    if (data > 0) {
                                        $scope.ui.clear();
                                        Notification.success('Code setting save success !');
                                    } else {
                                        Notification.error('Account code already exists or some error');
                                    }
                                }),
                                function (data) {
                                    Notification.error('Account code already exists or some error');
                                };
                    }
                };
                $scope.http.delete = function (indexNo, index) {
                    ConfirmPane.successConfirm("Do you want to delete account code setting !")
                            .confirm(function () {

                                var check = true;
                                if (!$scope.model.userPermission.delete) {
                                    check = false;
                                    Notification.error('you have no permission  !');
                                }
                                if (check) {
                                    accCodeSettingFactory.delete(indexNo
                                            , function () {
                                                $scope.model.accCodeSettingList.splice(index, 1);
                                                Notification.success("Account Code Delete Success !");
                                            }
                                    , function (data) {
                                        Notification.error(data);
                                    });
                                }
                            });
                };
                $scope.ui.clear = function () {
                    $scope.model.codeSetting = {};
                    $scope.model.codeSetting.maxNo = 0;
                    $scope.model.loadAccCodeSetting();

                };
                $scope.model.loadAccCodeSetting = function () {
                    accCodeSettingFactory.loadAccCodeSetting(function (data) {
                        $scope.model.accCodeSettingList = data;
                    });
                };
                $scope.ui.init = function () {
                    //set ideal mode
                    $scope.ui.mode = "IDEAL";
                    $scope.model.loadAccCodeSetting();

                    accCodeSettingFactory.loadAllAccounts(function (data) {
                        $scope.model.accAccountList = data;
                    });
                    accCodeSettingFactory.getPermission('Code Setting', function (data) {
                        $scope.model.userPermission = data;
                    });

                };

                $scope.ui.init();
            });
}());


