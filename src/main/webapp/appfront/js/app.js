var routerApp = angular.module('routerApp', ['ui.router','ngMaterial','ngLoadingSpinner','ui.bootstrap', 'ui.bootstrap.datetimepicker','angularjs-datetime-picker']);
// var domain='http://localhost:8080/paytm';
var domain='http://172.25.38.131:8080/paytm';
//var domain='http://172.25.38.185:8080/paytm';
 //  var domain ='http://172.25.38.131:8080/paytm';
routerApp.config(function($stateProvider, $urlRouterProvider) {
   // var domain ='172.25.37.185:8080/paytm';
   //  var domain ='http://localhost:8080/paytm';


    $urlRouterProvider.otherwise('/index');

    $stateProvider

        // HOME STATES AND NESTED VIEWS ========================================

        .state('registration', {
            url: '/registration',
            templateUrl: 'Registration/agentregistration.html',
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
            controller:'myCtrl'
        })
        .state('telecalling', {
            url: '/telecalling',
            templateUrl: 'Telecalling/telecalling.html',
            controller:'telecalling'

        }) .state('QCInterface', {
            url: '/QCInterface',
            templateUrl: 'QCInterface/qcInterface.html',
            controller:'QCInterface'

        })
        .state('report', {
            url: '/report',
            templateUrl: 'report/report.html',
            controller:'report'

        });


});

routerApp.controller('agentCtrl',['$scope', '$http','$q','$log','$location','$mdDialog','$mdMedia', function($scope,$http,$q,$log,$location,$mdDialog,$mdMedia){

    $scope.name='';
    $scope.agent_code='';
    $scope.employee='';
    $scope.phone='';
    $scope.spoke_code='';
    $scope.circle_office='';
    $scope.avl_time='';
    $scope.altr_number='';
    $scope.pin_code='';
    $scope.email='';


    $scope.offices = [{id: 1, officet:"Delhi"}];
    $scope.codes = [{id: 1, code:"ANESH11"}];
    /* Function for get CircleOffice and Spoke office */
    $scope.getcircleoffice = function(){
        var dfr = $q.defer();
        $http.get(domain+'/getCirles').
            success(function(data) {
                dfr.resolve(data);
            }).error(function(error){dfr.reject("Failed");});
        return dfr.promise;
    };
    $scope.getcircleoffice().then(function(data){
        //console.log('data:   '+data);
        $scope.circleofiice= data.circles;
        $scope.offices1= data.spokeList;
        console.log($scope.circleofiice);
        console.log($scope.offices1);
        // console.log( $scope.offices);
    }, function(reason) {
        console.log('Error:   '+reason);
    });


    /*$scope.spokecode = function(id){
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
    }*/


    $scope.errormessage = '';

    $scope.submit = function(ev) {



        // $scope.submit = function($event) {
        //alert('fdsfsd');
        if($scope.name.length == 0 || $scope.name == undefined){
            //alert('Agent Name is not valid');
            ev.preventDefault();
        }
        else if($scope.agent_code.length == 0 || $scope.agent_code == undefined){
            // alert('Agent Code is not valid');
            ev.preventDefault();
        }
        else if( $scope.employee.length == 0 ||  $scope.employee == undefined){
            //  alert('Enter the name Employee');
            ev.preventDefault();
        }
        else if($scope.phone.length == 0 || $scope.phone == undefined){
            // alert('Enter the Phone Number');
            ev.preventDefault();
        }
       /* else if($scope.circle_office.length == 0 || $scope.circle_office == undefined){
            // alert('Select Circle Office');
            ev.preventDefault();
        }*/
        else if($scope.spoke_code.length == 0 || $scope.spoke_code == undefined){
            //  alert('Select Spoke Code');
            ev.preventDefault();
        }
        else if($scope.pin_code.length == 0 ||$scope.pin_code == undefined){
            // alert('Enter Pin Number');
            ev.preventDefault();
        }
        else {
           //
            // alert('fdsfsd');

            var pin = $scope.multi_pin;
            if(pin == undefined || pin == 'N')
            {
                pin = 'N';
            }
            var data = 'agent_name=' + $scope.name + '&agent_code=' + $scope.agent_code + '&employee=' + $scope.employee + '&phone=' + $scope.phone + '&circle_office=' + $scope.circleofiice + '&spoke_code=' + $scope.spoke_code + '&avl_time=' + $scope.avl_time + '&altr_number=' + $scope.altr_number + '&pin_code=' + $scope.pin_code + '&multi_pin=' + pin + '&email=' + $scope.email;


            console.log(data);
            $http.get(domain+'/agentRegistration?' + data)
                /*$http.post('http://localhost:8080/paytm/agentRegistration', dataObject)*/
                .success(function (data, status, headers, config) {
                    $scope.message = data;

                    if(data.status == 'success'){



                        /*$scope.successTextAlert = "Agent Successfully Registered";
                        window.setTimeout(function(){
                            location.reload();
                        }, 2000);*/
                        $scope.status = '';
                        $scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

                        $mdDialog.show(
                            $mdDialog.alert()
                                .parent(angular.element(document.querySelector('#popupContainer')))
                                .clickOutsideToClose(true)
                                //.title('This is an alert title')
                                .textContent(data.msg)
                                .ariaLabel('Alert Dialog Demo')
                                .ok('OK')
                                .targetEvent(ev)

                        );
                        ClearForm();
                    }
                    if(data.status == 'error'){
                       // alert('Agent Already Registered');
                        $scope.status = '';
                        $scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

                        $mdDialog.show(
                            $mdDialog.alert()
                                .parent(angular.element(document.querySelector('#popupContainer')))
                                .clickOutsideToClose(true)
                                .title('')
                                .textContent(data.msg)
                                .ariaLabel('Alert Dialog Demo')
                                .ok('OK')
                                .targetEvent(ev)

                        );
                    }
                })
                .error(function (data, status, headers, config) {
                    alert("failure message: " + JSON.stringify({data: data}));
                });


        };
    }

    function ClearForm() {
        //$scope.FileDescription = '';
        angular.forEach(angular.element("input[type='text'],select"), function (inputElem) {
            angular.element(inputElem).val(null);
        });

        $scope.IsFormSubmitted = false;
        //$scope.description = '';
        //$scope.description = '';
        //$scope.description = '';
        //$scope.description = '';
        //$scope.SelectedFileForUpload = null;

    }
}]);


