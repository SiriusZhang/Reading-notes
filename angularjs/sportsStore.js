angular.module("sportsStore")
.constant("dataUrl", "http://localhost:8080/angular/Products")
.controller("sportsStoreCtrl", function ($scope, $http, dataUrl) {
    
    $scope.data = {};

    $http.get(dataUrl)
    .success(function(data) {
        $scope.data = data;
    })
    .error(function(error){
        $scope.data.error = error;
        console.log(error);
    })
        
});