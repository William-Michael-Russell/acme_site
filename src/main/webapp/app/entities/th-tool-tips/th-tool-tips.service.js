(function() {
    'use strict';
    angular
        .module('acmeSiteApp')
        .factory('ThToolTips', ThToolTips);

    ThToolTips.$inject = ['$resource'];

    function ThToolTips ($resource) {
        var resourceUrl =  'api/th-tool-tips/:id';

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
