/**
 * Created by rserafin on 21/01/2014.
 */

var weddingModule = angular.module("wedding", ["ngRoute"]);

weddingModule.config([ '$routeProvider', function ($routeProvider) {
    $routeProvider.when('/login', {
        templateUrl: '/partials/login.html',
        controller: 'LoginCtrl'
    }).when('/:groupId/home',{
        templateUrl: '/partials/home.html',
        controller: 'HomeCtrl',
        resolve: {
            group: "group"
        }
    }).when('/:groupId/calendar',{
        templateUrl: '/partials/calendar.html',
        controller: 'HomeCtrl',
        resolve: {
            group: "group"
        }
    }).when('/:groupId/church',{
        templateUrl: '/partials/church.html',
        controller: 'HomeCtrl',
        resolve: {
            group: "group"
        }
    }).when('/:groupId/dinner',{
        templateUrl: '/partials/dinner.html',
        controller: 'HomeCtrl',
        resolve: {
            group: "group"
        }
    }).when('/:groupId/upload',{
        templateUrl: '/partials/fake.html',
        controller: 'HomeCtrl',
        resolve: {
            group: "group"
        }
    }).when('/:groupId/comment',{
        templateUrl: '/partials/fake.html',
        controller: 'HomeCtrl',
        resolve: {
            group: "group"
        }
    }).when('/:groupId/accomodation',{
        templateUrl: '/partials/fake.html',
        controller: 'HomeCtrl',
        resolve: {
            group: "group"
        }
    }).when('/:groupId/questionnaire',{
        templateUrl: '/partials/fake.html',
        controller: 'HomeCtrl',
        resolve: {
            group: "group"
        }
    }).when("/:groupId/survey/:stageId",{
        templateUrl: "/partials/survey.html",
        controller: 'SurveyCtrl',
        resolve: {
            group: "group"
        }
    }).otherwise({
        redirectTo: '/login'
    });
} ]);

weddingModule.factory("groupService", ["$http","$q", function($http,$q){
    var service = {}
    service.group = null;

    service.loadGroup= function(groupId){
        if (service.group==null || service.group.id != parseInt(groupId)){
            return service.loadGroupFromServer(groupId);
        } else {
            return service.group;
        }
    };

    service.loadGroupFromServer= function(groupId){
        if (parseInt(groupId)!=0){
            return $http.get('/groups/' + groupId)
                .then(function(response){
                    service.group = response.data;
                    return response.data;
                });
        } else {
        	var deferred = $q.defer();
        	
            service.group = {
            	id: 0,
                profile : {
                    id: 0,
                    complete: true
                }
            };
            
            deferred.resolve(service.group);
            return deferred.promise;
        }
    };

    service.saveGroup= function(group){
        return $http.put("/groups/"+ group.id,group)
            .success(function(data){
                service.group = data;
            });
    };

    service.removeUser= function(group,user){
        if (user.id){
            $http.delete("/groups/" + group.id + "/users/" + user.id);
//                .success(function(data){
//                    $scope.group.users = data;
//                });
        }
    }


    return service;
}]);

weddingModule.factory("group", ["$route","groupService", function($route,groupService){
    return groupService.loadGroup($route.current.params.groupId);
}]);

weddingModule.controller("IndexCtrl",["$scope", "$location", function($scope,$location){
	$scope.bodyStyle = function(){
		var parts=$location.path().split('/');
		if (parts.length>2){
			var style = parts[2];
			
			if (parts[2] == "church" || parts[2] == "dinner" || parts[2] == "calendar"){
				style += " static-inner";
			}
			
			return style;
		} else {
			return parts[parts.length-1];
		}
	};
}]);

weddingModule.controller("LoginCtrl",["$scope", "$location", "groupService", function($scope,$location,groupService){
	$scope.access = function(){		
		if ($scope.signinForm.group.$valid){
			groupService
				.loadGroup($scope.groupId)
				.then(function(group){
					if (group.profile.complete){
						$location.path("/" + $scope.groupId + "/home");
					} else {
						$location.path("/" + group.id + "/survey/" + group.profile.stage);
					}
				});
		}
	}
		
}]);

weddingModule.controller("HomeCtrl",["$scope", "$location", "group", function($scope,$location,group){
    $scope.group = group;

    if (!group.profile.complete){
        $location.path("/" + group.id + "/survey/" + group.profile.stage).replace();
    }

    $scope.goTo = function(page){
    	if (page!="questionnaire"){
    		$location.path("/" + $scope.group.id + "/" + page);
    	} else {
    		$location.path("/" + $scope.group.id + "/survey/1");
    	}
	}
    
    $scope.openChurchMap = function(){
    	var os = $.ua.os.name;
    	
    	if (os == 'iOS' || os == 'Mac OS X'){
    		return "http://maps.apple.com/?q=Iglesia+de+San+Juan+del+Hospital";
    	} else if (os == "Android") {
    		return "geo:0,0?q=Iglesia+de+San+Juan+del+Hospital";
    	} else {
    		return "http://maps.google.com/?q=Iglesia+de+San+Juan+del+Hospital";
    	}
    }
    
    $scope.openDinnerMap = function(){
    	var os = $.ua.os.name;
    	
    	if (os == 'iOS' || os == 'Mac OS X'){
    		return "http://maps.apple.com/?q=Calle+102+37+Ribarroja+Valencia";
    	} else if (os == "Android") {
    		return "geo:39.550424,-0.538643";
    	} else {
    		return "http://maps.google.com/?q=Calle+103,+37,+ribarroja";
    	}
    }
    
    $scope.openBusMap = function(){
    	alert("¡Hemos dicho próximamente!")
    }
}]);

weddingModule.controller("SurveyCtrl",["$scope","group","$routeParams","$location","groupService",function($scope, group,$routeParams, $location, groupService){
    $scope.group = group;
    $scope.stage = parseInt($routeParams.stageId);    

    $scope.next = function(){
    	if ($scope.stage==1 && group.profile.participationConfirmed == false){
    		$scope.stage = 6;
    	}else if ($scope.stage==3 && group.profile.origin == 'Valencia'){
    		// Skip accomodation if from valencia
    		$scope.stage = 6;        
    	} else {    		
    		$scope.stage += 1;
    	}
    	
    	$location.path("/" + group.id + "/survey/" + $scope.stage);
    };

    $scope.add = function(){
        group.users.push({
           name: null,
           email: null
        });
    };

    $scope.canAdd = function(){
        if (group.users.length){
            return group.users[group.users.length -1].name;
        } else {
            return true;
        }
    };

    $scope.remove = function(index){
        var user = group.users.splice(index,1)[0];
        service.removeUser(group,user);
    };

    $scope.save = function(){
        group.profile.stage = $scope.stage;
        group.profile.complete = true;

        groupService.saveGroup(group).then(function(data){
            $location.path("/" + group.id + "/home").replace();
        });
    }
}]);