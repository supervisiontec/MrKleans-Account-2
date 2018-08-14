(function () {
    angular.module("appModule")
            .controller("editTransactionController", function ($scope, editTransactionModel,calculator, Notification, ConfirmPane) {
                $scope.model = new editTransactionModel();
                $scope.ui = {};
                $scope.param = {
                    "fromDate": null,
                    "toDate": null,
                    "branch": null,
                    "finacialYear": null,
                    "account": null,
                    "invDate": null,
                    "refNo": null

                };
                $scope.ui.selectLedgerType = function (name) {
                    $scope.model.getLedgerTypeList(name, $scope.param);
                };
                $scope.ui.clearParam = function () {
                    $scope.param = {
                        "fromDate": null,
                        "toDate": null,
                        "branch": null,
                        "finacialYear": null,
                        "account": null,
                        "invDate": null,
                        "refNo": null

                    };
                    $scope.model.accLedgerTypeDataList = [];
                    ;
                };
                $scope.ui.edit = function (number) {
                    $scope.model.getDeleteRefDetails(number);
                };
                $scope.ui.new = function () {
                    $scope.ui.mode = "NEW";
                };
                $scope.ui.setEdit = function (index, data) {
                    $scope.model.setEdit(index, data);
                };
                $scope.ui.checkDebitCredit = function () {
                    var debit = 0.00;
                    var credit = 0.00;
                    angular.forEach($scope.model.deleteRefDetailList, function (data) {
                        if (data.debit) {
                            debit += parseFloat(data.debit);
                        }
                        if (data.credit) {
                            credit += parseFloat(data.credit);
                        }
                    });
                    if (debit === credit && debit !== 0) {
                        return true;
                    }
                    return false;
                };
                //save
                $scope.ui.save = function () {
                    var check = true;
                    if (!$scope.ui.checkDebitCredit()) {
                        check = false;
                        Notification.error('transaction debit credit amount not match !');
                    }
                    if (!$scope.model.userPermission.add) {
                        check = false;
                        Notification.error('you have no permission ');
                    }
                    if (check) {
                        ConfirmPane.successConfirm("DO YOU WANT TO SAVE !")
                                .confirm(function () {
                                    $scope.model.save()
                                            .then(function (data) {
                                                Notification.success('save success.. !');
                                                //clear
                                                $scope.ui.mode = "IDEAL";
                                            });

                                });
                    }
                };
                $scope.ui.addData = function () {
                    var check = true;
                    if (check) {
                        $scope.model.addData();
                    }
                };
                $scope.ui.focusAddFunctionDebit = function (e, debit) {
                    var code = e ? e.keyCode || e.which : 13;
                    if (code === 13) {
                        var value = calculator.cal(debit);
                        console.log(value);
                        $scope.model.tempData.debit = value;
                    }
                };
                $scope.ui.focusAddFunctionCredit = function (e, credit) {
                    var code = e ? e.keyCode || e.which : 13;
                    if (code === 13) {
                        var value = calculator.cal(credit);
                        console.log(value);
                        $scope.model.tempData.credit = value;
                    }
                };
                $scope.ui.setIsEditClass = function (isEdit) {
                    if (isEdit === 0 || isEdit === null) {
                        return 'label-primary';
                    }
                    if (isEdit === 1) {
                        return 'label-warning';
                    }
                    if (isEdit === 2) {
                        return 'label-danger';
                    }
                };
                $scope.ui.delete = function () {
                    ConfirmPane.dangerConfirm("DO YOU WANT TO DELETE !")
                            .confirm(function () {
                                $scope.model.delete()
                                        .then(function (data) {
                                            Notification.success('save success.. !');
                                            //clear
                                            $scope.ui.mode = "IDEAL";
                                        });
                            });
                };

                $scope.ui.init = function () {
                    $scope.ui.mode = "IDEAL";
                };
                $scope.ui.init();
            });
}());