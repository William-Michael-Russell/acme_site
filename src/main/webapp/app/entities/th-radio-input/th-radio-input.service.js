(function() {
    'use strict';
    angular
        .module('acmeSiteApp')
        .factory('ThRadioInput', ThRadioInput);

    ThRadioInput.$inject = ['$resource'];

    function ThRadioInput ($resource) {
        var resourceUrl =  'api/th-radio-inputs/:id';

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
