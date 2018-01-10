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
                
                return factory;
            });


    //controller
    angular.module("accountSettingModule")
            .controller("accountSettingController", function ($scope, $filter, $log, $routeParams, accountSettingFactory, Notification, $timeout) {
                //data models 
                $scope.model = {};
                $scope.model.accSettingList = [];

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

                    $scope.ui.focus('#any');
                };


                $scope.ui.init = function () {
                    //set ideal mode
                    $scope.ui.mode = "IDEAL";

                    accountSettingFactory.loadAllSetting(function (data) {
                        $scope.model.accSettingList = data;
                        console.log(data);
                    });

                };

                $scope.ui.init();
            });
}());

