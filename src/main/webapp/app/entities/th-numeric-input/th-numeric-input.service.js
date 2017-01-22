(function() {
    'use strict';
    angular
        .module('acmeSiteApp')
        .factory('ThNumericInput', ThNumericInput);

    ThNumericInput.$inject = ['$resource'];

    function ThNumericInput ($resource) {
        var resourceUrl =  'api/th-numeric-inputs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
