(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThFrameViewDeleteController',ThFrameViewDeleteController);

    ThFrameViewDeleteController.$inject = ['$uibModalInstance', 'entity', 'ThFrameView'];

    function ThFrameViewDeleteController($uibModalInstance, entity, ThFrameView) {
        var vm = this;
        vm.thFrameView = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ThFrameView.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
