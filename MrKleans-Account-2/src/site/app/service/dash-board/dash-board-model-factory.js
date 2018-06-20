(function () {
    angular.module("appModule")
            .factory("dashBoardFactory", function () {
                var factory = {};
                factory.newData = function () {
                    var data = {
                    };
                    return data;
                };
                factory.filter = function () {
                    var data = {
                        "fromDate":null,
                        "toDate":null
                    };
                    return data;
                };
                return factory;
            });
}());