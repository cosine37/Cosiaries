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
		
		$http.get('/whoami').then(function(response){
			$scope.username = response.data.value[0];
		});
		
		$scope.create = function(){
			if ($scope.title == null || $scope.title == ""){
				alert("Title cannot be empty!");
			} else {
				data = {}
				data["title"] = $scope.title;
				data["content"] = $scope.content;
				
				$http({url: "/createpage", method: "POST", params: data}).then(function(response){
					alert("Page named " + $scope.title + " has been created successfully!");
				}, function(response){
					alert(response.status);
				});
			}
		}
		
}]);