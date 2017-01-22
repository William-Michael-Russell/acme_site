(function() {
    'use strict';
    angular
        .module('acmeSiteApp')
        .factory('ThGUI', ThGUI);

    ThGUI.$inject = ['$resource'];

    function ThGUI ($resource) {
        var resourceUrl =  'api/th-guis/:id';

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
