(function () {
    angular.module("appModule")
            .factory("voucherFactory", function () {
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
                        "formName": 'payment_voucher',
                        "refNumber": null,
                        "type": 'voucher',
                        "typeIndexNo": null,
                        "description": '',
                        "chequeDate": null,
                        "bankReconciliation": false,
                        "reconcileAccount": null,
                        "isMain": false,
                        "isCheque": false,
                        "financialYear": null,
                        "costDepartment": null,
                        "costCenter": null,
                        "payTo": null,

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
                        "formName": 'payment_voucher',
                        "refNumber": null,
                        "type": 'voucher',
                        "typeIndexNo": null,
                        "description": '',
                        "chequeDate": null,
                        "bankReconciliation": false,
                        "reconcileAccount": null,
                        "isMain": false,
                        "isCheque": false,
                        "financialYear": null,
                        "costDepartment": "",
                        "costCenter": ""
                    };
                    return data;
                };
                return factory;
            });
}());
