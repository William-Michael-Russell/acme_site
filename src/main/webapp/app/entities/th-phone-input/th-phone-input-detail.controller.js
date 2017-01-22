(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThPhoneInputDetailController', ThPhoneInputDetailController);

    ThPhoneInputDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ThPhoneInput', 'User'];

    function ThPhoneInputDetailController($scope, $rootScope, $stateParams, entity, ThPhoneInput, User) {
        var vm = this;
        vm.thPhoneInput = entity;
        
        var unsubscribe = $rootScope.$on('acmeSiteApp:thPhoneInputUpdate', function(event, result) {
            vm.thPhoneInput = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
