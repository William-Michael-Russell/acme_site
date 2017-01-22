(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThImageInputDetailController', ThImageInputDetailController);

    ThImageInputDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'ThImageInput', 'User'];

    function ThImageInputDetailController($scope, $rootScope, $stateParams, DataUtils, entity, ThImageInput, User) {
        var vm = this;
        vm.thImageInput = entity;
        
        var unsubscribe = $rootScope.$on('acmeSiteApp:thImageInputUpdate', function(event, result) {
            vm.thImageInput = result;
        });
        $scope.$on('$destroy', unsubscribe);

        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
    }
})();
