(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThPhoneInputDeleteController',ThPhoneInputDeleteController);

    ThPhoneInputDeleteController.$inject = ['$uibModalInstance', 'entity', 'ThPhoneInput'];

    function ThPhoneInputDeleteController($uibModalInstance, entity, ThPhoneInput) {
        var vm = this;
        vm.thPhoneInput = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ThPhoneInput.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
