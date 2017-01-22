(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThPasswordInputDeleteController',ThPasswordInputDeleteController);

    ThPasswordInputDeleteController.$inject = ['$uibModalInstance', 'entity', 'ThPasswordInput'];

    function ThPasswordInputDeleteController($uibModalInstance, entity, ThPasswordInput) {
        var vm = this;
        vm.thPasswordInput = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ThPasswordInput.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
