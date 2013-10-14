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

receiptControllers.controller('ReceiptDetailCtrl', ['$scope', '$location', '$routeParams', 'Receipt',
    function ($scope, $location, $routeParams, Receipt) {
        $scope.receipt = Receipt.get({receiptId: $routeParams.receiptId});

        $scope.delete = function () {
            Receipt.delete({receiptId: $routeParams.receiptId}).$promise.then(function () {
                $location.path('/receipts');
            });
        }

        $scope.fileUrl = '/receipt/' + $routeParams.receiptId + '/image';
    }]);

receiptControllers.controller('CreateReceiptCtrl', ['$scope', '$http', '$location', '$q', '$routeParams', 'Receipt',
    function ($scope, $http, $location, $q, $routeParams, Receipt) {
        $scope.save = function () {
            $q.all([
                    Receipt.save($scope.receipt).promise])
                .then(function (data) {
                    $location.path('/receipts');
                });
        }


        $scope.selectedFiles = [];

        $scope.onFileSelect = function ($files) {
            //$files: an array of files selected, each file has name, size, and type.
            for (var i = 0; i < $files.length; i++) {
                var $file = $files[i];

                $http.uploadFile({
                    url: '/receipt/1/image',
                    data: {myObj: 'test'},
                    file: $file

                }).then(function (data, status, headers, config) {
                        // file is uploaded successfully
                        console.log(data);
                    });
            }
        }


    }]);

// ----------------------------------------------
// -------------- Application -------------------

var receiptApp = angular.module('receiptApp', ['ngRoute', 'receiptControllers', 'receiptServices', 'angularFileUpload']);


receiptApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
            when('/receipts', {
                templateUrl: 'partials/receipt-list.html',
                controller: 'ReceiptListCtrl'
            }).
            when('/receipt/new', {
                templateUrl: 'partials/receipt-detail.html',
                controller: 'CreateReceiptCtrl'
            }).
            when('/receipt/edit/:receiptId', {
                templateUrl: 'partials/receipt-detail.html',
                controller: 'ReceiptDetailCtrl'
            }).
            otherwise({
                redirectTo: '/receipts'
            });
    }]);















