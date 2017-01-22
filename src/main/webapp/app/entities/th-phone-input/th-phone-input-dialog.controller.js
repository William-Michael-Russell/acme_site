(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThPhoneInputDialogController', ThPhoneInputDialogController);

    ThPhoneInputDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ThPhoneInput', 'User'];

    function ThPhoneInputDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ThPhoneInput, User) {
        var vm = this;
        vm.thPhoneInput = entity;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('acmeSiteApp:thPhoneInputUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.thPhoneInput.id !== null) {
                ThPhoneInput.update(vm.thPhoneInput, onSaveSuccess, onSaveError);
            } else {
                ThPhoneInput.save(vm.thPhoneInput, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
