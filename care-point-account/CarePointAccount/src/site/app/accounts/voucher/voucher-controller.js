(function () {
    angular.module("appModule")
            .controller("voucherController", function ($scope, voucherModel, $timeout, $uibModal, Notification, ConfirmPane) {
                $scope.model = new voucherModel();
                $scope.ui = {};
                $scope.model.currentBranch = {};
                $scope.model.type = null;

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
                    $scope.model.data.transactionDate = new Date();
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

                    if (!$scope.model.data.transactionDate) {
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
                        $scope.ui.focus('#subAccount');
                    }
                };
                $scope.ui.save = function () {
                    var checkSave = true;
                    if (!$scope.model.data.accAccount) {
                        checkSave = false;
                        Notification.error('select a main account to save !');
                    }
                    if ($scope.model.saveDataList.length === 0) {
                        checkSave = false;
                        Notification.error('Empty voucher item to save !');
                    }
                    if (!$scope.model.data.credit) {
                        checkSave = false;
                        Notification.error('total value 0.00 !');
                    }
                    if ($scope.model.selectAccType.name==='BANK') {
                        
                        if (!$scope.model.data.refNumber) {
                            checkSave=false;
                            Notification.error('Cheque No is Empty !');
                        }
                        if (!$scope.model.data.chequeDate) {
                            checkSave=false;
                            Notification.error('plz enter Cheque Date !');
                        }
                        if (!$scope.model.data.description) {
                            checkSave=false;
                            Notification.error('Description is empty !');
                        }
                    }
                    if (checkSave) {
                        $scope.model.save()
                                .then(function (data) {
                                    Notification.success('Payment voucher save Sucess');
                                    $scope.ui.mode = "IDEAL";
                                    $scope.model.data = {};
                                    $scope.model.saveDataList = [];
                                });
                    }
                };
                $scope.ui.edit = function (index, data) {
                    $scope.model.tempData = data;
                    $scope.model.saveDataList.splice(index, 1);
                    $scope.model.totalCalculation();
                };
                $scope.ui.delete = function (index) {
                    $scope.model.saveDataList.splice(index, 1);
                    $scope.model.totalCalculation();
                };
                $scope.ui.selectAccType = function (type) {
                    $scope.model.selectAccType = type;
                    $scope.model.type = type.value;
                    $scope.ui.focus('#account');
                    $scope.model.setClear();                    
                    
                    console.log($scope.model.selectAccType);
                };
                $scope.ui.focusAdd = function (model) {
                    if (model.which === 13) {
                        $scope.ui.addData();
                    }
                };
                $scope.ui.init = function () {
                    $scope.ui.mode = "IDEAL";

                };
                $scope.ui.init();
            });
}());