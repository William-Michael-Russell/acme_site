(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThRadioInputDeleteController',ThRadioInputDeleteController);

    ThRadioInputDeleteController.$inject = ['$uibModalInstance', 'entity', 'ThRadioInput'];

    function ThRadioInputDeleteController($uibModalInstance, entity, ThRadioInput) {
        var vm = this;
        vm.thRadioInput = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ThRadioInput.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
