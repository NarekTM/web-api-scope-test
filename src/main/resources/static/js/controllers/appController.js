(function () {
    angular.module('notesApp')
        .controller('appController', function ($scope, AuthService, $location) {

            $scope.logout = function () {
                AuthService.logout();
                $location.path('/login');
            };

        });
})();
