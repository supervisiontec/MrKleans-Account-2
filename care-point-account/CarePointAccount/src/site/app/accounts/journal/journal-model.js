
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
            accCategory1List: [],
            accCategory2List: [],
            accCategory3List: [],
            accCategoryMainList: [],
            accAccountList: [],
            branchList: [],
            //constructor
            constructor: function () {
                var that = this;
                that.data = journalFactory.Data();
                that.tempData = journalFactory.tempData();

                journalService.loadAccCategory1()
                        .success(function (data) {
                            that.accCategory1List = data;
                        });
                journalService.loadAccCategory2()
                        .success(function (data) {
                            that.accCategory2List = data;
                        });
                journalService.loadAccCategory3()
                        .success(function (data) {
                            that.accCategory3List = data;
                        });
                journalService.loadAccCategoryMain()
                        .success(function (data) {
                            that.accCategoryMainList = data;
                        });
                journalService.loadAccAccounts()
                        .success(function (data) {
                            that.accAccountList = [];
                            for (var i = 0; i < data.length; i++) {
                                data[i].accMainIndex = data[i].accMain.indexNo;
                                that.accAccountList.push(data[i]);
                            }
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
            new:function (){
                this.data.transactionDate = new Date();
                this.tempData.branch = this.currentBranch.indexNo;
                
            }
            , accMainLable: function (model) {
                var label;
                angular.forEach(this.accCategoryMainList, function (value) {
                    if (value.indexNo === model) {
                        label = value.indexNo + ' - ' + value.name;
                        return;
                    }
                });
                return label;
            }
            , accountLable: function (model) {
                var label;
                angular.forEach(this.accAccountList, function (value) {
                    if (value.indexNo === model) {
                        label = value.indexNo + ' - ' + value.name;
                        return;
                    }
                });
                return label;
            }
            , accCategory1Lable: function (model) {
                var label;
                angular.forEach(this.accCategory1List, function (value) {
                    if (value.indexNo === model) {
                        label = value.indexNo + ' - ' + value.name;
                        return;
                    }
                });
                return label;
            }
            , accCategory2Lable: function (model) {
                var label;
                angular.forEach(this.accCategory2List, function (value) {
                    if (value.indexNo === model) {
                        label = value.indexNo + ' - ' + value.name;
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
                var that = this;
                angular.forEach(this.accAccountList, function (value) {
                    if (value.indexNo === parseInt(accIndexNo)) {
                        that.tempData.accMain = value.accMainIndex;
                        that.tempData.category1 = value.accCategory1.indexNo;
                        that.tempData.category2 = value.accCategory2.indexNo;
                        that.tempData.cop = value.cop;
                        console.log(that.tempData.accMain);
                        return;
                    }
                });
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
            }
        };
        return employeeModel;
    };
    angular.module("appModule")
            .factory("journalModel", factory);
}());


