(function () {
    var factory = function (itemFactory, itemService, $q, $filter, Notification) {
        function itemModel() {
            this.constructor();
        }

        //prototype functions
        itemModel.prototype = {
            //data model
            itemData: {},
            tempItemData: {},
            itemUnitData: {},
            packageData: {},
            consumableData: {},
            itemCheckData: {},
            priceCategoryDetail: {},
            //uib-typeahead
            items: [],
            itemsTypeWiseItems: [],
            itemunits: [],
            itemDepartments: [],
            categorys: [],
            itemCategorys: [],
            subCategorys: [],
            brands: [],
            suppliers: [],
            consumableItemList: [],
            //view table lists
            itemViewList: [],
            itemunitsViewList: [],
            packageViewList: [],
            itemCheckDetailList: [],
            priceCategoryDetailsList: [],
            timeList: [],

            selectedConsumableItem: {},
            selectedItem: {},
            selectedItemFromPriceCategory: {},
            consumableItemIsView: false,
            selectedItemIsView: false,
            //constructor
            constructor: function () {
                var that = this;

                that.itemData = itemFactory.newItemData();
                that.tempItemData = itemFactory.newItemData();
                that.itemUnitData = itemFactory.newItemUnitData();
                that.packageUnitData = itemFactory.newPackageData();
                that.consumableData = itemFactory.newConsumableData();
                that.itemCheckData = itemFactory.newItemCheckData();
                that.priceCategoryDetail = itemFactory.newPriceCategoryDetails();

                
                itemService.loadSupplier()
                        .success(function (data) {
                            that.suppliers = data;
                        });

                this.timeList = [
                    "00:00:30", "00:01:00", "00:10:00", "00:15:00", "00:20:00", "00:25:00", "00:30:00",
                    "00:35:00", "00:40:00", "00:45:00", "00:50:00", "00:55:00", "01:00:00", "01:10:00",
                    "01:20:00", "01:30:00", "01:40:00", "01:50:00", "02:00:00"
                ];

                this.loadConsumableItem();
                this.loadCategories();
                this.loadSubCategory();
                this.loadItem();
                this.loadBrand();
                this.loadItemDepartment();
                this.loadItemCategory();
                this.loadItemCheckDetailList();
            },
            clear: function () {
                this.constructor();
            },
            findItemByItemType: function (itemType) {
                var that = this;
                var defer = $q.defer();
                itemService.findItemByItemType(itemType)
                        .success(function (data) {
                            that.itemsTypeWiseItems = [];
                            that.itemsTypeWiseItems = data;
                            defer.resolve();
                        })
                        .error(function () {
                            that.itemsTypeWiseItems = [];
                            defer.reject();
                        });
                return defer.promise;
            },
            loadCategories: function () {
                var that = this;
                itemService.loadCategory()
                        .success(function (data) {
                            that.categorys = data;
                        });
            },
            loadItemCategory: function () {
                var that = this;
                itemService.loadItemCategory()
                        .success(function (data) {
                            that.itemCategorys = data;
                        });
            },
            loadSubCategory: function () {
                var that = this;
                itemService.loadSubCategory()
                        .success(function (data) {
                            that.subCategorys = data;
                        });
            },
            loadItemDepartment: function () {
                var that = this;
                itemService.loadItemDepartment()
                        .success(function (data) {
                            that.itemDepartments = data;
                        });
            },
            loadBrand: function () {
                var that = this;
                itemService.loadBrand()
                        .success(function (data) {
                            that.brands = data;
                        });

            },
            loadItem: function () {
                var that = this;
                itemService.loadItem()
                        .success(function (data) {
                            that.items = data;
                            that.itemViewList = data;
                        });
            },
            loadConsumableItem: function () {
                var that = this;
                itemService.loadConsumableItem()
                        .success(function (data) {
                            that.consumableItemList = data;
                        });
            },
            loadItemCheckDetailList: function () {
                var that = this;
                itemService.loadItemCheckDetails()
                        .success(function (data) {
                            that.itemCheckDetailList = data;
                        });
            },
            saveItem: function () {
                var defer = $q.defer();
                var that = this;

                var data = {
                    item: null,
                    department: null,
                    brand: null,
                    category: null,
                    itemCategory: null,
                    subCategory: null
                };
                data.item = this.itemData;
                data.department = this.itemDepartment(this.itemData.department);
                data.category = this.category(this.itemData.category);
                data.subCategory = this.subCategory(this.itemData.sub_category);
                data.brand = this.brand(this.itemData.brand);
                data.itemCategory = this.itemCategory(this.itemData.itemCategory);

                this.itemData.department = this.itemDepartment(this.itemData.department) === null ? this.itemData.department : null;
                this.itemData.category = this.category(this.itemData.category) === null ? this.itemData.category : null;
                this.itemData.sub_category = this.subCategory(this.itemData.sub_category) === null ? this.itemData.sub_category : null;
                this.itemData.brand = this.brand(this.itemData.brand) === null ? this.itemData.brand : null;
                this.itemData.itemCategory = this.itemCategory(this.itemData.itemCategory) === null ? this.itemData.itemCategory : null;
                console.log(data);

                itemService.saveItem(JSON.stringify(data))
                        .success(function (data) {
                            that.itemViewList.unshift(data);
                            that.itemData = {};
                            that.refreshCategories();
                            defer.resolve();
                        })
                        .error(function (data) {
                            defer.reject(data);
                        });

                return defer.promise;
            },
            refreshCategories: function () {
                this.loadBrand();
                this.loadCategories();
                this.loadSubCategory();
                this.loadItemDepartment();
                this.loadItemCategory();
            },

            editeItem: function (items, $index) {
                this.itemData = items;
                this.itemsTypeWiseItems.splice($index, 1);
            },
            deleteItem: function (items, $index) {
                var defer = $q.defer();
                var that = this;
                itemService.deleteItem(items.indexNo)
                        .success(function (data) {
                            that.itemsTypeWiseItems.splice($index, 1);
                            defer.resolve();
                        })
                        .error(function (data) {
                            defer.reject();
                        });
                return defer.promise;
            },
            loadItemUnitByItem: function (item) {
                var that = this;
                var defer = $q;
                console.log('service ' + item);
                itemService.loadItemUnitByItem(item)
                        .success(function (data) {
                            that.itemunitsViewList = [];
                            that.itemunitsViewList = data;
                            console.log(data);
                            defer.resolve();
                        })
                        .error(function () {
                            that.itemunitsViewList = [];
                            defer.reject();
                        });
                return defer.promise;
            },
            saveItemUnit: function () {
                var defer = $q.defer();
                var that = this;
                this.itemUnitData.itemUnitType = "NORMAL";
                itemService.saveItemUnit(JSON.stringify(this.itemUnitData))
                        .success(function (data) {
                            that.itemunitsViewList.unshift(data);
                            that.itemUnitData = {};

                            defer.resolve();
                        })
                        .error(function (data) {
                            defer.reject();
                        });
                return defer.promise;
            },
            editeItemUnits: function (itemsUnits, $index) {
                this.itemUnitData = itemsUnits;
                this.itemunitsViewList.splice($index, 1);
            },
            deleteItemUnits: function (itemsUnits, $index) {
                var defer = $q.defer();
                var that = this;
                itemService.deleteItemUnit(itemsUnits.indexNo)
                        .success(function (data) {
                            that.itemunitsViewList.splice($index, 1);
                            defer.resolve();
                        })
                        .error(function (data) {
                            defer.reject();
                        });
                return defer.promise;
            },
            //package items
            addPackageItem: function () {
                var defer = $q.defer();
                var that = this;
                itemService.savePackageItem(this.packageData)
                        .success(function (data) {
                            that.packageViewList.unshift(data);
                            that.packageData.item = null;
                            that.packageData.time = null;
                            that.totalTimeCalac();
                            defer.resolve();
                        })
                        .error(function (data) {
                            defer.reject();
                        });
                return defer.promise;
            },
            getPackageItems: function (indexNo) {
                var defer = $q.defer();
                var that = this;
                itemService.getPackageItems(indexNo)
                        .success(function (data) {
                            that.packageViewList = [];
                            that.packageViewList = data;
                            that.totalTimeCalac();

                            defer.resolve();
                        })
                        .error(function () {
                            that.packageViewList = [];
                            defer.reject();
                        });
                return defer.promise;
            },
            totalTimeCalac: function () {
                var that = this;
                that.packageData.count = that.packageViewList.length;
            },
//            , floorTime: function (milisecondsDiff) {
//                return [this.pad(Math.floor(milisecondsDiff / 3600) % 60),
//                    this.pad(Math.floor(milisecondsDiff / 60) % 60),
//                    this.pad(milisecondsDiff % 60)
//                ].join(":");
//            },
//            pad: function (num) {
//                if (num < 10) {
//                    return "0" + num;
//                } else {
//                    return "" + num;
//                }
//            },
            deletePackageItems: function (package, $index) {
                var defer = $q.defer();
                var that = this;
                itemService.deletePackageItem(package.indexNo)
                        .success(function (data) {
                            that.packageViewList.splice($index, 1);
                            that.totalTimeCalac();
                            defer.resolve();
                        })
                        .error(function (data) {
                            defer.reject();
                        });
                return defer.promise;
            },
            saveConsumable: function () {
                var confirmation = true;
                if (!this.consumableData.service) {
                    confirmation = false;
                    Notification.error('Select a Service Item For Save !');
                }
                if (!this.consumableData.consumable) {
                    confirmation = false;
                    Notification.error('Select a Consumable Item For Save !');
                }
                if (this.consumableData.qty <= 0.0) {
                    confirmation = false;
                    Notification.error('Quantity less then 0 !');
                }
                if (confirmation) {

                    var defer = $q.defer();
                    var that = this;
                    itemService.saveConsumableItem(JSON.stringify(this.consumableData))
                            .success(function (data) {
                                that.consumableData.consumable = null;
                                that.consumableData.qty = 0.00;
                                that.loadConsumableItem();
                                this.consumableItemIsView = false;
                                defer.resolve();
                            })
                            .error(function (data) {
                                defer.reject();
                            });
                    return defer.promise;
                }
            },
            saveItemChechDetail: function () {
                var confirmation = true;
                if (!this.itemCheckData.item) {
                    confirmation = false;
                    Notification.error('Select a Item For Save !');
                }
                if (!this.itemCheckData.name) {
                    confirmation = false;
                    Notification.error('Select a Check Description !');
                }
                if (confirmation) {

                    var defer = $q.defer();
                    var that = this;
                    itemService.saveItemCheckDetail(JSON.stringify(this.itemCheckData))
                            .success(function (data) {
                                that.itemCheckData.name = null;
                                that.loadItemCheckDetailList();
                                defer.resolve();
                            })
                            .error(function (data) {
                                defer.reject();
                            });
                    return defer.promise;
                }
            },
            selectCunsumableItel: function (index) {
                var item = this.itemObject(index);
                this.selectedConsumableItem = item;
                this.consumableItemIsView = true;
            },
            deleteConsumableItem: function (index) {
                var defer = $q.defer();
                var that = this;
                itemService.deleteConsumableItem(index)
                        .success(function (data) {
                            that.consumableData.consumable = null;
                            that.consumableData.qty = 0.00;
                            that.loadConsumableItem();
                            defer.resolve();
                        })
                        .error(function (data) {
                            defer.reject();
                        });
                return defer.promise;
            },
            deleteItemCheckDetail: function (index) {
                var defer = $q.defer();
                var that = this;
                itemService.deleteItemCheckDetail(index)
                        .success(function (data) {
                            that.itemCheckData.name = null;
                            that.itemCheckData.item = null;
                            that.loadItemCheckDetailList();
                            defer.resolve();
                        })
                        .error(function (data) {
                            defer.reject();
                        });
                return defer.promise;
            },

            brandLable: function (indexNo) {
                var item;
                angular.forEach(this.brands, function (value) {
                    if (value.indexNo === parseInt(indexNo)) {
                        item = value.indexNo + "-" + value.name;
                        return;
                    }
                });
                return item;
            },
            supplierLable: function (indexNo) {
                var supplier;
                angular.forEach(this.suppliers, function (value) {
                    if (value.indexNo === parseInt(indexNo)) {
                        supplier = value.name + " - " + value.contactNo;
                        return;
                    }
                });
                return supplier;
            },
            itemLable: function (indexNo) {
                var item;
                angular.forEach(this.items, function (value) {
                    if (value.indexNo === parseInt(indexNo)) {
                        item = value.indexNo + " - " + value.name;
                        return;
                    }
                });
                return item;
            },
            itemObject: function (indexNo) {
                var item;
                angular.forEach(this.items, function (value) {
                    if (value.indexNo === parseInt(indexNo)) {
                        item = value;
                        return;
                    }
                });
                return item;
            },
            categoryLable: function (indexNo) {
                var item;
                angular.forEach(this.categorys, function (value) {
                    if (value.indexNo === parseInt(indexNo)) {
                        item = value.indexNo + "-" + value.name;
                        return;
                    }
                });
                return item;
            },
            itemCategoryLable: function (indexNo) {
                var item;
                angular.forEach(this.itemCategorys, function (value) {
                    if (value.indexNo === parseInt(indexNo)) {
                        item = value.indexNo + "-" + value.name;
                        return;
                    }
                });
                return item;
            },
            subCategoryLable: function (indexNo) {
                var item;
                angular.forEach(this.subCategorys, function (value) {
                    if (value.indexNo === parseInt(indexNo)) {
                        item = value.indexNo + "-" + value.name;
                        return;
                    }
                });
                return item;
            },
            itemDepartmentLable: function (indexNo) {
                var item = null;
                angular.forEach(this.itemDepartments, function (value) {
                    if (value.indexNo === indexNo) {
                        item = value.indexNo + "-" + value.name;
                        return;
                    }
                });
                return item;
            },
            timeLable: function (timePara) {
                var lable = null;
                angular.forEach(this.timeList, function (time) {
                    if (time === timePara) {
                        lable = time;
                        return;
                    }
                });
                return lable;
            },

            item: function (indexNo) {
                var item;
                angular.forEach(this.items, function (value) {
                    if (value.indexNo === parseInt(indexNo)) {
                        item = value;
                        return;
                    }
                });
                return item;
            }
            ,
            itemDepartment: function (name) {
                var item = name;
                angular.forEach(this.itemDepartments, function (value) {
                    if (value.indexNo === name) {
                        item = null;
                        return;
                    }
                });
                return item;
            },
            category: function (name) {
                var item = name;
                angular.forEach(this.categorys, function (value) {
                    if (value.indexNo === name) {
                        item = null;
                        return;
                    }
                });
                return item;
            },
            subCategory: function (name) {
                var item = name;
                angular.forEach(this.subCategorys, function (value) {
                    if (value.indexNo === name) {
                        item = null;
                        return;
                    }
                });
                return item;
            },
            brand: function (name) {
                var item = name;
                angular.forEach(this.brands, function (value) {
                    if (value.indexNo === name) {
                        item = null;
                        return;
                    }
                });
                return item;
            },
            itemCategory: function (name) {
                var item = name;
                angular.forEach(this.itemCategorys, function (value) {
                    if (value.indexNo === name) {
                        item = null;
                        return;
                    }
                });
                return item;
            },
            selectedItemForCheckItem: function (indexNo) {
                var item;
                angular.forEach(this.items, function (value) {
                    if (value.indexNo === parseInt(indexNo)) {
                        item = value;
                        return;
                    }
                });
                this.selectedItem = item;
                this.selectedItemIsView = true;
            }
            ,
            // ------------ price category details ------------
            loadPriceCategoryDetailByItem: function (item) {
                var that = this;
                var defer = $q.defer();

                //select item data
                that.tempItemData = that.item(item);
                if (that.tempItemData.type === 'PACKAGE' || that.tempItemData.type === 'SERVICE') {
                    angular.forEach(that.items, function (data) {
                        if (data.indexNo === item) {
                            that.selectedItemFromPriceCategory = data;
                            return;
                        }
                    });

                    itemService.loadPriceCategoryDetailByItem(item)
                            .success(function (data) {
                                that.priceCategoryDetailsList = [];
                                that.priceCategoryDetailsList = data;
                                defer.resolve();
                            })
                            .error(function () {
                                that.priceCategoryDetailsList = [];
                                defer.reject();
                            });
                    return defer.promise;
                } else {
                    that.selectedItemFromPriceCategory = null;
                    that.priceCategoryDetail.item = "";
                    Notification.error('select Package Item or Activity to save price category !');
                }
            },
            getTotalTimeByPriceCategory: function (packageId, priceCategoryId) {
                var that = this;
                var defer = $q.defer();

                var time = "00:00:00";

                itemService.getTotalTimeByPriceCategory(packageId, priceCategoryId)
                        .success(function (data) {
                            time = data;
                            console.log(time);
                            defer.resolve();
                        })
                        .error(function () {
                            defer.reject();
                        });
//                return time;
            },
            savePriceCategoryDetail: function () {
                var that = this;
                var defer = $q.defer();
                itemService.saveMPriceCategoryDetails(this.priceCategoryDetail)
                        .success(function (data) {
                            that.priceCategoryDetailsList.unshift(data);
                            that.priceCategoryDetail = {};
                            that.tempItemData = {};
                            defer.resolve();
                        })
                        .error(function () {
                            defer.reject();
                        });
                return defer.promise;
            },
            duplicateCheckPriceCategoryDetails: function (item, priceCategory) {
                var data;
                angular.forEach(this.priceCategoryDetailsList, function (values) {
                    if (values.item === parseInt(item) && values.priceCategory === parseInt(priceCategory)) {
                        data = values;
                        return;
                    }
                });
                return data;
            },
            deletePriceCategoryDetail: function ($index, indexNo) {
                var that = this;
                var defer = $q.defer();
                itemService.deleteMPriceCategoryDetails(indexNo)
                        .success(function (data) {
                            that.priceCategoryDetailsList.splice($index, 1);
                            defer.resolve();
                        })
                        .error(function () {
                            defer.reject();
                        });
                return defer.promise;
            },
            editePriceCategoryDetail: function (priceCategoryDetail, $index) {
                this.priceCategoryDetail = priceCategoryDetail;
                this.priceCategoryDetailsList.splice($index, 1);
            },
            selectPackageItem: function (index) {
                this.packageData.time = this.item(index).time;
            }
        };
        return itemModel;
    };
    angular.module("appModule")
            .factory("itemModel", factory);
}());


