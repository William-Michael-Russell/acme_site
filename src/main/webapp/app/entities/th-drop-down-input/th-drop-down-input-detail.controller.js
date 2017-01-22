(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThDropDownInputDetailController', ThDropDownInputDetailController);

    ThDropDownInputDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ThDropDownInput', 'User'];

    function ThDropDownInputDetailController($scope, $rootScope, $stateParams, entity, ThDropDownInput, User) {
        var vm = this;
        vm.thDropDownInput = entity;
        
        var unsubscribe = $rootScope.$on('acmeSiteApp:thDropDownInputUpdate', function(event, result) {
            vm.thDropDownInput = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
