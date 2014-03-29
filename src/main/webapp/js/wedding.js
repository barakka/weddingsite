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
    }).when('/:groupId/church',{
        templateUrl: '/partials/fake.html',
        controller: 'HomeCtrl',
        resolve: {
            group: "group"
        }
    }).when('/:groupId/dinner',{
        templateUrl: '/partials/fake.html',
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

weddingModule.factory("groupService", ["$http", function($http){
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
            service.group = {
                profile : {
                    id: 0,
                    complete: true
                }
            };
            return service.group;
        }
    };

    service.saveGroup= function(){
        return $http.put("/groups/"+ service.group.id,service.group)
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
	$scope.loc = function(){
		var parts=$location.path().split('/');
		return parts[parts.length-1];
	};
}]);

weddingModule.controller("LoginCtrl",["$scope", "$location", function($scope,$location){
	$scope.access = function(){		
		if ($scope.signinForm.group.$valid){
			$location.path("/" + $scope.groupId + "/home");
		}
	}
		
}]);

weddingModule.controller("HomeCtrl",["$scope", "$location", "group", function($scope,$location,group){
    $scope.group = group;

    if (!group.profile.complete){
        $location.path("/" + group.id + "/survey/" + group.profile.stage).replace();
    }

    $scope.goTo = function(page){
		$location.path("/" + $scope.group.id + "/" + page);
	}
}]);

weddingModule.controller("SurveyCtrl",["$scope","group","$routeParams","$location","groupService",function($scope, group,$routeParams, $location, groupService){
    $scope.group = group;
    $scope.stage = parseInt($routeParams.stageId);

    console.log("Stage reset")

    $scope.next = function(){
        $scope.stage += 1;
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

        groupService.saveGroup().then(function(data){
            console.log("saveGroup completed");
            console.log(data);
            $location.path("/" + group.id + "/home").replace();
        });
    }
}]);