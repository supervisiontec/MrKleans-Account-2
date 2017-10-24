(function () {
    //index module
    angular.module("appModule", [
        "ngRoute",
        "ngCookies",
        "ui.bootstrap",
        //master
        "clientModule",
        "vehicleModule",
        "app",
        "vehicleEntranceModule"
    ]);

    //constants
    angular.module("appModule")
            .constant("systemConfig", {
                apiUrl:
                        location.hostname === 'localhost'
                        ? "http://localhost:8090"
                        : location.protocol + "//" + location.hostname + (location.port ? ":" + location.port : "")
            });

    //route config
    angular.module("appModule")
            .config(function ($routeProvider) {
                $routeProvider
                        //system
                        .when("/", {
                            redirectTo: "/account/home"
                        })
                        .when("/login", {
                            templateUrl: "app/system/login/login.html",
                            controller: "LoginController"
                        })
                        //home
                        .when("/account/home", {
                            templateUrl: "app/service/vehicle-entrance/vehicle-entrance.html",
                            controller: "vehicleEntranceController"
                        })

                        //reports
                        .when("/reports/general/report-viewer", {
                            templateUrl: "app/reports/report-viewer/report-viewer.html",
                            controller: "ReportViewerController"
                        })

                        //registration
                        .when("/master/create-account", {
                            templateUrl: "app/master/create-account/create-account.html",
                            controller: "createAccountController"
                        })
                        //setting
                        .when("/setting/account-settings", {
                            templateUrl: "app/master/account-settings/account-settings.html"
//                            controller: "reOrderLevelController"
                        })
                        //transaction
                        .when("/transaction/write-cheque", {
                            templateUrl: "app/accounts/cheque/write-cheque.html"
//                            controller: "chequeController"
                        })
                        .when("/transaction/voucher", {
                            templateUrl: "app/accounts/voucher/voucher.html",
                            controller: "voucherController"
                        })
                        .when("/transaction/fund-transfer", {
                            templateUrl: "app/accounts/fund-transfer/fund-transfer.html"
//                            controller: "chequeController"
                        })
                        .when("/transaction/deposit", {
                            templateUrl: "app/accounts/deposit/deposit.html"
//                            controller: "chequeController"
                        })
                        .when("/transaction/return-cheque", {
                            templateUrl: "app/accounts/return-cheque/return-cheque.html"
//                            controller: "chequeController"
                        })
                        .when("/transaction/payment-to-diposit", {
                            templateUrl: "app/accounts/payment-to-deposit/payment-to-deposit.html"
//                            controller: "chequeController"
                        })
                        .when("/transaction/journal", {
                            templateUrl: "app/accounts/journal/journal.html",
                            controller: "journalController"
                        })
                        
                        //otherwise
                        .otherwise({
                            redirectTo: "/"
                        });
            });

}());
