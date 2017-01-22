(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('th-numeric-input', {
            parent: 'entity',
            url: '/th-numeric-input?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThNumericInputs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-numeric-input/th-numeric-inputs.html',
                    controller: 'ThNumericInputController',
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
        .state('th-numeric-input-detail', {
            parent: 'entity',
            url: '/th-numeric-input/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThNumericInput'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-numeric-input/th-numeric-input-detail.html',
                    controller: 'ThNumericInputDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ThNumericInput', function($stateParams, ThNumericInput) {
                    return ThNumericInput.get({id : $stateParams.id});
                }]
            }
        })
        .state('th-numeric-input.new', {
            parent: 'th-numeric-input',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-numeric-input/th-numeric-input-dialog.html',
                    controller: 'ThNumericInputDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                numericInputField: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('th-numeric-input', null, { reload: true });
                }, function() {
                    $state.go('th-numeric-input');
                });
            }]
        })
        .state('th-numeric-input.edit', {
            parent: 'th-numeric-input',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-numeric-input/th-numeric-input-dialog.html',
                    controller: 'ThNumericInputDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ThNumericInput', function(ThNumericInput) {
                            return ThNumericInput.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-numeric-input', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('th-numeric-input.delete', {
            parent: 'th-numeric-input',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-numeric-input/th-numeric-input-delete-dialog.html',
                    controller: 'ThNumericInputDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ThNumericInput', function(ThNumericInput) {
                            return ThNumericInput.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-numeric-input', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
