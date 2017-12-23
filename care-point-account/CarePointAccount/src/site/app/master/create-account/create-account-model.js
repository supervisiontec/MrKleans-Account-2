
(function () {
    var factory = function (createAccountFactory, createAccountService, $q, $filter, Notification) {
        function employeeModel() {
            this.constructor();
        }
//prototype functions
        employeeModel.prototype = {
            data: {},
            accAccountList: [],
            accTypeList: ["COMMON", "CASH", "BANK"],

            //constructor
            constructor: function () {
                var that = this;
                that.data = createAccountFactory.Data();

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
                this.loadAccAccount();
                this.data=createAccountFactory.Data();
            },
            clearData: function () {
                var that = this;
                that.data = createAccountFactory.Data();
            },
            edit: function (account) {
                this.clearData();
                this.data = account;
            },
            delete: function (accIndex) {
                var that = this;
                var defer = $q.defer();
                createAccountService.delete(accIndex)
                        .success(function (data) {
                            that.clearData();
                            that.loadAccAccount();
                            defer.resolve(data);
                        })
                        .error(function (data) {
                            Notification.error(data);
                            defer.reject(data);
                        });
                return defer.promise;
            }

            , accAccountLabel: function (model) {
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

                console.log(this.data);
                var defer = $q.defer();
                createAccountService.saveNewAccount(JSON.stringify(that.data))
                        .success(function (data) {
                            that.loadAccAccount();
                            defer.resolve();
                        })
                        .error(function () {
                            defer.reject();
                        });
                return defer.promise;
            }
        };
        return employeeModel;
    };
    angular.module("appModule")
            .factory("createAccountModel", factory);
}());


