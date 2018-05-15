(function () {
    angular.module("appModule")
            .controller("editTransactionController", function ($scope, editTransactionModel, $timeout, Notification, ConfirmPane) {
                $scope.model = new editTransactionModel();
                $scope.ui = {};
                $scope.param = {
                    "fromDate": null,
                    "toDate": null,
                    "branch": null,
                    "finacialYear": null
                };


                $scope.ui.selectLedgerType = function (name) {
                    $scope.model.getLedgerTypeList(name, $scope.param);
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
                    console.log('debit credit func');
                    angular.forEach($scope.model.deleteRefDetailList, function (data) {
                        if (data.debit) {
                            debit += parseFloat(data.debit);
                        }
                        if (data.credit) {
                            credit += parseFloat(data.credit);
                        }
                    });
                    console.log('credit ' + credit);
                    console.log('debit ' + debit);
                    if (debit === credit && debit !== 0) {
                        return true;
                    }
                    return false;
                };
                //save
                $scope.ui.save = function () {
                    var check = true;
                    if (!$scope.ui.checkDebitCredit()) {
//                    if (true) {
                        check = false;
                        Notification.error('transaction debit credit amount not match !');
                    }
//                    if (!$scope.model.data.description) {
//                        check = false;
//                        Notification.error('insert description to save !');
//                    }
//                    if (!$scope.model.dataList.length) {
//                        check = false;
//                        Notification.error('insert data to save !');
//                    }
//                    if ($scope.model.data.totalDebit !== $scope.model.data.totalCredit && $scope.model.data.totalCredit !== 0) {
//                        check = false;
//                        Notification.error('total debit and credit amount is deference!');
//                    }
//                    if (!$scope.model.userPermission.add) {
//                        check = false;
//                        Notification.error('you have no permission ');
//                    }
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
//
//                //add data
                $scope.ui.addData = function () {
                    var check = true;
//                    if (!$scope.model.data.transactionDate) {
//                        check = false;
//                        Notification.error('insert date to add !');
//                    }
//                    if (!$scope.model.data.description) {
//                        check = false;
//                        Notification.error('insert description to add !');
//                    }
//                    if (!$scope.model.tempData.debit && !$scope.model.tempData.credit) {
//                        check = false;
//                        Notification.error('insert debit or credit amount to add !');
//                    }
//                    if ($scope.model.tempData.debit && $scope.model.tempData.credit) {
//                        check = false;
//                        Notification.error('invalied debit and credit amount !');
//                    }
//                    if (!$scope.model.tempData.accAccount) {
//                        check = false;
//                        Notification.error('select a account name to add !');
//                    }
//                    if (!$scope.model.tempData.branch) {
//                        check = false;
//                        Notification.error('select a branch name to add !');
//                    }
                    if (check) {
                        $scope.model.addData();
                    }
                };
//                $scope.ui.focusAddFunction = function (e) {
//                    var code = e ? e.keyCode || e.which : 13;
//                    if (code === 13) {
//                        $scope.ui.addData();
//                    }
//                };
//                $scope.ui.searchJournalByNumber = function (number) {
//                    var key = event ? event.keyCode || event.which : 13;
//                    if (key === 13) {
//                        $scope.model.searchJournalByNumber(number)
//                                .then(function () {
//                                    Notification.success('Find a journal entery ');
//                                }, function () {
//                                    Notification.error('Invalid number !!!');
//                                });
//                    }
//                };
//                $scope.ui.delete = function (index) {
//                    $scope.model.dataList.splice(index, 1);
//                    $scope.model.totalCalculated();
//                };
//                $scope.ui.edit = function (index, data) {
//                    $scope.model.dataList.splice(index, 1);
//                    $scope.model.tempData.accAccount = data.accAccount;
//                    $scope.model.tempData.debit = data.debit;
//                    $scope.model.tempData.credit = data.credit;
//                    $scope.model.tempData.branch = data.branch;
//                    $scope.model.tempData.costCenter = data.costCenter;
//                    $scope.model.tempData.costDepartment = data.costDepartment;
//                    $scope.model.totalCalculated();
//                    $scope.model.setMainAccount(data.accAccount);
//
//                };

                $scope.ui.init = function () {
                    $scope.ui.mode = "IDEAL";
                };
                $scope.ui.init();
            });
}());