(function () {
//index module
    angular.module("appModule", [
        "ngRoute",
        "ngCookies",
        "ui.bootstrap",
        //master
        "clientModule",
        "supplierModule",
        "vehicleModule",
        "itemModule",
        "costCenterModule",
        "costDepartmentModule",
        "profitAndLostModule",
        // service
        "dashBoardModule",
        "budgetModule",
        //transaction
        "itemSalesModule",
        "paymentVoucherModule",
        "purchaseOrderApproveModule",
        "purchaseOrderRequestModule",
        "grnApproveModule",
        "directGrnModule",
        "grnModule",
        "supplierReturnModule",
        //setting
        "accountSettingModule",
        "userPermissionModule",
        "ngFileSaver",
        "accCodeSettingModule"
    ]);
    //constants
    angular.module("appModule")
            .constant("systemConfig", {
                apiUrl:
                        location.hostname === 'localhost'
                        ? 'http://localhost:8072'
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
                            templateUrl: "app/service/dash-board/dash-board.html",
                            controller: "dashBoardController"
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

                        .when("/master/client", {
                            templateUrl: "app/master/client/client.html",
                            controller: "clientController"
                        })
                        .when("/master/supplier", {
                            templateUrl: "app/master/supplier/supplier.html",
                            controller: "supplierController"
                        })
                        .when("/master/item", {
                            templateUrl: "app/master/item/item.html",
                            controller: "itemController"
                        })
                        .when("/master/cost-center", {
                            templateUrl: "app/master/cost-center/cost-center.html",
                            controller: "costCenterController"
                        })
                        .when("/master/cost-department", {
                            templateUrl: "app/master/cost-department/cost-department.html",
                            controller: "costDepartmentController"
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
                        .when("/transaction/all-transaction", {
                            templateUrl: "app/accounts/all-transaction/all-transaction.html",
                            controller: "allTransactionController"
                        })
                        .when("/transaction/edit-transaction", {
                            templateUrl: "app/accounts/edit-transaction/edit-transaction.html",
                            controller: "editTransactionController"
                        })
                        .when("/transaction/profit-and-lost", {
                            templateUrl: "app/accounts/profit-and-lost/profit-and-lost.html",
                            controller: "profitAndLostController"
                        })

                        // services

                        .when("/service/item-sales", {
                            templateUrl: "app/service/item-sales/item-sales.html",
                            controller: "itemSalesController"
                        })
                        .when("/service/customer-payment", {
                            templateUrl: "app/service/customer-payment/customer-payment.html",
                            controller: "customerPaymentVoucherController"
                        })
                        .when("/service/grn-request", {
                            templateUrl: "app/service/grn/grn-request/grn-request.html",
                            controller: "grnController"
                        })
                        .when("/service/grn-approve", {
                            templateUrl: "app/service/grn/grn-approve/grn-approve.html",
                            controller: "grnApproveController"
                        })
                        .when("/service/grn-direct", {
                            templateUrl: "app/service/grn/grn-direct/grn.html",
                            controller: "directGrnController"
                        })
                        .when("/service/purchase-order-request", {
                            templateUrl: "app/service/purchase-order/request/purchase-order-request.html",
                            controller: "purchaseOrderRequestController"
                        })
                        .when("/service/purchase-order-approve", {
                            templateUrl: "app/service/purchase-order/approve/purchase-order-approve.html",
                            controller: "purchaseOrderApproveController"
                        })
                        .when("/service/supplier-return", {
                            templateUrl: "app/service/supplier-return/supplier-return.html",
                            controller: "supplierReturnController"
                        })
                        .when("/master/budget", {
                            templateUrl: "app/master/budget/budget.html",
                            controller: "budgetController"
                        })

                        // setting
                        .when("/setting/account-settings", {
                            templateUrl: "app/setting/account-setting/account-settings.html",
                            controller: "accountSettingController"
                        })
                        .when("/setting/user-permission", {
                            templateUrl: "app/setting/user-permission/user-permission.html",
                            controller: "userPermissionController"
                        })
                        .when("/setting/acc-code-setting", {
                            templateUrl: "app/setting/account-code-setting/account-code-setting.html",
                            controller: "accCodeSettingController"
                        })

                        //otherwise
                        .otherwise({
                            redirectTo: "/"
                        });
            });
}());
