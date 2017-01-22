(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThRadioInputDialogController', ThRadioInputDialogController);

    ThRadioInputDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ThRadioInput', 'User'];

    function ThRadioInputDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ThRadioInput, User) {
        var vm = this;
        vm.thRadioInput = entity;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('acmeSiteApp:thRadioInputUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.thRadioInput.id !== null) {
                ThRadioInput.update(vm.thRadioInput, onSaveSuccess, onSaveError);
            } else {
                ThRadioInput.save(vm.thRadioInput, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
