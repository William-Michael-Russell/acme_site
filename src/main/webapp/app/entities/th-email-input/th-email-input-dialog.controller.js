(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThEmailInputDialogController', ThEmailInputDialogController);

    ThEmailInputDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ThEmailInput', 'User'];

    function ThEmailInputDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ThEmailInput, User) {
        var vm = this;
        vm.thEmailInput = entity;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('acmeSiteApp:thEmailInputUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.thEmailInput.id !== null) {
                ThEmailInput.update(vm.thEmailInput, onSaveSuccess, onSaveError);
            } else {
                ThEmailInput.save(vm.thEmailInput, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
