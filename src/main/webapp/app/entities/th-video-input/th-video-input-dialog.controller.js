(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThVideoInputDialogController', ThVideoInputDialogController);

    ThVideoInputDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'ThVideoInput', 'User'];

    function ThVideoInputDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, ThVideoInput, User) {
        var vm = this;
        vm.thVideoInput = entity;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('acmeSiteApp:thVideoInputUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.thVideoInput.id !== null) {
                ThVideoInput.update(vm.thVideoInput, onSaveSuccess, onSaveError);
            } else {
                ThVideoInput.save(vm.thVideoInput, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.setVideoInputField = function ($file, thVideoInput) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        thVideoInput.videoInputField = base64Data;
                        thVideoInput.videoInputFieldContentType = $file.type;
                    });
                });
            }
        };

        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
    }
})();
