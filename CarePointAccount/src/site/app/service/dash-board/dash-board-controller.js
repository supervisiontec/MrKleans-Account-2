(function () {
//module
    angular.module("dashBoardModule", ['ui.bootstrap', 'ui-notification']);
    angular.module("dashBoardModule")
            .controller("dashBoardController", function ($scope, dashBoardModel) {
                $scope.model = new dashBoardModel();

                
            });
}());