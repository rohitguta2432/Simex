routerApp.filter('mainfilter',mainCtrl);
function mainCtrl(){

    return function(agents,selectedReference){
        var filtered=[];
var details=$scope.manual;
        angular.forEach(agents,function(agents){
            if(agents.customerid!=null){
                filtered.push(agents);
            }
        })

        return filtered;
    }

}