routerApp.controller('QCInterface',['$scope', '$http','$q','$log','$location','$mdDialog','$mdMedia','$sce', function($scope,$http,$q,$log,$location,$mdDialog,$mdMedia, $sce){

    //$scope.cust_number='';
   // $scope.url='';
   // $scope.flag=false;
    $scope.state1=true;
    $scope.trustSrc = function(src) {
        return $sce.trustAsResourceUrl(src);
    }



    $scope.errormessage = '';


    $scope.change=function(){

        $http.get(domain+'/getCustomer')
            .success(function (data, status, headers, config) {
                $scope.cust_number=data.mobile;

                if(data.status == 'error'){
                    // alert('Agent Already Registered');
                    $scope.status = '';
                    $scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

                    $mdDialog.show(
                        $mdDialog.alert()
                            .parent(angular.element(document.querySelector('#popupContainer')))
                            .clickOutsideToClose(true)
                            .title('')
                            .textContent(data.msg)
                            .ariaLabel('Alert Dialog Demo')
                            .ok('OK')
                            .targetEvent(ev)

                    );
                }
            })
            .error(function (data, status, headers, config) {
                alert("failure message: " + JSON.stringify({data: data}));
            });



    };


    var call=function(){
        $scope.change();
    }

     call();


    $scope.submit = function(ev) {
        if($scope.cust_number.length == 0 || $scope.cust_number == undefined){
            ev.preventDefault();
        } else
            var data = 'customer_Number=' + $scope.cust_number;


           //console.log(data);
            //alert(" data   "+data);

            $http.get(domain+'/getPdfUrl?' + data)
                .success(function (data, status, headers, config) {
                    $scope.url1= data.url;
                    $scope.resuri = {src: data.url, title:data.url.toString()};
                    $scope.abc=true;

                        console.log("dataa" +$scope.url1);

                    if(data.status == 'error'){
                        // alert('Agent Already Registered');
                        $scope.status = '';
                        $scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

                        $mdDialog.show(
                            $mdDialog.alert()
                                .parent(angular.element(document.querySelector('#popupContainer')))
                                .clickOutsideToClose(true)
                                .title('')
                                .textContent(data.msg)
                                .ariaLabel('Alert Dialog Demo')
                                .ok('OK')
                                .targetEvent(ev)

                        );
                    }
                })
                .error(function (data, status, headers, config) {
                    alert("failure message: " + JSON.stringify({data: data}));
                });


        };

    $scope.qcReject= function(ev) {
        if($scope.cust_number.length == 0 || $scope.cust_number == undefined){
            ev.preventDefault();
        } else
        //    alert(" Reject "+ $scope.rejct_pages);

        var data = '&mobileNo=' + $scope.cust_number +'&status=3' + '&rejectedPage=' + $scope.rejct_pages +'&remarks=' + $scope.user_comment;


        //console.log(data);
        //alert(" data   "+data);

        $http.get(domain+'/qcstatus?' + data)
         .success(function (data, status, headers, config) {
     //    $scope.url1= data.url;

         console.log("dataa" +$scope.url1);

                location.reload();

         if(data.status == 'error'){
         // alert('Agent Already Registered');
         $scope.status = '';
         $scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

         $mdDialog.show(
         $mdDialog.alert()
         .parent(angular.element(document.querySelector('#popupContainer')))
         .clickOutsideToClose(true)
         .title('')
         .textContent(data.msg)
         .ariaLabel('Alert Dialog Demo')
         .ok('OK')
         .targetEvent(ev)

         );
         }
         })
         .error(function (data, status, headers, config) {
         alert("failure message: " + JSON.stringify({data: data}));
         });


    };


   /* $scope.checkStatus2= function(ev) {
        if($scope.cust_number.length == 0 || $scope.cust_number == undefined){
            ev.preventDefault();
        }
        alert("hello checked ");

    };*/

 /*   $scope.change = function() {

        $scope.state1=true;
        $scope.state2=false;

    };
    $scope.change1 = function() {
        $scope.state2=true;
        $scope.state1=false;


    };*/
    $scope.qcOK= function(ev) {
        if($scope.cust_number.length == 0 || $scope.cust_number == undefined){
            ev.preventDefault();
        } else
            alert(" OK  "+$scope.rejct_pages);
        var data = '&mobileNo=' + $scope.cust_number +'&status=2' + '&rejectedPage=' + $scope.rejct_pages +'&remarks=' + $scope.user_comment;


        //console.log(data);
        //alert(" data   "+data);

        $http.get(domain+'/qcstatus?' + data)
            .success(function (data, status, headers, config) {
           //     $scope.url1= data.url;

                console.log("dataa" +$scope.url1);
                location.reload();
                if(data.status == 'error'){
                    // alert('Agent Already Registered');
                    $scope.status = '';
                    $scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

                    $mdDialog.show(
                        $mdDialog.alert()
                            .parent(angular.element(document.querySelector('#popupContainer')))
                            .clickOutsideToClose(true)
                            .title('')
                            .textContent(data.msg)
                            .ariaLabel('Alert Dialog Demo')
                            .ok('OK')
                            .targetEvent(ev)

                    );
                }
            })
            .error(function (data, status, headers, config) {
                alert("failure message: " + JSON.stringify({data: data}));
            });


    };


}]);





