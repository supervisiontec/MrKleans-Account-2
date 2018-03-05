
(function () {
    var factory = function (supplierPaymentFactory, supplierPaymentService, $q, $timeout, $filter, Notification) {
        function supplierPaymentModel() {
            this.constructor();
        }
//      prototype functions
        supplierPaymentModel.prototype = {
            data: {},
            currentBranch: {},
            accAccountList: [],
            typeAccAccountList: [],
            branchList: [],
            supplierList: [],
            accTypeList: [],
            accflowList: [],
            billList: [],
            accType: null,
            userPermission: {},
            //constructor
            constructor: function () {
                var that = this;
                that.data = supplierPaymentFactory.Data();

                supplierPaymentService.loadBranch()
                        .success(function (data) {
                            that.branchList = data;
                        });
                supplierPaymentService.loadSupplier()
                        .success(function (data) {
                            that.supplierList = data;
                        });
                supplierPaymentService.currentBranch()
                        .success(function (data) {
                            that.currentBranch = data;
                            that.data.branch = data.indexNo;
                        });
                supplierPaymentService.getPermission('Item Registration')
                        .success(function (data) {
                            console.log(data);
                            that.userPermission = data;
                        });


                this.loadAccAccount();
            },
            loadAccAccount: function () {
                var that = this;
                supplierPaymentService.loadAccAccounts()
                        .success(function (data) {
                            that.accAccountList = data;
                        });
            },
            getValue: function (accAccount) {
                var that = this;
                supplierPaymentService.loadAccBalance(accAccount)
                        .success(function (data) {
                            that.data.value = data;
                            console.log(that.data.value);
                        });

            },
            getPayableBills: function (supplierId) {
                var that = this;
                var accAccount = null;
                angular.forEach(that.supplierList, function (supplier) {
                    if (supplierId === parseInt(supplier.indexNo)) {
                        accAccount = supplier.accAccount;
                        return;
                    }
                });
                if (accAccount !== null) {
                    this.getOverPaymentAmount(supplierId);
                    supplierPaymentService.getPayableBills(accAccount)
                            .success(function (data) {
                                that.billList = [];
                                angular.forEach(data, function (bill) {
                                    bill.pay = 0.00;
                                    that.billList.push(bill);
                                });
                            });

                } else {
                    that.billList = [];
                    this.setPayTotalAndOverPayment();
                    Notification.error("this supplier not linked to account system yet !");
                }
            },
            getOverPaymentAmount: function (supplier) {
                var that = this;
                supplierPaymentService.getOverPaymentAmount(supplier)
                        .success(function (data) {
                            that.data.overAmount = data;
                        });
            },
            checkOverValueAvailable: function () {
                if (this.data.accType === 'OVER_PAYMENT') {

                    var balance = this.data.overAmount - this.data.credit;
                    if (balance < 0) {
                        Notification.error('overpayment value not enough !');

                    }
                }
            },
            setBalanceAmountToPay: function (bill) {
                if (!bill.pay) {
                    bill.pay = (bill.credit - bill.debit);
                }
                this.setPayTotalAndOverPayment();
            },
            changePayAmount: function () {
                this.setPayTotalAndOverPayment();
            },
            setPayTotalAndOverPayment: function () {
                var that = this;
                that.data.billTotal = 0;
                that.data.overPay = 0;

                angular.forEach(that.billList, function (bill) {
                    var balance = bill.credit - bill.debit;
                    if (balance < bill.pay) {
                        Notification.error("Can't set over amount for this bill !");
                        bill.pay = 0.00;
                    }
                    that.data.billTotal += bill.pay;
                });
                that.data.overPay = (that.data.credit - that.data.billTotal) > 0 ? (that.data.credit - that.data.billTotal) : 0.00;
                this.checkOverValueAvailable();

            },
            clearTypeChange: function () {
                var that = this;
//                that.data.credit = 0.00;
                that.data.refNumber = '';
                that.data.chequeDate = null;
                that.data.accAccount = null;
//                that.data.value = 0.00;

            },
            checkType: function (type) {
                var that = this;
                that.data.accType = type;
                this.clearTypeChange();

                if (type === 'CASH') {
                    that.typeAccAccountList = [];
                    angular.forEach(that.accAccountList, function (account) {
                        if (account.accType === type) {
                            that.typeAccAccountList.push(account);
                        }
                    });
                }
                if (type === 'BANK') {
                    that.typeAccAccountList = [];
                    angular.forEach(that.accAccountList, function (account) {
                        if (account.accType === type) {
                            that.typeAccAccountList.push(account);
                        }
                    });
                }
                if (type === 'ONLINE') {
                    that.typeAccAccountList = [];
                    angular.forEach(that.accAccountList, function (account) {
                        if (account.accType === 'BANK') {
                            that.typeAccAccountList.push(account);
                        }
                    });
                }
                if (type === 'OVER_PAYMENT') {
                    that.typeAccAccountList = [];
                    supplierPaymentService.getOverPaymentIssueAccount()
                            .success(function (data) {
                                if (data) {
                                    that.typeAccAccountList.push(data);
                                    that.data.accAccount = data.indexNo;
                                }
                            });
                    this.checkOverValueAvailable();
                }
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
                data.dataList = this.billList;
                this.data.transactionDate = $filter('date')(this.data.transactionDate, 'yyyy-MM-dd');

                this.data.chequeDate = $filter('date')(this.data.chequeDate, 'yyyy-MM-dd');
                data.data = this.data;

                console.log(data);

                var defer = $q.defer();
                supplierPaymentService.saveSupplierPayment(JSON.stringify(data))
                        .success(function (data) {
                            that.billList = [];
                            that.data = supplierPaymentFactory.Data();
                            defer.resolve(data);
                        })
                        .error(function (data) {
                            defer.reject(data);
                        });
                return defer.promise;
            }
        };
        return supplierPaymentModel;
    };
    angular.module("appModule")
            .factory("supplierPaymentModel", factory);
}());


