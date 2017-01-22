(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('th-text-input', {
            parent: 'entity',
            url: '/th-text-input?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThTextInputs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-text-input/th-text-inputs.html',
                    controller: 'ThTextInputController',
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
        .state('th-text-input-detail', {
            parent: 'entity',
            url: '/th-text-input/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThTextInput'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-text-input/th-text-input-detail.html',
                    controller: 'ThTextInputDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ThTextInput', function($stateParams, ThTextInput) {
                    return ThTextInput.get({id : $stateParams.id});
                }]
            }
        })
        .state('th-text-input.new', {
            parent: 'th-text-input',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-text-input/th-text-input-dialog.html',
                    controller: 'ThTextInputDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                textedInputField: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('th-text-input', null, { reload: true });
                }, function() {
                    $state.go('th-text-input');
                });
            }]
        })
        .state('th-text-input.edit', {
            parent: 'th-text-input',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-text-input/th-text-input-dialog.html',
                    controller: 'ThTextInputDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ThTextInput', function(ThTextInput) {
                            return ThTextInput.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-text-input', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('th-text-input.delete', {
            parent: 'th-text-input',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-text-input/th-text-input-delete-dialog.html',
                    controller: 'ThTextInputDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ThTextInput', function(ThTextInput) {
                            return ThTextInput.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-text-input', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
