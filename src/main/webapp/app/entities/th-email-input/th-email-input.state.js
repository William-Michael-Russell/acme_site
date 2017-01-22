(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('th-email-input', {
            parent: 'entity',
            url: '/th-email-input?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThEmailInputs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-email-input/th-email-inputs.html',
                    controller: 'ThEmailInputController',
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
        .state('th-email-input-detail', {
            parent: 'entity',
            url: '/th-email-input/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThEmailInput'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-email-input/th-email-input-detail.html',
                    controller: 'ThEmailInputDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ThEmailInput', function($stateParams, ThEmailInput) {
                    return ThEmailInput.get({id : $stateParams.id});
                }]
            }
        })
        .state('th-email-input.new', {
            parent: 'th-email-input',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-email-input/th-email-input-dialog.html',
                    controller: 'ThEmailInputDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                emailInputField: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('th-email-input', null, { reload: true });
                }, function() {
                    $state.go('th-email-input');
                });
            }]
        })
        .state('th-email-input.edit', {
            parent: 'th-email-input',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-email-input/th-email-input-dialog.html',
                    controller: 'ThEmailInputDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ThEmailInput', function(ThEmailInput) {
                            return ThEmailInput.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-email-input', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('th-email-input.delete', {
            parent: 'th-email-input',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-email-input/th-email-input-delete-dialog.html',
                    controller: 'ThEmailInputDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ThEmailInput', function(ThEmailInput) {
                            return ThEmailInput.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-email-input', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
