(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThGUIDeleteController',ThGUIDeleteController);

    ThGUIDeleteController.$inject = ['$uibModalInstance', 'entity', 'ThGUI'];

    function ThGUIDeleteController($uibModalInstance, entity, ThGUI) {
        var vm = this;
        vm.thGUI = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ThGUI.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
