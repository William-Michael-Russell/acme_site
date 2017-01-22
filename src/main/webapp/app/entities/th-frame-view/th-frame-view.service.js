(function() {
    'use strict';
    angular
        .module('acmeSiteApp')
        .factory('ThFrameView', ThFrameView);

    ThFrameView.$inject = ['$resource'];

    function ThFrameView ($resource) {
        var resourceUrl =  'api/th-frame-views/:id';

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
