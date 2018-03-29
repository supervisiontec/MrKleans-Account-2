(function () {
//module
    angular.module("dashBoardModule", ['ui.bootstrap', 'ui-notification', 'chart.js']);
    angular.module("dashBoardModule")
            .controller("dashBoardController", function ($scope, SecurityService, dashBoardModel) {
                $scope.model = new dashBoardModel();

                $scope.init = function () {
                    SecurityService.getViewTrue()
                            .success(function (data) {
                                $scope.permissionList = data;
                            });
                };

                $scope.label = ["Jan", "February", "March", "April", "May", "June", "July"];
                $scope.series = ['Income', 'Expense'];
                $scope.datas = [
                    [65, 59, 80, 81, 56, 55, 40],
                    [28, 48, 40, 19, 86, 27, 90]
                ];
                $scope.onClick = function (points, evt) {
                    console.log(points, evt);
                };
                $scope.datasetOverride = [{yAxisID: 'y-axis-1'}, {yAxisID: 'y-axis-2'}];
                $scope.options = {
                    scales: {
                        yAxes: [
                            {
                                id: 'y-axis-1',
                                type: 'linear',
                                display: true,
                                position: 'left'
                            },
                            {
                                id: 'y-axis-2',
                                type: 'linear',
                                display: true,
                                position: 'right'
                            }
                        ]
                    }
                };

                $scope.label1 = ['2006', '2007', '2008', '2009', '2010', '2011', '2012'];
                $scope.series1 = ['Series A', 'Series B'];

                $scope.data1 = [
                    [65, 59, 80, 81, 56, 55, 40],
                    [28, 48, 40, 19, 86, 27, 90]
                ];
                $scope.init();
            });
}());