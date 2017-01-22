(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThNumericInputDeleteController',ThNumericInputDeleteController);

    ThNumericInputDeleteController.$inject = ['$uibModalInstance', 'entity', 'ThNumericInput'];

    function ThNumericInputDeleteController($uibModalInstance, entity, ThNumericInput) {
        var vm = this;
        vm.thNumericInput = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ThNumericInput.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
