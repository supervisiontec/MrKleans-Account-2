(function () {
    var service = function ($http, systemConfig) {

        this.loadAccAccounts = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/master/acc-account/find-only-cash-bank-cheque");
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
        this.loadAccTypes = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/account-type");
        };
        this.saveFundTransfer = function (data) {
            return $http.post(systemConfig.apiUrl + "/api/care-point/transaction/fund-transfer/save", data);
        };
        this.getPermission = function (form) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/account-setting/user-permission/by-form/" + form);
        };
        this.findFundTransferByNumberAndBranch = function (number) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/fund-transfer/find-fund-transfer-by-number-and-branch/" + number);
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
            .service("fundTransferService", service);
}());

