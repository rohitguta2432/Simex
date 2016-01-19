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
            controller:'myCtrl'
        })
        .state('telecalling', {
            url: '/telecalling',
            templateUrl: 'telecalling/telecalling.html',
            controller:'telecalling'

        })
        .state('report', {
            url: '/report',
            templateUrl: 'report/report.html',
            controller:'report'

        });

});

routerApp.controller('agentCtrl',['$scope', '$http','$q','$log','$location', function($scope,$http,$q,$log,$location){

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

    $scope.submit = function($event) {



        // $scope.submit = function($event) {
        //alert('fdsfsd');
        if($scope.name.length == 0 || $scope.name == undefined){
            //alert('Agent Name is not valid');
            $event.preventDefault();
        }
        else if($scope.agent_code.length == 0 || $scope.agent_code == undefined){
            // alert('Agent Code is not valid');
            $event.preventDefault(); $event.preventDefault();
        }
        else if( $scope.employee.length == 0 ||  $scope.employee == undefined){
            //  alert('Enter the name Employee');
            $event.preventDefault();
        }
        else if($scope.phone.length == 0 || $scope.phone == undefined){
            // alert('Enter the Phone Number');
            $event.preventDefault();
        }
        else if($scope.circle_office.length == 0 || $scope.circle_office == undefined){
            // alert('Select Circle Office');
            $event.preventDefault();
        }
        else if($scope.spoke_code.length == 0 || $scope.spoke_code == undefined){
            //  alert('Select Spoke Code');
            $event.preventDefault();
        }
        else if($scope.pin_code.length == 0 ||$scope.pin_code == undefined){
            // alert('Enter Pin Number');
            $event.preventDefault();
        }
        else {
           //
            // alert('fdsfsd');

            var pin = $scope.multi_pin;
            if(pin == undefined || pin == 'N')
            {
                pin = 'N';
            }
            var data = 'agent_name=' + $scope.name + '&agent_code=' + $scope.agent_code + '&employee=' + $scope.employee + '&phone=' + $scope.phone + '&circle_office=' + $scope.circle_office + '&spoke_code=' + $scope.spoke_code + '&avl_time=' + $scope.avl_time + '&altr_number=' + $scope.altr_number + '&pin_code=' + $scope.pin_code + '&multi_pin=' + pin + '&email=' + $scope.email;


            console.log(data);
            $http.get('http://localhost:8080/paytm/agentRegistration?' + data)
                /*$http.post('http://localhost:8080/paytm/agentRegistration', dataObject)*/
                .success(function (data, status, headers, config) {
                    $scope.message = data;

                    if(data.status == 'success'){
                        $scope.successTextAlert = "Agent Successfully Registered";
                        window.setTimeout(function(){
                            location.reload();
                        }, 2000);
                    }
                    if(data.status == 'error'){
                        alert('Agent Already Registered');
                    }
                })
                .error(function (data, status, headers, config) {
                    alert("failure message: " + JSON.stringify({data: data}));
                });


        };
    }
}]);



routerApp.controller('telecalling',['$scope', '$http','$q','$log','$location', function($scope,$http,$q,$log,$location){

    /* $scope.message = {
     text: 'hello world!',
     time: new Date()
     };*/

    $scope.states= [];
    $scope.times = [];
    $scope.date = new Date();
    $scope.mob={};
    $scope.codes=[];
    $scope.statuses = [{csmCode: 1, status:"Delhi"}];
    /* Function for get Telecalling Screen */
    $scope.getscreen = function(){
        var dfr = $q.defer();
        $http.get('http://localhost:8080/paytm/telecallingScreen').
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

    $scope.changestatus = function(){
        var data = 'status=' + $scope.status.csmCode + '&mobileNo=' + $scope.mob.mobileNo;

        if($scope.status.csmCode == 'CON')
        {
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

                $http.get('http://localhost:8080/paytm/postCallingStatus?' + data)
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

    $scope.screen = function(){

        var data = 'mobileNo=' + $scope.mob.mobileNo + '&name=' + $scope.mob.customerName +'&address=' + $scope.codes.address1 + '&area=' + $scope.codes.address2 + '&emailId=' + $scope.codes.email + '&city=' + $scope.codes.city + '&state=' + $scope.state.stateCode + '&pincode=' + $scope.codes.pincode + '&landmark=' + $scope.land_mark + '&visitDate=' + $scope.visit_date + '&visitTime=' + $scope.visit_time + '&status=' + $scope.status.csmCode;
        $http.get('http://localhost:8080/paytm/postCalling?'+ data)
            .success(function(data, status, headers, config) {
                // alert('jfgkfj');
                $scope.message = data;
                 console.log(data.status);
                if(data.status == 'success'){
                    $scope.successTextAlert = "Data Successfully inserted";
                    window.setTimeout(function(){
                        location.reload();
                    }, 3000);
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


routerApp.controller('myCtrl', ['$scope', '$http', 'FileProductUploadService', function ($scope, $http, FileProductUploadService) {

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

    $scope.SaveFile = function () {
        $scope.IsFormSubmitted = true;
        $scope.Message = '';
        $scope.checkFileValid($scope.SelectedFileForUpload);

        if ($scope.IsFormValid && $scope.IsFileValid) {
            FileProductUploadService.UploadFile($scope.SelectedFileForUpload).then(function (d) {
                //alert(d.Message);c
                alert(d.data.Message);
                console.log(d.data.Message);
                ClearForm();

            }, function (err) {
                alert(err);
            });
        }else {
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

}]).factory('FileProductUploadService', function ($http, $q) {

    var fac = {};

    fac.UploadFile = function (file) {

        var formData = new FormData();
        formData.append("file", file);

        var defer = $q.defer();
        $http.post("http://localhost:8080/paytm/upload", formData, {
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


routerApp.controller('logout', ['$scope','$http','$window', function($scope, $http, $window) {
    $scope.logout = function () {
        $http.get('http://localhost:8080/paytm/logout').success(function (data, status, headers, config) {
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
        $http.get('http://localhost:8080/paytm/getReportsType').
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
               // $scope.successTextAlert = 'Data Submit Successfully ';
            }, function(err){
                console.log(err);
            }).catch(function(err){
                console.log(err);
            }).finally(function(){
                window.setTimeout(function(){
                    $scope.exportToExcel();
                    location.reload();

                }, 3000);
            })

        };
    }
    $scope.exportToExcel=function(){// ex: '#my-table'
        var blob = new Blob([document.getElementById('exportable').innerHTML], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
        });
        saveAs(blob, "Report.xls");
    }

}]).factory('ExportService', function ($http, $q) {

    var fac = {};

    fac.SendData = function (data) {

        var defer = $q.defer();
        $http.get('http://localhost:8080/paytm/getReports?' + data).then(function (data) {
            defer.resolve(data);
        },function (errData) {
            defer.reject("failure message: " + JSON.stringify({data: errData}));
        });
        return defer.promise;
    }

    return fac;
});
