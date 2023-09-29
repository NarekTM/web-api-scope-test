(function () {
    angular.module('notesApp')
        .controller('notesController', function ($scope, $http) {

            $scope.isEditCreateView = false;
            $scope.notes = [];
            $scope.selectedNote = {};

            $scope.saveNote = function () {
                if ($scope.originalNote) {
                    $http.put('/api/notes/' + $scope.originalNote.id, $scope.selectedNote).then(function (response) {
                        for (let i = 0; i < $scope.notes.length; i++) {
                            if ($scope.notes[i].id === $scope.originalNote.id) {
                                $scope.notes[i] = response.data;
                                break;
                            }
                        }
                        $scope.originalNote = null;
                    });
                } else {
                    $http.post('/api/notes', $scope.selectedNote).then(function (response) {
                        $scope.notes.push(response.data);
                    });
                }
                $scope.isEditCreateView = false;
                $scope.selectedNote = {};
            };

            $scope.cancelEdit = function () {
                $scope.isEditCreateView = false;
                $scope.originalNote = null;
                $scope.selectedNote = {};
            };

            $http.get('/api/notes').then(function (response) {
                $scope.notes = response.data;
            });

            $scope.newNoteView = function () {
                $scope.selectedNote = {};
                $scope.isEditCreateView = true;
            };

            $scope.deleteNote = function (note) {
                const isDeleteConfirmed = confirm("Are you sure you want to delete this note?");
                if (isDeleteConfirmed === true) {
                    $http.delete('/api/notes/' + note.id).then(function (response) {
                        let index = $scope.notes.indexOf(note);
                        if (index > -1) {
                            $scope.notes.splice(index, 1);
                        }
                    });
                }
            };

            $scope.viewNote = function (note) {
                $scope.originalNote = note;
                $scope.selectedNote = angular.copy(note);
                $scope.isEditCreateView = true;
            };

        });
})();
