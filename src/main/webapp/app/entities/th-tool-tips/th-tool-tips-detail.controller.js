(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThToolTipsDetailController', ThToolTipsDetailController);

    ThToolTipsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ThToolTips'];

    function ThToolTipsDetailController($scope, $rootScope, $stateParams, entity, ThToolTips) {
        var vm = this;
        vm.thToolTips = entity;
        
        var unsubscribe = $rootScope.$on('acmeSiteApp:thToolTipsUpdate', function(event, result) {
            vm.thToolTips = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
