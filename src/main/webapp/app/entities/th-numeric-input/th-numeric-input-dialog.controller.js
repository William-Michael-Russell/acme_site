(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThNumericInputDialogController', ThNumericInputDialogController);

    ThNumericInputDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ThNumericInput', 'User'];

    function ThNumericInputDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ThNumericInput, User) {
        var vm = this;
        vm.thNumericInput = entity;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('acmeSiteApp:thNumericInputUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.thNumericInput.id !== null) {
                ThNumericInput.update(vm.thNumericInput, onSaveSuccess, onSaveError);
            } else {
                ThNumericInput.save(vm.thNumericInput, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
