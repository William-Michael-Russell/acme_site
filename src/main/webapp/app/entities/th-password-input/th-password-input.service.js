(function() {
    'use strict';
    angular
        .module('acmeSiteApp')
        .factory('ThPasswordInput', ThPasswordInput);

    ThPasswordInput.$inject = ['$resource'];

    function ThPasswordInput ($resource) {
        var resourceUrl =  'api/th-password-inputs/:id';

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
