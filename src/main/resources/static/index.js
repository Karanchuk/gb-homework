var app = angular.module('app', []);
app.controller('indexController', function ($scope, $http) {

    const contextPath = 'http://localhost:8080/api/v1';

    $scope.sortCustomer = function () {
        $http.get(contextPath + '/customer/sort')
            .then(function (resp) {
                $scope.Customers = resp.data
            });
    };

    $scope.filterCustomer = function (){
        $http.get(contextPath + '/customer?name=' + $scope.CustomerFilter)
            .then(function (resp) {
                $scope.Customers = resp.data
            })
    }

    $scope.saveCustomer = function () {
        $http.post(contextPath + '/customer', $scope.NewCustomer)
            .then(function (resp) {
                $scope.NewCustomer.name = ''
                $scope.fillTable()
            });
    };

    $scope.saveProduct = function () {
        $http.post(contextPath + '/product', $scope.NewProduct)
            .then(function (resp) {
                $scope.NewProduct.title = ''
                $scope.NewProduct.cost = 0
                $scope.fillTable()
            });
    };

    $scope.removeProduct = function (id){
        $http.delete(contextPath + '/product/' + id)
            .then(function (resp) {
                $scope.fillTable()
            })
    }

    $scope.removeCustomer = function (id){
        $http.delete(contextPath + '/customer/' + id)
            .then(function (resp) {
                $scope.fillTable()
            })
    }

    $scope.fillTable = function () {
        $http.get(contextPath + '/customer')
            .then(function (resp) {
                $scope.Customers = resp.data
            })
        $http.get(contextPath + '/product')
            .then(function (resp) {
                $scope.Products = resp.data
            })
    };

    $scope.fillTable()

});