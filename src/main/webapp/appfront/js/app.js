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
            templateUrl: 'telecalling/telecalling.html',
            controller:'telecalling'

        });

});

routerApp.controller('agentCtrl',['$scope', '$http','$q','$log','$location', function($scope,$http,$q,$log,$location){

    $scope.offices = [{id: 1, office:"Delhi"}];
    $scope.codes = [{id: 1, code:"ANESH11"}];
/* Function for get CircleOffice and Spoke office */
    $scope.getcircleoffice = function(){
        var dfr = $q.defer();
        $http.get('http://localhost:8080/paytm/getCirles').
            success(function(data) {
                dfr.resolve(data);
            }).error(function(error){dfr.reject("Failed");});
        return dfr.promise;
    };

    $scope.spokecode = function(id){
        var dfr = $q.defer();
        $http.get('http://localhost:8080/paytm/getSpokeCode?circleName='+id).
            success(function(data) {
                dfr.resolve(data);
            }).error(function(error){dfr.reject("Failed");});
        return dfr.promise;
    };


    $scope.getcircleoffice().then(function(data){
        //console.log('data:   '+data);
        $scope.offices= data.circles;
       // console.log( $scope.offices);
    }, function(reason) {
        console.log('Error:   '+reason);
    });


    $scope.getspokecode = function(){
        $log.log('getspokecode  - '+$scope.circle_office);
        $scope.spokecode($scope.circle_office).then(function(data) {
            $scope.codes = data.spokeList;
        }, function(reason) {
        });
    }


    $scope.errormessage = '';

    //$scope.submitData = function(data){
    //       var dfr = $q.defer();
    //
    //       $http.get(data).
    //           success(function(data) {
    //               dfr.resolve(data);
    //           }).error(function(error){dfr.reject("Failed");});
    //
    //       return dfr.promise;
    //   };
    $scope.registration = function(){
        alert('fdsfsd');
        var data = 'agent_name=' + $scope.agent_name + '&agent_code=' + $scope.agent_code + '&employee=' + $scope.employee + '&phone=' +$scope.phone + '&circle_office=' +$scope.circle_office + '&spoke_code=' +$scope.spoke_code + '&avl_time=' +$scope.avl_time +'&altr_number=' +$scope.altr_number +'&pin_code=' +$scope.pin_code +'&multi_pin=' +$scope.multi_pin +'&email=' +$scope.email;


        console.log(data);
        $http.get('http://localhost:8080/paytm/agentRegistration?'+ data )
            /*$http.post('http://localhost:8080/paytm/agentRegistration', dataObject)*/
            .success(function(data, status, headers, config) {
                $scope.message = data;
                console.log($scope.message);
                // $location.path('/draft');
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



routerApp.controller('telecalling',['$scope', '$http','$q','$log','$location', function($scope,$http,$q,$log,$location){

   /* $scope.message = {
        text: 'hello world!',
        time: new Date()
    };*/
<<<<<<< HEAD
=======

    $scope.times = [];
>>>>>>> 2c7ee3d... commit for telecaling Screen
    $scope.date = new Date();
    $scope.mob={};
    $scope.codes=[];
    $scope.statuses = [{csmCode: 1, status:"Delhi"}];
    /* Function for get Telecalling Screen */
  $scope.getscreen = function(){
        var dfr = $q.defer();
        $http.get('http://localhost:8080/paytm/telecallingScreen').
            success(function(data) {
                dfr.resolve(data);
            }).error(function(error){dfr.reject("Failed");});
        return dfr.promise;
    };
    $scope.getscreen().then(function(data) {
        //alert('getscreen');
        $scope.statuses = data.statusList;
        $scope.states = data.stateList;
        $scope.date = data.dateList;
        $scope.codes = data.paytmmastjson;
        $scope.mob = data.teleData;
      // console.log($scope.mob);
       // console.log($scope.statuses);
       // console.log($scope.codes);
    }, function(reason) {
    });


    $scope.changestatus = function(){
         var data = 'status=' + $scope.status.csmCode + '&mobileNo=' + $scope.mob.mobileNo;

        if($scope.status.csmCode == 'CON')
        {
            //alert(data);
           // $scope.screen();
            //$scope.disabled= true;
        }
        else {
           // $scope.disabled= true;
            //alert('Are you want to sure?');
           // alert('postcaaling')
            var cnf= confirm('Are You Sure?');
            if (cnf == false) {
                location.reload();
            }
            else {

                $http.get('http://localhost:8080/paytm/postCallingStatus?' + data)
                    /*$http.post('http://localhost:8080/paytm/agentRegistration', dataObject)*/
                    .success(function (data, status, headers, config) {
                        //  alert(data);
                        $scope.message = data;
                        console.log($scope.message);
                        $scope.getscreen();
                        // $location.path('/draft');
                    })
                    .error(function (data, status, headers, config) {
                        alert("failure message: " + JSON.stringify({data: data}));
                    });

            }
        };

       // $scope.visit_time = [{time:"8:00"},{time:'9:00'},{time:'10:00'},{time:'11:00'},{time:'12:00'},{time:'13:00'},{time:'14:00'},{time:'15:00'},{time:'16:00'},{time:'17:00'},{time:'18:00'},{time:'19:00'}];
    };

    $scope.screen = function(){

        var data = 'mobileNo=' + $scope.mob.mobileNo + '&name=' + $scope.mob.customerName +'&address=' + $scope.codes.address1 + '&area=' + $scope.codes.address2 + '&emailId=' + $scope.codes.email + '&city=' + $scope.codes.city + '&state=' + $scope.state.stateCode + '&pincode=' + $scope.codes.pincode + '&landmark=' + $scope.land_mark + '&visitDate=' + $scope.visit_date + '&visitTime=' + $scope.visit_time + '&status=' + $scope.status.csmCode;
        //alert(data);
        $http.get('http://localhost:8080/paytm/postCalling?'+ data)
            /*$http.post('http://localhost:8080/paytm/agentRegistration', dataObject)*/
            .success(function(data, status, headers, config) {
               // alert(data);
                $scope.message = data;
                console.log($scope.message);
                $scope.getscreen();
                // $location.path('/draft');
            })
            .error(function(data, status, headers, config) {
                alert( "failure message: " + JSON.stringify({data: data}));
            });


        // $scope.visit_time = [{time:"8:00"},{time:'9:00'},{time:'10:00'},{time:'11:00'},{time:'12:00'},{time:'13:00'},{time:'14:00'},{time:'15:00'},{time:'16:00'},{time:'17:00'},{time:'18:00'},{time:'19:00'}];
    };

<<<<<<< HEAD
=======
    $scope.getTime = function(){      /////get visit time according to date
        var myarr = [];
        var varDate = $scope.visit_date;
        var d = new Date();
        var tdate = d.getDate();
        if (tdate <= 9) {
            dd = '0' + tdate;
        }
        else {
            dd = tdate;
        }
        var tmonth = d.getMonth() + 1;
        if (tmonth <= 9) {
            mm = '0' + tmonth;
        }
        else {
            mm = tmonth;
        }

        var tyear = d.getFullYear();
        todaydate =  dd + '/' + mm + '/' + tyear;

        if(todaydate == varDate)
        {
            var d1 = new Date();
            var n = d1.getHours();
            // document.getElementById("demo").innerHTML = n;


            hour = n + 2;
            for (; hour <= 21; hour++) {
                var am = '';
                if (hour < 12)
                    am = 'AM';
                else
                    am = 'PM';

                myarr.push(hour + ':00 ');
                $scope.times= myarr;
            }
        }
        else
        {
            for (var i = 8; i <= 21; i++)
            {
                myarr.push(i + ':00 ');
                $scope.times= myarr;
            }
        }

       // $scope.times= myarr;
        //console.log(myarr+todaydate+varDate);
    }

>>>>>>> 2c7ee3d... commit for telecaling Screen
    $scope.calling = function(){            /////for customer calling

        var data = 'customer_number=' + $scope.mob.mobileNo;
        //alert(data);
        $http.get('http://localhost:8080/paytm/customerCalling?'+ data)
            /*$http.post('http://localhost:8080/paytm/agentRegistration', dataObject)*/
            .success(function(data, status, headers, config) {
                // alert(data);
                $scope.message = data;
                console.log($scope.mob.mobileNo);
                //$scope.getscreen();
                // $location.path('/draft');
            })
            .error(function(data, status, headers, config) {
                alert( "failure message: " + JSON.stringify({data: data}));
            });


        // $scope.visit_time = [{time:"8:00"},{time:'9:00'},{time:'10:00'},{time:'11:00'},{time:'12:00'},{time:'13:00'},{time:'14:00'},{time:'15:00'},{time:'16:00'},{time:'17:00'},{time:'18:00'},{time:'19:00'}];
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
                        if(key == 10 || key == 127){} //allow backspace and delete and whitespace
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




