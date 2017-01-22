(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThDropDownInputDeleteController',ThDropDownInputDeleteController);

    ThDropDownInputDeleteController.$inject = ['$uibModalInstance', 'entity', 'ThDropDownInput'];

    function ThDropDownInputDeleteController($uibModalInstance, entity, ThDropDownInput) {
        var vm = this;
        vm.thDropDownInput = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ThDropDownInput.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
