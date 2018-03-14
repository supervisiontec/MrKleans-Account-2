(function () {
    angular.module("appModule")
            .controller("trialBalanceController", function ($scope, trialBalanceModel, $timeout, Notification) {
                $scope.model = new trialBalanceModel();
                $scope.ui = {};

                //focus
                $scope.ui.focus = function (id) {
                    $timeout(function () {
                        document.querySelectorAll(id)[0].focus();
                    }, 10);
                };
                //new
                $scope.ui.new = function () {
                    $scope.ui.mode = "NEW";
                    $scope.ui.focus('#date');
                    $scope.model.date = new Date();

                };
                $scope.ui.selectDate=function (){
                    console.log('data');
                    $scope.model.selectDate();
                };


                $scope.ui.init = function () {
                    $scope.ui.mode = "IDEAL";
                };
                $scope.ui.init();
            });
}());