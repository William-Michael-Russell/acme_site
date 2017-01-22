(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThTextInputDetailController', ThTextInputDetailController);

    ThTextInputDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ThTextInput', 'User'];

    function ThTextInputDetailController($scope, $rootScope, $stateParams, entity, ThTextInput, User) {
        var vm = this;
        vm.thTextInput = entity;
        
        var unsubscribe = $rootScope.$on('acmeSiteApp:thTextInputUpdate', function(event, result) {
            vm.thTextInput = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
