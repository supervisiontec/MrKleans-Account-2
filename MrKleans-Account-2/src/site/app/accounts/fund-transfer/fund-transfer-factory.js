(function () {
    angular.module("appModule")
            .factory("fundTransferFactory", function () {
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
                        "formName": 'fund_transfer',
                        "refNumber": null,
                        "type": 'voucher',
                        "typeIndexNo": null,
                        "description": '',
                        "chequeDate": null,
                        "bankReconciliation": false,
                        "reconcileAccount":null,
                        "reconcileGroup":0

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
                        "formName": 'fund_transfer',
                        "refNumber": null,
                        "type": 'voucher',
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
