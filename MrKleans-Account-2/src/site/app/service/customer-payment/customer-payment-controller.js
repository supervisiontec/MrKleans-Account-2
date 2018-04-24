(function () {
    angular.module("paymentVoucherModule", ['ui.bootstrap']);
    angular.module("paymentVoucherModule")
            .controller("customerPaymentVoucherController", function ($scope, $filter, customerPaymentVoucherModel, Notification, ConfirmPane) {

                $scope.model = new customerPaymentVoucherModel();
                $scope.prineModel = {};
                $scope.ui = {};
                $scope.ui.type = 'ADVANCE PAYMENT';

                $scope.ui.paymentType = function (value) {
                    $scope.ui.type = value;
                    $scope.ui.mode = 'IDEAL';
                    $scope.model.clear();

                };
                $scope.ui.selectClient = function (index) {
                    $scope.model.selectClient(index);
                };
                $scope.ui.selectClientFromBalance = function (index) {
                    $scope.model.selectClientFromBalance(index);
                };

                $scope.ui.selectVehicle = function (index) {
                    $scope.model.selectVehicle(index);
                };

                $scope.ui.getCashPayment = function (amount, type) {
                    $scope.model.getInsertCashPayment(amount, type);

                };

                $scope.ui.getCashPaymentDelete = function () {
                    $scope.model.information.cash = 0.0;
                    $scope.model.payment.cashAmount = 0.0;
                    $scope.model.getCashPaymentDelete();
                };

                $scope.ui.new = function () {
//                    $scope.ui.clear();
                    $scope.ui.mode = 'NEW';
                    $scope.model.customerLedger.date = $filter('date')(new Date(), 'yyyy-MM-dd');
                };
                $scope.ui.getInsertCardAndChequePayment = function (paymentInformation, type) {

                    if (type === "CHEQUE") {
                        if (!paymentInformation.number) {
                            Notification.error("please enter cheque no");
                        } else if (!paymentInformation.chequeDate) {
                            Notification.error("please enter cheque date");
                        } else if (!paymentInformation.bank) {
                            Notification.error("please enter cheque bank");
                        } else if (!paymentInformation.bankBranch) {
                            Notification.error("please enter cheque branch");
                        } else if (!paymentInformation.amount) {
                            Notification.error("please enter cheque amount");
                        } else {
                            $scope.model.getInsertCardAndChequePayment(paymentInformation, type);
                        }
                    } else if (type === "CARD") {
                        if (!paymentInformation.cardType) {
                            Notification.error("please enter card type");
                        } else if (!paymentInformation.amount) {
                            Notification.error("please enter card amount");
                        } else {
                            $scope.model.getInsertCardAndChequePayment(paymentInformation, type);
                        }
                    }
                };

                $scope.ui.getCardAndChequePaymentDelete = function (number) {
                    $scope.model.getCardAndChequePaymentDelete(number);
                };

                $scope.ui.saveAdvancePayment = function () {
                    console.log(1);
                    var check = true;
                    if (!$scope.model.payment.totalAmount) {
                        check = false;
                        Notification.error('Empty Value to save Advance Payment');
                    }
                    if (!$scope.model.userPermission.add) {
                        check = false;
                        Notification.error('you have no permission ');
                    }
                    if (check) {
                        console.log(2);
                        ConfirmPane.successConfirm("Do you want to save advance payment !")
                                .confirm(function () {
                                    $scope.model.saveAdvancePayment()
                                            .then(function (data) {
                                                console.log(3);
                                                $scope.ui.mode = "IDEAL";
                                                Notification.success('Advance Payment Save Successfully !');

                                            });
                                });
                    }
                };
                $scope.ui.saveBalancePayment = function () {
                    var check = true;
                    if (!$scope.model.userPermission.add) {
                        check = false;
                        Notification.error('you have no permission ');
                    }
                    if (check) {
                        if ($scope.model.payment.totalAmount) {
                            if ($scope.model.payment.totalAmount === $scope.model.information.invoiceTotalPayment) {
                                //default save
                                ConfirmPane.successConfirm("Do you want to save balance payment !")
                                        .confirm(function () {
                                            $scope.ui.saveBalancePaymentSecond();
                                        });

                            } else if ($scope.model.payment.totalAmount > $scope.model.information.invoiceTotalPayment) {
                                //over payment
                                ConfirmPane.warningConfirm("There is a Balance Payment.Do you want to Save ? ")
                                        .confirm(function () {
                                            $scope.ui.saveBalancePaymentSecond();
                                        });
                            } else {
                                //dont save
                                Notification.error("Pay Amount and Paid Total doesn't Match !");
                            }
                        } else {
                            Notification.error('Empty Value to save Advance Payment');
                        }
                    }

                };
                $scope.ui.saveBalancePaymentSecond = function () {
                    var check = true;
                    if (!$scope.model.userPermission.add) {
                        check = false;
                        Notification.error('you have no permission ');
                    }
                    if (check) {
                        $scope.model.saveBalancePayment()
                                .then(function (data) {
                                    $scope.ui.mode = "IDEAL";
                                    Notification.success('Customer Balance Payment Save Successfully !');

                                });
                    }
                };

                $scope.ui.insertClientOverPaymentSettlment = function () {
                    if ($scope.model.information.overPayment >= $scope.model.paymentInformation.amount) {
                        console.log($scope.model.paymentInformation.amount);
                        $scope.model.insertClientOverPaymentSettlment($scope.model.paymentInformation.amount, 'OVER_PAYMENT_SETTLEMENT');
                    } else {
                        Notification.error("plase enter valid amount");
                    }
                };


                $scope.init = function () {
                    $scope.$watch("[model.balanceInvoiceList]", function (newVal, oldVal) {
                        $scope.model.getInvoicePayAmount();
                    }, true);
                    $scope.ui.mode = 'IDEAL';
                };

                $scope.init();
            });
}());

