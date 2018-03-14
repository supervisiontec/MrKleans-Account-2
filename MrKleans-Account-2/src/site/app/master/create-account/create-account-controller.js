(function () {
    angular.module("appModule")
            .controller("createAccountController", function ($scope,printService,$filter, createAccountModel, $timeout, PrintPane, Notification, ConfirmPane) {
                $scope.model = new createAccountModel();
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
                    $scope.ui.focus("#sub_acc_of");
                };
                $scope.ui.edit = function (account, index) {
                    if (!$scope.model.userPermission.update) {
                        Notification.error('you have no permission to edit account !');
                    } else {
                        $scope.model.edit(account);
                        $scope.model.accAccountList.splice(index, 1);
                        $scope.model.data.clearAll = true;
                        $scope.ui.mode = "EDIT";
                    }

                };
                $scope.ui.refersh = function () {
                    $scope.model.refresh();
                };
                $scope.ui.delete = function (acc) {
                    var check = true;
                    if (!acc.isAccAccount) {
                        check = false;
                        Notification.error("Can't delete because there are another transaction with relation  !");
                    }
                    if (!$scope.model.userPermission.delete) {
                        check = false;
                        Notification.error('you have no permission to delete account !');
                    }
                    if (check) {
                        ConfirmPane.warningConfirm("DO YOU WANT TO DELECT !")
                                .confirm(function () {
                                    $scope.model.delete(acc.indexNo)
                                            .then(function (data) {
                                                Notification.success('Delete success !');
                                            });
                                });
                    }

                };

                $scope.ui.save = function () {
                    var checkSave = true;
                    if (!$scope.model.data.name) {
                        checkSave = false;
                        Notification.error('Insert a name to save !');
                    }

                    if (!$scope.model.data.accType) {
                        checkSave = false;
                        Notification.error('Select Account Type to save !');
                    }

                    if (!$scope.model.data.subAccountOf) {
                        checkSave = false;
                        Notification.error('Select subAccountOf to save !');
                    }
                    if (!$scope.model.data.indexNo) {
                        if (!$scope.model.userPermission.add) {
                            checkSave = false;
                            Notification.error('you have no permission to save new account !');
                        }
                    } else {
                        if (!$scope.model.userPermission.update) {
                            checkSave = false;
                            Notification.error('you have no permission to edit account !');
                        }
                    }
                    if (checkSave) {
                        $scope.ui.mode = "IDEAL";
                        $scope.model.save()
                                .then(function (data) {
                                    Notification.success('New Account save Sucess');
                                    if ($scope.model.data.clearAll) {
                                        $scope.model.clearData();
                                    }
                                });
                    }
                };
                $scope.ui.exportExcel = function () {

                    PrintPane.printConfirm("")
                            .confirm(function () {
//                                var divToPrint = document.getElementById("printDiv");
//                                newWin = window.open("financial_accounts");
//                                newWin.document.write(divToPrint.outerHTML);
//                                newWin.print();
//                                newWin.close();
                            $scope.printService.printPdf('printDiv');
                            })
                            .discard(function () {
                            $scope.printService.printExcel('printDiv');
//                                var blob = new Blob([document.getElementById('printDiv').innerHTML], {
////                                    type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
//                                    type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
//                                });
//                                 var date=$filter('date')(new Date(), 'yyyy-MM-dd HH:mm:ss');
//                                FileSaver.saveAs(blob, "financial_accounts("+date+").xls");
                            });
                };
                $scope.ui.getFinalAccountCount = function (list) {
                    var count = 0;
                    angular.forEach(list, function (val) {
                        if (val.isAccAccount) {
                            count++;
                        }
                        return;
                    });
                    return count;
                };

                $scope.ui.init = function () {
                    $scope.ui.mode = "IDEAL";

                };
                $scope.ui.init();
            });
}());