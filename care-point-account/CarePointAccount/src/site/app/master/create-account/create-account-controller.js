(function () {
    angular.module("appModule")
            .controller("createAccountController", function ($scope, createAccountModel, $timeout, Notification, ConfirmPane) {
                $scope.model = new createAccountModel();
                $scope.ui = {};


                //focus
                $scope.ui.focus = function () {
                    $timeout(function () {
                        document.querySelectorAll("#categoryMain")[0].focus();
                    }, 10);
                };
                //new
                $scope.ui.new = function () {
                    $scope.ui.mode = "NEW";
                    $scope.ui.focus();
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

                $scope.ui.init = function () {
                    $scope.ui.mode = "IDEAL";

                };
                $scope.ui.init();
            });
}());