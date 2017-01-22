(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThVideoInputDetailController', ThVideoInputDetailController);

    ThVideoInputDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'ThVideoInput', 'User'];

    function ThVideoInputDetailController($scope, $rootScope, $stateParams, DataUtils, entity, ThVideoInput, User) {
        var vm = this;
        vm.thVideoInput = entity;
        
        var unsubscribe = $rootScope.$on('acmeSiteApp:thVideoInputUpdate', function(event, result) {
            vm.thVideoInput = result;
        });
        $scope.$on('$destroy', unsubscribe);

        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
    }
})();
