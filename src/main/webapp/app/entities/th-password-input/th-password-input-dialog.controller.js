(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThPasswordInputDialogController', ThPasswordInputDialogController);

    ThPasswordInputDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ThPasswordInput', 'User'];

    function ThPasswordInputDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ThPasswordInput, User) {
        var vm = this;
        vm.thPasswordInput = entity;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('acmeSiteApp:thPasswordInputUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.thPasswordInput.id !== null) {
                ThPasswordInput.update(vm.thPasswordInput, onSaveSuccess, onSaveError);
            } else {
                ThPasswordInput.save(vm.thPasswordInput, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
