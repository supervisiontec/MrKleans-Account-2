(
        function () {
            var factory = function ($filter, trialBalanceService) {
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
                            if (data.isAccAccount === 1) {
                                debit += data.debit;
                                credit += data.credit;
                                console.log(debit+' - '+credit);
                            }
                            data.view = false;
                            data.amountShow = true;
                            if (data.level === 1) {
                                data.view = true;
                            }
                        });
                        that.totalCredit = credit;
                        that.totalDebit = debit;
                        console.log('total cr:',that.totalCredit);
                        console.log('total de',that.totalDebit);
                        that.loderView = false;

                    }
                    , viewLevel: function (data) {
                        var that = this;
//                if (that.level === 2 && data.level === 1) {
//                    that.level = 1;
//                } else {
                        that.level = parseInt(data.level) + 1;
//                }
                        if (data.count > 0) {
                            data.amountShow = data.amountShow === true ? false : true;

                        }
                        that.filter1(data);
                    },
                    filter1: function (data) {
                        console.log("filter func");
                        var that = this;
                        angular.forEach(that.accMainList, function (acc) {
                            var code = data.accCode;

                            console.log(acc.accCode.substring(0, code.length));
                            if (acc.accCode.substring(0, code.length) === code && that.level >= acc.level) {
                                if (acc.subAccountOf === data.accNo) {
                                    acc.view = acc.view === true ? false : true;
                                }
                            }
                        });
                    }

                };
                return trialBalanceModel;
            };
            angular.module("appModule")
                    .factory("trialBalanceModel", factory);
        }());


