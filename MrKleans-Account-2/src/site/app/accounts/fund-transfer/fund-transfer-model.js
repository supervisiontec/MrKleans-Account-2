
(function () {
    var factory = function (fundTransferFactory, fundTransferService, $q, $timeout, $filter, Notification) {
        function fundTransferModel() {
            this.constructor();
        }
//prototype functions
        fundTransferModel.prototype = {
            data: {},
            tempData: {},
            currentBranch: {},
            selectAccType: {},
            userPermission: {},
            accAccountList: [],
            branchList: [],
            saveDataList: [],
            accTypeList: [],
            //constructor
            constructor: function () {
                var that = this;
                that.data = fundTransferFactory.Data();
                that.tempData = fundTransferFactory.tempData();


                fundTransferService.loadBranch()
                        .success(function (data) {
                            that.branchList = data;
                        });
                fundTransferService.currentBranch()
                        .success(function (data) {
                            that.currentBranch = data;
                            that.data.branch = data.indexNo;
                        });
                fundTransferService.loadAccTypes()
                        .success(function (data) {
                            that.accTypeList = data;
                        });
                fundTransferService.getPermission('Fund Transfer')
                        .success(function (data) {
                            that.userPermission = data;
                        });

                this.loadAccAccount();
            },
            loadAccAccount: function () {
                var that = this;
                fundTransferService.loadAccAccounts()
                        .success(function (data) {
                            that.accAccountList = data;
                        });
            },
            getValue: function (accIndex) {
                var that = this;
                fundTransferService.loadAccBalance(accIndex)
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
            addData: function () {
                var that = this;
                this.tempData.transactionDate = $filter('date')(this.data.transactionDate, 'yyyy-MM-dd');
                this.saveDataList.push(this.tempData);
                this.tempData = fundTransferFactory.tempData();
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
                data.data.isCheque = angular.isUndefined(this.data.isCheque)?false:this.data.isCheque;

                var defer = $q.defer();
                fundTransferService.saveFundTransfer(JSON.stringify(data))
                        .success(function (data) {
                            that.setClear();
                            defer.resolve(data);
                        })
                        .error(function (data) {
                            defer.reject(data);
                        });
                return defer.promise;
            },
            setClear: function () {

                this.tempData = fundTransferFactory.tempData();
                this.data = fundTransferFactory.Data();
                this.tempData.branch = this.currentBranch.indexNo;
                this.data.branch = this.currentBranch.indexNo;
                this.saveDataList = [];
            },
            searchByNumber: function (number) {
                var defer = $q.defer();
                var that = this;
                fundTransferService.findFundTransferByNumberAndBranch(number)
                        .success(function (data) {
                            if (data.length > 0) {
//                                for (var i = 0; i < data.length; i++) {
//                                    that.tempData = data[i];
//                                    that.addData();
//                                }
//                                that.data.description = data[1].description;
//                                that.data.refNumber = data[1].refNumber;
//                                that.data.accAccount = data[1].accAccount;
//                                that.accountLable(that.data.accAccount);
                                for (var i = 0; i < data.length; i++) {
                                    if (!data[i].isMain) {
                                        that.tempData = data[i];
                                        that.addData();
                                    } else {
                                        that.data.accAccount = data[i].accAccount;
                                        that.data.value = data[i].value;
                                        that.data.description = data[i].description;
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
        return fundTransferModel;
    };
    angular.module("appModule")
            .factory("fundTransferModel", factory);
}());


