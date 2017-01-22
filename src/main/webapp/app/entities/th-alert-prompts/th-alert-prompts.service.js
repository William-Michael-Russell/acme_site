(function() {
    'use strict';
    angular
        .module('acmeSiteApp')
        .factory('ThAlertPrompts', ThAlertPrompts);

    ThAlertPrompts.$inject = ['$resource'];

    function ThAlertPrompts ($resource) {
        var resourceUrl =  'api/th-alert-prompts/:id';

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
