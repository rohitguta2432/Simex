var routerApp = angular.module('routerApp', ['ui.router']);

routerApp.config(function($stateProvider, $urlRouterProvider) {
    
    $urlRouterProvider.otherwise('/index');
    
    $stateProvider
        
        // HOME STATES AND NESTED VIEWS ========================================

        .state('registration', {
                  url: '/registration',
            templateUrl: 'registration/agentregistration.html',
            controller:'agentCtrl'
        })
        
        // ABOUT PAGE AND MULTIPLE NAMED VIEWS =================================
        .state('dataentry', {
                  url: '/dataentry',
            templateUrl: 'Dataentry/dataentry.html'
        })
        
        .state('uploadScreen', {
                  url: '/uploadScreen',
            templateUrl: 'uploadScreen/upload_screen.html',
            controller:'fileUpload'
        })
        .state('telecalling', {
                  url: '/telecalling',
            templateUrl: 'telecalling/telecalling.html'

        });
        
});

routerApp.controller('agentCtrl',['$scope', '$http', function($scope,$http,$q){
/*
    $scope.offices  = [{id:1, office:"GUJARAT"},{id:2, office:"KARNATAKA"},{id:3, office:"B&amp;J"}, {id:4, office:"DELHI"}, {id:5, office:"HARYANA"}, {id:6, office:"HP"}, {id:7, office:"RAJASTHAN"}, {id:8, office:"MPCG"}, {id:9, office:"ANE"},{id:10, office:"M&amp;G"},{id:11, office:"PUNJAB"}, {id:12, office:"ROB"}, {id:13, office:"UPEAST"}, {id:14, office:"UPWEST"}, {id:15, office:"MUMBAI"}];
*/

    $scope.offices = [{id: 1, office:"Delhi"}];
    $scope.codes = [{id: 1, code:"ANESH11"}];

 /*  $scope.getcircleoffice = function(){
        var dfr = $q.defer();
        $http.get('http://localhost:8080/paytm/+').
            success(function(data) {
                dfr.resolve(data);
            }).error(function(error){dfr.reject("Failed");});
        return dfr.promise;
    };

    $scope.spokecode = function(id){
        var dfr = $q.defer();
        $http.get('http://localhost:8080/paytm/+').
            success(function(data) {
                dfr.resolve(data);
            }).error(function(error){dfr.reject("Failed");});
        return dfr.promise;
    };


    $scope.getcircleoffice().then(function(data){
        //console.log('data:   '+data);
        $scope.offices= data.circles;
    }, function(reason) {
        console.log('Error:   '+reason);
    });


    $scope.getspokecode = function(id){
        $log.log('getspokecode  - '+$scope.circle_office.id);
        $scope.spokecode($scope.circle_office.id).then(function(data) {
            $scope.codes = data;
            //$scope.model = $scope.models[2]; //default selected index
            $ionicLoading.hide();
        }, function(reason) {
        });
    }*/


    $scope.errormessage = '';
    $scope.registration = function(){
    alert('fdsfsd');
        var data = 'agent_name=' + $scope.agent_name + '&agent_code=' + $scope.agent_code + '&employee=' + $scope.employee + '&phone=' +$scope.phone + '&circle_office=' +$scope.circle_office + '&spoke_code=' +$scope.spoke_code + '&avl_time=' +$scope.avl_time +'&altr_number=' +$scope.altr_number +'&pin_code=' +$scope.pin_code +'&multi_pin=' +$scope.multi_pin +'&email=' +$scope.email;


       console.log(data);
        $http.get('http://localhost:8080/paytm/agentRegistration?'+ data )
        /*$http.post('http://localhost:8080/paytm/agentRegistration', dataObject)*/
            .success(function(data, status, headers, config) {
                $scope.message = data;
                console.log($scope.message);
            })
            .error(function(data, status, headers, config) {
                alert( "failure message: " + JSON.stringify({data: data}));
            });
// Making the fields empty
//
        $scope.agent_name='';
        $scope.agent_code='';
        $scope.employee='';
        $scope.phone='';
        $scope.circle_office='';
        $scope.spoke_code='';
        $scope.avl_time='';
        $scope.altr_number='';
        $scope.pin_code='';
        $scope.multi_pin='';
        $scope.email='';


    };

}]);



var PHONE_REGEXP = /^[(]{0,1}[0-9]{3}[)\.\- ]{0,1}[0-9]{3}[\.\- ]{0,1}[0-9]{4}$/;

routerApp.directive('phone', function() {
    return {
        restrice: 'A',
        require: 'ngModel',
        link: function(scope, element, attrs, ctrl) {
            angular.element(element).bind('blur', function() {
                var value = this.value;
                if(PHONE_REGEXP.test(value)) {
                    // Valid input
                    scope.errormessage = '';
                    console.log("valid phone number");
                    angular.element(this).next().next().css('display','none');
                } else {
                    // Invalid input
                    scope.errormessage = 'Invalid Phone Number';
                    console.log("invalid phone number");
                    angular.element(this).next().next().css('display','block');
                    /*
                     Looks like at this point ctrl is not available,
                     so I can't user the following method to display the error node:
                     ctrl.$setValidity('currencyField', false);
                     */
                }
            });
        }
    }
});



routerApp.directive('allowPattern', [allowPatternDirective]);

function allowPatternDirective() {
    return {
        restrict: "A",
        compile: function(tElement, tAttrs) {
            return function(scope, element, attrs) {
                // I handle key events
                element.bind("keypress", function(event,key) {
                    var keyCode = event.which || event.keyCode; // I safely get the keyCode pressed from the event.

                    if (key < 48 || key > 57) {
                        if(key == 8 || key == 46){} //allow backspace and delete and whitespace
                        else{
                            if (e.preventDefault) e.preventDefault();
                            e.returnValue = false;
                        }
                    }
                    var keyCodeChar = String.fromCharCode(keyCode); // I determine the char from the keyCode.

                    // If the keyCode char does not match the allowed Regex Pattern, then don't allow the input into the field.
                    if (!keyCodeChar.match(new RegExp(attrs.allowPattern, "i"))) {
                        event.preventDefault();
                        return false;
                    }

                });
            };
        }
    };
}




