angular.module("sportsStore")
.constant("dataUrl", "http://47.90.107.224:8080/angular/Products")
.constant("orderUrl", "http://47.90.107.224:8080/angular/Order")
.controller("sportsStoreCtrl", function ($scope, $http, $location, dataUrl, orderUrl, cart) {
    
    $scope.data = {};

    $http.get(dataUrl)
    .success(function(data) {
        $scope.data.products = data.data;
    })
    .error(function(error){
        $scope.data.error = error;
        console.log(error);
    });

    $scope.sendOrder = function(shippingDetails) {
        var order = angular.copy(shippingDetails);
        order.products = cart.getProducts();
        console.log(JSON.stringify(order));

        $http.post(orderUrl, order)
        .success(function (data){
            console.log(data);
            $scope.data.orderId = data;
            cart.getProducts().length = 0;
        })
        .error(function (error) {
            console.log(data);
            $scope.data.orderError = error;
        })
        .finally(function(){
            $location.path("/complete");
        });
    }
        
});