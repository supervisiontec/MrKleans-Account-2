(function () {
    angular.module("appModule")
            .controller("journalController", function ($scope, journalModel, $timeout, Notification, ConfirmPane) {
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
                    $scope.model.new();
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
                                                Notification.success(data + ' data records save success !');
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
                                    
                                }, function () {
                                    Notification.error('Invalid number !!!');
                                });
                    }
                };

                $scope.ui.init = function () {
                    $scope.ui.mode = "IDEAL";
                };
                $scope.ui.init();
            });
}());