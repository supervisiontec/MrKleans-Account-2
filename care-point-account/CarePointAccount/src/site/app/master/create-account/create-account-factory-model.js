(function () {
    angular.module("appModule")
            .factory("createAccountFactory", function () {
                var factory = {};
                factory.Data = function () {
                    var data = {
                        "indexNo": null,
                        "name": "",
                        "cop": false,
                        "user": 1,
                        "accCategory1": {
                            "indexNo": null,
                            "name": "",
                            "orderNo": 0
                        },
                        "accCategory2": {
                            "indexNo": null,
                            "name": "",
                            "orderNo": 0
                        },
                        "accCategory3": {
                            "indexNo": null,
                            "name": "",
                            "orderNo": 0
                        },
                        "accMain": {
                            "indexNo": null,
                            "name": "",
                            "increment": "",
                            "isExpence": false,
                            "isIncome": false,
                            "isBalanceSheet": false
                        }

                    };
                    return data;
                };
                factory.search = function () {
                    var data = {
                        "accountName": null,
                        "category1": {
                            "indexNo": null,
                            "name": "",
                            "orderNo": 0
                        },
                        "category2": {
                            "indexNo": null,
                            "name": "",
                            "orderNo": 0
                        },
                        "category3": {
                            "indexNo": null,
                            "name": "",
                            "orderNo": 0
                        },
                        "main": {
                            "indexNo": null,
                            "name": "",
                            "increment": "",
                            "isExpence": false,
                            "isIncome": false,
                            "isBalanceSheet": false
                        }

                    };
                    return data;
                };
                return factory;
            });
}());