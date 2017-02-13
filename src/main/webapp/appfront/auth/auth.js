'use strict';

angular.module('PaytmAuth.auth', ['ngRoute'])
//$scope.domain='http://localhost:8080/simex';
    .config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/auth', {
          templateUrl: '/simex/appfront/auth/login.htm',
          controller: 'AuthCtrl'
        });
    }])
    .controller('AuthCtrl', ['$scope','$http','$window',function($scope, $http, $window) {

        $scope.show1 = false;
        $scope.result="";
        $scope.result1="";
        $scope.loginData = function()
        {
            //alert('Login.');
            var daata = 'userName='+$scope.user+'&password='+encodeURIComponent($scope.pass) ;
           /* $http.get('http://172.25.38.49:8080/simex/login?'+daata).success(function(data, status, headers, config){*/

                $http.get('/simex/login?'+daata).success(function(data, status, headers, config){
                   // alert('daata');
                $scope.msg=data;
               // console.log(data.status);
               if(data.status == 'success') {
                   $window.location.href = '/simex/appfront/portal.jsp';
               } else if(data.status == 'expirePassword'){
                   $scope.show2 = true;
                   $scope.result = "Password Expired Please Reset";
               } else
               {
                   $scope.result=data.status;
                   //alert('Invalid Username and Password.');
                   $scope.show1 = true;
               }
            }).error(function () {

            });
        }

        $scope.expire= function()
        {

            if($scope.pass==undefined){
                alert(" password length should be 8 to 10 with atlest 1 charecter  numeric, spacial,small alphabet,capital alphabet ");
                return false;
            }
            else if($scope.pass!=$scope.pass1){
             alert("Password mismatch Please try again");
                return false;
            }else {

                var daata = 'userName=' + $scope.user + '&password=' + encodeURIComponent($scope.pass) +'&oldpassword='+encodeURIComponent($scope.oldpass);
                /* $http.get('http://172.25.38.49:8080/simex/login?'+daata).success(function(data, status, headers, config){*/

                $http.get('/simex/resetPassword?' + daata).success(function (data, status, headers, config) {
                    // alert('daata');
                    $scope.msg = data;
                    // console.log(data.status);
                    if (data.status == 'success') {
                        alert("Password Change Successfully   ");
                        $scope.show2 = false;
                        $window.location.href = '/simex/appfront/app.jsp';
                    }else{
                        $scope.result1=data.status;
                    }
                }).error(function () {

                });
            }
        }


        $scope.expireOpen= function()
        {
            $window.location.href = '/simex/appfront/auth/expire.jsp?user='+$scope.user;
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
