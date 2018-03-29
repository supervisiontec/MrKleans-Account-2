(function () {
    angular.module("appModule")
            .factory("supplierPaymentFactory", function () {
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
                        "formName": 'supplier_payment',
                        "refNumber": null,
                        "type": 'supplier_payment',
                        "typeIndexNo": null,
                        "description": null,
                        "chequeDate": null,
                        "bankReconciliation": false,
                        "reconcileAccount":null,
                        "reconcileGroup":0,
                        "accTypeSub":null

                    };
                    return data;
                };
                
                return factory;
            });
}());
