(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThToolTipsDeleteController',ThToolTipsDeleteController);

    ThToolTipsDeleteController.$inject = ['$uibModalInstance', 'entity', 'ThToolTips'];

    function ThToolTipsDeleteController($uibModalInstance, entity, ThToolTips) {
        var vm = this;
        vm.thToolTips = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ThToolTips.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
