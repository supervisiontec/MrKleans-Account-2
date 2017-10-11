(function () {
    var factory = function (vehicleEntranceService, vehicleEntranceFactory, $q, $filter) {
        function vehicleEntranceModel() {
            this.constructor();
        }

        vehicleEntranceModel.prototype = {
            //data model
            data: {},
            constructor: function () {
                this.data = vehicleEntranceFactory.newData();
            }
            
        };
        return vehicleEntranceModel;
    };
    angular.module("appModule")
            .factory("vehicleEntranceModel", factory);
}());