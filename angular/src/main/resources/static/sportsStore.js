angular.module("sportsStore")
.constant("dataUrl", "http://47.90.107.224:8080/angular/Products")
.controller("sportsStoreCtrl", function ($scope, $http, dataUrl) {
    
    $scope.data = {};

    $http.get(dataUrl)
    .success(function(data) {
        $scope.data.products = data.data;
    })
    .error(function(error){
        $scope.data.error = error;
        console.log(error);
    })
        
});