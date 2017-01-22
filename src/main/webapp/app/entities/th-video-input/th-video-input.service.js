(function() {
    'use strict';
    angular
        .module('acmeSiteApp')
        .factory('ThVideoInput', ThVideoInput);

    ThVideoInput.$inject = ['$resource'];

    function ThVideoInput ($resource) {
        var resourceUrl =  'api/th-video-inputs/:id';

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
