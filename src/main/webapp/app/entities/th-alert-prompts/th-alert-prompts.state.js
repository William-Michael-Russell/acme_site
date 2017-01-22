(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('th-alert-prompts', {
            parent: 'entity',
            url: '/th-alert-prompts?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThAlertPrompts'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-alert-prompts/th-alert-prompts.html',
                    controller: 'ThAlertPromptsController',
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
        .state('th-alert-prompts-detail', {
            parent: 'entity',
            url: '/th-alert-prompts/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThAlertPrompts'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-alert-prompts/th-alert-prompts-detail.html',
                    controller: 'ThAlertPromptsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ThAlertPrompts', function($stateParams, ThAlertPrompts) {
                    return ThAlertPrompts.get({id : $stateParams.id});
                }]
            }
        })
        .state('th-alert-prompts.new', {
            parent: 'th-alert-prompts',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-alert-prompts/th-alert-prompts-dialog.html',
                    controller: 'ThAlertPromptsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('th-alert-prompts', null, { reload: true });
                }, function() {
                    $state.go('th-alert-prompts');
                });
            }]
        })
        .state('th-alert-prompts.edit', {
            parent: 'th-alert-prompts',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-alert-prompts/th-alert-prompts-dialog.html',
                    controller: 'ThAlertPromptsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ThAlertPrompts', function(ThAlertPrompts) {
                            return ThAlertPrompts.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-alert-prompts', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('th-alert-prompts.delete', {
            parent: 'th-alert-prompts',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-alert-prompts/th-alert-prompts-delete-dialog.html',
                    controller: 'ThAlertPromptsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ThAlertPrompts', function(ThAlertPrompts) {
                            return ThAlertPrompts.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-alert-prompts', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
