var routerApp = angular.module('routerApp', ['ui.router']);

routerApp.config(function($stateProvider, $urlRouterProvider) {
    
    $urlRouterProvider.otherwise('/index');
    
    $stateProvider
        
        // HOME STATES AND NESTED VIEWS ========================================

        .state('registration', {
                  url: '/registration',
            templateUrl: 'registration/agentregistration.html'
        })
        
        // ABOUT PAGE AND MULTIPLE NAMED VIEWS =================================
        .state('dataentry', {
                  url: '/dataentry',
            templateUrl: 'Dataentry/dataentry.html'
        })
        
        .state('uploadScreen', {
                  url: '/uploadScreen',
            templateUrl: 'uploadScreen/upload_screen.html'
        })
        .state('telecalling', {
                  url: '/telecalling',
            templateUrl: 'telecalling/telecalling.html'
        });
        
});