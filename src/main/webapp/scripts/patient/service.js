'use strict';

octopusApp.factory('Patient', ['$resource',
    function ($resource) {
        return $resource('app/rest/patients/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    }]);
