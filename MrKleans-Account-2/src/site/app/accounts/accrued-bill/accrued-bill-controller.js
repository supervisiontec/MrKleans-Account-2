(function () {
    angular.module("appModule")
            .controller("accruedBillController", function ($scope, $uibModalStack, $cookies, $uibModal, accruedBillModel, $timeout, $filter, calculator, Notification, ConfirmPane) {
                $scope.model = new accruedBillModel();
                $scope.ui = {};
                $scope.model.currentBranch = {};
                $scope.model.type = null;
                $scope.ui.costCenterRequired = false;
                $scope.ui.costDepartmentRequired = false;

                //focus
                $scope.ui.focus = function (id) {
                    $timeout(function () {
                        document.querySelectorAll(id)[0].focus();
                    }, 10);
                };
                $scope.ui.modelCancel = function () {
                    $uibModalStack.dismissAll();
                };
                //new
                $scope.ui.new = function () {
                    $scope.ui.mode = "NEW";
                    $scope.ui.focus('#account');
                    $scope.model.tempData.branch = $scope.model.data.branch;
                };
                $scope.ui.addData = function () {
                    var checkSave = true;
                    if (!$scope.model.tempData.accAccount) {
                        checkSave = false;
                        Notification.error('select a account to add !');
                    }
                    if (!$scope.model.tempData.branch) {
                        checkSave = false;
                        Notification.error('select a branch to add !');
                    }
                    if (!$scope.model.tempData.description) {
                        checkSave = false;
                        Notification.error('insert description to add !');
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
                    if ($scope.ui.costCenterRequired) {
                        if (!$scope.model.tempData.costCenter) {
                            checkSave = false;
                            Notification.error('select a cost center to add !');
                        }
                    }
                    if ($scope.ui.costDepartmentRequired) {
                        if (!$scope.model.tempData.costDepartment) {
                            checkSave = false;
                            Notification.error('select a cost Department to add !');
                        }
                    }
                    if (!$scope.model.data.typeIndexNo) {
                        checkSave = false;
                        Notification.error('select an accrued account to save !');
                    }
                    if (checkSave) {
                        $scope.model.addData();
                        $scope.ui.new();
                        $scope.ui.focus('#subAccount');
                    }
                };
                $scope.ui.popupSave = function () {
                    var checkSave = true;
                    if (!$scope.model.tempData.accAccount) {
                        checkSave = false;
                        Notification.error('select a account to add !');
                    }
                    if (!$scope.model.tempData.branch) {
                        checkSave = false;
                        Notification.error('select a branch to add !');
                    }
                    if (!$scope.model.tempData.description) {
                        checkSave = false;
                        Notification.error('insert description to add !');
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
                    if (!$scope.model.data.typeIndexNo) {
                        checkSave = false;
                        Notification.error('select an accrued account to save !');
                    }

                    if ($scope.ui.costDepartmentRequired) {
                        if (!$scope.model.tempData.costDepartment) {
                            checkSave = false;
                            Notification.error('select a cost Department to add !');
                        }
                    }
                    if (checkSave) {
                        $scope.model.addDataWithCostCenter();

                        $scope.ui.new();
                        $scope.ui.focus('#subAccount');
                        $scope.ui.modelCancel();
                    }
                };
                $scope.ui.viewModel = function () {
                    $uibModal.open({
                        animation: true,
                        ariaLabelledBy: 'modal-title',
                        ariaDescribedBy: 'modal-body',
                        templateUrl: 'costCenter.html',
                        scope: $scope,
                        size: 'sm'
                    });
                };
                $scope.ui.save = function () {
                    var checkSave = true;
                    if (!$scope.model.data.accAccount) {
                        checkSave = false;
                        Notification.error('select a main account to save !');
                    }
                    if ($scope.model.saveDataList.length === 0) {
                        checkSave = false;
                        Notification.error('empty transfer item to save !');
                    }
                    if (!$scope.model.data.credit) {
                        checkSave = false;
                        Notification.error('total value 0.00 !');
                    }
                    if (!$scope.model.data.description) {
                        checkSave = false;
                        Notification.error('insert description to save !');
                    }
                    if (!$scope.model.userPermission.add) {
                        checkSave = false;
                        Notification.error('you have no permission ');
                    }
                    var transactionDate = $filter('date')($scope.model.data.transactionDate, 'yyyy-MM-dd');
                    var newDate = $filter('date')(new Date(), 'yyyy-MM-dd');
                    if (transactionDate > newDate) {
                        checkSave = false;
                        Notification.error("transfer date not valid ! ");
                    }

                    if (checkSave) {
                        ConfirmPane.primaryConfirm("DO YOU WANT TO SAVE THIS BILL !")
                                .confirm(function () {
                                    $scope.model.save()
                                            .then(function (data) {
                                                Notification.success('Save Sucess. Accrued transaction No :' + data);
                                                $scope.ui.mode = "IDEAL";
//                                                $scope.model.data = {};
//                                                $scope.model.saveDataList = [];
                                            });
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
                $scope.ui.focusAdd = function (model, debit) {
                    if (model.which === 13) {
                        var value = calculator.cal(debit);
                        console.log(value);
                        $scope.model.tempData.debit = value;
                    }
                };
                $scope.ui.searchByNumber = function (number) {
                    var key = event ? event.keyCode || event.which : 13;
                    if (key === 13) {
                        $scope.model.searchByNumber(number)
                                .then(function () {

                                }, function () {
                                    Notification.error('Invalid number !!!');
                                });
                    }
                };
                $scope.ui.init = function () {
                    $scope.ui.mode = "IDEAL";
                    $scope.ui.costCenterRequired = $cookies.get("cost_center");
                    $scope.ui.costDepartmentRequired = $cookies.get("cost_department");

                };
                $scope.ui.init();
            });
}());