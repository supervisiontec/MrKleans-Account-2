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
                    $scope.model.edit(account);
                    $scope.model.accAccountList.splice(index, 1);
                    $scope.model.data.clearAll = true;
                    $scope.ui.mode = "EDIT";

                };
                $scope.ui.delete = function (acc) {
                    if (!acc.isAccAccount) {
                        Notification.error("Can't delete because there are another transaction with relation  !");
                    } else {
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