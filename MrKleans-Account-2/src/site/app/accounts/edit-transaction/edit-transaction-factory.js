(function () {
    angular.module("appModule")
            .factory("editTransactionFactory", function () {
                var factory = {};
                factory.Data = function () {
                    var data = {
                        
                    };
                    return data;
                };
                factory.tempData = function () {
                    var data = {
                        
                    };
                    return data;
                };
                return factory;
            });
}());