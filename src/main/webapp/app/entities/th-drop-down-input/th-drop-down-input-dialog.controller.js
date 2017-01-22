(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThDropDownInputDialogController', ThDropDownInputDialogController);

    ThDropDownInputDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ThDropDownInput', 'User'];

    function ThDropDownInputDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ThDropDownInput, User) {
        var vm = this;
        vm.thDropDownInput = entity;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('acmeSiteApp:thDropDownInputUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.thDropDownInput.id !== null) {
                ThDropDownInput.update(vm.thDropDownInput, onSaveSuccess, onSaveError);
            } else {
                ThDropDownInput.save(vm.thDropDownInput, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
