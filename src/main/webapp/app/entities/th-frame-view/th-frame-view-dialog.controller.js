(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThFrameViewDialogController', ThFrameViewDialogController);

    ThFrameViewDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ThFrameView'];

    function ThFrameViewDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ThFrameView) {
        var vm = this;
        vm.thFrameView = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('acmeSiteApp:thFrameViewUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.thFrameView.id !== null) {
                ThFrameView.update(vm.thFrameView, onSaveSuccess, onSaveError);
            } else {
                ThFrameView.save(vm.thFrameView, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
