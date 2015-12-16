'use strict';

angular.module('PaytmAuth.auth', ['ngRoute'])
    .config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/auth', {
          templateUrl: '/paytm/appfront/auth/login.htm',
          controller: 'AuthCtrl'
        });
    }])
    .controller('AuthCtrl', [function() {

    }]);