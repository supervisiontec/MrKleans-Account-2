
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
//                01 working
                var divToPrint = document.getElementById(id);
                newWin = window.open("financial_accounts");
                newWin.document.write(divToPrint.outerHTML);
                newWin.print();
                newWin.close();

//                02 working
//                var printContents = document.getElementById(id).innerHTML;
//                var originalContents = document.body.innerHTML;
//                document.body.innerHTML = printContents;
//                window.print();
//                document.body.innerHTML = originalContents;
            }
            , printExcel: function (id,name) {
                var blob = new Blob([document.getElementById(id).innerHTML], {
                    type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
                });
                var date = $filter('date')(new Date(), 'yyyy-MM-dd HH-mm-ss');
                FileSaver.saveAs(blob, name+"(" + date + ").xls");
            }

        };
        return printService;
    };
    angular.module("appModule")
            .factory("printService", factory);
}());
