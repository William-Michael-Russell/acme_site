(function() {
    'use strict';
    angular
        .module('acmeSiteApp')
        .factory('ThDropDownInput', ThDropDownInput);

    ThDropDownInput.$inject = ['$resource'];

    function ThDropDownInput ($resource) {
        var resourceUrl =  'api/th-drop-down-inputs/:id';

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
