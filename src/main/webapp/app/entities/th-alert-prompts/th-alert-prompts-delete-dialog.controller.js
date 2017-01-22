(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThAlertPromptsDeleteController',ThAlertPromptsDeleteController);

    ThAlertPromptsDeleteController.$inject = ['$uibModalInstance', 'entity', 'ThAlertPrompts'];

    function ThAlertPromptsDeleteController($uibModalInstance, entity, ThAlertPrompts) {
        var vm = this;
        vm.thAlertPrompts = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ThAlertPrompts.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
