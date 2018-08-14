
(function () {
    var factory = function (allTransactionFactory, allTransactionService, $q, $filter, Notification) {
        function employeeModel() {
            this.constructor();

        }
        //prototype functions
        employeeModel.prototype = {
            data: {},
            tempData: {},
            currentBranch: {},
            userPermission: {},
            dataList: [],
            accAccountList: [],
            accflowList: [],
            branchList: [],
            //constructor
            constructor: function () {
                var that = this;
                that.data = allTransactionFactory.Data();
                that.tempData = allTransactionFactory.tempData();

                allTransactionService.loadAccAccounts()
                        .success(function (data) {
                            that.accAccountList = data;
                        });
                allTransactionService.loadBranch()
                        .success(function (data) {
                            that.branchList = data;
                        });
                allTransactionService.currentBranch()
                        .success(function (data) {
                            that.currentBranch = data;
                        });
                allTransactionService.activeCostDepartmentList()
                        .success(function (data) {
                            that.activeCostDepartmentList = data;
                        });
                allTransactionService.activeCostCenterList()
                        .success(function (data) {
                            that.activeCostCenterList = data;
                        });
                allTransactionService.getPermission('Journal')
                        .success(function (data) {
                            that.userPermission = data;
                        });
            },
            setAccountFlow: function (acc) {
                var that = this;
                allTransactionService.setAccFlow(acc)
                        .success(function (data) {
                            that.accflowList = data;
                        });
            },
            setBranch: function () {
                this.tempData.branch = this.currentBranch.indexNo;

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
            setMainAccount: function (accIndexNo) {
                this.setAccountFlow(accIndexNo);
            },
            add: function () {
                var saveData = {};
                var that = this;
                saveData.indexNo = null;
                saveData.transactionDate = $filter('date')(new Date(this.data.transactionDate), 'yyyy-MM-dd');
                saveData.currentDate = null;
                saveData.time = null;
                saveData.branch = this.tempData.branch;
                saveData.currentBranch = null;
                saveData.user = null;
                saveData.debit = this.tempData.debit;
                saveData.credit = this.tempData.credit;
                saveData.accAccount = this.tempData.accAccount;
                saveData.formName = null;
                saveData.refNumber = null;
                saveData.type = null;
                saveData.typeIndexNo = null;
                saveData.description = this.data.description;
                saveData.costDepartment = this.tempData.costDepartment;
                saveData.costCenter = this.tempData.costCenter;
                
                this.dataList.unshift(saveData);
                this.tempData = allTransactionFactory.tempData();
                this.totalCalculated();
                that.setBranch();
                that.accflowList = [];

            },
            totalCalculated: function () {
                var debit = 0.00;
                var credit = 0.00;
                angular.forEach(this.dataList, function (value) {
                    debit += value.debit;
                    credit += value.credit;
                });
                this.data.totalDebit = debit;
                this.data.totalCredit = credit;
            },
            save: function () {
                var defer = $q.defer();
                var that = this;
                allTransactionService.saveJournal(JSON.stringify(that.dataList))
                        .success(function (data) {
                            that.refresh();
                            defer.resolve(data);
                        })
                        .error(function (data) {
                            defer.reject(data);
                        });
                return defer.promise;
            },
            refresh: function () {
                this.data = allTransactionFactory.Data();
                this.tempData = allTransactionFactory.tempData();
                this.dataList = [];
                this.accflowList = [];
            },
            searchJournalByNumber: function (number) {
                var defer = $q.defer();
                var that = this;
                that.refresh();
                allTransactionService.findJournalByNumberAndBranch(number)
                        .success(function (data) {
                            if (data.length > 0) {
                                for (var i = 0; i < data.length; i++) {
                                    that.tempData = data[i];
                                    that.add();
                                }
                                that.data.description = data[1].description;
                                console.log(new Date(data[1].transactionDate));
                                that.data.transactionDate = new Date(data[1].transactionDate);
                            } else {
                                defer.reject(data);
                            }
                        });
                return defer.promise;
            }
        };
        return employeeModel;
    };
    angular.module("appModule")
            .factory("journalModel", factory);
}());


