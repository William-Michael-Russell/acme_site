(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThScrollingViewDetailController', ThScrollingViewDetailController);

    ThScrollingViewDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ThScrollingView'];

    function ThScrollingViewDetailController($scope, $rootScope, $stateParams, entity, ThScrollingView) {
        var vm = this;
        vm.thScrollingView = entity;
        
        var unsubscribe = $rootScope.$on('acmeSiteApp:thScrollingViewUpdate', function(event, result) {
            vm.thScrollingView = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
