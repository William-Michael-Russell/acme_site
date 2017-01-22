(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThGUIDetailController', ThGUIDetailController);

    ThGUIDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ThGUI'];

    function ThGUIDetailController($scope, $rootScope, $stateParams, entity, ThGUI) {
        var vm = this;
        vm.thGUI = entity;
        
        var unsubscribe = $rootScope.$on('acmeSiteApp:thGUIUpdate', function(event, result) {
            vm.thGUI = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
