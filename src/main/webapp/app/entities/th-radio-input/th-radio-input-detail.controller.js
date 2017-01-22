(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThRadioInputDetailController', ThRadioInputDetailController);

    ThRadioInputDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ThRadioInput', 'User'];

    function ThRadioInputDetailController($scope, $rootScope, $stateParams, entity, ThRadioInput, User) {
        var vm = this;
        vm.thRadioInput = entity;
        
        var unsubscribe = $rootScope.$on('acmeSiteApp:thRadioInputUpdate', function(event, result) {
            vm.thRadioInput = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
