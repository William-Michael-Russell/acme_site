(function() {
    'use strict';
    angular
        .module('acmeSiteApp')
        .factory('ThScrollingView', ThScrollingView);

    ThScrollingView.$inject = ['$resource'];

    function ThScrollingView ($resource) {
        var resourceUrl =  'api/th-scrolling-views/:id';

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
