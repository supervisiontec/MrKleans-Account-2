(function () {
    angular.module("appModule")
            .controller("voucherController", function ($scope, voucherModel, $timeout, Notification, ConfirmPane) {
                $scope.model = new voucherModel();
                $scope.ui = {};
                $scope.model.currentBranch = {};

                //focus
                $scope.ui.focus = function (id) {
                    $timeout(function () {
                        document.querySelectorAll(id)[0].focus();
                    }, 10);
                };
                //new
                $scope.ui.new = function () {
                    $scope.ui.mode = "NEW";
                    $scope.ui.focus('#account');
                    $scope.model.tempData.transactionDate = new Date();
                    $scope.model.tempData.branch = $scope.model.data.branch;
//                    $scope.model.data.branch = $scope.model.data.branch;
                };
                $scope.ui.addData = function () {
                    var checkSave = true;
                    if (!$scope.model.tempData.accAccount) {
                        checkSave = false;
                        Notification.error('Select a Account to add !');
                    }
                    if (!$scope.model.tempData.branch) {
                        checkSave = false;
                        Notification.error('Select a branch to add !');
                    }
                   
                    if (!$scope.model.tempData.transactionDate) {
                        checkSave = false;
                        Notification.error('insert a Date to add !');
                    }
                    if (!$scope.model.tempData.debit) {
                        checkSave = false;
                        Notification.error('insert amount to add !');
                    }
                    if (!$scope.model.data.accAccount) {
                        checkSave = false;
                        Notification.error('select a main account to add !');
                    }
                    if (checkSave) {
                        $scope.model.addData();
                        $scope.ui.new();
                    }
                };
                $scope.ui.save = function () {
                    var checkSave = true;
                    if (!$scope.model.data.accAccount) {
                        checkSave = false;
                        Notification.error('select a main account to save !');
                    }
                    if ($scope.model.saveDataList.length===0) {
                        checkSave = false;
                        Notification.error('Empty voucher item to save !');
                    }
                    if (!$scope.model.data.credit) {
                        checkSave = false;
                        Notification.error('total value 0.00 !');
                    }
                    if (checkSave) {
                        $scope.ui.mode = "IDEAL";
                        $scope.model.save()
                                .then(function (data) {
                                    Notification.success('Payment voucher save Sucess');
                                    $scope.model.data={};
                                    $scope.model.saveDataList=[];
                                });
                    }
                };

                $scope.ui.init = function () {
                    $scope.ui.mode = "IDEAL";

                };
                $scope.ui.init();
            });
}());