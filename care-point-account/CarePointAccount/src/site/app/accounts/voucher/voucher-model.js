
(function () {
    var factory = function (voucherFactory, voucherService, $q,$timeout, $filter, Notification) {
        function employeeModel() {
            this.constructor();
        }
//prototype functions
        employeeModel.prototype = {
            data: {},
            tempData: {},
            currentBranch: {},
            accCategory1List: [],
            accCategory2List: [],
            AccCategory3List: [],
            accCategoryMainList: [],
            accAccountList: [],
            branchList: [],
            saveDataList: [],
            //constructor
            constructor: function () {
                var that = this;
                that.data = voucherFactory.Data();
                that.tempData = voucherFactory.tempData();

                voucherService.loadAccCategory1()
                        .success(function (data) {
                            that.accCategory1List = data;
                        });
                voucherService.loadAccCategory2()
                        .success(function (data) {
                            that.accCategory2List = data;
                        });
                voucherService.loadAccCategory3()
                        .success(function (data) {
                            that.accCategory3List = data;
                        });
                voucherService.loadAccCategoryMain()
                        .success(function (data) {
                            that.accCategoryMainList = data;
                        });
                voucherService.loadBranch()
                        .success(function (data) {
                            that.branchList = data;
                        });
                voucherService.currentBranch()
                        .success(function (data) {
                            that.data.branch = data.indexNo;
                        });

                this.loadAccAccount();
            },
            loadAccAccount: function () {
                var that = this;
                voucherService.loadAccAccounts()
                        .success(function (data) {
                            that.accAccountList = data;
                        });
            },
            getValue: function (accIndex) {
                var that = this;
                voucherService.loadAccBalance(accIndex)
                        .success(function (data) {
                            that.data.value = data;
                        });
            },

//            delete: function (accIndex, index) {
//                var that = this;
//                var defer = $q.defer();
//                createAccountService.delete(accIndex)
//                        .success(function (data) {
//                            that.clearData();
//                            that.loadAccAccount();
//                            defer.resolve(data);
//                        })
//                        .error(function (data) {
//                            Notification.error(data.massage);
//                            defer.reject(data);
//                        });
//                return defer.promise;
//
//            },
            accMainLable: function (model) {
                var label;
                angular.forEach(this.accCategoryMainList, function (value) {
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
            , accCategory3Lable: function (model) {
                var label;
                angular.forEach(this.accCategory3List, function (value) {
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
            addData: function () {
                var that=this;
                this.tempData.transactionDate=$filter('date')(this.tempData.transactionDate, 'yyyy-MM-dd');
                this.saveDataList.push(this.tempData);
                this.tempData = voucherFactory.tempData();;
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
                data.voucherList = this.saveDataList;
                data.voucher = this.data;
                console.log(data);

                var defer = $q.defer();
                voucherService.saveVoucher(JSON.stringify(data))
                        .success(function (data) {
                            defer.resolve(data);
                        })
                        .error(function (data) {
                            defer.reject(data);
                        });
                return defer.promise;
            }
        };
        return employeeModel;
    };
    angular.module("appModule")
            .factory("voucherModel", factory);
}());


