
(function () {
    var factory = function (accruedBillFactory, accruedBillService, $q, $timeout, $filter, Notification) {
        function accruedBillModel() {
            this.constructor();
        }
//      prototype functions
        accruedBillModel.prototype = {
            data: {},
            tempData: {},
            currentBranch: {},
            selectAccType: {},
            userPermission: {},
            accAccountList: [],
            branchList: [],
            supplierList: [],
            saveDataList: [],
            accTypeList: [],
            accflowList: [],
            activeCostDepartmentList: [],
            activeCostCenterList: [],
            //constructor
            constructor: function () {
                var that = this;
                that.data = accruedBillFactory.Data();
                that.tempData = accruedBillFactory.tempData();

                accruedBillService.loadBranch()
                        .success(function (data) {
                            that.branchList = data;
                        });
                accruedBillService.loadSupplier()
                        .success(function (data) {
                            that.supplierList = data;
                        });
                accruedBillService.currentBranch()
                        .success(function (data) {
                            that.currentBranch = data;
                            that.data.branch = data.indexNo;
                        });
                accruedBillService.loadAccTypes()
                        .success(function (data) {
                            that.accTypeList = data;
                        });
                accruedBillService.getPermission('Accrued Bill')
                        .success(function (data) {
                            that.userPermission = data;
                        });
                accruedBillService.activeCostDepartmentList()
                        .success(function (data) {
                            that.activeCostDepartmentList = data;
                        });
                accruedBillService.activeCostCenterList()
                        .success(function (data) {
                            that.activeCostCenterList = data;
                        });
                this.loadAccAccount();
            },
            loadAccAccount: function () {
                var that = this;
                accruedBillService.loadAccAccounts()
                        .success(function (data) {
                            that.accAccountList = data;
                        });
            },
            getValue: function (supplierId) {
                var that = this;
                var accAccount = null;
                angular.forEach(that.supplierList, function (supplier) {
                    if (supplierId === parseInt(supplier.indexNo)) {
                        accAccount = supplier.accAccount;
                        return;
                    }
                });
                that.data.accAccount = accAccount;
                if (accAccount !== null) {
                    $timeout(function () {
                        that.loadAccValue(accAccount);
                    }, 30);

                    that.setAccountFlow(accAccount);

                } else {
                    Notification.error("this supplier not link  to account system !");
                }
            },
            setAccountFlow: function (acc) {
                var that = this;
                accruedBillService.setAccFlow(acc)
                        .success(function (data) {
                            that.accflowList = data;
                        });
            },
            loadAccValue: function (accAccount) {
                var that = this;
                accruedBillService.loadAccBalance(accAccount)
                        .success(function (data) {
                            that.data.value = data;
                        });
            },

            branchLable: function (model) {
                var label;
                angular.forEach(this.branchList, function (value) {
                    if (value.indexNo === model) {
                        label = value.branchCode + ' - ' + value.name;
                        return;
                    }
                });
                return label;
            },
            supplierLable: function (model) {
                var label;
                angular.forEach(this.supplierList, function (value) {
                    if (value.indexNo === model) {
                        label = value.indexNo + ' - ' + value.name;
                        return;
                    }
                });
                return label;
            },
            accountLable: function (model) {
                var label;
                angular.forEach(this.accAccountList, function (value) {
                    if (value.indexNo === model) {
                        label = value.accCode + ' - ' + value.name;
                        return;
                    }
                });
                return label;
            },
            activeCostDepartmentLable: function (model) {
                var label;
                angular.forEach(this.activeCostDepartmentList, function (value) {
                    if (value.indexNo === model) {
                        label = value.indexNo + ' - ' + value.name;
                        return;
                    }
                });
                return label;
            },
            activeCostCenterLable: function (model) {
                var label;
                angular.forEach(this.activeCostCenterList, function (value) {
                    if (value.indexNo === model) {
                        label = value.indexNo + ' - ' + value.name;
                        return;
                    }
                });
                return label;
            },
            addData: function () {
                var that = this;
                this.tempData.transactionDate = $filter('date')(this.data.transactionDate, 'yyyy-MM-dd');
                this.saveDataList.push(this.tempData);
                this.tempData = accruedBillFactory.tempData();
                $timeout(function () {
                    that.totalCalculation();
                }, 30);
            },
            totalCalculation: function () {
                var value = 0.00;
                angular.forEach(this.saveDataList, function (data) {
                    console.log(data);
                    value += parseFloat(data.debit);
                    return;
                });
                this.data.credit = value;
            },
            save: function () {

                var that = this;
                var data = {};
                data.dataList = this.saveDataList;
                this.data.transactionDate = $filter('date')(this.data.transactionDate, 'yyyy-MM-dd');
                this.data.chequeDate = $filter('date')(this.data.chequeDate, 'yyyy-MM-dd');
                data.data = this.data;

                console.log(data);

                var defer = $q.defer();
                accruedBillService.saveAccruedBill(JSON.stringify(data))
                        .success(function (data) {
                            that.saveDataList = [];
                            defer.resolve(data);
                        })
                        .error(function (data) {
                            defer.reject(data);
                        });
                return defer.promise;
            },
            setClear: function () {

                this.tempData = accruedBillFactory.tempData();
                this.data = accruedBillFactory.Data();
                this.tempData.transactionDate = new Date();
                this.data.transactionDate = new Date();
                this.tempData.branch = this.currentBranch.indexNo;
                this.data.branch = this.currentBranch.indexNo;
                this.saveDataList = [];
            },
            searchByNumber: function (number) {
                var defer = $q.defer();
                var that = this;
                accruedBillService.findAccruedBillByNumberAndBranch(number)
                        .success(function (data) {
                            if (data.length > 0) {
                                for (var i = 0; i < data.length; i++) {
                                    if (!data[i].isMain) {
                                        that.tempData = data[i];
                                        that.addData();
                                    } else {
                                        that.data.description = data[i].description;
                                        that.data.refNumber = data[i].refNumber;
                                        that.data.accAccount = data[i].accAccount;
                                        that.data.typeIndexNo = data[i].typeIndexNo;
                                        that.data.transactionDate = new Date(data[i].transactionDate);
                                        $timeout(function () {
                                            that.loadAccValue(that.data.accAccount);
                                        }, 30);
                                        defer.resolve();
                                    }
                                }

                            } else {
                                defer.reject(data);
                            }
                        });
                return defer.promise;
            }
        };
        return accruedBillModel;
    };
    angular.module("appModule")
            .factory("accruedBillModel", factory);
}());


