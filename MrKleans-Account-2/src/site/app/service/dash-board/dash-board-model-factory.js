(function () {
    angular.module("appModule")
            .factory("dashBoardFactory", function () {
                var factory = {};
                factory.newData = function () {
                    var data = {
                    };
                    return data;
                };
                return factory;
            });
}());