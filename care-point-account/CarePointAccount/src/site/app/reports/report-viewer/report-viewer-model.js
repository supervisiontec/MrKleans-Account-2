(function () {
    angular.module("appModule")
            .factory("reportViewerModel", function (ReportViewerService) {
                function reportViewer() {
                    this.constructor();
                }
                
                reportViewer.prototype = {

                    clientList: [],
                    supplierList: [],
                    branchList: [],
                    employeeList: [],
                    accountList: [],
                    ledgerTypeList: [],

                    constructor: function () {
                        var that = this;

                        
                        ReportViewerService.getClientList()
                                .success(function (data) {
                                    that.clientList = data;
                                });
                        ReportViewerService.getSupplierList()
                                .success(function (data) {
                                    that.supplierList = data;
                                });
                        ReportViewerService.getBranchList()
                                .success(function (data) {
                                    that.branchList = data;
                                });
                        ReportViewerService.getEmployeeList()
                                .success(function (data) {
                                    that.employeeList = data;
                                });
                        ReportViewerService.getAccountList()
                                .success(function (data) {
                                    that.accountList = data;
                                });
                        
                        this.ledgerTypeList = [
                            'voucher',
                            'reconciliation',
                            'supplier_payment',
                            'cheque_return',
                            'transfer',
                            'journal',
                            'accrued'
                        ];
                    }
                    
                    
                    , accountLable: function (id) {
                        var lable = '';
                        angular.forEach(this.accountList, function (account) {
                            if (account.indexNo === parseInt(id)) {
                                lable = account.accCode + " - " + account.name;
                                return;
                            }
                        });
                        return lable;
                    }
                    
                    , supplierLable: function (id) {
                        var lable = '';
                        angular.forEach(this.supplierList, function (supplier) {
                            if (supplier.indexNo === parseInt(id)) {
                                lable = supplier.indexNo + " - " + supplier.name;
                                return;
                            }
                        });
                        return lable;
                    }
                    , branchLable: function (id) {
                        var lable = '';
                        angular.forEach(this.branchList, function (branch) {
                            if (branch.indexNo === parseInt(id)) {
                                lable = branch.branchCode + " - " + branch.name;
                                return;
                            }
                        });
                        return lable;
                    }
                    
                    , employeeLable: function (id) {
                        var lable = '';
                        angular.forEach(this.employeeList, function (employee) {
                            if (employee.indexNo === parseInt(id)) {
                                lable = employee.indexNo + " - " + employee.name;
                                return;
                            }
                        });
                        return lable;
                    }
                    , ledgerTypeLable: function (type) {
                        
                        return type;
                    }

                };
                return reportViewer;
            });
}());
