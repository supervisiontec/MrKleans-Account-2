(function () {
    angular.module("appModule")
            .controller("returnChequeController", function ($scope, returnChequeModel, $timeout, $filter, Notification, ConfirmPane) {
                $scope.model = new returnChequeModel();
                $scope.ui = {};
                $scope.model.currentBranch = {};
                $scope.model.type = null;

                //focus
                $scope.ui.focus = function (id) {
                    $timeout(function () {
                        document.querySelectorAll(id)[0].focus();
                    }, 10);
                };
                //new
                $scope.ui.new = function () {
                    $scope.ui.mode = "NEW";
                    $scope.model.data.transactionDate = new Date();
//                    $scope.model.tempData.branch = $scope.model.data.branch;
                };
                $scope.ui.checkType = function (type) {
                    $scope.model.checkType(type);
                };
                // save
                $scope.ui.save = function () {
                    ConfirmPane.primaryConfirm("DO YOU WANT TO SAVE CHEQUE RETURN !")
                                .confirm(function () {
                                    $scope.model.save()
                                            .then(function (data) {
                                                Notification.success('cheque return save Success !');
                                                $scope.ui.mode = "IDEAL";
                                            });
                                });
                };


                $scope.ui.focusAdd = function (model) {
                    if (model.which === 13) {
                        $scope.ui.addData();
                    }
                };
                $scope.ui.init = function () {
                    $scope.ui.mode = "IDEAL";

                };
                $scope.ui.init();
            });
}());