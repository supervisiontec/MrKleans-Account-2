(function () {
    angular.module("supplierReturnModule", ['ui.bootstrap']);
    angular.module("supplierReturnModule")
            .controller("supplierReturnController", function ($scope, $timeout, $filter, supplierReturnModel, Notification, ConfirmPane) {
                $scope.model = new supplierReturnModel();
                $scope.ui = {};

                $scope.ui.new = function () {
                    $scope.ui.mode = 'NEW';
                    $scope.model.clear();
                    //set current date
                    $scope.ui.focus('#date');
                    $scope.model.data.date = $filter('date')(new Date(), 'yyyy-MM-dd');

                };
//
                $scope.ui.validateBarcode = function (event) {
                    var key = event ? event.keyCode || event.which : 13;
                    if (key === 13) {
                        $scope.model.validateBarcode($scope.model.tempData.barcode);
                        if ($scope.model.tempData.item) {
                            $scope.ui.focus('#price');
                        } else {
                            Notification.error("Item not found!");
                            $scope.ui.focus('#barcode');
                        }
                    }
                };

                $scope.ui.calculatedValue = function () {
                    $scope.model.tempData.value = $scope.model.tempData.qty * $scope.model.tempData.price;
                    $scope.model.tempData.discountValue = 0;
                    $scope.model.tempData.discount = 0;
                    $scope.model.tempData.netValue = $scope.model.tempData.value;
                };
                $scope.ui.calculateDiscountWithRate = function () {
                    $scope.model.tempData.discountValue = ($scope.model.tempData.value * $scope.model.tempData.discount) / 100;
                    $scope.model.tempData.netValue = $scope.model.tempData.value - $scope.model.tempData.discountValue;
                };
                $scope.ui.calculateDiscountWithValue = function () {
                    $scope.model.tempData.discount = ($scope.model.tempData.discountValue * 100) / $scope.model.tempData.value;
                    $scope.model.tempData.netValue = $scope.model.tempData.value - $scope.model.tempData.discountValue;
                };
                $scope.ui.callAddData = function () {
                    var key = event ? event.keyCode || event.which : 13;
                    if (key === 13) {
                        $scope.ui.addData();
                    }
                };
                $scope.ui.checkBoxNBTFunction = function () {
                    if ($scope.chxNBT) {
                        $scope.model.data.nbt = 2;
                    } else {
                        $scope.model.data.nbt = 0;
                    }
                    $scope.ui.calculateNBT();
                };
                $scope.ui.checkBoxVATFunction = function () {
                    if ($scope.chxVAT) {
                        $scope.model.data.vat = 15;
                    } else {
                        $scope.model.data.vat = 0;
                    }
                    $scope.ui.calculateVAT($scope.model.data.vat);
                };

                $scope.ui.calculateNBT = function () {
                    $scope.model.data.nbtValue = ($scope.model.data.amount * $scope.model.data.nbt) / 100;
                    $scope.model.data.grandAmount = $scope.model.data.amount + $scope.model.data.nbtValue;
                    $scope.model.data.vatValue = null;
                    $scope.model.data.vat = null;
                    $scope.chxVat = false;
                };

                $scope.ui.calculateVAT = function (vatRate) {
                    var nbtValue = $scope.model.data.nbtValue;
                    if (!$scope.model.data.nbtValue) {
                        nbtValue = 0.00;
                    }
                    $scope.model.data.vatValue = (($scope.model.data.amount + nbtValue) * vatRate) / 100;
                    $scope.model.data.grandAmount = $scope.model.data.amount + nbtValue + $scope.model.data.vatValue;
                };
                $scope.ui.edit = function (indexNo) {
                    $scope.model.edit(indexNo);
                };

                $scope.ui.delete = function (indexNo) {
                    $scope.model.delete(indexNo);
                };

                $scope.ui.addData = function () {
                    $scope.model.addData();
                    $scope.ui.focus('#barcode');
                };
//
                $scope.ui.focus = function (id) {
                    $timeout(function () {
                        angular.element(document.querySelectorAll(id))[0].focus();
                    }, 10);
                };
                $scope.ui.saveReturn = function () {
                    var check = true;
                    if (!$scope.model.userPermission.add) {
                        check = false;
                        Notification.error('you have no permission !');
                    }
                    if (check) {
                        ConfirmPane.primaryConfirm("Do you want to Save Supplier Return !")
                                .confirm(function () {
                                    $scope.model.saveSupplierReturn()
                                            .then(function () {
                                                $scope.ui.mode = "IDEAL";
                                                Notification.success("Supplier Return Save Success !");
                                                $scope.chxVat = false;
                                                $scope.chxNbt = false;
                                            });
                                })
                                .discard(function () {
                                    Notification.error("Supplier Return Save Fail !");
                                });
                    }
                };
                $scope.ui.searchGrnByNumber = function (number) {
                    var key = event ? event.keyCode || event.which : 13;
                    if (key === 13) {
                        $scope.model.searchGrnByNumber(number)
                                .then(function (data) {
                                    Notification.info(data.number+" Can be Finded "+data.type);
                                }, function (data) {
                                    Notification.error('Invalid number !!!');
                                });
                    }
                };
//

                $scope.init = function () {
                    $scope.ui.mode = 'IDEAL';
                };

                $scope.init();
            });
}());



