(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThNumericInputDetailController', ThNumericInputDetailController);

    ThNumericInputDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ThNumericInput', 'User'];

    function ThNumericInputDetailController($scope, $rootScope, $stateParams, entity, ThNumericInput, User) {
        var vm = this;
        vm.thNumericInput = entity;
        
        var unsubscribe = $rootScope.$on('acmeSiteApp:thNumericInputUpdate', function(event, result) {
            vm.thNumericInput = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
