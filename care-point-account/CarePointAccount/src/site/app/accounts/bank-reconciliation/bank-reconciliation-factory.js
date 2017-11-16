(function () {
    angular.module("appModule")
            .factory("bankReconciliationFactory", function () {
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
                        "formName":'PAYMENT VOUCHER',
                        "refNumber": null,
                        "type": 'VOUCHER',
                        "typeIndexNo": null,
                        "description": '',
                        "chequeDate": null,
                        "monthStart": null,
                        "balance":0.00,
                        "deleteRefNo":0,
                        "reconciliationGroup":0

                    };
                    return data;
                };
                factory.tempDate = function () {
                    var tempDate = {
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
                        "formName":'PAYMENT VOUCHER',
                        "refNumber": null,
                        "type": 'VOUCHER',
                        "typeIndexNo": null,
                        "description": '',
                        "chequeDate": null,
                        "deleteRefNo":0,
                        "reconciliationGroup":0
                        

                    };
                    return tempDate;
                };
                
                return factory;
            });
}());
