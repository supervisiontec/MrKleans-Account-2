(function () {
    var service = function ($http, systemConfig) {

        this.loadAccAccounts = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/master/acc-account/find-only-account");
        };
        this.currentBranch = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/branch/current-branch");
        };
        this.loadBranch = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/branch");
        };
        this.loadAccBalance = function (index) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/master/acc-account/find-account-value/" + index);
        };
        this.loadSupplier = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/supplier");
        };
        this.getOverPaymentIssueAccount = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/master/acc-account/find-over-payment-issue-account");
        };
        this.getPayableBills = function (account) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/supplier-payment/get-payable-bills/" + account);
        };
        this.getOverPaymentAmount = function (supplier) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/supplier-payment/get-over-payment-amount/" + supplier);
        };
        this.saveSupplierPayment = function (data) {
            return $http.post(systemConfig.apiUrl + "/api/care-point/transaction/supplier-payment/save", data);
        };
        this.getPermission = function (form) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/account-setting/user-permission/by-form/" + form);
        };
        this.findSupplierPaymentByNumberAndBranch = function (number) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/supplier-payment/find-supplier-payments-by-number-and-branch/" + number);
        };
        this.activeCostCenterList = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/cost-center/get-active-list");
        };
        this.activeCostDepartmentList = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/cost-department/get-active-list");
        };
         this.getSupplierBalanceList = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/journal/get-supplier-balance-list");
        };
         this.loadAccLedgerByAccount = function (account) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/journal/get-account-ledger-by-account/"+account);
        };

        //report api

        //invoice viewer and print
        this.listParameters = function (report) {
            return $http.post(systemConfig.apiUrl + "/api/v1/report/report-viewer/report-parameters", JSON.stringify(report));
        };

        this.reportData = function (reportName) {
            return $http.get(systemConfig.apiUrl + "/api/v1/report/report-viewer/invoice-report-data/" + reportName);
        };

        this.getReportUrl = function (report, params, reportValues) {
            var url = systemConfig.apiUrl + "/api/v1/report/report-viewer/report";

            var action = btoa(report.fileName);
            url = url + "?action=" + action;

            angular.forEach(reportValues, function (value, key) {
                url = url + "&" + key + "=" + value;
            });

            return url;
        };

        this.viewReport = function (report, params, reportValues) {
            return $http.get(this.getReportUrl(report, params, reportValues), {responseType: 'arraybuffer'});
        };
    };
    angular.module("appModule")
            .service("supplierPaymentService", service);
}());

