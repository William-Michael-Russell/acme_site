(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThTextInputDeleteController',ThTextInputDeleteController);

    ThTextInputDeleteController.$inject = ['$uibModalInstance', 'entity', 'ThTextInput'];

    function ThTextInputDeleteController($uibModalInstance, entity, ThTextInput) {
        var vm = this;
        vm.thTextInput = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ThTextInput.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
