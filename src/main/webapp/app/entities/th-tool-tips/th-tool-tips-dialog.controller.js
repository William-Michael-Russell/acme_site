(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThToolTipsDialogController', ThToolTipsDialogController);

    ThToolTipsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ThToolTips'];

    function ThToolTipsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ThToolTips) {
        var vm = this;
        vm.thToolTips = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('acmeSiteApp:thToolTipsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.thToolTips.id !== null) {
                ThToolTips.update(vm.thToolTips, onSaveSuccess, onSaveError);
            } else {
                ThToolTips.save(vm.thToolTips, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
