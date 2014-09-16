'use strict';

octopusApp.controller('PatientController', ['$scope', 'resolvedPatient', 'Patient',
    function ($scope, resolvedPatient, Patient) {

        $scope.patients = resolvedPatient;

        $scope.create = function () {
            Patient.save($scope.patient,
                function () {
                    $scope.patients = Patient.query();
                    $('#savePatientModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.patient = Patient.get({cin: id});
            $('#savePatientModal').modal('show');
        };

        $scope.delete = function (id) {
            Patient.delete({cin: id},
                function () {
                    $scope.patients = Patient.query();
                });
        };

        $scope.clear = function () {
            $scope.patient = {cin: "", name: "", age: ""};
        };
    }]);
