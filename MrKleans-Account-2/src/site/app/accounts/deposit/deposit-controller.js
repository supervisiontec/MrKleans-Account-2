(function () {
    angular.module("appModule")
            .controller("depositController", function ($scope, printService, $uibModal, calculator,depositService, $sce, depositModel, $timeout, $filter, Notification, ConfirmPane) {
                $scope.model = new depositModel();
                $scope.printService = new printService();
                $scope.ui = {};

                $scope.reportName = "Fund Transfer Voucher";
                $scope.printModel = {};
                $scope.printModel.currentReportGroup = {};
                $scope.printModel.currentReport = {};
                $scope.printModel.currentReport.parameterValues = {};

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
                    $scope.ui.focus('#account');
                    $scope.model.tempData.transactionDate = new Date();
                    $scope.model.data.transactionDate = new Date();
                    $scope.model.tempData.branch = $scope.model.data.branch;
//                    $scope.model.data.branch = $scope.model.data.branch;
                };
                $scope.ui.addData = function () {
                    var checkSave = true;
                    if (!$scope.model.tempData.accAccount) {
                        checkSave = false;
                        Notification.error('select a account to add !');
                    }
                    if (!$scope.model.tempData.branch) {
                        checkSave = false;
                        Notification.error('select a branch to add !');
                    }
                    if (!$scope.model.tempData.description) {
                        checkSave = false;
                        Notification.error('insert description to add !');
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
                    if ($scope.model.saveDataList.length >= 1) {
                        checkSave = false;
                        Notification.error('transfer account Limited to one');
                    }
                    var transactionDate = $filter('date')($scope.model.data.transactionDate, 'yyyy-MM-dd');
                    var newDate = $filter('date')(new Date(), 'yyyy-MM-dd');
                    if (transactionDate > newDate) {
                        checkSave = false;
                        Notification.error("transfer date not valid ! ");
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
                    if (!$scope.model.userPermission.add) {
                        checkSave = false;
                        Notification.error('you have no permission ');
                    }
                    if ($scope.model.saveDataList.length === 0) {
                        checkSave = false;
                        Notification.error('empty transfer item to save !');
                    }
                    if (!$scope.model.data.credit) {
                        checkSave = false;
                        Notification.error('total value 0.00 !');
                    }
                    if (!$scope.model.data.description) {
                        checkSave = false;
                        Notification.error('insert description to save !');
                    }

                    if (checkSave) {
                        ConfirmPane.successConfirm("DO YOU WANT TO SAVE THIS FUND TRANSFER !")
                                .confirm(function () {
                                    $scope.model.save()
                                            .then(function (data) {
                                                Notification.success('Transfer save Sucess');
                                                $scope.ui.mode = "IDEAL";

                                                ConfirmPane.primaryConfirm("DO YOU WANT TO PRINT !")
                                                        .confirm(function () {
                                                            $scope.ui.modalOpen(data.searchCode);
                                                        });
                                                $scope.model.setClear();
                                            })
                                            .then(function (data) {
                                                Notification.error(data.message);
                                            });
                                });
                    }
                };
                $scope.ui.modalOpen = function (searchCode) {
                    var reportName = "DEPOSIT";
                    //get report details
                    depositService.reportData(reportName)
                            .success(function (data) {
                                $scope.printModel.currentReport.report = data;

                                //get report paramiters
                                depositService.listParameters(data)
                                        .success(function (data) {
                                            $scope.printModel.currentReport.parameters = data;
                                        });

                                //set paramiters values

                                $scope.printModel.currentReport.parameterValues.REPORT_NAME = $scope.reportName;
//                                $scope.printModel.currentReport.parameterValues.COMPANY_NAME = $scope.model.company.name;
                                $scope.printModel.currentReport.parameterValues.CURRENT_BRANCH = $scope.model.currentBranch.indexNo;
                                $scope.printModel.currentReport.parameterValues.SEARCH_CODE = searchCode;

                                //view reports
                                depositService.viewReport(
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

                    console.log($scope.model.selectAccType);
                };
                $scope.ui.focusAdd = function (model, debit) {
                    if (model.which === 13) {
                        var value = calculator.cal(debit);
                        console.log(value);
                        $scope.model.tempData.debit = value;
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