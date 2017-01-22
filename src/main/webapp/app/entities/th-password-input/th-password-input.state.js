(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('th-password-input', {
            parent: 'entity',
            url: '/th-password-input?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThPasswordInputs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-password-input/th-password-inputs.html',
                    controller: 'ThPasswordInputController',
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
        .state('th-password-input-detail', {
            parent: 'entity',
            url: '/th-password-input/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThPasswordInput'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-password-input/th-password-input-detail.html',
                    controller: 'ThPasswordInputDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ThPasswordInput', function($stateParams, ThPasswordInput) {
                    return ThPasswordInput.get({id : $stateParams.id});
                }]
            }
        })
        .state('th-password-input.new', {
            parent: 'th-password-input',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-password-input/th-password-input-dialog.html',
                    controller: 'ThPasswordInputDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                passwordInputField: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('th-password-input', null, { reload: true });
                }, function() {
                    $state.go('th-password-input');
                });
            }]
        })
        .state('th-password-input.edit', {
            parent: 'th-password-input',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-password-input/th-password-input-dialog.html',
                    controller: 'ThPasswordInputDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ThPasswordInput', function(ThPasswordInput) {
                            return ThPasswordInput.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-password-input', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('th-password-input.delete', {
            parent: 'th-password-input',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-password-input/th-password-input-delete-dialog.html',
                    controller: 'ThPasswordInputDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ThPasswordInput', function(ThPasswordInput) {
                            return ThPasswordInput.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-password-input', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
