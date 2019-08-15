(function () {
    angular.module("appModule")
            .factory("depositFactory", function () {
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
                        "formName": 'DEPOSIT',
                        "refNumber": null,
                        "type": 'DEPOSIT',
                        "typeIndexNo": null,
                        "description": '',
                        "chequeDate": null,
                        "bankReconciliation": false,
                        "reconcileAccount":null,
                        "reconcileGroup":0,
                        "isCheque":0

                    };
                    return data;
                };
                factory.tempData = function () {
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
                        "formName": 'DEPOSIT',
                        "refNumber": null,
                        "type": 'DEPOSIT',
                        "typeIndexNo": null,
                        "description": '',
                        "chequeDate": null,
                        "bankReconciliation": false,
                        "reconcileAccount":null,
                        "reconcileGroup":0
                    };
                    return data;
                };
                return factory;
            });
}());
