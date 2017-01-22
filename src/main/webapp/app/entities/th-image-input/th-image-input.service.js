(function() {
    'use strict';
    angular
        .module('acmeSiteApp')
        .factory('ThImageInput', ThImageInput);

    ThImageInput.$inject = ['$resource'];

    function ThImageInput ($resource) {
        var resourceUrl =  'api/th-image-inputs/:id';

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
