(
        function () {
            var factory = function ($filter, $q, trialBalanceService) {
                function trialBalanceModel() {
                    this.constructor();
                }
//prototype functions
                trialBalanceModel.prototype = {
                    accMainList: [],
                    totalDebit: 0.00,
                    totalCredit: 0.00,
                    level: 1,
                    subAccountOf: 0,

                    //constructor
                    constructor: function () {

                    },
                    selectDate: function () {
                        var that = this;
                        that.loderView = true;
                        var date = $filter('date')(that.date, 'yyyy-MM-dd');
                        trialBalanceService.loadMainAcc(date)
                                .success(function (data) {
                                    that.accMainList = data;
                                    that.totalCalc();
                                });
                    }
                    , totalCalc: function () {
                        var debit = 0.00;
                        var credit = 0.00;
                        var that = this;

                        angular.forEach(that.accMainList, function (data) {

                            debit += data.debit;
                            credit += data.credit;

                            data.view = true;
                            data.amountShow = true;

                        });
                        that.totalCredit = credit;
                        that.totalDebit = debit;
                        that.loderView = false;

                    }
                    , viewLevel: function (data) {
                        var that = this;
                        var defer = $q.defer();
                        if (data.amountShow) {
                            var date = $filter('date')(that.date, 'yyyy-MM-dd');
                            trialBalanceService.getSubData(date, data.accNo)
                                    .success(function (subDataList) {
                                        data.amountShow = data.amountShow === true ? false : true;
                                        if (subDataList) {
                                            angular.forEach(subDataList, function (sub) {
                                                sub.view = true;
                                                sub.amountShow = true;
                                                that.accMainList.unshift(sub);
                                            });
                                        } else {
                                            defer.reject(data);
                                        }
                                    });
                            return defer.promise;
                        } else {
                            angular.forEach(that.accMainList, function (sub) {
                                if (sub.subAccountOf === data.accNo) {
                                    sub.amountShow = false;
                                    sub.view = false;
                                }
                            });
                            data.amountShow = true;
                        }
                    }
//                    
                };
                return trialBalanceModel;
            };
            angular.module("appModule")
                    .factory("trialBalanceModel", factory);
        }());


