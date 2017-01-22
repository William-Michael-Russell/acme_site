(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('th-drop-down-input', {
            parent: 'entity',
            url: '/th-drop-down-input?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThDropDownInputs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-drop-down-input/th-drop-down-inputs.html',
                    controller: 'ThDropDownInputController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('th-drop-down-input-detail', {
            parent: 'entity',
            url: '/th-drop-down-input/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThDropDownInput'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-drop-down-input/th-drop-down-input-detail.html',
                    controller: 'ThDropDownInputDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ThDropDownInput', function($stateParams, ThDropDownInput) {
                    return ThDropDownInput.get({id : $stateParams.id});
                }]
            }
        })
        .state('th-drop-down-input.new', {
            parent: 'th-drop-down-input',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-drop-down-input/th-drop-down-input-dialog.html',
                    controller: 'ThDropDownInputDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dropDownInputField: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('th-drop-down-input', null, { reload: true });
                }, function() {
                    $state.go('th-drop-down-input');
                });
            }]
        })
        .state('th-drop-down-input.edit', {
            parent: 'th-drop-down-input',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-drop-down-input/th-drop-down-input-dialog.html',
                    controller: 'ThDropDownInputDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ThDropDownInput', function(ThDropDownInput) {
                            return ThDropDownInput.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-drop-down-input', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('th-drop-down-input.delete', {
            parent: 'th-drop-down-input',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-drop-down-input/th-drop-down-input-delete-dialog.html',
                    controller: 'ThDropDownInputDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ThDropDownInput', function(ThDropDownInput) {
                            return ThDropDownInput.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-drop-down-input', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
