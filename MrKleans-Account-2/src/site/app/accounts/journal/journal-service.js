(function () {
    var service = function ($http, systemConfig) {
        
        
        this.loadAccAccounts = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/master/acc-account/find-only-account");
        };
        this.loadBranch = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/branch");
        };
        this.activeCostCenterList = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/cost-center/get-active-list");
        };
        this.activeCostDepartmentList = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/cost-department/get-active-list");
        };
        this.currentBranch = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/branch/current-branch");
        };
        this.setAccFlow = function (acc) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/master/acc-account/get-account-flow/"+acc);
        };
        this.saveJournal = function (journalList) {
            return $http.post(systemConfig.apiUrl + "/api/care-point/transaction/journal/save", journalList);
        };
        this.getPermission = function (form) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/account-setting/user-permission/by-form/"+form);
        };
        this.findJournalByNumberAndBranch = function (number) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/journal/find-journal-and-branch/"+number);
        };
    };
    angular.module("appModule")
            .service("journalService", service);
}());