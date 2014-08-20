'use strict';

octopusApp
    .config(['$routeProvider', '$httpProvider', '$translateProvider',
        function ($routeProvider, $httpProvider, $translateProvider) {
            $routeProvider
                .when('/patient', {
                    templateUrl: 'views/patients.html',
                    controller: 'PatientController',
                    resolve:{
                        resolvedPatient: ['Patient', function (Patient) {
                            return Patient.query();
                        }]
                    }
                })
        }]);
