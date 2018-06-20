(function () {
    angular.module("paymentVoucherModule", ['ui.bootstrap']);
    angular.module("paymentVoucherModule")
            .controller("customerPaymentVoucherController", function ($scope, $filter,$timeout, customerPaymentVoucherModel, Notification, ConfirmPane) {

                $scope.model = new customerPaymentVoucherModel();
                $scope.ui = {};

                 //focus
                $scope.ui.focus = function (id) {
                    $timeout(function () {
                        document.querySelectorAll(id)[0].focus();
                    }, 10);
                };

                $scope.ui.new = function () {
                    $scope.ui.mode = "NEW";
                    $scope.ui.focus('#customer');
                    $scope.model.typeCash = true;
                    $scope.model.typeOverPayment = false;
                    $scope.model.data.transactionDate = new Date();
                };
                $scope.ui.checkType = function (type) {
                    $scope.ui.focus('#account');
                    console.log(type);
                    $scope.model.checkType(type);
                };
                 $scope.ui.checkTypeSub = function (type) {
                    $scope.model.checkTypeSub(type);
                };
                $scope.ui.save = function () {
                    var checkSave = true;
                    if (!$scope.model.data.accAccount && $scope.model.data.accTypeSub !== "RETURN") {
                        checkSave = false;
                        Notification.error('select an account to save !');
                    }
                    if (!$scope.model.userPermission.add) {
                        checkSave = false;
                        Notification.error('you have no permission ');
                    }

                    if (!$scope.model.data.credit && $scope.model.data.accTypeSub !== "RETURN") {
                        checkSave = false;
                        Notification.error('total value 0.00 !');
                    }
                    if (!$scope.model.data.accType) {
                        checkSave = false;
                        Notification.error('Select Payment Type !');
                    }
                    if (!$scope.model.data.description) {
                        checkSave = false;
                        Notification.error('insert description to save !');
                    }
                    if (!$scope.model.data.transactionDate) {
                        checkSave = false;
                        Notification.error('insert transaction date to save !');
                    }
                    if ($scope.model.data.credit < $scope.model.data.billTotal) {
                        checkSave = false;
                        Notification.error('pay amount not enough to settle bills !');
                    }
                    if ($scope.model.data.accType === 'OVER_PAYMENT' && $scope.model.data.overPay > 0) {
                        checkSave = false;
                        Notification.error("can't save because there is an overpayment !");
                    }
                    if ($scope.model.data.credit > $scope.model.data.overAmount && $scope.model.data.accType === 'OVER_PAYMENT') {
                        checkSave = false;
                        Notification.error("overpayment value not enough to save !");
                    }

                    if (checkSave) {
                        ConfirmPane.primaryConfirm("DO YOU WANT TO SAVE !")
                                .confirm(function () {
                                    $scope.model.save()
                                            .then(function () {
                                                Notification.success('supplier payment save Success !');
                                                $scope.ui.mode = "IDEAL";
                                            })
                                            .then(function (data) {
                                                Notification.error(data.message);
                                            });
                                });
                    }
                };
                $scope.ui.focusAdd = function (model) {
                    if (model.which === 13) {
                        $scope.ui.addData();
                    }
                };

                $scope.init = function () {
                    $scope.ui.mode = 'IDEAL';
                };

                $scope.init();
            });
}());

