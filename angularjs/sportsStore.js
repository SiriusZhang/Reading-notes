angular.module("sportsStore").controller("sportsStoreCtrl", function ($scope) {
    
    $scope.data = {
        products: [
            {name: "product #1", description: "A product", category: "Category #1", price: 100},
            {name: "product #2", description: "A product", category: "Category #2", price: 110},
            {name: "product #3", description: "A product", category: "Category #3", price: 200},
            {name: "product #4", description: "A product", category: "Category #4", price: 202}
        ]
    };
});