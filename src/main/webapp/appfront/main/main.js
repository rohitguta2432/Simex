'use strict';

angular.module('PaytmKyc.main', ['ngRoute'])
    .config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/main', {
          templateUrl: 'main/main.htm',
          controller: 'KycCtrl'
        });
    }])
    .controller('KycCtrl', [function() {

    }]);