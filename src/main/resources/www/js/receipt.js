'use strict';

// -------------------------------------------
// -------------- Services -------------------

var receiptServices = angular.module('receiptServices', ['ngResource']);

receiptServices.factory('Receipt', ['$resource',
    function ($resource) {
        return $resource('/receipt/:receiptId', {}, {
            query: {method: 'GET', params: {}, isArray: true}
        });
    }]);


// ----------------------------------------------
// -------------- Controllers -------------------

var receiptControllers = angular.module('receiptControllers', []);

receiptControllers.controller('ReceiptListCtrl', ['$scope', 'Receipt',
    function ($scope, Receipt) {
        $scope.receipts = Receipt.query();
    }]);

receiptControllers.controller('ReceiptDetailCtrl', ['$scope', '$routeParams', 'Receipt',
    function ($scope, $routeParams, Receipt) {
        $scope.receipt = Receipt.get({receiptId: $routeParams.receiptId});
    }]);

// ----------------------------------------------
// -------------- Application -------------------

var receiptApp = angular.module('receiptApp', ['ngRoute', 'receiptControllers', 'receiptServices']);


receiptApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
            when('/receipts', {
                templateUrl: 'partials/receipt-list.html',
                controller: 'ReceiptListCtrl'
            }).
            when('/receipt/:receiptId', {
                templateUrl: 'partials/receipt-detail.html',
                controller: 'ReceiptDetailCtrl'
            }).
            otherwise({
                redirectTo: '/receipts'
            });
    }]);
