(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThVideoInputDeleteController',ThVideoInputDeleteController);

    ThVideoInputDeleteController.$inject = ['$uibModalInstance', 'entity', 'ThVideoInput'];

    function ThVideoInputDeleteController($uibModalInstance, entity, ThVideoInput) {
        var vm = this;
        vm.thVideoInput = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ThVideoInput.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
