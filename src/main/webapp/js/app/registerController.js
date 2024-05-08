var setUrl = function(d){
	hearder = "http://";
	server = "localhost:8080";
	return header + server + d;
}

var app = angular.module("registerApp", []);
app.controller("registerCtrl", ['$scope', '$window', '$http', '$document',
	function($scope, $window, $http, $document){
	
		$scope.goto = function(d){
			var x = "http://" + $window.location.host;
			$window.location.href = x + "/" + d;
		}
		
		$scope.username = "";
		$scope.email = "";
		$scope.password = "";
		$scope.reenter = "";
		
		validEmail = function(email){
			f = false;
			if (email.includes("@")) f = true;
			return f;
		}
		
		$scope.register = function(){
			if ($scope.username == ""){
				alert("username cannot be empty!");
			} else if ($scope.password == ""){
				alert("password cannot be empty!");
			} else if ($scope.email == ""){
				alert("email cannot be empty!");
			} else if ($scope.password != $scope.reenter){
				alert("passwords do not match");
				$scope.password = "";
				$scope.reenter = "";
			} else if (!validEmail($scope.email)){
				alert("email address not valid!");
			} else {
				var data = {"username": $scope.username, "password": $scope.password, "email": $scope.email};
				$http({url: "/register", method: "POST", params: data}).then(function(response){
					if (response.status == 201){
						alert("registered successfully!");
						$scope.username = "";
						$scope.password = "";
						$scope.reenter = "";
						$scope.email = "";
					} else {
						alert("unknown error");
					}
				},function(response){
					if (response.status == 406){
						alert("user named \"" + $scope.username + "\" alreay exists");
						$scope.username = "";
					} else {
						alert("unknown error");
					}
				})
			}
		}
		
		
		
}]);