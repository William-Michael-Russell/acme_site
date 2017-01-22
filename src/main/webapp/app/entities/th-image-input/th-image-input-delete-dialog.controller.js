(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThImageInputDeleteController',ThImageInputDeleteController);

    ThImageInputDeleteController.$inject = ['$uibModalInstance', 'entity', 'ThImageInput'];

    function ThImageInputDeleteController($uibModalInstance, entity, ThImageInput) {
        var vm = this;
        vm.thImageInput = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ThImageInput.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
