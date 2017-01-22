(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThGUIDialogController', ThGUIDialogController);

    ThGUIDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ThGUI'];

    function ThGUIDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ThGUI) {
        var vm = this;
        vm.thGUI = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('acmeSiteApp:thGUIUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.thGUI.id !== null) {
                ThGUI.update(vm.thGUI, onSaveSuccess, onSaveError);
            } else {
                ThGUI.save(vm.thGUI, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
