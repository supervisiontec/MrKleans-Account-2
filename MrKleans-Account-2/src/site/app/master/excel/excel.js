
(function () {
//module
    angular.module("excelModule", ['ui.bootstrap', 'ui-notification']);
    angular.module("excelModule")
            .controller("excelController", function ($scope,$timeout) {
                $scope.excelJsonObj = [];

                $scope.uploadExcel = function () {
                    var myFile = document.getElementById('file');
                    var input = myFile;
                    var reader = new FileReader();

                    reader.onload = function () {
                        var fileData = reader.result;
                        var workBook = XLSX.read(fileData, {type: 'binary'});
                        
                        $timeout(function () {
                            workBook.SheetNames.forEach(function (sheetName) {
                                var rowObject = XLSX.utils.sheet_to_row_object_array(workBook.Sheets[sheetName]);
                                if (rowObject.length > 0) {
                                    $scope.excelJsonObj = rowObject;
                                    console.log($scope.excelJsonObj);
                                }
                            });
                        }, 100);

                    };
                    reader.readAsBinaryString(input.files[0]);
                };
            });
}());