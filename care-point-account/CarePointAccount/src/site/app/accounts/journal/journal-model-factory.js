(function () {
    angular.module("appModule")
            .factory("journalFactory", function () {
                var factory = {};
                factory.Data = function () {
                    var data = {
                        "indexNo": null,
                        "transactionDate": null,
                        "currentDate": null,
                        "time": null,
                        "branch": null,
                        "currentBranch": null,
                        "user": null,
                        "debit": 0.00,
                        "credit": 0.00,
                        "accAccount": null,
                        "formName": null,
                        "refNumber": null,
                        "type": null,
                        "typeIndexNo": null,
                        "description": null,
                        "bankReconciliation": false,
                        "reconciliationGroup":0
                    };
                    return data;
                };
                factory.tempData = function () {
                    var data = {
                        "accMain": null,
                        "accountName": null,
                        "branch": null,
                        "debit": 0.00,
                        "credit": 0.00,
                        "cop": false,
                        "description": null,
                        "date": null
                    };
                    return data;
                };
                return factory;
            });
}());