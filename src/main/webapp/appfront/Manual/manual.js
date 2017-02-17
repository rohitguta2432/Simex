routerApp.controller('manualController',mainCtrl);
function mainCtrl($http,$modal,$scope,$rootScope,$location,$filter) {
    var ctx = this;
    $http.get(domain + '/leaddetails')
        .success(function (data, status, headers, config) {
            ctx.manual = data.agentLeads;
            $scope.do='Re-Assign';
            location.reload;
        })
        .error(function (data, status, headers, config) {
            alert('data not found');
        })

    ctx.searchFields=function(){

    }

    $scope.changeAgent = function (agentCode, customerid) {
        var customerid = customerid;
        var agentCode=agentCode;
        var scope = $rootScope.$new();
        scope.customerid = customerid;
        scope.agentCode=agentCode;
        scope.params={agentcode:agentCode}
        var modalInstance = $modal.open({
            scope: scope,
            templateUrl: "Manual/AssignAgent.html",
            controller: 'manualController'
        });
    }

    $scope.getAgentCode = function () {
        $scope.agentCode=$scope.params.agentcode;
        $http.get(domain + '/availableAgents?agentCodes='+$scope.agentCode)
            .success(function (data, status, headers, config) {
                $scope.AgentCodelist = data.agentEmpCode;
            })
            .error(function (data, status, headers, config) {
                alert('code not found');
            })
    }
    $scope.UpdateAgents=function(AgentCode){
        $scope.agentCode=$scope.params.agentcode;
        var agentCode='agentscodes='+ $scope.AgentCode + '&cust_uid='+$scope.customerid +'&last_agentCode='+$scope.agentCode;
        $http.get(domain + '/UpdateAgentslead?'+agentCode)
            .success(function (data, status, headers, config) {
                $scope.isSelected = !$scope.isSelected;
                $scope.do='Assigned';
              $scope.status='yes';
                location.reload();

            })
            .error(function (data, status, headers, config) {
               /* alert('Not found Data');*/
            })
    }
}