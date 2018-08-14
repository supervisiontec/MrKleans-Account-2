(function () {
    angular.module("appModule")
            .controller("allTransactionController", function ($scope, journalModel, $timeout, Notification, ConfirmPane) {
                $scope.model = new journalModel();
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
                    $scope.model.refresh();
                    $scope.model.data.transactionDate = new Date();
                    $scope.model.setBranch();
                };

                //save
                $scope.ui.save = function () {
                    var check = true;
                    if (!$scope.model.data.transactionDate) {
                        check = false;
                        Notification.error('insert date to save !');
                    }
                    if (!$scope.model.data.description) {
                        check = false;
                        Notification.error('insert description to save !');
                    }
                    if (!$scope.model.dataList.length) {
                        check = false;
                        Notification.error('insert data to save !');
                    }
                    if ($scope.model.data.totalDebit !== $scope.model.data.totalCredit && $scope.model.data.totalCredit !== 0) {
                        check = false;
                        Notification.error('total debit and credit amount is deference!');
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
                                                Notification.success(' journal entery save success.. !');
                                                $scope.ui.mode = "IDEAL";
                                            });

                                });
                    }
                };

                //add data
                $scope.ui.addData = function () {
                    var check = true;
                    if (!$scope.model.data.transactionDate) {
                        check = false;
                        Notification.error('insert date to add !');
                    }
                    if (!$scope.model.data.description) {
                        check = false;
                        Notification.error('insert description to add !');
                    }
                    if (!$scope.model.tempData.debit && !$scope.model.tempData.credit) {
                        check = false;
                        Notification.error('insert debit or credit amount to add !');
                    }
                    if ($scope.model.tempData.debit && $scope.model.tempData.credit) {
                        check = false;
                        Notification.error('invalied debit and credit amount !');
                    }
                    if (!$scope.model.tempData.accAccount) {
                        check = false;
                        Notification.error('select a account name to add !');
                    }
                    if (!$scope.model.tempData.branch) {
                        check = false;
                        Notification.error('select a branch name to add !');
                    }
                    if (check) {
                        $scope.model.add();
                        $scope.ui.focus('#accMain');
                    }
                };
                $scope.ui.focusAddFunction = function (e) {
                    var code = e ? e.keyCode || e.which : 13;
                    if (code === 13) {
                        $scope.ui.addData();
                    }
                };
                $scope.ui.searchJournalByNumber = function (number) {
                    var key = event ? event.keyCode || event.which : 13;
                    if (key === 13) {
                        $scope.model.searchJournalByNumber(number)
                                .then(function () {
                                    Notification.success('Find a journal entery ');
                                }, function () {
                                    Notification.error('Invalid number !!!');
                                });
                    }
                };
                $scope.ui.delete = function (index) {
                    $scope.model.dataList.splice(index, 1);
                    $scope.model.totalCalculated();
                };
                $scope.ui.edit = function (index, data) {
                    $scope.model.dataList.splice(index, 1);
                    $scope.model.tempData.accAccount = data.accAccount;
                    $scope.model.tempData.debit = data.debit;
                    $scope.model.tempData.credit = data.credit;
                    $scope.model.tempData.branch = data.branch;
                    $scope.model.tempData.costCenter = data.costCenter;
                    $scope.model.tempData.costDepartment = data.costDepartment;
                    $scope.model.totalCalculated();
                    $scope.model.setMainAccount(data.accAccount);

                };

                $scope.ui.init = function () {
                    $scope.ui.mode = "IDEAL";
                };
                $scope.ui.init();
            });
}());