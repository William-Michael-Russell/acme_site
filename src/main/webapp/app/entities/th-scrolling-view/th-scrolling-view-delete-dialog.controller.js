(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThScrollingViewDeleteController',ThScrollingViewDeleteController);

    ThScrollingViewDeleteController.$inject = ['$uibModalInstance', 'entity', 'ThScrollingView'];

    function ThScrollingViewDeleteController($uibModalInstance, entity, ThScrollingView) {
        var vm = this;
        vm.thScrollingView = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ThScrollingView.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
