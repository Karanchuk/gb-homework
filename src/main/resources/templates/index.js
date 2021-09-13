angular.module('app', []).controller('indexController', function ($scope, $http) {

    const contextPath = 'http://localhost:8080';


    $scope.saveProduct = function () {
        $http.post(contextPath + '/customer', $scope.NewProduct)
            .then(function (resp) {
                $scope.fillTable()
            });
    };

    $scope.fillTable = function () {
        $http.get(contextPath + '/customer')
            .then(function (resp) {
                $scope.Customers = resp.data
            })
    };

    $scope.removeProduct = function (id){
        $http.delete(contextPath + '/customer/'+id)
            .then(function (resp) {
                $scope.fillTable()
            })
    }

    $scope.fillTable()

});