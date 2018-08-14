(function () {
    angular.module("appModule")
            .controller("supplierPaymentController", function ($scope, printService,calculator, $uibModal, supplierPaymentModel, $sce, supplierPaymentService, $timeout, $filter, Notification, ConfirmPane) {
                $scope.model = new supplierPaymentModel();
                $scope.printService = new printService();
                $scope.ui = {};
                $scope.ui.toggleTypeLabel = "SELECTION";

                $scope.reportName = "Journal Voucher";

                $scope.printModel = {};
                $scope.printModel.currentReportGroup = {};
                $scope.printModel.currentReport = {};
                $scope.printModel.currentReport.parameterValues = {};
                $scope.model.currentBranch = {};
                $scope.model.type = null;

                $scope.model.supplierBalanceList = [];
                $scope.model.tabActive = 0;
                $scope.model.selectSupplier = null;
                $scope.model.accLedgerList = [];

                //focus
                $scope.ui.focus = function (id) {
                    $timeout(function () {
                        document.querySelectorAll(id)[0].focus();
                    }, 10);
                };
                $scope.ui.toggleType = function (type) {
                    $scope.ui.toggleTypeLabel = type;
                };
                $scope.ui.setToggle = function () {
                    if ($scope.model.selectSupplier !== null && $scope.ui.mode === 'NEW') {
                        $scope.model.tabActive = 1;
                        $scope.model.data.typeIndexNo = $scope.model.selectSupplier;
                        $scope.model.getPayableBills($scope.model.selectSupplier);
                    } else {
                        Notification.error("select a supplier");
                        $scope.model.tabActive = 0;
                    }
                };
                $scope.ui.setFocusAdd=function (model,amount){
                    if (model.which === 13) {
                        var value = calculator.cal(amount);
                        console.log(value);
                        $scope.model.data.credit = value;
                    }
                };
                
                //new
                $scope.ui.new = function () {
                    $scope.ui.mode = "NEW";
                    $scope.ui.focus('#supplier');
                    $scope.model.typeCash = true;
                    $scope.model.typeOverPayment = false;
                    $scope.model.data.transactionDate = new Date();
                    $scope.model.selectSupplier = null;
                    $scope.model.accLedgerList = [];

                };
                $scope.ui.checkType = function (type) {
                    $scope.ui.focus('#account');
                    $scope.model.data.accAccount=null;
                    $scope.model.checkType(type);
                };
                $scope.ui.checkTypeSub = function (type) {
                    $scope.model.data.accAccount=null;
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

                    var transactionDate = $filter('date')($scope.model.data.transactionDate, 'yyyy-MM-dd');
                    var newDate = $filter('date')(new Date(), 'yyyy-MM-dd');
                    if (transactionDate > newDate) {
                        checkSave = false;
                        Notification.error("transfer date not valid ! ");
                    }

                    if (checkSave) {
                        ConfirmPane.successConfirm("DO YOU WANT TO SAVE SUPPLIER PAYMENT !")
                                .confirm(function () {
                                    $scope.model.save()
                                            .then(function (data) {
                                                console.log('save success');
                                                Notification.success('supplier payment save Success !');
                                                $scope.ui.mode = "IDEAL";

                                                ConfirmPane.primaryConfirm("DO YOU WANT TO PRINT !")
                                                        .confirm(function () {
                                                            $scope.ui.modalOpen(data.searchCode);
                                                        });
                                            })
                                            .then(function (data) {
                                                Notification.error(data.message);
                                            });
                                });
                    }
                };
                $scope.ui.modalOpen = function (searchCode) {

                    var reportName = "VOUCHER";
                    //get report details
                    supplierPaymentService.reportData(reportName)
                            .success(function (data) {
                                $scope.printModel.currentReport.report = data;

                                //get report paramiters
                                supplierPaymentService.listParameters(data)
                                        .success(function (data) {
                                            $scope.printModel.currentReport.parameters = data;
                                        });

                                //set paramiters values
                                console.log($scope.model.currentBranch.indexNo);
                                console.log(searchCode);

                                $scope.printModel.currentReport.parameterValues.REPORT_NAME = $scope.reportName;
//                                $scope.printModel.currentReport.parameterValues.COMPANY_NAME = $scope.model.company.name;
                                $scope.printModel.currentReport.parameterValues.CURRENT_BRANCH = $scope.model.currentBranch.indexNo;
                                $scope.printModel.currentReport.parameterValues.SEARCH_CODE = searchCode;

                                //view reports
                                supplierPaymentService.viewReport(
                                        $scope.printModel.currentReport.report,
                                        $scope.printModel.currentReport.parameters,
                                        $scope.printModel.currentReport.parameterValues
                                        )
                                        .success(function (response) {
                                            var file = new Blob([response], {type: 'application/pdf'});
                                            var fileURL = URL.createObjectURL(file);

                                            $scope.content = $sce.trustAsResourceUrl(fileURL);

                                            $uibModal.open({
                                                animation: true,
                                                ariaLabelledBy: 'modal-title',
                                                ariaDescribedBy: 'modal-body',
                                                templateUrl: 'voucher_popup.html',
                                                scope: $scope,
                                                size: 'lg'
                                            });

                                        });
                            });
                };
                $scope.ui.exportExcel = function () {
                    console.log("exportExcel");
                    console.log("not support yet !");
//                    $scope.printService.printExcel('printDiv', $scope.reportName);
                };
                $scope.ui.focusAdd = function (model) {
                    if (model.which === 13) {
                        $scope.ui.addData();
                    }
                };
                $scope.ui.searchByNumber = function (number) {
                    var key = event ? event.keyCode || event.which : 13;
                    if (key === 13) {
                        $scope.model.searchByNumber(number)
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