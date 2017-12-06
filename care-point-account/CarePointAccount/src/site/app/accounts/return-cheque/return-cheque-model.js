
(function () {
    var factory = function (returnChequeFactory, returnChequeService, $q, $timeout, $filter, Notification) {
        function returnChequeModel() {
            this.constructor();
        }
//      prototype functions
        returnChequeModel.prototype = {
            data: {},
            tempData: {},
            currentBranch: {},
            accAccountList: [],
            typeAccAccountList: [],
            branchList: [],
            supplierList: [],
            accTypeList: [],
            userList: [],
            chequeDetailList: [],
            accType: null,
            selectedCheque: {},
            //constructor
            constructor: function () {
                var that = this;
                that.data = returnChequeFactory.Data();
                that.tempData = returnChequeFactory.tempData();
                
                returnChequeService.loadBranch()
                        .success(function (data) {
                            that.branchList = data;
                        });
                returnChequeService.loadSupplier()
                        .success(function (data) {
                            that.supplierList = data;
                        });
                returnChequeService.currentBranch()
                        .success(function (data) {
                            that.currentBranch = data;
                            that.data.branch = data.indexNo;
                        });
                returnChequeService.getUsers()
                        .success(function (data) {
                            that.userList = data;
                        });
                this.loadAccAccount();
            },
            loadAccAccount: function () {
                var that = this;
                returnChequeService.loadAccAccounts()
                        .success(function (data) {
                            that.accAccountList = data;
                        });
            },

            selectCheque: function (cheque) {
                var that = this;
                that.selectedCheque=cheque;
                that.tempData.supplier = "";
                angular.forEach(that.supplierList, function (supplier) {
                    if (supplier.indexNo === cheque.typeIndexNo) {
                        that.tempData.supplier = supplier.indexNo + " - " + supplier.name;
                        return;
                    }
                });
                angular.forEach(that.userList, function (user) {
                    if (user.indexNo === cheque.user) {
                        that.tempData.user = user.indexNo + " - " + user.username;
                        return;
                    }
                });
                that.tempData.transactionNo = cheque.number;
                that.tempData.transactionDate = cheque.transactionDate;
                that.tempData.formName = cheque.formName;

                this.getChequeDetails(cheque.deleteRefNo);
            },
            getChequeDetails: function (deleteIndex) {
                var that = this;
                returnChequeService.getSelectedChequeDetails(deleteIndex)
                        .success(function (data) {
                            that.chequeDetailList = data;
                            console.log(data);
                        });
            },
            checkType: function (type) {
                var that = this;
                that.data.accType = type;
                that.chequeList = [];
                returnChequeService.getCheques(type)
                        .success(function (data) {
                            that.chequeList = data;
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

            save: function () {
                var that = this;
                var data = {};
                data.dataList = this.chequeDetailList;
                this.data.transactionDate = $filter('date')(this.data.transactionDate, 'yyyy-MM-dd');
                data.data = this.data;

                console.log(data);

                var defer = $q.defer();
                returnChequeService.save(JSON.stringify(data))
                        .success(function (data) {
                            that.chequeDetailList = [];
                            that.chequeList = [];
                            that.data = returnChequeFactory.Data();
                            that.tempData = returnChequeFactory.tempData();
                            defer.resolve(data);
                        })
                        .error(function (data) {
                            defer.reject(data);
                        });
                return defer.promise;
            }
        };
        return returnChequeModel;
    };
    angular.module("appModule")
            .factory("returnChequeModel", factory);
}());


