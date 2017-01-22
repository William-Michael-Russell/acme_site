(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThFrameViewDetailController', ThFrameViewDetailController);

    ThFrameViewDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ThFrameView'];

    function ThFrameViewDetailController($scope, $rootScope, $stateParams, entity, ThFrameView) {
        var vm = this;
        vm.thFrameView = entity;
        
        var unsubscribe = $rootScope.$on('acmeSiteApp:thFrameViewUpdate', function(event, result) {
            vm.thFrameView = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
