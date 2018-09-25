
(function () {
    var factory = function (editTransactionFactory, editTransactionService, $q, $filter, Notification) {
        function editTransactionModel() {
            this.constructor();

        }
        //prototype functions
        editTransactionModel.prototype = {
            data: {},
            tempData: {},
            userPermission: {},
            ledgerTypeList: [],
            user: [],
            accLedgerTypeDataList: [],
            accountList: [],
            costCenterList: [],
            deleteRefDetailList: [],
            costDepartmentList: [],
            financialYearList: [],
            selectLedgerType: null,
            tabActive: 0,
            //constructor
            constructor: function () {
                var that = this;
                that.data = editTransactionFactory.Data();
                that.tempData = editTransactionFactory.tempData();
                editTransactionService.loadBranch()
                        .success(function (data) {
                            that.branchList = data;
                        });
                editTransactionService.loadUsers()
                        .success(function (data) {
                            that.userList = data;
                        });
                editTransactionService.getAccLedgerTypeList()
                        .success(function (data) {
                            that.ledgerTypeList = data;
                        });
                editTransactionService.loadAccounts()
                        .success(function (data) {
                            that.accountList = data;
                        });
                editTransactionService.loadCostDepartment()
                        .success(function (data) {
                            that.costDepartmentList = data;
                        });
                editTransactionService.loadCostCenter()
                        .success(function (data) {
                            that.costCenterList = data;
                        });
                editTransactionService.loadfinancialYear()
                        .success(function (data) {
                            that.financialYearList = data;
                        });
                editTransactionService.getPermission('Edit Transaction')
                        .success(function (data) {
                            that.userPermission = data;
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
            costDepartmentLable: function (model) {
                var label;
                angular.forEach(this.costDepartmentList, function (value) {
                    if (value.indexNo === model) {
                        label = value.name;
                        return;
                    }
                });
                return label;
            },
            costCenterLable: function (model) {
                var label;
                angular.forEach(this.costCenterList, function (value) {
                    if (value.indexNo === model) {
                        label = value.name;
                        return;
                    }
                });
                return label;
            },
            accountLable: function (model) {
                var label;
                angular.forEach(this.accountList, function (value) {
                    if (value.indexNo === model) {
                        label = value.accCode + ' - ' + value.name;
                        return;
                    }
                });
                return label;
            },
            financialYearLable: function (model) {
                var label;
                angular.forEach(this.financialYearList, function (value) {
                    if (value.indexNo === model) {
                        label = value.name;
                        return;
                    }
                });
                return label;
            },
            userLable: function (model) {
                var label;
                angular.forEach(this.userList, function (value) {
                    if (value.indexNo === model) {
                        label = value.name;
                        return;
                    }
                });
                return label;
            }
            , getLedgerTypeList: function (name, param) {
                var paramModel = {};
                var defer = $q.defer();
                var that = this;
                that.selectLedgerType = name;
                var fDate = $filter('date')(param.fromDate, 'yyyy-MM-dd');
                var tDate = $filter('date')(param.toDate, 'yyyy-MM-dd');
                var invDate = $filter('date')(param.invDate, 'yyyy-MM-dd');
                var paramModel = {
                    "name": that.selectLedgerType,
                    "fromDate": fDate,
                    "toDate": tDate,
                    "branch": param.branch,
                    "financialYear": param.financialYear,
                    "account": param.account,
                    "invDate": invDate,
                    "refNo": param.refNo

                };
                editTransactionService.getLedgerTypeDataList(JSON.stringify(paramModel))
                        .success(function (data) {
                            if (data) {
                                that.accLedgerTypeDataList = data;
                            } else {
                                Notification.error("Empty data. change parameeter and try again !");
                                that.accLedgerTypeDataList = [];
                            }
                        });
                return defer.promise;
            }
            , getDeleteRefDetails: function (number) {

                var defer = $q.defer();
                var that = this;
                editTransactionService.getDeleteRefDetails(number)
                        .success(function (data) {
                            that.deleteRefDetailList = data;
                            that.tabActive = 1;
                        });
                return defer.promise;
            }
            , setEdit: function (index, data) {
                var that = this;
                that.deleteRefDetailList.splice(index, 1);
                that.tempData = data;
            }
            , addData: function () {
                this.tempData.isEdit = 1;
                this.deleteRefDetailList.push(this.tempData);
                this.tempData = editTransactionFactory.tempData();
            }
            , refresh: function () {
                this.deleteRefDetailList = [];
                this.tempData = editTransactionFactory.tempData();
                this.tabActive = 0;
                this.accLedgerTypeDataList = [];
            }
            , save: function () {
                var defer = $q.defer();
                var that = this;
                editTransactionService.saveEditedData(JSON.stringify(this.deleteRefDetailList))
                        .success(function (data) {
                            that.refresh();
                            defer.resolve(data);
                        })
                        .error(function (data) {
                            defer.reject(data);
                        });
                return defer.promise;
            }
            , delete: function () {
                var defer = $q.defer();
                var that = this;
                console.log(this.deleteRefDetailList[0].deleteRefNo);
                editTransactionService.delete(this.deleteRefDetailList[0].deleteRefNo)
                        .success(function (data) {
                            that.refresh();
                            defer.resolve(data);
                        })
                        .error(function (data) {
                            defer.reject(data);
                        });
                return defer.promise;
            }
        };
        return editTransactionModel;
    };
    angular.module("appModule")
            .factory("editTransactionModel", factory);
}());


