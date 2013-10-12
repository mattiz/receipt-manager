var receiptServices = angular.module('receiptServices', ['ngResource']);

receiptServices.factory('Receipt', ['$resource',
    function ($resource) {
        return $resource('/receipt', {}, {
            query: {method: 'GET', params: {}, isArray: true}
        });
    }]);






var receiptApp = angular.module('receiptApp', ['receiptServices']);

receiptApp.controller('ReceiptCtrl', ['$scope', 'Receipt', function ReceiptCtrl($scope, Receipt) {
    $scope.receipts = Receipt.query();
}]);