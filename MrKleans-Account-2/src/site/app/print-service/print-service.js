
(function () {
    var factory = function (FileSaver, $filter) {
        function printService() {
            this.constructor();
        }
//      prototype functions
        printService.prototype = {

            //constructor
            constructor: function () {
            }
            
            , printPdf: function (id) {
                var divToPrint = document.getElementById(id);
                newWin = window.open("financial_accounts");
                newWin.document.write(divToPrint.outerHTML);
                newWin.print();
                newWin.close();
            }
            , printExcel: function (id) {
                var blob = new Blob([document.getElementById(id).innerHTML], {
                    type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
                });
                var date = $filter('date')(new Date(), 'yyyy-MM-dd HH-mm-ss');
                FileSaver.saveAs(blob, "financial_accounts(" + date + ").xls");
            }

        };
        return printService;
    };
    angular.module("appModule")
            .factory("printService", factory);
}());
