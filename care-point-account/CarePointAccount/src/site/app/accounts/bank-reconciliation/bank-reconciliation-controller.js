(function () {
    angular.module("appModule")
            .controller("bankReconciliationController", function ($scope, $filter, bankReconciliationModel, $timeout, $uibModalStack, $uibModal, Notification, ConfirmPane) {
                $scope.model = new bankReconciliationModel();
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
                    $scope.ui.focus('#bankAccount');
                    $scope.model.tempData.transactionDate = new Date();

                };
                $scope.ui.selectReconciliationCheque = function (data) {
                    var check = true;
                    if (!$scope.model.data.transactionDate) {
                        check = false;
                        Notification.error('select a reconciliation date !');
                    }
                    if (!$scope.model.data.accAccount) {
                        check = false;
                        Notification.error('select an account to reconcile selected cheque !');
                    }
                    var transactionDate = $filter('date')($scope.model.data.transactionDate, 'yyyy-MM-dd');
                    var newDate = $filter('date')(new Date(), 'yyyy-MM-dd');
                    if (transactionDate > newDate) {
                        check = false;
                        Notification.error("transfer date not valid ! ");
                    }
                    if (check) {
                        ConfirmPane.primaryConfirm("DO YOU WANT TO RECONCILE THIS CHEQUE !")
                                .confirm(function () {
                                    $scope.model.addReconciliationCheque(data);
                                });
                    }
                };
                $scope.ui.addReconciliationData = function () {
                    if ($scope.model.data.accAccount) {

                        $scope.model.setIncomeExpenceAccount();
                        $uibModal.open({
                            animation: true,
                            ariaLabelledBy: 'modal-title',
                            ariaDescribedBy: 'modal-body',
                            templateUrl: 'reconciliationPopup.html',
                            scope: $scope,
                            size: 'lg'
                        });
                    } else {
                        Notification.error('select a bank account to view oher reconcile popup ! ');
                    }
                };
                $scope.ui.addClass = function (data) {

                    if (data.class === 'reconcile') {
                        return 'bg-danger';
                    }
                    if (data.class === 'other') {
                        return 'bg-success';
                    }
                    return 'background-dark';
                };

                $scope.ui.modelCancel = function () {
                    $uibModalStack.dismissAll();
                };
                $scope.ui.save = function () {
                    var cheque = true;
                    if (!$scope.ui.reconcileCheque()) {
                        cheque = false;
                        Notification.error('Reconcile Cheque is empty !');
                    }
                    if (cheque) {
                        $scope.model.save();
                    }
                };
                $scope.ui.reconcileCheque = function () {
                    var cheque = false;
                    angular.forEach($scope.model.saveDataList, function (data) {
                        if (data.class === 'reconcile') {
                            cheque = true;
                            return;
                        }

                    });
                    return cheque;
                };
                $scope.ui.delete = function (data) {
                    if (data.class) {
                        $scope.model.remove(data);
                    } else {
                        ConfirmPane.warningConfirm("DO YOU WANT TO DELETE THIS TRANSACTION !")
                                .confirm(function () {
                                    $scope.model.delete(data);
                                });

                    }
                };

                $scope.ui.popupSelectAccAccount = function (indexNo) {
                    $scope.model.disableValue = '';
                    angular.forEach($scope.model.incomeExpenceAccList, function (data) {
                        if (data.indexNo === indexNo) {
                            $scope.model.disableValue = data.accMain.isExpence ? 'debit' : 'credit';
                            return;
                        }
                    });
                };
                $scope.ui.popupAddData = function () {
                    var check = true;
                    if (!$scope.model.tempData.accAccount) {
                        check = false;
                        Notification.error("select an account to add data ");
                    }
                    if (!$scope.model.tempData.branch) {
                        check = false;
                        Notification.error("select a branch to add data");
                    }
                    if (!$scope.model.tempData.transactionDate) {
                        check = false;
                        Notification.error("select a date to add data");
                    }
                    if (!$scope.model.tempData.credit && !$scope.model.tempData.debit) {
                        check = false;
                        Notification.error("add credit or debit amount to add date");
                    }
                    if (check) {
                        $scope.model.popupAddData();
                    }
                };
                $scope.ui.popupSave = function () {
                    var check = true;
                    if (!$scope.model.popupDataList.length) {
                        check = false;
                        Notification.error('Empty data to save');
                    }
                    if (check) {
                        $scope.model.popupSave();
                        $scope.ui.modelCancel();
                    }
                };

                $scope.ui.init = function () {
                    $scope.ui.mode = "IDEAL";

                };
                $scope.ui.init();
            });
}());