routerApp.controller('telecalling',['$scope', '$http','$q','$log','$location','$mdDialog','$mdMedia','$modal' ,function($scope,$http,$q,$log,$location,$mdDialog,$mdMedia,$modal){

    /* $scope.message = {

     text: 'hello world!',
     time: new Date()
     };*/

    var that = this;


 this.picker3 = {
        date: new Date()
    };


    this.openCalendar = function(e, picker) {
       that[picker].open = true;

    };




    $scope.states= [];
    $scope.times = [];
    $scope.date = new Date();
    $scope.mob={};
    $scope.codes=[];
    $scope.statuses = [{csmCode: 1, status:"Delhi"}];
    $scope.statuscode;
    /* Function for get Telecalling Screen */
    $scope.getscreen = function(){
        var dfr = $q.defer();
        $http.get(domain+'/telecallingScreen').
            success(function(data) {
                $scope.date = data.dateList;
                console.log( $scope.date);
                //$scope.statuses = data.statusList;
                //console.log( $scope.statuses);
                dfr.resolve(data);
            }).error(function(error){dfr.reject("Failed");});
        return dfr.promise;
    };

    var GetScreen = function() {
        $scope.getscreen().then(function (data) {

            //alert('getscreen');
            $scope.statuses = data.statusList;
            $scope.states = data.stateList;
            $scope.date = data.dateList;
            $scope.codes = data.paytmmastjson;
            $scope.mob = data.teleData;
        }, function (reason) {
        });
    }
    GetScreen();
    $scope.ab=false;


    $scope.changestatus = function(){

        $scope.ab=true;
        var data = 'status=' + $scope.status.csmCode + '&mobileNo=' + $scope.mob.mobileNo;


       if($scope.status.csmCode == '2-CB')
        {
            var modalInstance = $modal.open({
                templateUrl: 'Telecalling/EnterDateTime.html',
                controller: 'telecalling',
            });

        } else if($scope.status.csmCode == 'CON')
        {
            //$scope.ab=true;
            //alert(data);
            // $scope.screen();
            //$scope.disabled= true;
            // location.reload();
        }

        else {

            var cnf= confirm('Are You Sure?');
            if (cnf == false) {
                location.reload();
            }
            else {

                $http.get(domain+'/postCallingStatus?' + data)
                    .success(function (data, status, headers, config) {
                        $scope.postss = data;
                        console.log( $scope.postss);

                        if(data.status == 'success'){
                            location.reload();
                        }

                    })
                    .error(function (data, status, headers, config) {
                    });

            }
        };

    };





    $scope.screen = function(ev){

        var data = 'mobileNo=' + $scope.mob.mobileNo + '&name=' + $scope.mob.customerName +'&address=' + $scope.codes.address1 + '&area=' + $scope.codes.address2 + '&emailId=' + $scope.codes.email + '&city=' + $scope.codes.city + '&state=' + $scope.codes.state + '&pincode=' + $scope.codes.pincode + '&landmark=' + $scope.land_mark + '&visitDate=' + $scope.visit_date + '&visitTime=' + $scope.visit_time + '&status=' + $scope.status.csmCode;
          console.log(data);
        $http.get(domain+'/postCalling?'+ data)
            .success(function(data, status, headers, config) {
                // alert('jfgkfj');
                $scope.message = data;
                 console.log(data.status);
                if(data.status == 'success'){
                  alert(data.msg);
                    location.reload();
                    /*$scope.status = '';
                    $scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');
                    $mdDialog.show(
                        $mdDialog.alert()
                            .parent(angular.element(document.querySelector('#popupContainer')))
                            .clickOutsideToClose(true)
                            .title('')
                            .textContent(data.msg)
                            .ariaLabel('Alert Dialog Demo')
                            .ok('OK')
                            .targetEvent(ev)

                    );
                    window.setTimeout(function(){
                            location.reload();
                        }, 3000);
*/
                    //ClearForm();

                    //$scope.successTextAlert = "Data Successfully inserted";
                    //window.setTimeout(function(){
                    //    location.reload();
                    //}, 3000);
                }

            else{
                    alert('Record not Inserted due to Visit Date more than 5 Days');
                    location.reload();
                }
            })
            .error(function(data, status, headers, config) {

                alert( "failure message: " + JSON.stringify({data: data}));
            });


    };
    $scope.getdateTime=function(){


        var data ='status=2-CB'+'&mobileNo=' + $scope.mob.mobileNo+'&visit_date=' + $scope.visit_date + '&visit_time=' + $scope.visit_time;


        $http.get(domain+'/postCallingStatus?' + data)
            .success(function (data, status, headers, config) {
                $scope.postss = data;
                console.log( $scope.postss);

                if(data.status == 'success'){
                    location.reload();
                }

            })
            .error(function (data, status, headers, config) {
            });



    };

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

    }


    $scope.calling = function(){            /////for customer calling

        var data = 'customer_number=' + $scope.mob.mobileNo;
        //alert(data);
        $http.get(domain+'/customerCalling?'+ data)
            /*$http.post('http://localhost:8080/paytm/agentRegistration', dataObject)*/
            .success(function(data, status, headers, config) {
                // alert(data);
                $scope.message = data;
                console.log($scope.mob.mobileNo);
                //$scope.getscreen();
                // $location.path('/draft');
            })
            .error(function(data, status, headers, config) {
            });


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
                }
            });
        }
    }
});



