(function () {
    angular.module("appModule")
            .factory("reportViewerModel", function (ReportViewerService) {
                function reportViewer() {
                    this.constructor();
                }
                
                reportViewer.prototype = {

                    clientList: [],
                    vehicleList: [],
                    itemTypeList: [],
                    itemList: [],
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
                        ReportViewerService.getVehicleList()
                                .success(function (data) {
                                    that.vehicleList = data;
                                });
                        ReportViewerService.getItemList()
                                .success(function (data) {
                                    that.itemList = data;
                                });
                        ReportViewerService.getAccLedgerTypeList()
                                .success(function (data) {
                                    that.ledgerTypeList = data;
                                });
                        
                        this.itemTypeList = [
                            'STOCK',
                            'NON_STOCK',
                            'SERVICE'
                        ];
                        this.reportNameList = [
                            'Petty Cash Voucher',
                            'Payment Voucher',
                            'Supplier Payment Voucher',
                            'Fund Transfer',
                            'Journal Voucher'
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
                    , clientLable: function (id) {
                        var lable = '';
                        angular.forEach(this.clientList, function (client) {
                            if (client.indexNo === parseInt(id)) {
                                lable = client.indexNo + " - " + client.name;
                                return;
                            }
                        });
                        return lable;
                    }
                    , itemLable: function (id) {
                        var lable = '';
                        angular.forEach(this.itemList, function (client) {
                            if (client.indexNo === parseInt(id)) {
                                lable = client.barcode + " - " + client.name;
                                return;
                            }
                        });
                        return lable;
                    }
                    , vehicleLable: function (id) {
                        var lable = '';
                        angular.forEach(this.vehicleList, function (client) {
                            if (client.indexNo === parseInt(id)) {
                                lable = client.indexNo + " - " + client.vehicleNo;
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
                    , ledgerTypeLable: function (name) {
                         var lable = '';
                        angular.forEach(this.ledgerTypeList, function (tedgerType) {
                            if (tedgerType.name === name) {
                                lable = tedgerType.indexNo + " - " + tedgerType.name;
                                return;
                            }
                        });
                        return lable;
                    }
                    , itemTypeLable: function (type) {
                        return type;
                    }
                    , reportNameLable: function (name) {
                        return name;
                    }

                };
                return reportViewer;
            });
}());
