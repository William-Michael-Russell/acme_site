(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThEmailInputDeleteController',ThEmailInputDeleteController);

    ThEmailInputDeleteController.$inject = ['$uibModalInstance', 'entity', 'ThEmailInput'];

    function ThEmailInputDeleteController($uibModalInstance, entity, ThEmailInput) {
        var vm = this;
        vm.thEmailInput = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ThEmailInput.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
