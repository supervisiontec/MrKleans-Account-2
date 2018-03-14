(function () {
    angular.module("appModule")
            .factory("createAccountFactory", function () {
                var factory = {};
                factory.Data = function () {
                    var data = {
                        "indexNo": null,
                        "name": "",
                        "level": null,
                        "accCode": null,
                        "cop": false,
                        "user": null,
                        "accType": 'COMMON',
                        "isAccAccount": true,
                        "description": null,
                        "subAccountOf": null,
                        "accMain": null
                    };
                    return data;
                };
                
                return factory;
            });
}());