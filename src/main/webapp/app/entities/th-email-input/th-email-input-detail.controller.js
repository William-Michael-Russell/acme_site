(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThEmailInputDetailController', ThEmailInputDetailController);

    ThEmailInputDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ThEmailInput', 'User'];

    function ThEmailInputDetailController($scope, $rootScope, $stateParams, entity, ThEmailInput, User) {
        var vm = this;
        vm.thEmailInput = entity;
        
        var unsubscribe = $rootScope.$on('acmeSiteApp:thEmailInputUpdate', function(event, result) {
            vm.thEmailInput = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
