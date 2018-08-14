(function () {
    angular.module("appModule")
            .controller("journalController", function ($scope, $uibModal, printService, calculator, $sce, journalService, journalModel, $timeout, Notification, ConfirmPane) {
                $scope.model = new journalModel();
                $scope.printService = new printService();
                $scope.ui = {};

                $scope.reportName = "Journal Voucher";

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
                    $scope.ui.focus('#date');
                    $scope.model.refresh();
                    $scope.model.data.transactionDate = new Date();
                    $scope.model.setBranch();
                };

                //save
                $scope.ui.save = function () {
                    var check = true;
                    if (!$scope.model.data.transactionDate) {
                        check = false;
                        Notification.error('insert date to save !');
                    }
                    if (!$scope.model.data.description) {
                        check = false;
                        Notification.error('insert description to save !');
                    }
                    if (!$scope.model.dataList.length) {
                        check = false;
                        Notification.error('insert data to save !');
                    }
                    if ($scope.model.data.totalDebit !== $scope.model.data.totalCredit && $scope.model.data.totalCredit !== 0) {
                        check = false;
                        Notification.error('total debit and credit amount is deference!');
                    }
                    if (!$scope.model.userPermission.add) {
                        check = false;
                        Notification.error('you have no permission ');
                    }
                    if (check) {
                        ConfirmPane.successConfirm("DO YOU WANT TO SAVE !")
                                .confirm(function () {
                                    $scope.model.save()
                                            .then(function (data) {
                                                Notification.success('journal enteries save success.. !');
                                                $scope.ui.mode = "IDEAL";

                                                ConfirmPane.primaryConfirm("DO YOU WANT TO PRINT !")
                                                        .confirm(function () {
                                                            $scope.ui.modalOpen(data.searchCode);
                                                        });
                                            });

                                });
                    }
                };

                //add data
                $scope.ui.addData = function () {
                    var check = true;
                    if (!$scope.model.data.transactionDate) {
                        check = false;
                        Notification.error('insert date to add !');
                    }
                    if (!$scope.model.data.description) {
                        check = false;
                        Notification.error('insert description to add !');
                    }
                    if (!$scope.model.tempData.debit && !$scope.model.tempData.credit) {
                        check = false;
                        Notification.error('insert debit or credit amount to add !');
                    }
                    if ($scope.model.tempData.debit && $scope.model.tempData.credit) {
                        check = false;
                        Notification.error('invalied debit and credit amount !');
                    }
                    if (!$scope.model.tempData.accAccount) {
                        check = false;
                        Notification.error('select a account name to add !');
                    }
                    if (!$scope.model.tempData.branch) {
                        check = false;
                        Notification.error('select a branch name to add !');
                    }
                    if (check) {
                        $scope.model.add();
                        $scope.ui.focus('#accMain');
                    }
                };
                $scope.ui.focusAddFunctionDebit = function (e, debit) {
                    var code = e ? e.keyCode || e.which : 13;
                    if (code === 13) {
                        var value = calculator.cal(debit);
                        console.log(value);
                        $scope.model.tempData.debit = value;
                    }
                };
                $scope.ui.focusAddFunctionCredit = function (e, credit) {
                    var code = e ? e.keyCode || e.which : 13;
                    if (code === 13) {
                        var value = calculator.cal(credit);
                        console.log(value);
                        $scope.model.tempData.credit = value;
                    }
                };
                $scope.ui.searchJournalByNumber = function (number) {
                    var key = event ? event.keyCode || event.which : 13;
                    if (key === 13) {
                        $scope.model.searchJournalByNumber(number)
                                .then(function () {
                                    Notification.success('Find a journal entery ');
                                }, function () {
                                    Notification.error('Invalid number !!!');
                                });
                    }
                };
                $scope.ui.delete = function (index) {
                    $scope.model.dataList.splice(index, 1);
                    $scope.model.totalCalculated();
                };
                $scope.ui.edit = function (index, data) {
                    $scope.model.dataList.splice(index, 1);
                    $scope.model.tempData.accAccount = data.accAccount;
                    $scope.model.tempData.debit = data.debit;
                    $scope.model.tempData.credit = data.credit;
                    $scope.model.tempData.branch = data.branch;
                    $scope.model.tempData.costCenter = data.costCenter;
                    $scope.model.tempData.costDepartment = data.costDepartment;
                    $scope.model.totalCalculated();
                    $scope.model.setMainAccount(data.accAccount);

                };
                $scope.ui.modalOpen = function (searchCode) {

                    var reportName = "VOUCHER";
                    //get report details
                    journalService.reportData(reportName)
                            .success(function (data) {
                                $scope.printModel.currentReport.report = data;

                                //get report paramiters
                                journalService.listParameters(data)
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
                                journalService.viewReport(
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

                $scope.ui.init = function () {
                    $scope.ui.mode = "IDEAL";

                };
                $scope.ui.init();
            });
}());