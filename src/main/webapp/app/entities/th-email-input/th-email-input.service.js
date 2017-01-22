(function() {
    'use strict';
    angular
        .module('acmeSiteApp')
        .factory('ThEmailInput', ThEmailInput);

    ThEmailInput.$inject = ['$resource'];

    function ThEmailInput ($resource) {
        var resourceUrl =  'api/th-email-inputs/:id';

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
