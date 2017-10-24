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
                    $scope.ui.mode = "NEW";

                };
                $scope.ui.delete = function (accIndex, index) {
                    ConfirmPane.warningConfirm("DO YOU WANT TO DELECT !")
                            .confirm(function () {
                                $scope.model.delete(accIndex, index)
                                        .then(function (data) {
                                            Notification.success('Delete success !');
                                        });
                            });
                };

                $scope.ui.save = function () {
                    console.log($scope.model.data.accCategory1);
                    console.log($scope.model.data.accCategory1);
                    console.log($scope.model.data.accCategory1.indexNo);
                    var checkSave = true;
                    if (!$scope.model.data.accMain.indexNo) {
                        checkSave = false;
                        Notification.error('Select a main category to save !');
                    }

                    if (angular.isUndefined($scope.model.data.accCategory1.indexNo)) {
                        if (!$scope.model.data.accCategory1) {
                            checkSave = false;
                            Notification.error('Select a category1 to save !');
                        }
                    } else if (!$scope.model.data.accCategory1.indexNo) {
                        checkSave = false;
                        Notification.error('Select a category1 to save !');

                    }
                    if (angular.isUndefined($scope.model.data.accCategory2.indexNo)) {
                        if (!$scope.model.data.accCategory2) {
                            checkSave = false;
                            Notification.error('Select a category2 to save !');
                        }
                    } else if (!$scope.model.data.accCategory2.indexNo) {
                        checkSave = false;
                        Notification.error('Select a category2 to save !');

                    }

                    if (!$scope.model.data.name) {
                        checkSave = false;
                        Notification.error('Type account name to save !');
                    }
                    if (checkSave) {
                        $scope.ui.mode = "IDEAL";
                        $scope.model.save()
                                .then(function (data) {
                                    Notification.success('New Account save Sucess');
                                    if ($scope.model.data.clearAll) {
                                        $scope.model.clearData();
                                    } else {
                                        $scope.model.data = data;
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