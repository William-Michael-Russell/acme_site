(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThPasswordInputDetailController', ThPasswordInputDetailController);

    ThPasswordInputDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ThPasswordInput', 'User'];

    function ThPasswordInputDetailController($scope, $rootScope, $stateParams, entity, ThPasswordInput, User) {
        var vm = this;
        vm.thPasswordInput = entity;
        
        var unsubscribe = $rootScope.$on('acmeSiteApp:thPasswordInputUpdate', function(event, result) {
            vm.thPasswordInput = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
