(function() {
    'use strict';
    angular
        .module('acmeSiteApp')
        .factory('ThPhoneInput', ThPhoneInput);

    ThPhoneInput.$inject = ['$resource'];

    function ThPhoneInput ($resource) {
        var resourceUrl =  'api/th-phone-inputs/:id';

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
