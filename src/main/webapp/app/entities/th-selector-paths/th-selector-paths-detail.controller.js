(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThSelectorPathsDetailController', ThSelectorPathsDetailController);

    ThSelectorPathsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ThSelectorPaths'];

    function ThSelectorPathsDetailController($scope, $rootScope, $stateParams, entity, ThSelectorPaths) {
        var vm = this;
        vm.thSelectorPaths = entity;
        
        var unsubscribe = $rootScope.$on('acmeSiteApp:thSelectorPathsUpdate', function(event, result) {
            vm.thSelectorPaths = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
