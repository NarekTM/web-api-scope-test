(function () {
    angular.module('notesApp')
        .controller('loginController', function ($scope, AuthService, $location) {

            $scope.invalidCreds = false;
            $scope.login = {
                username: null,
                password: null
            };

            $scope.login = function () {
                AuthService.login($scope.login.username, $scope.login.password).then(function (user) {
                    $location.path("/");
                }, function (error) {
                    $scope.invalidCreds = true;
                });
            };
        });
})();