/*routerApp.directive('allowPattern', [allowPatternDirective]);

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
}*/


routerApp.controller('Ctrl',['$scope', '$http','$q','$log', function($scope,$http,$q,$log){

    $scope.send = function(){

        alert(jgndfjg);
    };
}]);
routerApp.directive("datepicker", function () {
    return {
        restrict: "A",
        require: "ngModel",
        link: function (scope, elem, attrs, ngModelCtrl) {
            var updateModel = function (dateText) {
                // call $apply to bring stuff to angular model
                scope.$apply(function () {
                    ngModelCtrl.$setViewValue(dateText);
                });
            };

            var options = {
                dateFormat: "dd/mm/yy",
                // handle jquery date change
                onSelect: function (dateText) {
                    updateModel(dateText);
                }

            };
            elem.datepicker(options);
        }
    }
});

routerApp.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

routerApp.controller('myCtrl', ['$scope', '$http', 'FileProductUploadService','$mdDialog','$mdMedia', function ($scope, $http, FileProductUploadService,$mdDialog, $mdMedia) {

    $scope.Message = '';
    $scope.FileInvalidMessage = '';
    $scope.SelectedFileForUpload = null;
    $scope.FileDescription = '';
    $scope.IsFormSubmitted = false;
    $scope.IsFileValid = false;
    $scope.IsFormValid = false;
    $scope.selectedProduct = {};
    $scope.$watch("f1.$valid", function (isValid) {
        $scope.IsFormValid = isValid;

    });
    $scope.checkFileValid = function (file) {
        var isValid = true;
        $scope.IsFileValid = isValid;
    };

    $scope.selectedFileforUpload = function (file) {
        $scope.SelectedFileForUpload = file[0];
    };

    $scope.SaveFile = function (ev) {


        $scope.IsFormSubmitted = true;
        $scope.Message = '';
        $scope.checkFileValid($scope.SelectedFileForUpload);

        if ($scope.IsFormValid && $scope.IsFileValid) {
            FileProductUploadService.UploadFile($scope.SelectedFileForUpload).then(function (d) {

                var confirm = $mdDialog.confirm()
                    // .title('Would you like to delete your debt?')
                    .textContent(d.data.Message)
                    .ariaLabel('Lucky day')
                    .targetEvent(ev)
                    .ok('OK')
                // .cancel('Cancel');
                $mdDialog.show(confirm).then(function() {
                    $scope.status = 'You decided to get rid of your debt.';
                    $scope.rejectReport = d.data.rejectedRecord
                    window.setTimeout(function(){
                        $scope.exportToExcel();
                        //location.reload();

                    }, 2000)
                }, function() {
                    $scope.status = 'You decided to keep your debt.';
                });
                ClearForm();

            }, function (err) {


                var confirm = $mdDialog.confirm()
                    // .title('Would you like to delete your debt?')
                    .textContent(err)
                    .ariaLabel('Lucky day')
                    .targetEvent(ev)
                    .ok('OK')
                // .cancel('Cancel');
                $mdDialog.show(confirm).then(function() {
                    $scope.status = 'You decided to get rid of your debt.';
                }, function() {
                    $scope.status = 'You decided to keep your debt.';
                });

            });
        }
        else
        {
            $scope.Message = 'all the fields are required';
        }
    };

    function ClearForm() {
        $scope.FileDescription = '';
        angular.forEach(angular.element("input[type='file']"), function (inputElem) {
            angular.element(inputElem).val(null);
        });

        $scope.IsFormSubmitted = false;
        $scope.description = '';
        $scope.SelectedFileForUpload = null;

    }

    $scope.files = [];

    $scope.$on("fileSelected", function (event, args) {
        $scope.$apply(function () {
            //add the file object to the scope's files collection
            $scope.files.push(args.file);
        });
    });


    $scope.exportToExcel=function(){// ex: '#my-table'
        var blob = new Blob([document.getElementById('exportable').innerHTML], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
        });
        saveAs(blob, "Report.xls");
    }



}]).factory('FileProductUploadService', function ($http, $q) {

    var fac = {};

    fac.UploadFile = function (file) {

        var formData = new FormData();
        formData.append("file", file);

        var defer = $q.defer();
        $http.post(domain+"/upload", formData, {
            withCredentials: true,
            headers: { "Content-Type": undefined },
            transformRequest: angular.identity
        }).then(
            function (d) {
                defer.resolve(d);
            },function (err) {
                defer.reject("File Upload Failed");
            });
        return defer.promise;
    }

    return fac;
});

