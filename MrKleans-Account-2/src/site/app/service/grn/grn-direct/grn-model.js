(function () {
    angular.module("appModule")
            .factory("directGrnModel", function (GrnService, grnModelFactory, $q, Notification) {
                function directGrnModel() {
                    this.constructor();
                }

                directGrnModel.prototype = {

                    //model factory data
                    data: {},
                    tempData: {},
                    summaryData: {},
                    userPermission: {},
                    //master data lists
                    items: [],
                    suppliers: [],

                    //opreation data
                    categoryIndex: null,
                    brandIndex: null,

                    constructor: function () {
                        var that = this;
                        that.data = grnModelFactory.newData();
                        that.tempData = grnModelFactory.tempData();
                        that.summaryData = grnModelFactory.summaryData();

                        GrnService.loadItems()
                                .success(function (data) {
                                    that.items = data;
                                });

                        GrnService.loadSuppliers()
                                .success(function (data) {
                                    that.suppliers = data;
                                });

                        GrnService.getPermission('Grn Direct')
                                .success(function (data) {
                                    that.userPermission = data;
                                });

                    },
                    itemLable: function (index) {
                        var label;
                        var that = this;
                        angular.forEach(this.items, function (value) {
                            if (value.indexNo === index) {
                                label = value.barcode + ' - ' + value.name;
                                that.getStockQty(value);
                                return;
                            }
                        });
                        return label;
                    }
                    ,
                    getStockQty: function (item) {
                        GrnService.getStockQty(item.indexNo)
                                .success(function (data) {
                                    this.tempData.stockQty = data;
                                    console.log(this.tempData.stockQty);
                                });
                    },
                    supplierLable: function (index) {
                        var label;
                        var that = this;
                        angular.forEach(this.suppliers, function (value) {
                            if (value.indexNo === index) {
                                label = value.indexNo + ' - ' + value.name;
                                that.getCreditPeriod(value);

                                return;
                            }
                        });
                        return label;
                    },
                    getCreditPeriod: function (supplier) {
                        this.data.creditPeriod = supplier.creditPeriod;
                    },
//
                    //validation
                    validateBarcode: function (barcode) {
                        var c = null;
                        angular.forEach(this.items, function (value) {
                            if (value.barcode === barcode) {
                                c = value;
                                return;
                            }
                        });
                        if (c) {
                            this.tempData.item = c.indexNo;
                            this.tempData.price = c.costPrice;
                        } else {
                            this.tempData.item = null;
                        }
                    },
                    addData: function () {
                        var that = this;

                        var saveConfirmation = true;

                        if (!that.tempData.item) {
                            saveConfirmation = false;
                            Notification.error("Select Item for Add Purchase Order Item !");
                        }
                        if (!that.tempData.price) {
                            saveConfirmation = false;
                            Notification.error("Select price for Add Purchase Order Item !");
                        }
                        if (!that.tempData.qty) {
                            saveConfirmation = false;
                            Notification.error("Select Qty for Add Purchase Order Item !");
                        }
                        if (!that.tempData.netValue) {
                            saveConfirmation = false;
                            Notification.error("Invalide Format");
                        }
                        if (saveConfirmation) {
                            this.data.grnItemList.push(this.tempData);
                            this.tempData = grnModelFactory.tempData();
                            this.summaryCalculator();
                        }

                    },
                    getItemName: function (indexNo) {
                        var itemName = null;
                        angular.forEach(this.items, function (value) {
                            if (value.indexNo === indexNo) {
                                itemName = value.name;
                                return;
                            }
                        });
                        return itemName;
                    },
                    summaryCalculator: function () {
                        var qty = 0;
                        var val = 0;
                        var discount = 0;
                        var itemValue = 0;
                        angular.forEach(this.data.grnItemList, function (value) {
                            qty = parseFloat(qty) + parseFloat(value.qty);
                            val = parseFloat(val) + parseFloat(value.value);
                            discount = parseFloat(discount) + parseFloat(value.discountValue);
                            itemValue = parseFloat(itemValue) + parseFloat(value.netValue);
                        });
                        this.summaryData.qty = qty;
                        this.summaryData.value = val;
                        this.summaryData.discountValue = discount;
                        this.data.amount = itemValue;
                        this.data.grandAmount = itemValue;
                    },
                    edit: function (indexNo) {
                        this.tempData = this.data.grnItemList[indexNo];
                        this.data.grnItemList.splice(indexNo, 1);
                        this.summaryCalculator();
                    },
                    delete: function (indexNo) {
                        this.data.grnItemList.splice(indexNo, 1);
                        this.summaryCalculator();
                    },
                    saveDirectGrn: function () {
                        console.log(this.data);
                        var defer = $q.defer();
                        var that = this;

                        var saveConfirmation = true;

                        if (!that.data.supplier) {
                            saveConfirmation = false;
                            Notification.error("Select Supplier for Save GRN !");
                        }
                        if (!that.data.date) {
                            saveConfirmation = false;
                            Notification.error("Select Date for Save GRN !");
                        }

                        if (that.data.grnItemList.length === 0) {
                            saveConfirmation = false;
                            Notification.error("Add GRN Item for Save GRN !");
                        }
                        if (that.tempData.netValue) {
                            saveConfirmation = false;
                            Notification.error("Remove Editing Item for save GRN !");
                        }
                        if (that.tempData.item) {
                            saveConfirmation = false;
                            Notification.error("Remove Editing Item for save GRN !");
                        }

                        if (saveConfirmation) {
                            console.log(this.data);
                            GrnService.saveDirectGrn(JSON.stringify(this.data))
                                    .success(function (data) {
                                        that.clear();
                                        defer.resolve();
                                    })
                                    .error(function (data) {
                                        defer.reject();
                                    });
                            return defer.promise;
                        }
                    }
                    , clear: function () {
                        this.data = grnModelFactory.newData();
                        this.tempData = grnModelFactory.tempData();
                        this.summaryData = grnModelFactory.summaryData();

                    },
                    searchGrnByNumber: function (number) {
                        var defer = $q.defer();
                        var that = this;
                        GrnService.findGrnByNumberAndBranch(number)
                                .success(function (data) {
                                    if (data) {
                                        that.clear();
                                        that.data = data;
                                        defer.resolve(data);
                                    } else {
                                        that.clear();
                                        defer.reject(data);
                                    }
                                });
                        return defer.promise;
                    }
                };
                return directGrnModel;
            });
}());