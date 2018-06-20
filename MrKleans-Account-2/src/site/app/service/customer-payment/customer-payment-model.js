(function () {
    angular.module("appModule")
            .factory("customerPaymentVoucherModel", function (customerPaymentVoucherService, customerPaymentVoucherFactory, $filter, $q, Notification) {
                function customerPaymentVoucherModel() {
                    this.constructor();
                }
                customerPaymentVoucherModel.prototype = {
                    //master list
                    vehicleList: [],
                    clientList: [],
                    data: {},
                    userPermission: {},
                    paymentInformationList: [],
                    branchSearchList: [],
                    cardTypeList: [],
                    balanceInvoiceList: [],
                    typeAccAccountList: [],

                    constructor: function () {
                        var that = this;
                        this.data = customerPaymentVoucherFactory.Data();

                        customerPaymentVoucherService.loadClient()
                                .success(function (data) {
                                    that.clientList = data;
                                });

                        customerPaymentVoucherService.getPermission('Customer Payment')
                                .success(function (data) {
                                    that.userPermission = data;
                                });
                        this.loadAccAccount();
                    },
                    loadAccAccount: function () {
                        var that = this;
                        customerPaymentVoucherService.loadAccAccounts()
                                .success(function (data) {
                                    that.accAccountList = data;
                                });
                    },
                    clearTypeChange: function () {
                        var that = this;
                        that.data.refNumber = '';
                        that.data.chequeDate = null;
                        that.data.accAccount = null;
                        that.data.value = 0.00;
                    },
                    setBalanceAmountToPay: function (bill) {
                        if (!bill.pay) {
                            bill.pay = (bill.debit - bill.credit);
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
                            }
                            that.data.billTotal += parseFloat(bill.pay);
                        });
                        that.data.overPay = (that.data.credit - that.data.billTotal) > 0 ? (that.data.credit - that.data.billTotal) : 0.00;
                        this.checkOverValueAvailable();

                    },
                    clientLable: function (indexNo) {
                        var lable = "";
                        angular.forEach(this.clientList, function (value) {
                            if (value.indexNo === parseInt(indexNo)) {
                                lable = value.indexNo + ' - ' + value.name;
                                return;
                            }
                        });
                        return lable;
                    },
                    getPayableBills: function (clientId) {
                        var that = this;
                        var accAccount = null;
                        angular.forEach(that.clientList, function (client) {
                            if (clientId === parseInt(client.indexNo)) {
                                accAccount = client.accAccount;
                                return;
                            }
                        });
                        if (accAccount !== null) {
                            this.getOverPaymentAmount(clientId);
                            customerPaymentVoucherService.getPayableBills(accAccount)
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
                    getOverPaymentAmount: function (customer) {
                        var that = this;
                        customerPaymentVoucherService.getOverPaymentAmount(customer)
                                .success(function (data) {
                                    that.data.overAmount = data;
                                });
                    },
                    checkType: function (type) {
                        var that = this;
                        that.data.accType = type;
                        this.clearTypeChange();
                        that.typeAccAccountList = [];

                        if (type === 'CASH') {
                            angular.forEach(that.accAccountList, function (account) {
                                if (account.accType === type) {
                                    that.typeAccAccountList.push(account);
                                }
                            });
                        }
                        if (type === 'BANK') {
                            angular.forEach(that.accAccountList, function (account) {
                                if (account.accType === type) {
                                    that.typeAccAccountList.push(account);
                                }
                            });
                        }
                        if (type === 'ONLINE') {
                            angular.forEach(that.accAccountList, function (account) {
                                if (account.accType === 'BANK') {
                                    that.typeAccAccountList.push(account);
                                }
                            });
                        }
                        if (type === 'OVER_PAYMENT') {
                            customerPaymentVoucherService.getOverPaymentReceviedAccount()
                                    .success(function (data) {
                                        if (data) {
                                            that.typeAccAccountList.push(data);
                                            that.data.accAccount = data.indexNo;
                                        }
                                    });
                            this.checkOverValueAvailable();
                        }
                    },
                    checkTypeSub: function (type) {
                        var that = this;
                        that.data.accTypeSub = type;
                        this.clearTypeChange();
                        that.typeAccAccountList = [];

                        if (type === 'ACCOUNT') {
                            that.typeAccAccountList = that.accAccountList;
                        }
                    },
                    checkOverValueAvailable: function () {
                        if (this.data.accType === 'OVER_PAYMENT') {

                            var balance = this.data.overAmount - this.data.credit;
                            if (balance < 0) {
                                Notification.error('overpayment value not enough !');

                            }
                        }
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
                    getValue: function (accAccount) {
                        var that = this;
                        customerPaymentVoucherService.loadAccBalance(accAccount)
                                .success(function (data) {
                                    that.data.value = data;
                                });

                    },save: function () {

                var that = this;
                var data = {};
                data.dataList = this.billList;
                this.data.transactionDate = $filter('date')(this.data.transactionDate, 'yyyy-MM-dd');

                this.data.chequeDate = $filter('date')(this.data.chequeDate, 'yyyy-MM-dd');
                data.data = this.data;

                var defer = $q.defer();
                customerPaymentVoucherService.save(JSON.stringify(data))
                        .success(function (data) {
                            that.billList = [];
                            that.data = customerPaymentVoucherFactory.Data();
                            defer.resolve(data);
                        })
                        .error(function (data) {
                            defer.reject(data);
                        });
                return defer.promise;
            },

                };
                return customerPaymentVoucherModel;
            });
}());