routerApp.controller('DateTimeController', ['$scope', function($scope) {

    var that = this;

    // date picker
    this.picker1 = {
        date: new Date('2015-03-01T00:00:00Z'),
        datepickerOptions: {
            showWeeks: false,
            startingDay: 1,
            dateDisabled: function(data) {
                return (data.mode === 'day' && (new Date().toDateString() == data.date.toDateString()));
            }
        }
    };

    // time picker
    this.picker2 = {
        date: new Date('2015-03-01T12:30:00Z'),
        timepickerOptions: {
            readonlyInput: false,
            showMeridian: false
        }
    };

    // date and time picker
    this.picker3 = {
        date: new Date()
    };

    // min date picker
    this.picker4 = {
        date: new Date(),
        datepickerOptions: {
            maxDate: null
        }
    };

    // max date picker
    this.picker5 = {
        date: new Date(),
        datepickerOptions: {
            minDate: null
        }
    };

    // set date for max picker, 10 days in future
    this.picker5.date.setDate(this.picker5.date.getDate() + 10);

    // global config picker
    this.picker6 = {
        date: new Date()
    };

    // dropdown up picker
    this.picker7 = {
        date: new Date()
    };

    // view mode picker
    this.picker8 = {
        date: new Date(),
        datepickerOptions: {
            mode: 'year',
            minMode: 'year',
            maxMode: 'year'
        }
    };

    // dropdown up picker
    this.picker9 = {
        date: null
    };

    // min time picker
    this.picker10 = {
        date: new Date('2016-03-01T09:00:00Z'),
        timepickerOptions: {
            max: null
        }
    };

    // max time picker
    this.picker11 = {
        date: new Date('2016-03-01T10:00:00Z'),
        timepickerOptions: {
            min: null
        }
    };

    // button bar
    this.picker12 = {
        date: new Date(),
        buttonBar: {
            show: true,
            now: {
                show: true,
                text: 'Now!'
            },
            today: {
                show: true,
                text: 'Today!'
            },
            clear: {
                show: false,
                text: 'Wipe'
            },
            date: {
                show: true,
                text: 'Date'
            },
            time: {
                show: true,
                text: 'Time'
            },
            close: {
                show: true,
                text: 'Shut'
            }
        }
    };

    // when closed picker
    this.picker13 = {
        date: new Date(),
        closed: function(args) {
            that.closedArgs = args;
        }
    };

    // saveAs - ISO
    this.picker14 = {
        date: new Date().toISOString()
    }

    this.openCalendar = function(e, picker) {
        that[picker].open = true;
    };

    // watch min and max dates to calculate difference
    var unwatchMinMaxValues = $scope.$watch(function() {
        return [that.picker4, that.picker5, that.picker10, that.picker11];
    }, function() {
        // min max dates
        that.picker4.datepickerOptions.maxDate = that.picker5.date;
        that.picker5.datepickerOptions.minDate = that.picker4.date;

        if (that.picker4.date && that.picker5.date) {
            var diff = that.picker4.date.getTime() - that.picker5.date.getTime();
            that.dayRange = Math.round(Math.abs(diff/(1000*60*60*24)))
        } else {
            that.dayRange = 'n/a';
        }

        // min max times
        that.picker10.timepickerOptions.max = that.picker11.date;
        that.picker11.timepickerOptions.min = that.picker10.date;
    }, true);


    // destroy watcher
    $scope.$on('$destroy', function() {
        unwatchMinMaxValues();
    });

}]);







