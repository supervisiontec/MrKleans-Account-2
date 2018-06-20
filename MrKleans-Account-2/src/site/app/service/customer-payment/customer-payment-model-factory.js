(function () {
    angular.module("appModule")
            .factory("customerPaymentVoucherFactory", function () {
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
                        "formName": 'CUSTOMER_PAYMENT',
                        "refNumber": null,
                        "type": 'CUSTOMER_PAYMENT',
                        "typeIndexNo": null,
                        "description": null,
                        "chequeDate": null,
                        "bankReconciliation": false,
                        "reconcileAccount":null,
                        "reconcileGroup":0,
                        "accTypeSub":null,
                        "financialYear": null,
                        "costDepartment": "",
                        "costCenter": "",
                        "isEdit":0

                    };
                    return data;
                };

                

                return factory;
            });
}());

