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
           /* $http.get('http://172.25.38.49:8080/paytm/login?'+daata).success(function(data, status, headers, config){*/

                $http.get('/paytm/login?'+daata).success(function(data, status, headers, config){
                   // alert('daata');
                $scope.msg=data;
               // console.log(data.status);
               if(data.status == 'success') {
                   $window.location.href = '/paytm/appfront/portal.jsp';
               } else if(data.status == 'expirePassword'){
                   $scope.show2 = true;
               } else
               {
                   //alert('Invalid Username and Password.');
                   $scope.show1 = true;
               }
            }).error(function () {

            });
        }

        $scope.expire= function()
        {
            alert("Expire calling  "+$scope.pass1 + $scope.pass);
            if($scope.pass1==undefined){
                alert("Password Minimum Length should be 9 ");
                ev.preventDefault();
            }
            else if($scope.pass!=$scope.pass1){
             alert("Password mismatch Please try again");
                ev.preventDefault();
            }else {

                var daata = 'userName=' + $scope.user + '&password=' + $scope.pass;
                /* $http.get('http://172.25.38.49:8080/paytm/login?'+daata).success(function(data, status, headers, config){*/

                $http.get('/paytm/resetPassword?' + daata).success(function (data, status, headers, config) {
                    // alert('daata');
                    $scope.msg = data;
                    // console.log(data.status);
                    if (data.status == 'success') {
                        $scope.show2 = false;
                        $window.location.href = '/paytm/appfront/app.jsp';
                    }
                }).error(function () {

                });
            }
        }


        $scope.expireOpen= function()
        {
            $window.location.href = '/paytm/appfront/auth/expire.jsp?user='+$scope.user;
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
