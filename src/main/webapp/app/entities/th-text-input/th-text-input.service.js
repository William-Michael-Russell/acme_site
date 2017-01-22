(function() {
    'use strict';
    angular
        .module('acmeSiteApp')
        .factory('ThTextInput', ThTextInput);

    ThTextInput.$inject = ['$resource'];

    function ThTextInput ($resource) {
        var resourceUrl =  'api/th-text-inputs/:id';

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