routerApp.controller('logout', ['$scope','$http','$window', function($scope, $http, $window) {
    $scope.logout = function () {
        $http.get(domain+'/logout').success(function (data, status, headers, config) {
            // alert('daata');
            $scope.msg = data;
            console.log(data);
            if(data.status == 'success') {
                $window.location.href = '/paytm/#/auth';
            }
        });
    };
}]);

routerApp.controller('report',['$scope', '$http','$q','$log', 'ExportService' ,function($scope,$http,$q,$log, ExportService){
    //$scope.reports =[];
    $scope.getreport = function(){
        var dfr = $q.defer();
        $http.get(domain+'/getReportsType').
            success(function(data) {
                dfr.resolve(data);
            }).error(function(error){dfr.reject("Failed");});
        return dfr.promise;
    };
    $scope.getreport().then(function(data){
        //console.log('data:   '+data);
        $scope.reports= data.reportTypes;
        console.log($scope.reports);
    }, function(reason) {
        console.log('Error:   '+reason);
    });

    $scope.date = '';
    $scope.date1 ='';
    $scope.report1 ='';
    $scope.message ={};

    $scope.send = function($event) {
        if ($scope.date.length == 0 || $scope.date == undefined) {
            //alert('Agent Name is not valid');
            $event.preventDefault();
        }
        else if ($scope.date1.length == 0 || $scope.date1 == undefined) {
            // alert('Agent Code is not valid');
            $event.preventDefault();
        }
        else if ($scope.report1.length == 0 || $scope.report1 == undefined) {
            // alert('Agent Code is not valid');
            $event.preventDefault();
        }
        else {

            var data = 'from=' + $scope.date + '&to=' + $scope.date1 + '&type=' + $scope.report1.reportName;
            ExportService.SendData(data).then(function(result){
                $scope.message = result.data;
                if($scope.report1.reportName == 'KycMis')    ////////////function for kycMic
                {
                    console.log('KycMis '+ $scope.report1.reportName);
                    window.setTimeout(function(){
                        console.log($scope.message);
                        $scope.exportToExcelMis();
                        location.reload();

                    }, 3000);
                }
               else if($scope.report1.reportName == 'KycData') {    ////////////function for KycData

                    window.setTimeout(function(){
                        console.log($scope.message);
                        $scope.exportToExcelKycData();
                        location.reload();

                    }, 3000);

                }
                else if($scope.report1.reportName == 'Telecalling Output') {   ////////////function for TeleCallingOutput

                    window.setTimeout(function(){
                        console.log($scope.message);
                        $scope.exportToExcelOutput();
                        location.reload();

                    }, 3000);

                }
                else if($scope.report1.reportName == 'TeleCalling'){                                          ////////////function for Tellicalling

                    window.setTimeout(function(){
                        console.log($scope.message);
                        $scope.exportToExcel();
                        location.reload();

                    }, 3000);

                }
                // $scope.successTextAlert = 'Data Submit Successfully ';
            }, function(err){
                console.log(err);
            }).catch(function(err){
                console.log(err);
            }).finally(function(){
                //window.setTimeout(function(){
                //
                //        $scope.exportToExcel();
                //        location.reload();
                //
                //}, 3000);
            })

        };
    }
    $scope.exportToExcel=function(){// ex: '#my-table'    ///////////////////report for telecalling
        var blob = new Blob([document.getElementById('exportable').innerHTML], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
        });
        saveAs(blob, "Report.xls");
    }
    $scope.exportToExcelMis=function(){// ex: '#my-table'    ///////////////////report for KycMis
        var blob = new Blob([document.getElementById('exportableMis').innerHTML], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
        });
        saveAs(blob, "Report.xls");
    }
    $scope.exportToExcelKycData=function(){// ex: '#my-table'    ///////////////////report for KycMis
        var blob = new Blob([document.getElementById('exportableKycData').innerHTML], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
        });
        saveAs(blob, "Report.xls");
    }
    $scope.exportToExcelOutput=function(){// ex: '#my-table'    ///////////////////report for KycMis
        var blob = new Blob([document.getElementById('exportableOutput').innerHTML], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
        });
        saveAs(blob, "Report.xls");
    }

}]).factory('ExportService', function ($http, $q) {

    var fac = {};

    fac.SendData = function (data) {

        var defer = $q.defer();
        $http.get(domain+'/getReports?' + data).then(function (data) {
            defer.resolve(data);
        },function (errData) {
            defer.reject("failure message: " + JSON.stringify({data: errData}));
        });
        return defer.promise;
    }

    return fac;
});
