'use strict';

// Declare app level module which depends on views, and components
angular.module('PaytmKyc', [
    'ngRoute',
    'PaytmKyc.main',
    'PaytmKyc.version'
]).
    config(['$routeProvider', function($routeProvider) {
        $routeProvider.otherwise({redirectTo: '/main'});
    }]);
