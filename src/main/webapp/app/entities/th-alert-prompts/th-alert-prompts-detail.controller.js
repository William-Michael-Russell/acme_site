(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThAlertPromptsDetailController', ThAlertPromptsDetailController);

    ThAlertPromptsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ThAlertPrompts'];

    function ThAlertPromptsDetailController($scope, $rootScope, $stateParams, entity, ThAlertPrompts) {
        var vm = this;
        vm.thAlertPrompts = entity;
        
        var unsubscribe = $rootScope.$on('acmeSiteApp:thAlertPromptsUpdate', function(event, result) {
            vm.thAlertPrompts = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
