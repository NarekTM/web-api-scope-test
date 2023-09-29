(function () {
    angular.module('notesApp')
        .config(['$locationProvider', '$routeProvider',
            function ($locationProvider, $routeProvider) {
                $routeProvider
                    .when('/', {
                        templateUrl: '/partials/notes-view.html',
                        controller: 'notesController'
                    })
                    .when('/login', {
                        templateUrl: '/partials/login.html',
                        controller: 'loginController',
                    })
                    .otherwise('/');
            }
        ])
        .run(['$rootScope', '$location', 'AuthService', function ($rootScope, $location, AuthService) {
            $rootScope.$on('$routeChangeStart', function (event) {

                if ($location.path() === "/login") {
                    return;
                }

                if (!AuthService.isLoggedIn()) {
                    event.preventDefault();
                    $location.path('/login');
                }
            });
        }]);
})();
