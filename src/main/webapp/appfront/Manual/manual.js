routerApp.controller('manualController',mainCtrl);
function mainCtrl($http,$modal,$scope,$rootScope,$location,$filter) {
    var ctx = this;
    $http.get(domain + '/leadDetails')
        .success(function (data, status, headers, config) {
            ctx.manual = data.agentLeads;
            $scope.do='Re-Assign';
            location.reload;
        })
        .error(function (data, status, headers, config) {
            alert('Unable To get Lead Details');
        })

    ctx.searchFields=function(){

    }

    $scope.getAllocatedDate = function () {
        var customerid = $scope.params.customerid;
        $http.get(domain + '/getLeadAllocationDateList?custId='+customerid)
            .success(function (data, status, headers, config) {
                $scope.dateList = data.allocationDate;
                console.log('Allocated Date : '+$scope.dateList);
            })
            .error(function (data, status, headers, config) {
                alert('Unable To get Allocated Date');
            })
    }

    $scope.getAllocatedTime = function (allocatedDate) {

        $http.get(domain + '/getAllocationTime?allocatedDate='+allocatedDate)
            .success(function (data, status, headers, config) {
                $scope.allocationTimeList = data.timeList;
                console.log('Allocated Time: '+$scope.allocationTimeList);
            })
            .error(function (data, status, headers, config) {
                alert('Unable To get Allocated Time');
            })
    }

    $scope.changeAgent = function (agentCode, customerid, allocatedTime,agentPincode) {
        var customerid = customerid;
        var agentCode=agentCode;
        //console.log('Pincode : '+agentPincode);
        //$scope.getDateList = '';

        /*$http.get(domain + '/getLeadAllocationDateList?custId='+customerid)
            .success(function (data, status, headers, config) {
                $scope.dateList = data.dateList;
                console.log($scope.dateList);
            })
            .error(function (data, status, headers, config) {
                alert('Unable To get Allocated Date');
            })*/

        var scope = $rootScope.$new();
        scope.customerid = customerid;
        scope.agentCode=agentCode;
        scope.agentPincode = agentPincode;
        scope.allocatedTime = allocatedTime;
        scope.dateList = $scope.dateList;
        scope.params={agentcode:agentCode,allocatedTime:allocatedTime,customerid:customerid,allocatedTime:allocatedTime,agentPincode:agentPincode}
        scope.modalInstance = $modal.open({
            scope: scope,
            templateUrl: "Manual/AssignAgent.html",
            controller: 'manualController'
        });
    }

    $scope.IsVisible = false;
    $scope.ShowHide = function () {
        $scope.IsVisible = $scope.IsVisible ? false : true;
    }

    $scope.deAllocateLead = function ( customerid) {
        var customerid = customerid;
        $scope.custId = customerid;
        //console.log('Deallocated CustId : '+customerid);
        var scope = $rootScope.$new();
        scope.customerid = customerid;
        scope.params={customerid:customerid}
        scope.modalInstance = $modal.open({
            scope: scope,
            templateUrl: "Manual/DeAllocateModal.html",
            controller: 'manualController'
        });
    }

    $scope.getAgentCode = function () {
        $scope.agentCode=$scope.params.agentcode;
        console.log($scope.params.allocatedTime);
        $scope.APinCode=$scope.params.agentPincode;
        console.log('Pincode: '+$scope.APinCode);
        $http.get(domain + '/availableAgents?agentCodes='+$scope.agentCode + '&allocatedTime='+$scope.params.allocatedTime
                            + '&agentPincode='+$scope.APinCode)
            .success(function (data, status, headers, config) {
                $scope.AgentCodelist = data.agentCodes;
                console.log($scope.AgentCodelist);
            })
            .error(function (data, status, headers, config) {
                alert('Unable To Load Active Agents');
            })
    }
    $scope.UpdateAgents=function(AgentCode,allocationDate,assignTime){
        $scope.agentCode=$scope.params.agentcode;
        $scope.AgentCode = $scope.selectedAgentCode;
        $scope.allocatedDate = allocationDate;
        $scope.allocationTime = assignTime;
        $scope.oldAllocationTime = $scope.params.allocatedTime;
        console.log($scope.selectedAgentCode + $scope.allocatedDate + $scope.allocationTime);

        $scope.modalInstance.close();
        var agentCode='agentscodes='+ $scope.AgentCode + '&cust_uid='+$scope.customerid +'&last_agentCode='+$scope.agentCode
            +'&allocatedDate='+$scope.allocatedDate +'&allocationTime='+$scope.allocationTime +'&oldAllocationTime='+$scope.oldAllocationTime;
        $http.get(domain + '/UpdateAgentslead?'+agentCode)
            .success(function (data, status, headers, config) {
                $scope.isSelected = !$scope.isSelected;
                $scope.do='Assigned';
              $scope.status='yes';
                $scope.resultMessage = data.result;
                alert($scope.resultMessage);
                location.reload();

            })
            .error(function (data, status, headers, config) {
               /* alert('Not found Data');*/
            })
    }

    $scope.deleteLeadMaually = function () {
        var custId = $scope.params.customerid;
        $scope.modalInstance.close();
        console.log('Deallocated CustId : '+custId);
        $http.get(domain + '/leadDeAllocation?custId='+custId)
            .success(function (data, status, headers, config) {

                $scope.DeallocatedMessage = data.resultMessage;
                alert($scope.DeallocatedMessage);
                location.reload();
            })
            .error(function (data, status, headers, config) {
                alert('Unable To De-Allocate Lead');
            })

    }
    $scope.closeModal = function(){

        $scope.modalInstance.close();
    }
}