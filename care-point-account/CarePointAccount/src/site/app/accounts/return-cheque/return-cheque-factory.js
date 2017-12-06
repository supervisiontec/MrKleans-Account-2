(function () {
    angular.module("appModule")
            .factory("returnChequeFactory", function () {
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
                        "formName": 'cheque_return',
                        "refNumber": null,
                        "type": 'cheque_return',
                        "typeIndexNo": null,
                        "description": null,
                        "chequeDate": null,
                        "bankReconciliation": false,
                        "reconcileAccount":null,
                        "reconcileGroup":0

                    };
                    return data;
                };
                factory.tempData = function () {
                    var data = {
                        "supplier": null,
                        "transactionNo": null,
                        "transactionDate": null,
                        "formName": null,
                        "user": null
                    };
                    return data;
                };
                
                return factory;
            });
}());
