
(function () {
    var factory = function (journalFactory, journalService, $q, $filter, Notification) {
        function employeeModel() {
            this.constructor();

        }
        //prototype functions
        employeeModel.prototype = {
            data: {},
            tempData: {},
            currentBranch: {},
            dataList: [],
            accAccountList: [],
            accflowList: [],
            branchList: [],
            //constructor
            constructor: function () {
                var that = this;
                that.data = journalFactory.Data();
                that.tempData = journalFactory.tempData();


                journalService.loadAccAccounts()
                        .success(function (data) {
                            that.accAccountList = data;
                        });
                journalService.loadBranch()
                        .success(function (data) {
                            that.branchList = data;
                        });
                journalService.currentBranch()
                        .success(function (data) {
                            that.currentBranch = data;
                        });
            },
            setAccountFlow: function (acc) {
                var that = this;
                journalService.setAccFlow(acc)
                        .success(function (data) {
                            that.accflowList = data;
                            console.log(data);
                        });
            },
            new : function () {
                this.data.transactionDate = new Date();
                this.tempData.branch = this.currentBranch.indexNo;

            }

            , accountLable: function (model) {
                var label;
                angular.forEach(this.accAccountList, function (value) {
                    if (value.indexNo === model) {
                        label = value.accCode + ' - ' + value.name;
                        return;
                    }
                });
                return label;
            }

            , branchLable: function (model) {
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
                this.dataList.unshift(saveData);
                this.tempData = journalFactory.tempData();
                this.totalCalculated();
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
            }
            ,
            save: function () {
                var defer = $q.defer();
                var that = this;
                journalService.saveJournal(JSON.stringify(that.dataList))
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
                this.data = journalFactory.Data();
                this.tempData = journalFactory.tempData();
                this.dataList = [];
                this.accflowList = [];
            }
        };
        return employeeModel;
    };
    angular.module("appModule")
            .factory("journalModel", factory);
}());

