(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThSelectorPathsDialogController', ThSelectorPathsDialogController);

    ThSelectorPathsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ThSelectorPaths'];

    function ThSelectorPathsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ThSelectorPaths) {
        var vm = this;
        vm.thSelectorPaths = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('acmeSiteApp:thSelectorPathsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.thSelectorPaths.id !== null) {
                ThSelectorPaths.update(vm.thSelectorPaths, onSaveSuccess, onSaveError);
            } else {
                ThSelectorPaths.save(vm.thSelectorPaths, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
