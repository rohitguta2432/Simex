var routerApp = angular.module('routerApp', ['ui.router','ngMaterial','ngLoadingSpinner','ui.bootstrap']);
 //  var domain='http://localhost:8080/paytm';
var domain='/simex';
//var domain='http://172.43.44.203:8080/paytm';
// var domain='http://172.25.38.131:8080/paytm';
//var domain ='http://182.71.212.110:8080/paytm';
//var domain ='http://182.71.212.110:8080/paytm';
// var domain ='http://172.16.16.254:8080/paytm';
//var domain ='http://42.104.108.117:8080/paytm';
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
        }) .state('uploadAgent', {
            url: '/uploadAgent',
            templateUrl: 'AgentUpload/upload_agent.html',
            controller:'uploadAgent'
        })
        .state('telecalling', {
            url: '/telecalling',
            templateUrl: 'Telecalling/telecalling.html',
            controller:'telecalling'

        }) .state('CircleAudit', {
            url: '/CircleAudit',
            templateUrl: 'CircleAudit/circleaudit.html',
            controller:'CircleAudit'

        }).state('HRRegistration', {
            url: '/HRRegistration',
            templateUrl: 'HRRegistration/HRRegistration.html',
            controller:'HRRegistration'

        }).state('downloaddocuments', {
            url: '/downloaddocuments',
            templateUrl: 'DownloadDocuments/downloaddocuments.html',
            controller:'downloaddocuments'

        }).state('report', {
            url: '/report',
            templateUrl: 'report/report.html',
            controller:'report'

        }).state('createBatch', {
            url: '/createBatch',
            templateUrl: 'createBatch/createBatch.html',
            controller:'createBatch'

        }).state('batchIndexing', {
            url: '/batchIndexing',
            templateUrl: 'batchIndexing/batchIndexing.html',
            controller:'batchIndexing'

        }).state('searchBatch', {
            url: '/searchBatch',
            templateUrl: 'SearchBatch/searchbatch.html',
            controller:'searchBatch'

        }).state('companyRegistration', {
            url: '/companyRegistration',
            templateUrl: 'CompanyRegistration/companyRegistration.html',
            controller:'companyRegistration'

        }).state('AoAudit',{
            url:'/AoAudit',
            templateUrl:'AoAudit/aoaudit.html',
            controller:'AoAudit'
        }).state('FormRecieving',{
            url:'/FormRecieving',
            templateUrl:'FormRecieving/formRecieving.html',
            controller:'FormRecieving'
        })
        .state('projectRegistration', {
            url: '/projectRegistration',
            templateUrl: 'projectRegistration/projectRegistration.html',
            controller:'projectRegistration'

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

      var phone1=  $scope.phone;
        var firstphone=phone1.substring(0,1);
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
        else if(firstphone == 0 || firstphone==1 ||firstphone==2 || firstphone == 3 || firstphone==4 || firstphone == 5 || firstphone==6){
             alert('Enter the Valid Phone Number');
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
        }else if($scope.pin_code.length == 0 ||$scope.pin_code == undefined){
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



routerApp.controller('HRRegistration',['$scope', '$http','$q','$log','$location','$mdDialog','$mdMedia', function($scope,$http,$q,$log,$location,$mdDialog,$mdMedia){

    $scope.emp_name='';
    $scope.emp_code='';
    $scope.phone='';
    $scope.emp_Type='';
/*
    $scope.offices = [{id: 1, officet:"Delhi"}];
    $scope.codes = [{id: 1, code:"ANESH11"}];*/
    /* Function for get CircleOffice and Spoke office */
    $scope.getcircleoffice = function(){
        var dfr = $q.defer();
        $http.get(domain+'/getAllCircle').
            success(function(data) {
                dfr.resolve(data);
            }).error(function(error){dfr.reject("Failed");});
        return dfr.promise;
    };
    $scope.getcircleoffice().then(function(data){
        //console.log('data:   '+data);
        $scope.circleofiices= data.circles;
        console.log($scope.circleofiices);
        // console.log( $scope.offices);
    }, function(reason) {
        console.log('Error:   '+reason);
    });



    $scope.errormessage = '';

    $scope.submit = function(ev) {

        var phone2=  $scope.phone;
        var firstphone=phone2.substring(0,1);

        if($scope.emp_name.length == 0 || $scope.emp_name == undefined){
            //alert('Agent Name is not valid');
            ev.preventDefault();
        }
        else if($scope.emp_code.length == 0 || $scope.emp_code == undefined){
            // alert('Agent Code is not valid');
            ev.preventDefault();
        }
        else if( $scope.phone.length == 0 ||  $scope.phone == undefined){
            //  alert('Enter the name Employee');
            ev.preventDefault();
        }
        else if($scope.circlecode.code.length == 0 || $scope.circlecode.code == undefined){
            // alert('Enter the Phone Number');
            ev.preventDefault();
        }
        else if($scope.emp_Type.length == 0 || $scope.emp_Type == undefined){
            //   alert('Enter the Phone Number');
            ev.preventDefault();
        } else if(firstphone == 0 || firstphone==1 ||firstphone==2 || firstphone == 3 || firstphone==4 || firstphone == 5 || firstphone==6){
            alert('Enter the Valid Phone Number');
            ev.preventDefault();
        }

        else {

            var data = 'name=' + $scope.emp_name + '&empcode=' + $scope.emp_code  + '&phone=' + $scope.phone + '&circle_office=' + $scope.circlecode.code + '&empType=' + $scope.emp_Type ;


            console.log(data);
            $http.get(domain+'/registration?' + data)
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


routerApp.controller('createBatch',['$scope', '$http','$q','$log','$location','$mdDialog','$mdMedia', function($scope,$http,$q,$log,$location,$mdDialog,$mdMedia){

    $scope.submit = function(ev) {

         $scope.intoword1=parseInt($scope.inWordTo);
         $scope.infromword1=parseInt($scope.inWordFrom);
        $scope.totaluid=parseInt($scope.intoword1-$scope.infromword1);

        if($scope.inWordTo.length == 0 || $scope.inWordTo == undefined){
            //alert('Agent Name is not valid');
            ev.preventDefault();
        } else if($scope.intoword1< $scope.infromword1){
            alert("inwordTO should be greater than inWordFrom")
            ev.preventDefault();
        } else if($scope.totaluid>99){
            alert("Batch Should be less then 100 form");
            ev.preventDefault();
        }else{

            var data = 'inWordFrom=' + $scope.inWordFrom+'&inWordTo=' + $scope.inWordTo;


        //console.log(data);
        //alert(" data   "+data);

        $http.get(domain+'/createBatch?' + data)
            .success(function (data, status, headers, config) {
                alert("Batch Created Sucessfully  ");
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

        }
    };

    $scope.getinwordfrom = function(ev) {

        //console.log(data);
        //alert(" data   "+data);

        $http.get(domain+'/getInwordFrom')
            .success(function (data, status, headers, config) {
                $scope.inWordFrom= data.inWordFrom;
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


var callInwordfrom=function(){
    $scope.getinwordfrom();
}
    callInwordfrom();


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



routerApp.controller('batchIndexing',['$scope', '$http','$q','$log','$location','$mdDialog','$mdMedia', function($scope,$http,$q,$log,$location,$mdDialog,$mdMedia){
    $scope.uidflag=true;
    $scope.submit = function(ev) {
        if($scope.mobileno.length == 0 || $scope.mobileno == undefined){
            // alert('Enter the Phone Number');
            ev.preventDefault();
        } else if($scope.mobileno.length<10){
            ev.preventDefault();
        } else {

            var data = 'mobileno=' + $scope.mobileno;


            //console.log(data);
            //alert(" data   "+data);

            $http.get(domain + '/indexCustomerValidation?' + data)
                .success(function (data, status, headers, config) {
                    $scope.customerId = data.customerid;
                    $scope.emp_name = data.name;
                    if($scope.emp_name!="" && $scope.emp_name!=undefined){
                        $scope.flag = true;
                        $scope.flag1 = false;
                        $scope.uidflag = false;
                    } else{
                        alert("Mobile Number is not Valid  ");

                    }


                    //      location.reload();

                    if (data.status == 'error') {
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

        }
    };

    $scope.getbatchdetails = function(ev) {
        $scope.flag1=true;
        //console.log(data);
        //alert(" data   "+data);

        $http.get(domain+'/getBatchDetails')
            .success(function (data, status, headers, config) {
                $scope.batchno= data.batchNumber;
                $scope.uid= data.uidnumber;
                $scope.boxno1=data.boxno;

                if($scope.batchno==""){
                    alert("BoxNumber for this batch  "+$scope.boxno1);
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


    var callBatchDetails=function(){
        $scope.getbatchdetails();
    }
    callBatchDetails();


    function ClearForm() {
        //$scope.FileDescription = '';
        angular.forEach(angular.element("input[type='text'],select"), function (inputElem) {
            angular.element(inputElem).val(null);
        });

        $scope.IsFormSubmitted = false;

    }

    $scope.custReject= function(ev) {
        var number= $scope.uid;
        $scope.lastuid1=number.toString().substring(7,10);
        if($scope.user_comment == "" || $scope.user_comment == undefined){
            alert("Please enter Remark for ");
            ev.preventDefault();
        } else if($scope.uniqueuid!=$scope.lastuid1){
            alert("Please enter valid last digit of UID");
            ev.preventDefault();
        }else  if($scope.mobileno.length==0||$scope.mobileno == "" || $scope.mobileno == undefined){
            alert("Please enter valid mobile Number");
            ev.preventDefault();
        }
        else {
            var data = '&mobileno=' + $scope.mobileno + '&status=R' + '&batchno=' + $scope.batchno + '&uid=' + $scope.uid + '&emp_name=' + $scope.emp_name + '&customerId=' + $scope.customerId + '&remarks=' + $scope.user_comment;


            //console.log(data);
            //alert(" data   "+data);

            $http.get(domain + '/updateindexing?' + data)
                .success(function (data, status, headers, config) {
                    //    $scope.url1= data.url;

                    console.log("dataa" + $scope.url1);

                    location.reload();

                    if (data.status == 'error') {
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

        }
    };

    $scope.custAccept= function(ev) {
       var number= $scope.uid;
        $scope.lastuid1=number.toString().substring(7,10);
        if($scope.uniqueuid!=$scope.lastuid1){
           alert("Please enter valid last digit of UID");
            ev.preventDefault();
        }else  if($scope.mobileno.length == 0||$scope.mobileno == "" || $scope.mobileno == undefined){
            ev.preventDefault();
        }else{
            var data = '&mobileno=' + $scope.mobileno +'&status=A' + '&batchno=' + $scope.batchno +'&uid=' + $scope.uid+'&emp_name=' + $scope.emp_name+'&customerId=' + $scope.customerId+'&remarks=' + $scope.user_comment;



        //console.log(data);
        //alert(" data   "+data);

        $http.get(domain+'/updateindexing?' + data)
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

        }
    };



}]);







routerApp.controller('downloaddocuments',['$scope', '$http','$q','$log','$location','$mdDialog','$mdMedia', function($scope,$http,$q,$log,$location,$mdDialog,$mdMedia){

    $scope.lownloadlist={};
    var curDate = new Date();

    $scope.radiodata = {
        group1 : 'radiomobile1'
    };
    $scope.mobileflag1=true;
    $scope.dateflag=false;
    $scope.submit = function(ev) {
        if(($scope.cust_number1 == undefined||$scope.cust_number1 == "")&&(( $scope.fromdate1 == undefined||$scope.fromdate1 =="")||( $scope.todate1 == undefined||$scope.todate1 ==""))){
            alert("please select atlest one filter");
            ev.preventDefault();
        } else
        if((( $scope.fromdate1 != undefined||$scope.fromdate1 !="")||( $scope.todate1 != undefined||$scope.todate1 !=""))){
            if(new Date($scope.todate1) < new Date($scope.fromdate1)){
                alert("To Date should be greater than from date ");
                ev.preventDefault();
        } else{
                var data = 'mobilenumber=' + $scope.cust_number1 + '&fromdate=' + $scope.fromdate1 + '&todate=' + $scope.todate1;


                $http.get(domain + '/downloadList?' + data)
                    .success(function (data, status, headers, config) {
                        $scope.lownloadlist = data;
                        $scope.cust_number1='';
                        $scope.fromdate1='';
                        $scope.todate1='';

                        $scope.flag = true;
                        ClearForm();


                        if (data.status == 'error') {
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
            }

        } else {
            var data = 'mobilenumber=' + $scope.cust_number1 + '&fromdate=' + $scope.fromdate1 + '&todate=' + $scope.todate1;


            $http.get(domain + '/downloadList?' + data)
                .success(function (data, status, headers, config) {
                    $scope.lownloadlist = data;

                    $scope.flag = true;
                    $scope.cust_number1='';
                    $scope.fromdate1='';
                    $scope.todate1='';
                    ClearForm();
                    if (data.status == 'error') {
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
        }

    };

    $scope.downloadpdf = function(list1) {
        $scope.empNumber=list1.customerNo;

            var data = 'mobileNo=' + $scope.empNumber;

        $scope.$emit('download-start');
        window.location.href=domain +"/downloadPdf?mobileNo="+ $scope.empNumber;

        /*$http.get(domain+'/downloadPdf?' + data)
            .success(function (response) {
                $scope.$emit('downloaded', response.data);

                alert("hello response  "+response.data);

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
*/
    };

    $scope.filteroption=function(radiodata1){
        if(radiodata1=="radiomobile1")        {
            $scope.dateflag=false;
            $scope.mobileflag1=true;
        }

        if(radiodata1=="radioDate")        {
            $scope.mobileflag1=false;
            $scope.dateflag=true;
        }


    }
    function ClearForm() {
        //$scope.FileDescription = '';
        angular.forEach(angular.element("input[type='text'],select"), function (inputElem) {
            angular.element(inputElem).val(null);
        });

        $scope.IsFormSubmitted = false;

    }

}]);







//QCInterface changed to CircleAudit By Arpan
routerApp.controller('CircleAudit',['$scope', '$http','$q','$log','$location','$mdDialog','$mdMedia','$sce', function($scope,$http,$q,$log,$location,$mdDialog,$mdMedia, $sce){

    //$scope.cust_number='';
   // $scope.url='';
   // $scope.flag=false;
    var index=0;
    //$scope.state1=true;
    /*$scope.trustSrc = function(src) {
        return $sce.trustAsResourceUrl(src);
    }
*/


    $scope.errormessage = '';


    $scope.auditInit=function(){
        $http.get(domain+'/getCustomer')
            .success(function(data,status,headers,config){
                if(data.auditStatus=='No Images To Audit'){
                    alert('No Images To Audit')
                }else{
                $scope.cust_number=data.mobile;
                $scope.sim_number=data.simNo;
                $scope.cust_name=data.name;
                $scope.cust_address=data.address;
                $scope.scan_id=data.scanID;
                //$scope.image_path=data.imagePath;
                $scope.pathList=data.filePathList;
                $scope.img_count=data.imgCount;
                $scope.image_source=$scope.pathList[index];
                }
            })
            .error(function(data,status,headers,config){
                alert('Error');
            })

    }

    $scope.next=function(){
        var img=$scope.image_source;
        var substringIndex=img.lastIndexOf("_");
        var extensioinIndex=img.lastIndexOf(".");
        var imgNumber=img.substring(substringIndex+1,extensioinIndex);
        if(imgNumber<$scope.img_count){
            index=index+1;
            $scope.image_source=$scope.pathList[index];
        }else{
            index=$scope.img_count-1;
            $scope.image_source=$scope.pathList[index];
        }
    }

    $scope.previous=function(){
        var image=$scope.image_source;
        var substringIndex=image.lastIndexOf("_");
        var extensioinIndex=image.lastIndexOf(".");
        var imageNumber=image.substring(substringIndex+1,extensioinIndex);
        if(imageNumber > 1){
            index=index-1;
            $scope.image_source=$scope.pathList[index];
        }else{
            index=0;
            $scope.image_source=$scope.pathList[index];
        }
    }

    $scope.qcOK= function(ev) {
        var qcStatus='Accepted';
            var data = 'scanId='+$scope.scan_id +
                '&nameMatched='+$scope.name_matched+'&photoMatched='+$scope.photo_matched
                +'&signMatched='+$scope.sign_matched+'&dobMatched='+$scope.dob_matched+
                '&otherReason='+$scope.other_reason+'&qcStatus='+qcStatus;


        //console.log(data);
        //alert(" data   "+data);

        $http.get(domain+'/qcstatus?' + data)
            .success(function (data, status, headers, config) {
                //     $scope.url1= data.url;

                console.log("dataa" +$scope.url1);
                location.reload();
                alert(data.message);
                /*if(data.status == 'error'){
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
                }*/
            })
            .error(function (data, status, headers, config) {
                alert("failure message: " + JSON.stringify({data: data}));
            });


    };

    $scope.qcReject= function(ev) {
        //    alert(" Reject "+ $scope.rejct_pages);
        var qcStatus='Rejected';
            var data = 'scanId='+$scope.scan_id +
                '&nameMatched='+$scope.name_matched+'&photoMatched='+$scope.photo_matched
                +'&signMatched='+$scope.sign_matched+'&dobMatched='+$scope.dob_matched+
                '&otherReason='+$scope.other_reason+'&qcStatus='+qcStatus;


        //console.log(data);
        //alert(" data   "+data);

        $http.get(domain+'/qcstatus?' + data)
            .success(function (data, status, headers, config) {
                //    $scope.url1= data.url;

                console.log("dataa" +$scope.url1);

                location.reload();
                alert(data.message);
                /*if(data.status == 'error'){
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
                }*/
            })
            .error(function (data, status, headers, config) {
                alert("failure message: " + JSON.stringify({data: data}));
            });


    };

    /*$scope.change=function(){

        $http.get(domain+'/getCustomer')
            .success(function (data, status, headers, config) {
                $scope.cust_number=data.mobile;
                $scope.scan_id=data.scanID;
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
*/

 /*   var call=function(){
        $scope.change();
    }

     call();

*/
   /* $scope.submit = function(ev) {
        if($scope.cust_number.length == 0 || $scope.cust_number == undefined){
            ev.preventDefault();
        } else
            var data = 'customer_Number=' + $scope.cust_number +'&scanid=' + $scope.scan_id;


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



*/
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
    /**/


}]);


//QCInterface changed to CircleAudit By Arpan
routerApp.controller('AoAudit',['$scope', '$http','$q','$log','$location','$mdDialog','$mdMedia','$sce', function($scope,$http,$q,$log,$location,$mdDialog,$mdMedia, $sce){


    var index=0;



    $scope.errormessage = '';


    $scope.AoAuditInit=function(){
        $http.get(domain+'/getCustomerDetailsForAoAudit')
            .success(function(data,status,headers,config){
                alert(data.auditStatus);
                if(data.auditStatus=='No Images To Audit'){
                    alert('No Images To Audit')
                }else{
                    $scope.cust_number=data.mobile;
                    $scope.sim_number=data.simNo;
                    $scope.cust_name=data.name;
                    $scope.cust_address=data.address;
                    $scope.scan_id=data.scanID;
                    //$scope.image_path=data.imagePath;
                    $scope.pathList=data.filePathList;
                    $scope.img_count=data.imgCount;
                    $scope.image_source=$scope.pathList[index];
                    $scope.custUID=data.custuid;
                }
            })
            .error(function(data,status,headers,config){
                alert('Error');
            })

    }

    $scope.next=function(){
        var img=$scope.image_source;
        var substringIndex=img.lastIndexOf("_");
        var extensioinIndex=img.lastIndexOf(".");
        var imgNumber=img.substring(substringIndex+1,extensioinIndex);
        if(imgNumber<$scope.img_count){
            index=index+1;
            $scope.image_source=$scope.pathList[index];
        }else{
            index=$scope.img_count-1;
            $scope.image_source=$scope.pathList[index];
        }
    }

    $scope.previous=function(){
        var image=$scope.image_source;
        var substringIndex=image.lastIndexOf("_");
        var extensioinIndex=image.lastIndexOf(".");
        var imageNumber=image.substring(substringIndex+1,extensioinIndex);
        if(imageNumber > 1){
            index=index-1;
            $scope.image_source=$scope.pathList[index];
        }else{
            index=0;
            $scope.image_source=$scope.pathList[index];
        }
    }

    $scope.qcOK= function(ev) {
        var qcStatus='Accepted';
        var data = 'scanId='+$scope.scan_id +
            '&nameMatched='+$scope.name_matched+'&photoMatched='+$scope.photo_matched
            +'&signMatched='+$scope.sign_matched+'&dobMatched='+$scope.dob_matched+
            '&otherReason='+$scope.other_reason+'&qcStatus='+qcStatus+'&custUID='+$scope.custUID;




        $http.get(domain+'/aoAuditStatus?' + data)
            .success(function (data, status, headers, config) {
                //     $scope.url1= data.url;

                console.log("dataa" +$scope.url1);
                location.reload();
                alert(data.message);

            })
            .error(function (data, status, headers, config) {
                alert("failure message: " + JSON.stringify({data: data}));
            });


    };

    $scope.qcReject= function(ev) {
        //    alert(" Reject "+ $scope.rejct_pages);
        var qcStatus='Rejected';
        var data = 'scanId='+$scope.scan_id +
            '&nameMatched='+$scope.name_matched+'&photoMatched='+$scope.photo_matched
            +'&signMatched='+$scope.sign_matched+'&dobMatched='+$scope.dob_matched+
            '&otherReason='+$scope.other_reason+'&qcStatus='+qcStatus+'&custUID='+$scope.custUID;



        $http.get(domain+'/aoAuditStatus?' + data)
            .success(function (data, status, headers, config) {
                //    $scope.url1= data.url;

                console.log("dataa" +$scope.url1);

                location.reload();
                alert(data.message);

            })
            .error(function (data, status, headers, config) {
                alert("failure message: " + JSON.stringify({data: data}));
            });


    };



}]);



routerApp.controller('FormRecieving',['$scope', '$http','$q','$log','$location','$mdDialog','$mdMedia','$sce', function($scope,$http,$q,$log,$location,$mdDialog,$mdMedia, $sce){

    $scope.scanid='';
    $scope.simNumber='';
    $scope.useraddress='';
    $scope.username='';
    $scope.bucketvalue='';
    $scope.statusvalue='';
//$scope.mobFlag=false;
$scope.cust_number='';
    $scope.auditFlag=true;
    $scope.searchDetails=function(){
    if($scope.cust_number.length==10){
       // $scope.mobFlag=true;
        var data='mobNo='+$scope.cust_number;
        $http.get(domain+'/getFormRecievingDetails?'+data)
            .success(function(data,status,headers,config){
                alert(JSON.stringify({data: data}))
                alert($scope.auditFlag);
                $scope.scanid=data.scanID;
                if(data.bucket=='Ao Audit' && data.user_status=='Accepted'){
                    $scope.auditFlag=false;
                }
                alert($scope.auditFlag);
                $scope.simNumber=data.simNum;
                $scope.useraddress=data.user_address;
                $scope.username=data.user_name;
                $scope.bucketvalue=data.bucket;
                $scope.statusvalue=data.user_status;
            }).error(function(data,status,headers,config){
               alert('Error');
            });
    }
    else{
        $scope.auditFlag=true;
        $scope.scanid='';
        $scope.simNumber='';
        $scope.useraddress='';
        $scope.username='';
        $scope.bucketvalue='';
        $scope.statusvalue='';
    }
    }
    $scope.formSubmit=function(){
        var data='scanID='+$scope.scanid;
        $http.get(domain+'/formRecievingSubmit?'+data)
            .success(function(data,status,headers,config){
                alert(data.result);
            }).error(function(data,status,headers,config){
                alert('Error');
            })
    }
}]);



routerApp.controller('telecalling',['$scope', '$http','$q','$log','$location','$mdDialog','$mdMedia','$modal' ,function($scope,$http,$q,$log,$location,$mdDialog,$mdMedia,$modal){

    /* $scope.message = {

     text: 'hello world!',
     time: new Date()
     };*/

  /*  var that = this;


 this.picker3 = {
        date: new Date()
    };


    this.openCalendar = function(e, picker) {
       that[picker].open = true;

    };
*/



/*
    var myInit = function () {
     alert('Hello Angularjs Function ');
    };
    angular.element(document).ready(myInit);*/







    $scope.states= [];
    $scope.times = [];
    $scope.date = new Date();
    $scope.mob={};
    $scope.codes=[];
    $scope.statuses = [{csmCode: 1, status:"Delhi"}];
    $scope.statuscode;
    $scope.status1;
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
                $scope.datecallback = data.dateList1;
                $scope.codes = data.paytmmastjson;
                $scope.mob = data.teleData;
                agentslot();
            }, function (reason) {
            });
    }
    GetScreen();
    $scope.ab=false;

    $scope.callgetscreen = function(){
        location.reload();
    }


    $scope.changestatus = function(){


        $scope.ab=true;
        var data = 'status=' + $scope.status.csmCode + '&mobileNo=' + $scope.codes.customerPhone+ '&customerID='+$scope.codes.cust_uid +'&coStatus='+$scope.codes.coStatus;
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
     alert("hii"+$scope.codes.cust_uid);
        var data = 'mobileNo=' + $scope.codes.customerPhone + '&name=' + $scope.codes.username +'&address=' + $scope.codes.address + '&remarks=' + $scope.codes.remarks + '&emailId=' + $scope.codes.email + '&city=' + $scope.codes.city + '&state=' + $scope.codes.state + '&pincode=' + $scope.codes.pincode + '&simType=' + $scope.codes.simType + '&visitDate=' + $scope.visit_date + '&visitTime=' + $scope.visit_time + '&status=' + $scope.status.csmCode +'&customerID='+$scope.codes.cust_uid + '&coStatus='+$scope.codes.coStatus;
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

        if($scope.visit_date.length == 0 || $scope.visit_date == undefined){
            alert("Please enter Date");
            ev.preventDefault();
        }else if($scope.visit_time.length == 0 || $scope.visit_time == undefined){
            alert("Please enter Time");
            ev.preventDefault();
        }else {
            var data = 'status=2-CB' + '&mobileNo=' + $scope.codes.customerPhone + '&visit_date=' + $scope.visit_date + '&visit_time=' + $scope.visit_time + 'customerID='+$scope.codes.cust_uid+'coStatus='+$scope.codes.coStatus;


            $http.get(domain + '/postCallingStatus?' + data)
                .success(function (data, status, headers, config) {
                    $scope.postss = data;
                    console.log($scope.postss);

                    if (data.status == 'success') {
                        location.reload();
                    }

                })
                .error(function (data, status, headers, config) {
                });

        }

    };




    $scope.getAvlAgentChart=function(){


            var data = 'pincode='+$scope.codes.pincode + 'customerID='+$scope.codes.cust_uid+'coStatus='+$scope.codes.coStatus;

              //    alert("pincode "+$scope.codes.pincode);
            $http.get(domain + '/getAvailableSlot?' + data)
                .success(function (data, status, headers, config) {



                    $scope.dataSets = data.slotList;
                    $scope.dateList2 = data.dateList;
                    $scope.timediff  = data.timedeff;


                })
                .error(function (data, status, headers, config) {
                });


    };

    $scope.selectDateTime=function(time,vstdate,status){

     //   alert(" key "+ key +" value "+value );
        var data = 'mobileNo=' + $scope.codes.customerPhone + '&name=' + $scope.codes.username +'&address=' + $scope.codes.address + '&remarks=' + $scope.codes.remarks + '&emailId=' + $scope.codes.email + '&city=' + $scope.codes.city + '&state=' + $scope.codes.state + '&pincode=' + $scope.codes.pincode + '&landmark=' + $scope.land_mark + '&visitDate=' +vstdate + '&visitTime=' + time+':00' + '&status=' +status + 'customerID='+$scope.codes.cust_uid+'coStatus='+$scope.codes.coStatus;
        console.log(data);
        $http.get(domain+'/postCalling?'+ data)
            .success(function(data, status, headers, config) {
                // alert('jfgkfj');
                $scope.message = data;
                console.log(data.status);
                if(data.status == 'success'){
                    alert(data.msg);
                    location.reload();
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
                     window.setTimeout(function(){
                     location.reload();
                     }, 3000);

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

    }


  var agentslot=function(){
        $scope.getAvlAgentChart();
    }




    $scope.showchart=function(){

        $scope.flagcall=false;
            var modalInstance = $modal.open({
                templateUrl: 'Telecalling/availableAgentChart.html',
                controller: 'telecalling',
            });
    }
    $scope.getTimebyDate=function(){
        var data = 'pincode='+$scope.codes.pincode+'&date='+$scope.visit_date;
        //    alert("pincode "+$scope.codes.pincode);
        $http.get(domain + '/getAvailableSlotByDate?' + data)
            .success(function (data, status, headers, config) {
                $scope.times1 = data.timeList;
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
        var data = 'customer_number=' + $scope.codes.customerPhone + 'customerID='+$scope.codes.cust_uid;
       /*alert(data);*/
        $http.get(domain+'/customerCalling?'+ data)
            /*$http.post('http://localhost:8080/paytm/agentRegistration', dataObject)*/
            .success(function(data, status, headers, config) {
                // alert(data);
                $scope.message = data.msg;
                alert($scope.message);
                //$scope.getscreen();
                // $location.path('/draft');
            })
            .error(function(data, status, headers, config) {
               alert("Unable to Connect Customer due to Network Connectivity");
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




routerApp.controller('Ctrl',['$scope', '$http','$q','$log', function($scope,$http,$q,$log){
    $scope.send = function(){

        /*alert(jgndfjg);*/
    };
}]);

routerApp.directive("datepicker1", function () {
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


routerApp.directive('pdfDownload', ['$parse', function ($parse) {
    return {
        restrict: 'E',
         templateUrl: '/path/to/pdfDownload.tpl.html',
         scope: true,
         link: function(scope, element, attr) {
             var anchor = element.children()[0];

             // When the download starts, disable the link
             scope.$on('download-start', function () {
                 $(anchor).attr('disabled', 'disabled');
             });

             // When the download finishes, attach the data to the link. Enable the link and change its appearance.
             scope.$on('downloaded', function (event, data) {
                 $(anchor).attr({
                     href: 'data:application/pdf;base64,' + data,
                     download: attr.filename
                 })
                     .removeAttr('disabled')
                     .text('Save')
                     .removeClass('btn-primary')
                     .addClass('btn-success');

                 // Also overwrite the download pdf function to do nothing.
                 scope.downloadPdf = function () {
                 };
             });
         }
    }
}
]);



routerApp.controller('myCtrl', ['$scope', '$http', 'FileProductUploadService1','$mdDialog','$mdMedia', function ($scope, $http, FileProductUploadService1,$mdDialog, $mdMedia) {

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
    $scope.SaveFile1 = function (ev) {


        $scope.IsFormSubmitted = true;
        $scope.Message = '';
        $scope.checkFileValid($scope.SelectedFileForUpload);

        if ($scope.IsFormValid && $scope.IsFileValid) {
            FileProductUploadService1.UploadFile($scope.SelectedFileForUpload).then(function (d) {

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



}]).factory('FileProductUploadService1', function ($http, $q) {
    var fac1 = {};

    fac1.UploadFile = function (file) {
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

    return fac1;
});

routerApp.controller('uploadAgent', ['$scope', '$http', 'FileProductUploadService','$mdDialog','$mdMedia', function ($scope, $http, FileProductUploadService,$mdDialog, $mdMedia) {

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
   // alert("factory of agent");
    var fac = {};

    fac.UploadFile = function (file) {

        var formData = new FormData();
        formData.append("file", file);

        var defer = $q.defer();
        $http.post(domain+"/uploadAgent", formData, {
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



routerApp.controller('searchBatch',['$scope', '$http','$q','$log','$location','$mdDialog','$mdMedia', function($scope,$http,$q,$log,$location,$mdDialog,$mdMedia){


    $scope.radiodata = {
        group1 : 'radiomobile'
    };
    $scope.mobileflag=true;

    $scope.submit = function(ev) {
       if(($scope.cust_number== "" || $scope.cust_number == undefined)&&($scope.batchSearch == "" || $scope.batchSearch == undefined)&&($scope.uidnoSearch== "" || $scope.uidnoSearch == undefined)){
           alert("please select atleast one filter");
            ev.preventDefault();
        }else{
            var data = 'cust_number=' + $scope.cust_number+'&batchSearch=' + $scope.batchSearch+'&uidnoSearch=' + $scope.uidnoSearch;


        //console.log(data);
        //alert(" data   "+data);

        $http.get(domain+'/searchIndexing?' + data)
            .success(function (data, status, headers, config) {
                $scope.batchdocumentslist= data;
                $scope.cust_number="";
                $scope.batchSearch="";
                $scope.uidnoSearch="";
                $scope.flag=true;
                ClearForm();

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

       }
    };

    $scope.filteroption=function(radiodata1){
        if(radiodata1=="radiomobile")        {
            $scope.uidflag1=false;
            $scope.batchflag=false;
            $scope.mobileflag=true;
        }

      if(radiodata1=="radiobatch")        {
            $scope.uidflag1=false;
            $scope.mobileflag=false;
            $scope.batchflag=true;
        }
        if(radiodata1=="radioUID")        {
            $scope.batchflag=false;
            $scope.mobileflag=false;
            $scope.uidflag1=true;
        }

    }
    function ClearForm() {
        //$scope.FileDescription = '';
        angular.forEach(angular.element("input[type='text'],select"), function (inputElem) {
            angular.element(inputElem).val(null);
        });

        $scope.IsFormSubmitted = false;

    }

}]);



routerApp.controller('logout', ['$scope','$http','$window', function($scope, $http, $window) {
    $scope.logout = function () {
        $http.get(domain+'/logout').success(function (data, status, headers, config) {
            // alert('daata');
            $scope.msg = data;
            console.log(data);
            if(data.status == 'success') {
                $window.location.href = domain+'/#/auth';
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

        $scope.errMessage = '';
        var curDate = new Date();

        if(new Date($scope.date) > new Date($scope.date1)){
            alert("End Date should be greater than start date ");
          //  $scope.errMessage = 'End Date should be greater than start date';
            return false;
        }/*else if(new Date($scope.date1) > curDate){
          alert("End date should not be greater today.");
       //     $scope.errMessage = 'End date should not be greater today.';
            return false;
        }
*/


        if ($scope.date.length == 0 || $scope.date == undefined) {
            alert('To Date cannot be blank');
            $event.preventDefault();
        }
        else if ($scope.date1.length == 0 || $scope.date1 == undefined) {
            alert('From Date cannot be blank');
            $event.preventDefault();
        }
        else if ($scope.report1.length == 0 || $scope.report1 == undefined) {
            alert('Provide report Type to be generated');
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

                }else if($scope.report1.reportName == 'Call Status'){                                          ////////////function for Tellicalling

                    window.setTimeout(function(){
                        console.log($scope.message);
                        $scope.exportToCallExcel();
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
    $scope.exportToCallExcel=function(){// ex: '#my-table'    ///////////////////report for KycMis
        var blob = new Blob([document.getElementById('exportableCallSatus').innerHTML], {
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
