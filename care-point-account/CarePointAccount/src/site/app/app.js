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
                        ? "http://localhost:8080"
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
                        .when("/transaction/bank-reconciliation", {
                            templateUrl: "app/accounts/bank-reconciliation/bank-reconciliation.html",
                            controller: "bankReconciliationController"
                        })
                        .when("/transaction/fund-transfer", {
                            templateUrl: "app/accounts/fund-transfer/fund-transfer.html",
                            controller: "fundTransferController"
                        })
                        .when("/transaction/general-voucher", {
                            templateUrl: "app/accounts/voucher/voucher.html",
                            controller: "voucherController"
                        })
                        .when("/transaction/trial-balance", {
                            templateUrl: "app/accounts/trial-balance/trial-balance.html",
                            controller: "trialBalanceController"
                        })
                        .when("/transaction/supplier-payment", {
                            templateUrl: "app/accounts/supplier-payment/supplier-payment.html",
                            controller: "supplierPaymentController"
                        })
                        .when("/transaction/return-cheque", {
                            templateUrl: "app/accounts/return-cheque/return-cheque.html",
                            controller: "returnChequeController"
                        })
                        .when("/transaction/accrued-bill", {
                            templateUrl: "app/accounts/accrued-bill/accrued-bill.html",
                            controller: "accruedBillController"
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
