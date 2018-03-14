(function () {
//module
    angular.module("dashBoardModule", ['ui.bootstrap', 'ui-notification']);
    angular.module("dashBoardModule")
            .controller("dashBoardController", function ($scope, SecurityService,dashBoardModel) {
                $scope.model = new dashBoardModel();

                $scope.init = function () {
                    SecurityService.getViewTrue()
                            .success(function (data) {
                                $scope.permissionList = data;
                            });
                };
                $scope.init();
            });
}());