(function () {
    //module
    angular.module("accountSettingModule", ['ui.bootstrap', 'ui-notification']);

    //http factory
    angular.module("accountSettingModule")
            .factory("accountSettingFactory", function ($http, systemConfig) {
                var factory = {};

                factory.loadAllSetting = function (callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/account/account-setting";
                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                callback(data);
                            });
                };
                factory.loadAllAccounts = function (callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/account/master/acc-account";
                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                callback(data);
                            });
                };
                factory.save = function (details, callback, errorCallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/account/account-setting/save";

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

                return factory;
            });


    //controller
    angular.module("accountSettingModule")
            .controller("accountSettingController", function ($scope, $filter, $log, ConfirmPane, $routeParams, accountSettingFactory, Notification, $timeout) {
                //data models 
                $scope.model = {};
                $scope.model.accSettingList = [];
                $scope.model.accAccountList = [];

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
                    angular.forEach($scope.model.accSettingList, function (setting) {
                        setting.tempAccount = setting.accAccount;
                    });
                    console.log($scope.model.accSettingList);
                };
                $scope.ui.save = function () {
                    ConfirmPane.successConfirm("Do you want to save account setting !")
                            .confirm(function () {
                                angular.forEach($scope.model.accSettingList, function (setting) {
                                    setting.accAccount = setting.tempAccount;
                                });
                                $scope.http.save();
                            });

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
                    var detail = $scope.model.accSettingList;
                    var detailJSON = JSON.stringify(detail);
                    accountSettingFactory.save(
                            detailJSON,
                            function (data) {
                                Notification.success('Setting save success !');
                            }),
                            function (data) {
                                Notification.error(data.message);
                            };
                };

                $scope.ui.init = function () {
                    //set ideal mode
                    $scope.ui.mode = "IDEAL";

                    accountSettingFactory.loadAllSetting(function (data) {
                        $scope.model.accSettingList = data;
                    });
                    accountSettingFactory.loadAllAccounts(function (data) {
                        $scope.model.accAccountList = data;
                    });

                };

                $scope.ui.init();
            });
}());

