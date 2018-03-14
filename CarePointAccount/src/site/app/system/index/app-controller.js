(function () {
    angular.module("appModule")
            .controller("appController", function ($scope, $cookies, $rootScope, $window, $filter, $location, SecurityService) {
                $scope.hamburgerOpen = true;
                $scope.ui = {};
                $scope.permissionList = [];

                //route loading
                $rootScope.$watch("layout.loading", function () {
                    $scope.routeLoading = $rootScope.layout.loading;
                });

                $scope.userRoles = $rootScope.userRoles;

                $scope.homepageUrls = [];

                $scope.currentPageName = '';

                $scope.loginUser = "";
                $scope.currentBranch = "";
                $scope.loginUser = $cookies.get("nick-name");
                $scope.currentBranch = $cookies.get("branch-name");

                $scope.date = $filter('date')(new Date(), 'yyyy-MM-dd');

                //init homepage urls
                angular.forEach($scope.userRoles, function (value) {
                    if (value.homepageUrl) {
                        $scope.homepageUrls.push({
                            "name": value.name,
                            "url": value.homepageUrl
                        });
                    }
                });
                $scope.init = function () {
                    SecurityService.getViewTrue()
                            .success(function (data) {
                                $rootScope.permissionList = data;
                            });
                };


                $scope.toggleHamburger = function (value) {
                    $scope.hamburgerOpen = !$scope.hamburgerOpen;
                    $scope.currentPageName = value;

                    if ($scope.hamburgerOpen) {
                        $timeout(function () {
                            angular.element(document.querySelector(".side-bar-left")).css("display", "none");
                        }, 600);
                    } else {
                        angular.element(document.querySelector(".side-bar-left")).css("display", "flex");
                    }
                };

                $scope.isHomepage = function () {
                    $scope.loginUser = $cookies.get("nick-name");
                    $scope.currentBranch = $cookies.get("branch-name");
                    return $location.path() === "/";
                };

                $scope.logout = function () {
                    SecurityService.logout()
                            .success(function () {
                                $location.path("/login");
                            });
                };

                angular.element($window).bind("keyup", function ($event) {
                    if ($event.keyCode === 18)
                        $rootScope.ctrlDown = false;
                    $scope.$apply();
                });

                angular.element($window).bind("keydown", function ($event) {
                    if ($event.keyCode === 18)
                        $rootScope.ctrlDown = true;
                    $scope.$apply();
                });
                $scope.ui.include = function (name) {
                    return $rootScope.permissionList.includes(name);
                };
                $scope.ui.includeList = function (paramList) {
                    var check = false;
                    angular.forEach(paramList, function (name) {
                        var isView = $rootScope.permissionList.includes(name);
                        if (isView) {
                            check = true;
                        }
                    });
                    return check;
                };
                $scope.init();
            });
}());