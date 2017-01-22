(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThAlertPromptsDialogController', ThAlertPromptsDialogController);

    ThAlertPromptsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ThAlertPrompts'];

    function ThAlertPromptsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ThAlertPrompts) {
        var vm = this;
        vm.thAlertPrompts = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('acmeSiteApp:thAlertPromptsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.thAlertPrompts.id !== null) {
                ThAlertPrompts.update(vm.thAlertPrompts, onSaveSuccess, onSaveError);
            } else {
                ThAlertPrompts.save(vm.thAlertPrompts, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
