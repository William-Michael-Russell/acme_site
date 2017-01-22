(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThScrollingViewDialogController', ThScrollingViewDialogController);

    ThScrollingViewDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ThScrollingView'];

    function ThScrollingViewDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ThScrollingView) {
        var vm = this;
        vm.thScrollingView = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('acmeSiteApp:thScrollingViewUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.thScrollingView.id !== null) {
                ThScrollingView.update(vm.thScrollingView, onSaveSuccess, onSaveError);
            } else {
                ThScrollingView.save(vm.thScrollingView, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
