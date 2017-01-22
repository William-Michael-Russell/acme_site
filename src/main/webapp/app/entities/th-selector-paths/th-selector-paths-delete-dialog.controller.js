(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThSelectorPathsDeleteController',ThSelectorPathsDeleteController);

    ThSelectorPathsDeleteController.$inject = ['$uibModalInstance', 'entity', 'ThSelectorPaths'];

    function ThSelectorPathsDeleteController($uibModalInstance, entity, ThSelectorPaths) {
        var vm = this;
        vm.thSelectorPaths = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ThSelectorPaths.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
