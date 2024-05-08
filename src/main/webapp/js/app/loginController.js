var setUrl = function(d){
	hearder = "http://";
	server = "localhost:8080";
	return header + server + d;
}

var app = angular.module("loginApp", []);
app.controller("loginCtrl", ['$scope', '$window', '$http', '$document',
	function($scope, $window, $http, $document){
	
		$scope.goto = function(d){
			var x = "http://" + $window.location.host;
			$window.location.href = x + "/" + d;
			
			
		}
		
		$scope.encrypted = "";
		
		$scope.login = function(){
			var u = $scope.username;
			var p = $scope.password;
			
			if (p == null || u == null || p == "" || u == ""){
				alert("Username and/or password cannot be empty!");
				return;
			}
			
			var data = {"username": u, "password": p};
			$http({url: "/login", method: "POST", params: data}).then(function(response){
				$scope.goto('index');
			}, function(response){
				if (response.status == 401){
					alert("invalid credentials");
					$scope.username = "";
					$scope.password = "";
				} else {
					alert("unknown error");
				}
			});
		}
		
}]);