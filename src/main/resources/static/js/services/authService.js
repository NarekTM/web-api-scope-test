(function () {
    angular.module('notesApp')
        .service('AuthService', function ($http) {
            let loggedUser = JSON.parse(sessionStorage.getItem('user')) || null;

            function login(username, password) {
                return $http.post("api/login", {username: username, password: password}).then(function (response) {
                    loggedUser = response.data;
                    sessionStorage.setItem('user', JSON.stringify(loggedUser));
                    return loggedUser;
                }, function (error) {
                    loggedUser = null;
                    sessionStorage.removeItem('user');
                    return Promise.reject(error);
                });
            }

            function isLoggedIn() {
                return loggedUser != null;
            }

            function logout() {
                loggedUser = null;
                sessionStorage.removeItem('user');
            }

            return {
                login: login,
                isLoggedIn: isLoggedIn,
                logout: logout
            }
        });
})();
