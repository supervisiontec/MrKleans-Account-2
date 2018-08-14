(function () {
    angular.module("appModule")
            .controller("voucherController", function ($scope, $sce, voucherModel,calculator, printService, voucherService, $timeout, $uibModal, Notification, ConfirmPane) {
                $scope.model = new voucherModel();
                $scope.printService = new printService();
                $scope.ui = {};
                $scope.model.currentBranch = {};
                $scope.model.type = null;

                $scope.reportName = "General Voucher";

                $scope.printModel = {};
                $scope.printModel.currentReportGroup = {};
                $scope.printModel.currentReport = {};
                $scope.printModel.currentReport.parameterValues = {};

                //focus
                $scope.ui.focus = function (id) {
                    $timeout(function () {
                        document.querySelectorAll(id)[0].focus();
                    }, 10);
                };
                //new
                $scope.ui.new = function () {
                    $scope.ui.mode = "NEW";
                    $scope.ui.focus('#account');
                    $scope.model.tempData.branch = $scope.model.data.branch;
                    $scope.model.tempData.transactionDate = new Date();
                };
                $scope.ui.addData = function () {
                    var checkSave = true;
                    if (!$scope.model.tempData.accAccount) {
                        checkSave = false;
                        Notification.error('Select a Account to add !');
                    }
                    if (!$scope.model.tempData.branch) {
                        checkSave = false;
                        Notification.error('Select a branch to add !');
                    }
                    if (!$scope.model.data.transactionDate) {
                        checkSave = false;
                        Notification.error('insert a Date to add !');
                    }
                    if (!$scope.model.tempData.debit) {
                        checkSave = false;
                        Notification.error('insert amount to add !');
                    }
                    if (!$scope.model.data.accAccount) {
                        checkSave = false;
                        Notification.error('select a main account to add !');
                    }
                    if (checkSave) {
                        $scope.model.addData();
                        $scope.ui.new();
                        $scope.ui.focus('#subAccount');
                    }
                };
                $scope.ui.save = function () {
                    var checkSave = true;
                    if (!$scope.model.data.accAccount) {
                        checkSave = false;
                        Notification.error('select a main account to save !');
                    }
                    if ($scope.model.saveDataList.length === 0) {
                        checkSave = false;
                        Notification.error('Empty voucher item to save !');
                    }
                    if (!$scope.model.data.credit) {
                        checkSave = false;
                        Notification.error('total value 0.00 !');
                    }
                    if (!$scope.model.userPermission.add) {
                        checkSave = false;
                        Notification.error('you have no permission ');
                    }
                    if ($scope.model.selectAccType.name === 'BANK') {

                        if (!$scope.model.data.refNumber) {
                            checkSave = false;
                            Notification.error('Cheque No is Empty !');
                        }
                        if (!$scope.model.data.chequeDate) {
                            checkSave = false;
                            Notification.error('plz enter Cheque Date !');
                        }
                        if (!$scope.model.data.description) {
                            checkSave = false;
                            Notification.error('Description is empty !');
                        }
                    }
                    if (checkSave) {
                        ConfirmPane.successConfirm("DO YOU WANT TO SAVE !")
                                .confirm(function () {
                                    $scope.model.save()
                                            .then(function (data) {
                                                Notification.success('Payment voucher save Sucess');
                                                $scope.ui.mode = "IDEAL";
                                                $scope.model.saveDataList = [];

                                                ConfirmPane.primaryConfirm("DO YOU WANT TO PRINT !")
                                                        .confirm(function () {
                                                            $scope.ui.modalOpen(data.searchCode);
                                                        });
                                            });
                                });
                    }
                };
                $scope.ui.modalOpen = function (searchCode) {

                    var reportName = "VOUCHER";
                    //get report details
                    voucherService.reportData(reportName)
                            .success(function (data) {
                                $scope.printModel.currentReport.report = data;

                                //get report paramiters
                                voucherService.listParameters(data)
                                        .success(function (data) {
                                            $scope.printModel.currentReport.parameters = data;
                                        });

                                //set paramiters values

                                $scope.printModel.currentReport.parameterValues.REPORT_NAME = $scope.reportName;
//                                $scope.printModel.currentReport.parameterValues.COMPANY_NAME = $scope.model.company.name;
                                $scope.printModel.currentReport.parameterValues.CURRENT_BRANCH = $scope.model.currentBranch.indexNo;
                                $scope.printModel.currentReport.parameterValues.SEARCH_CODE = searchCode;

                                //view reports
                                voucherService.viewReport(
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
                $scope.ui.edit = function (index, data) {
                    $scope.model.tempData = data;
                    $scope.model.saveDataList.splice(index, 1);
                    $scope.model.totalCalculation();
                };
                $scope.ui.delete = function (index) {
                    $scope.model.saveDataList.splice(index, 1);
                    $scope.model.totalCalculation();
                };
                $scope.ui.selectAccType = function (type) {
                    $scope.model.selectAccType = type;
                    $scope.model.type = type.value;
                    $scope.ui.focus('#account');
                    $scope.model.setClear();

                };
                $scope.ui.focusAdd = function (model,amount) {
                    if (model.which === 13) {
                        var value = calculator.cal(amount);
                        console.log(value);
                        $scope.model.tempData.debit = value;
                    }
                };
                $scope.ui.searchVoucherByNumber = function (number) {
                    var key = event ? event.keyCode || event.which : 13;
                    if (key === 13) {
                        
                        $scope.model.searchVoucherByNumber(number)
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