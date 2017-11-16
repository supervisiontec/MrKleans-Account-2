
(function () {
    var factory = function (bankReconciliationFactory, bankReconciliationService, $q, $timeout, $filter, Notification) {
        function employeeModel() {
            this.constructor();
        }
//prototype functions
        employeeModel.prototype = {
            data: {},
            tempData: {},
            currentBranch: {},
            accAccountList: [],
            incomeExpenceAccList: [],
            branchList: [],
            reconciliationList: [],
            saveDataList: [],
            popupDataList: [],
            accTypeList: [],
            totBalance: 0.00,
            //constructor
            constructor: function () {
                var that = this;
                that.data = bankReconciliationFactory.Data();
                that.tempData = bankReconciliationFactory.tempDate();

                bankReconciliationService.loadBranch()
                        .success(function (data) {
                            that.branchList = data;
                        });
                bankReconciliationService.currentBranch()
                        .success(function (data) {
                            that.currentBranch = data;
                            that.data.branch = data.indexNo;
                        });

                this.loadAccAccount();
            },
            loadAccAccount: function () {
                var that = this;
                bankReconciliationService.loadAccAccounts()
                        .success(function (data) {
                            that.accAccountList = data;
                        });
            },

            selectBankAccount: function (acc, date) {
                var that = this;
                var formatDate = $filter('date')(date, 'yyyy-MM-dd');
                bankReconciliationService.getReconciliationDetail(acc, formatDate)
                        .success(function (data) {
                            that.reconciliationList = data;
                            that.reconciliationValueCaluclator();
                        });
                bankReconciliationService.loadAccBalance(acc)
                        .success(function (data) {
                            that.data.value = data;
                        });
            },
            selectDate: function () {
                if (!this.reconciliationList.length) {
                    this.selectBankAccount(this.data.accAccount, this.data.transactionDate);
                }
                //start balance
                var year = $filter('date')(this.data.transactionDate, 'yyyy');
                var month = $filter('date')(this.data.transactionDate, 'MM');
                var fDate = year + '-' + month + '-' + 01;
                var that = this;


                bankReconciliationService.getStartBalance(fDate, this.data.accAccount)
                        .success(function (data) {
                            that.data.monthStart = data;
                            that.totBalance = data;

                        });

                if (!this.saveDataList.length) {

                    bankReconciliationService.loadTransactions(year, month, this.data.accAccount)
                            .success(function (data) {
                                that.saveDataList = data;

                                var b_f = bankReconciliationFactory.Data();
                                b_f.transactionDate = new Date(fDate);
                                b_f.description = 'B / f ';
                                b_f.balance = that.data.monthStart;
                                that.saveDataList.unshift(b_f);

                                that.balanceCalculator();

                            });
                }

            },
            balanceCalculator: function () {
                console.log('balanceCalculator');
                var that = this;
                that.saveDataList = $filter('orderBy')(that.saveDataList, 'transactionDate');
                that.totBalance = that.data.monthStart;
                var debit = 0.00;
                var credit = 0.00;
                angular.forEach(that.saveDataList, function (data) {
                    that.totBalance += data.debit;
                    that.totBalance -= data.credit;
                    data.balance = that.totBalance;
                    debit += data.debit;
                    credit += data.credit;
                });
                that.data.value = that.totBalance;
                that.data.creditTotal = credit;
                that.data.debitTotal = debit;
            },
            reconciliationValueCaluclator: function () {
                console.log('reconciliationValueCaluclator');
                var debit = 0.00;
                var credit = 0.00;
                angular.forEach(this.reconciliationList, function (data) {
                    debit += data.debit;
                    credit += data.credit;
                });
                this.data.reconcileValue = debit - credit;
            },
            addReconciliationCheque: function (data) {
                var that = this;
                angular.forEach(this.reconciliationList, function (chequeData, index) {
                    data.class = "reconcile";
                    data.transactionDate = $filter('date')(that.data.transactionDate, 'yyyy-MM-dd');
                    if (data.indexNo === chequeData.indexNo) {
                        that.saveDataList.push(data);
                        that.reconciliationList.splice(index, 1);

                    }
                });
                that.reconciliationValueCaluclator();
                that.balanceCalculator();
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
            accountLable: function (model) {
                var label;
                angular.forEach(this.accAccountList, function (value) {
                    if (value.indexNo === model) {
                        label = value.indexNo + ' - ' + value.name;
                        return;
                    }
                });
                return label;
            },

            save: function () {
                var defer = $q.defer();
                var that = this;
                var reconcileData = [];
                angular.forEach(this.saveDataList, function (data) {
                    if (data.class === 'reconcile') {
                        reconcileData.push(data);
                    }
                });
                var data = {
                    'data': this.data,
                    'dataList': reconcileData
                };
                bankReconciliationService.saveBankReconciliation(JSON.stringify(data))
                        .success(function (data) {
                            Notification.success('Reconcile Cheque Save Success');
                            that.saveDataList = [];
                            that.reconciliationList = [];
                            that.data = bankReconciliationFactory.Data();
                            defer.resolve(data);
                        })
                        .error(function (data) {
                            defer.reject(data);
                        });
            },
            remove: function (index) {
                var that = this;
                angular.forEach(this.saveDataList, function (data, id) {
                    if (index === parseInt(data.indexNo)) {

                        that.saveDataList.splice(id, 1);
                        that.reconciliationValueCaluclator();
                        return;
                    }
                });
                that.getDeleteReconciliationCheque(index);
                that.balanceCalculator();
            },
            delete: function (data2) {
                var that = this;
                bankReconciliationService.delete(data2.deleteRefNo)
                        .success(function (data) {
                            angular.forEach(that.saveDataList, function (data, id) {
                                console.log(data2.indexNo + " = " + data.indexNo);
                                if (data2.indexNo === parseInt(data.indexNo)) {

                                    that.saveDataList.splice(id, 1);
                                    return;
                                }
                            });
                            that.balanceCalculator();
                            Notification.success('delete success');
                        });
            },
            getDeleteReconciliationCheque: function (index) {
                var that = this;
                bankReconciliationService.getDeleteReconciliationCheque(index)
                        .success(function (data) {
                            that.reconciliationList.push(data);
                            that.reconciliationValueCaluclator();
                        });

            },
            setIncomeExpenceAccount: function () {
                var that = this;
                angular.forEach(this.accAccountList, function (data) {

                    if (data.accMain.isExpence || data.accMain.isIncome) {
                        that.incomeExpenceAccList.push(data);
                    }
                });
            },
            popupAddData: function () {
                var that = this;
                that.tempData.transactionDate = $filter('date')(that.tempData.transactionDate, 'yyyy-MM-dd');
                that.popupDataList.push(that.tempData);
                that.tempData = bankReconciliationFactory.tempDate();
                that.tempData.branch = that.currentBranch.indexNo;
            },
            popupSave: function () {
                var defer = $q.defer();
                var that = this;
                var data = {
                    'data': that.data,
                    'dataList': that.popupDataList
                };
                console.log(data);
                bankReconciliationService.savePopupData(JSON.stringify(data))
                        .success(function (data) {
                            Notification.success('Other Reconcile data Save Success !');
                            that.popupDataList = [];

                            defer.resolve(data);
                        })
                        .error(function (data) {
                            defer.reject(data);
                        });
            }
        };
        return employeeModel;
    };
    angular.module("appModule")
            .factory("bankReconciliationModel", factory);
}());


