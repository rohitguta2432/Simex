'use strict';

angular.module('PaytmAuth.auth', ['ngRoute'])
//$scope.domain='http://localhost:8080/paytm';
    .config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/auth', {
          templateUrl: '/paytm/appfront/auth/login.htm',
          controller: 'AuthCtrl'
        });
    }])
    .controller('AuthCtrl', ['$scope','$http','$window',function($scope, $http, $window) {
        $scope.show1 = false;
        $scope.loginData = function()
        {
            //alert('Login.');
            var daata = 'userName='+$scope.user+'&password='+$scope.pass;
            $http.get('http://172.25.38.52:8080/paytm/login?'+daata).success(function(data, status, headers, config){
                   // alert('daata');
                $scope.msg=data;
               // console.log(data.status);
               if(data.status == 'success') {
                   $window.location.href = '/paytm/appfront/portal.jsp';
               }
                else
               {
                   //alert('Invalid Username and Password.');
                   $scope.show1 = true;
               }
            }).error(function () {

            });
        }
    }])
.controller('LogoutController', ['$scope','$location', function($scope, $location){
    //Session.clear();
    //$location.path('/auth');
        //$scope.logout = function () {
            localStorage.clearAll();
            window.location = '/logout';
   //     };
}]);
