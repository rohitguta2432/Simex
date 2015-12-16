'use strict';

// Declare app level module which depends on views, and components
angular.module('PaytmAuth', [
                'ngRoute',
                'PaytmAuth.auth',
                'PaytmAuth.version'
    ]).
    config(['$routeProvider','$locationProvider', function($routeProvider,$locationProvider) {
        $routeProvider.otherwise({redirectTo: '/auth'});
    }]);
