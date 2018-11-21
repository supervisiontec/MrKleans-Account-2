(function () {
    angular.module("appModule")
            .controller("trialBalanceController", function ($scope, trialBalanceModel, printService, $timeout, PrintPane, Notification) {
                $scope.model = new trialBalanceModel();
                $scope.printService = new printService();
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
                $scope.ui.selectDate = function () {
                    $scope.model.selectDate();
                };
                $scope.ui.exportExcel = function () {

                    PrintPane.printConfirm("")
                            .confirm(function () {
                                $scope.printService.printPdf('printDiv');
                            })
                            .discard(function () {
                                $scope.printService.printExcel('printDiv', 'Trial_Balance');
                            });
                };
                $scope.ui.viewLevel = function (data) {
                    $scope.model.viewLevel(data)
                            .then(function (data) {
                                Notification.success('Finded');
                            }, function () {
                                Notification.error('Empty');
                            });
                };


                $scope.ui.init = function () {
                    $scope.ui.mode = "IDEAL";
                };
                $scope.ui.init();
            });
}());