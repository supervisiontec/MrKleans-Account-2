(function () {
//module
    angular.module("dashBoardModule", ['ui.bootstrap', 'ui-notification', 'chart.js']);
    angular.module("dashBoardModule")
            .controller("dashBoardController", function ($scope, $uibModal, $filter, SecurityService, dashBoardModel) {
                $scope.model = new dashBoardModel();

                var date = new Date();
                $scope.model.filter.fromDate = new Date(date.getFullYear(), date.getMonth(), 1);
                $scope.model.filter.toDate = new Date();



                $scope.label = ["Jan", "February", "March", "April", "May", "June", "July"];
                $scope.series = ['Income', 'Expense'];
                $scope.datas = [
                    [65, 65, 65, 65, 65, 65, 65],
                    [28, 48, 40, 19, 86, 27, 90]
                ];

                $scope.onClick = function (points, event) {
                   

                    $uibModal.open({
                        animation: true,
                        ariaLabelledBy: 'modal-title',
                        ariaDescribedBy: 'modal-body',
                        templateUrl: 'detailPopup.html',
                        scope: $scope,
                        size: 'lg'
                    });

                    $scope.rows = [
                        {
                            "branch": "default",
                            "comment": "",
                            "name": "20141228.150706",
                            "score": "0.45000",
                            "time": "0.02",
                            "value": "0.02",
                            "object": "0.02",
                            "desc": "0.02",
                            "discount": "0.02",
                            "pay": "0.02",
                            "amonut": "0.02",
                            "grid": "0.02",
                            "other": 6565558783.5
                        }];
                    $scope.cols = Object.keys($scope.rows[0]);

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

                $scope.labels1 = ['2006', '2007', '2008', '2009', '2010', '2011', '2012'];
                $scope.series = ['Series A', 'Series B'];
                $scope.series2 = ['Expense', 'Income'];
                $scope.data1 = [
                    [65, 59, 80, 81, 56, 55, 40],
                    [28, 48, 40, 19, 86, 27, 90]
                ];



                $scope.labels4 = ["January", "February", "March", "April", "May", "June", "July"];
                $scope.series4 = ['Series A', 'Series B'];
                $scope.data4 = [
                    [65, 59, 80, 81, 56, 55, 40],
                    [28, 48, 40, 19, 86, 27, 90]
                ];
                $scope.colors4 = ['green', 'grey'];
                $scope.onClick4 = function (points, evt) {
                    console.log(points, evt);
                };
                $scope.datasetOverride4 = [{yAxisID: 'y-axis-1'}, {yAxisID: 'y-axis-2'}];
                $scope.options4 = {
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
                $scope.ui.filterDashboard = function () {
                    // 01 dashboard
                    $scope.ui.getDashboardMain();
                    //02         
                    $scope.ui.getDashboard2();
                    //03         
                    $scope.ui.getDashboard3();
                    //04         
                    $scope.ui.getDashboard4();
                    //05         
                    $scope.ui.getDashboard5();

                };
                $scope.ui.getDashboardMain = function () {
                    var toDate = $filter('date')($scope.model.filter.toDate, 'yyyy-MM-dd');
                    $scope.model.getDashboardMain(toDate);
                };
                $scope.ui.getDashboard2 = function () {
                    var toDate = $filter('date')($scope.model.filter.toDate, 'yyyy-MM-dd');
                    var fromDate = $filter('date')($scope.model.filter.fromDate, 'yyyy-MM-dd');
                    $scope.model.getDashboard2(fromDate, toDate);
                };
                $scope.ui.getDashboard3 = function () {
                    var toDate = $filter('date')($scope.model.filter.toDate, 'yyyy-MM-dd');
                    var fromDate = $filter('date')($scope.model.filter.fromDate, 'yyyy-MM-dd');
                    $scope.model.getDashboard3(fromDate, toDate);
                };
                $scope.ui.getDashboard4 = function () {
                    var toDate = $filter('date')($scope.model.filter.toDate, 'yyyy-MM-dd');
                    $scope.model.getDashboard4(toDate);
                };
                $scope.ui.getDashboard5 = function () {
                    var toDate = $filter('date')($scope.model.filter.toDate, 'yyyy-MM-dd');
                    $scope.model.getDashboard5(toDate);
                };

                $scope.init = function () {
                    SecurityService.getViewTrue()
                            .success(function (data) {
                                $scope.permissionList = data;
                            });
                    //01         
                    $scope.ui.getDashboardMain();
                    //02         
                    $scope.ui.getDashboard2();
                    //03         
                    $scope.ui.getDashboard3();
                    //04         
                    $scope.ui.getDashboard4();
                    //05         
                    $scope.ui.getDashboard5();
                };
                $scope.init();

            });
}());