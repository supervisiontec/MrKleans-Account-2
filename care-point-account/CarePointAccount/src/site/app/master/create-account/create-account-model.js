
(function () {
    var factory = function (createAccountFactory, createAccountService, $q, $filter, Notification) {
        function employeeModel() {
            this.constructor();
        }
        //prototype functions
        employeeModel.prototype = {
            data: {},
            search: {},
            accCategory1List: [],
            accCategory2List: [],
            AccCategory3List: [],
            accCategoryMainList: [],
            accAccountList: [],
            //constructor
            constructor: function () {
                var that = this;
                that.data = createAccountFactory.Data();
                that.search = createAccountFactory.search();

                createAccountService.loadAccCategory1()
                        .success(function (data) {
                            that.accCategory1List = data;
                        });
                createAccountService.loadAccCategory2()
                        .success(function (data) {
                            that.accCategory2List = data;
                        });
                createAccountService.loadAccCategory3()
                        .success(function (data) {
                            that.accCategory3List = data;
                        });
                createAccountService.loadAccCategoryMain()
                        .success(function (data) {
                            that.accCategoryMainList = data;
                        });
                this.loadAccAccount();
            },
            loadAccAccount: function () {
                var that = this;
                createAccountService.loadAccAccounts()
                        .success(function (data) {
                            that.accAccountList = data;
                        });
            },
            refresh: function () {
                var that = this;
                createAccountService.loadAccCategory2()
                        .success(function (data) {
                            that.accCategory2List = data;
                        });
                createAccountService.loadAccCategory3()
                        .success(function (data) {
                            that.accCategory3List = data;
                        });
                createAccountService.loadAccCategoryMain()
                        .success(function (data) {
                            that.accCategoryMainList = data;
                        });
                createAccountService.loadAccAccounts()
                        .success(function (data) {
                            that.accAccountList = data;
                        });
            },
            clearData: function () {
                var that = this;
                that.data = createAccountFactory.Data();
            },
            edit: function (account) {
                this.clearData();
                this.data = account;

            },
            delete: function (accIndex, index) {
                var that = this;
                var defer = $q.defer();
                createAccountService.delete(accIndex)
                        .success(function (data) {
                            that.clearData();
                            that.loadAccAccount();
                            defer.resolve(data);
                        })
                        .error(function (data) {
                            Notification.error(data.massage);
                            defer.reject(data);
                        });
                return defer.promise;

            },
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
            save: function () {
                var that = this;

                if (angular.isUndefined(this.data.accCategory1.indexNo)) {
                    var name = this.data.accCategory1;
                    that.data.accCategory1 = {};
                    this.data.accCategory1.indexNo = null;
                    this.data.accCategory1.name = name;
                    this.data.accCategory1.orderNo = 1;
                }
                if (angular.isUndefined(this.data.accCategory2.indexNo)) {
                    var name = this.data.accCategory2;
                    that.data.accCategory2 = {};
                    this.data.accCategory2.indexNo = null;
                    this.data.accCategory2.name = name;
                    this.data.accCategory2.orderNo = 1;
                }
                if (angular.isUndefined(this.data.accCategory3.indexNo)) {
                    var name = this.data.accCategory3;
                    that.data.accCategory3 = {};
                    this.data.accCategory3.indexNo = null;
                    this.data.accCategory3.name = name;
                    this.data.accCategory3.orderNo = 1;
                }
                console.log(this.data);
                var defer = $q.defer();
                createAccountService.saveNewAccount(JSON.stringify(that.data))
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
        return employeeModel;
    };
    angular.module("appModule")
            .factory("createAccountModel", factory);
}());


