(function () {
    angular.module("itemSalesModule", ['ui.bootstrap']);
    angular.module("itemSalesModule")
            .controller("itemSalesController", function ($scope, $window, systemConfig, $uibModalStack, $uibModal, optionPane, itemSalesModel, Notification, ConfirmPane) {
                $scope.model = new itemSalesModel();

                $scope.ui = {};

                $scope.init = function () {
                    $scope.ui.mode = 'NEW';
                    $scope.ui.model = 'SELECTION';
                };

                $scope.ui.tabPayment = function (val) {
                    if (val === 0) {
                        $scope.ui.model = 'SELECTION';
                    } else {
                        $scope.ui.model = 'PAYMENT';
                    }
                };

                $scope.ui.new = function () {
                    $scope.ui.mode = 'NEW';
                };

                $scope.ui.save = function () {
                    $scope.ui.model = 'NEW';
                    $scope.ui.mode = 'IDEAL';
                };

                $scope.ui.dismissAllModel = function () {
                    $uibModalStack.dismissAll();
                };

                $scope.ui.getQuickSeacrhItem = function (itemKey) {
                    $scope.quickSearchMode = 'show';
                    $scope.model.getNonPackageItemItem(itemKey)
                            .then(function (data) {
                                $scope.quickSearchMode = 'hide';
                            }, function () {
                                $scope.quickSearchMode = 'hide';
                            });
                };
                $scope.ui.focus = function (id) {
                    $timeout(function () {
                        angular.element(document.querySelectorAll(id))[0].focus();
                    }, 10);
                };

                $scope.ui.getItemUnitsDetails = function (detail) {
                    //set job item data
                    $scope.model.tempItem.item = detail[0];
                    $scope.model.tempItem.itemName = detail[1];
                    $scope.model.tempItem.itemType = "STOCK_ITEM";
                    $scope.model.tempItem.jobStatus = "APPROVE";
                    $scope.model.tempItem.orderStatus = "APPROVE";

                    $scope.model.getItemUnits(detail[0]);

                    $scope.itemName = detail[1];
                    $scope.itemType = detail[2];

                    $scope.itemStockItemQty = 0;

                    $scope.model.findByAvailableStockQty(detail[0])
                            .then(function (data) {
                                $scope.itemStockItemQty = data;
                            });

                    if ($scope.model.getItemUnits(detail[0]).length === 0) {
                        optionPane.dangerMessage("THERE ISN'T ITEM UNIT FOR THIS ITEM !");
                    } else {
                        if ($scope.itemStockItemQty > 0) {
                            optionPane.dangerMessage("THERE ISN'T QTY FOR THIS ITEM !");
                        } else {
                            $uibModal.open({
                                animation: true,
                                ariaLabelledBy: 'modal-title',
                                ariaDescribedBy: 'modal-body',
                                templateUrl: 'item_selection_popup.html',
                                scope: $scope,
                                size: 'lg'
                            });
                            $scope.ui.focus('#itemName');
                        }
                    }
                };

                //add stock item units
                $scope.ui.addItemUnit = function (itemUnit, qty) {
                    console.log(itemUnit);
                    if (itemUnit.indexNo) {
                        if (qty > $scope.itemStockItemQty) {
                            if ($scope.itemType === 'SERVICE') {
                                $scope.model.tempItem.itemUnit = itemUnit.indexNo;
                                $scope.model.tempItem.price = itemUnit.salePriceNormal;
                                $scope.model.tempItem.quantity = qty;
                                $scope.model.tempItem.value = itemUnit.salePriceNormal * qty;
                                $scope.model.tempItem.itemName = itemUnit.itemName;
                                $scope.model.tempItem.itemType = "SERVICE_ITEM";

                                $scope.model.jobItemList.push($scope.model.tempItem);
                                console.log($scope.model.jobItemList);
                                $scope.ui.getTotalAmount(itemUnit.salePriceNormal, qty);
                                $scope.model.tempItem = {};
                                $scope.ui.dismissAllModel();
                            } else {
                                optionPane.dangerMessage("NO QTY!");

                            }

                        } else {
                            $scope.model.tempItem.itemUnit = itemUnit.indexNo;
                            $scope.model.tempItem.price = itemUnit.salePriceNormal;
                            $scope.model.tempItem.quantity = qty;
                            $scope.model.tempItem.value = itemUnit.salePriceNormal * qty;
                            $scope.model.tempItem.itemType = "STOCK_ITEM";

                            $scope.model.jobItemList.push($scope.model.tempItem);
                            console.log($scope.model.jobItemList);
                            $scope.ui.getTotalAmount(itemUnit.salePriceNormal, qty);
                            $scope.model.tempItem = {};
                            $scope.ui.dismissAllModel();
                        }
                    } else {
                        Notification.error("select item");
                    }
                };

                $scope.ui.getTotalAmount = function (price, qty) {
                    $scope.model.invoice.amount = null;
                    var qty = parseInt(qty);
                    if ($scope.totalAmount) {
                        $scope.itemCount += 1;
                        $scope.totalAmount += price * qty;
                        $scope.model.invoice.amount = $scope.totalAmount;
                    } else {
                        $scope.totalAmount = price * qty;
                        $scope.model.invoice.amount = $scope.totalAmount;
                        $scope.itemCount = 1;
                    }
                };

                $scope.ui.getCashPayment = function (amount, type) {
                    $scope.model.getInsertCashPayment(amount, type);

                };
                $scope.ui.keyEvent = function (event, itemKey) {
                    if (event.keyCode === 13) {
                        $scope.ui.getQuickSeacrhItem(itemKey);

                    }
                };


                $scope.ui.getDiscountRate = function () {
                    $scope.model.invoice.discountRate = parseFloat(($scope.model.invoice.discountAmount * 100) / $scope.model.invoice.amount);
                    $scope.model.invoice.netAmount = parseFloat($scope.model.invoice.amount - $scope.model.invoice.discountAmount);
                };

                $scope.ui.getDiscountAmount = function () {
                    $scope.model.invoice.discountAmount = parseFloat(($scope.model.invoice.amount * $scope.model.invoice.discountRate) / 100);
                    $scope.model.invoice.netAmount = parseFloat($scope.model.invoice.amount - $scope.model.invoice.discountAmount);
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

                $scope.ui.insertClientOverPaymentSettlment = function () {
                    if ($scope.model.information.overPayment >= $scope.model.paymentInformation.amount) {
                        $scope.model.insertClientOverPaymentSettlment($scope.model.paymentInformation.amount, 'OVER_PAYMENT_SETTLEMENT');
                    } else {
                        Notification.error("please enter valid amount");
                    }
                };

                $scope.ui.getRepEmployeeData = function ($item, $model) {
                    $scope.empMobile = $item.mobile;
                    $scope.empType = $item.type;
                    $scope.empRol = $item.rol;
                    console.log($item);
                };

                $scope.ui.saveItemSale = function () {
                    var check = true;
                    if (!$scope.model.customerLedger.client) {
                        check = false;
                        Notification.error('Select Customer to save !');
                    }
                    if (!$scope.model.userPermission.add) {
                        check = false;
                        Notification.error('you have no permission ');
                    }
                    if ($scope.model.jobItemList.length === 0) {
                        check = false;
                        Notification.error('Select Invoice Item to save !');
                    }
                    if (check) {
                        ConfirmPane.successConfirm("Do you want to save Item Sale!")
                                .confirm(function () {
                                    $scope.model.saveItemSale()
                                            .then(function (data) {
                                                $scope.ui.mode = "IDEAL";
                                                $scope.itemKey = "";
                                                $scope.itemCount = 0;
                                                $scope.totalAmount = 0;
                                                $scope.model.clear();
                                                Notification.success('Item Sale Save Successfully !');

                                            });
                                });
                    }
                    ;
                };

                //delete item
                $scope.ui.deleteSelectDetails = function ($index, price) {
                    ConfirmPane.dangerConfirm("Are you sure want to remove item")
                            .confirm(function () {
                                $scope.itemCount -= 1;
                                $scope.totalAmount -= price;
                                $scope.model.invoice.amount = $scope.totalAmount;
                                $scope.model.jobItemList.splice($index, 1);
                            });

                };

                $scope.ui.registerNewCustomerDetail = function () {
                    $window.location.href = systemConfig.apiUrl + "#/master/client/";
                };

                $scope.ui.deleteOverPayment = function () {
                    $scope.model.payment.overPaymentAmount = 0.0;
                    $scope.model.deleteOverPayment();
                };

                $scope.ui.getCashPaymentDelete = function () {
                    $scope.model.information.cash = 0.0;
                    $scope.model.payment.cashAmount = 0.0;
                    $scope.model.getCashPaymentDelete();
                };

                $scope.ui.getCardAndChequePaymentDelete = function (number, type) {
                    $scope.model.getCardAndChequePaymentDelete(number, type);
                };

                $scope.ui.modelOpen = function () {
                    //model open
                    $scope.model.selectedCustomerObject($scope.model.customerLedger.client);
                    $uibModal.open({
                        animation: true,
                        ariaLabelledBy: 'modal-title',
                        ariaDescribedBy: 'modal-body',
                        templateUrl: 'customer.html',
                        scope: $scope,
                        size: 'lg'
                    });

                };
                $scope.ui.registerCustomer = function () {
                    var check = true;
                    if (!$scope.model.popupCustomer.name) {
                        check = false;
                        Notification.error('Customer Name is Empty !');
                    }
                    if (!$scope.model.popupCustomer.resident) {
                        check = false;
                        Notification.error('select a resident to save !');
                    }
                    if (check) {
                        $scope.model.registerCustomer()
                                .then(function (data) {
                                    Notification.success(data.resident + " " + data.name + " registration successfully");
                                    $scope.model.customerLedger.client = data.indexNo;
                                    $scope.ui.modelCancel();
                                });
                    }
                };
                $scope.ui.modelCancel = function () {
                    $uibModalStack.dismissAll();
                };


                $scope.init();
            });
}());
