(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('th-selector-paths', {
            parent: 'entity',
            url: '/th-selector-paths?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThSelectorPaths'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-selector-paths/th-selector-paths.html',
                    controller: 'ThSelectorPathsController',
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
        .state('th-selector-paths-detail', {
            parent: 'entity',
            url: '/th-selector-paths/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThSelectorPaths'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-selector-paths/th-selector-paths-detail.html',
                    controller: 'ThSelectorPathsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ThSelectorPaths', function($stateParams, ThSelectorPaths) {
                    return ThSelectorPaths.get({id : $stateParams.id});
                }]
            }
        })
        .state('th-selector-paths.new', {
            parent: 'th-selector-paths',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-selector-paths/th-selector-paths-dialog.html',
                    controller: 'ThSelectorPathsDialogController',
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
                    $state.go('th-selector-paths', null, { reload: true });
                }, function() {
                    $state.go('th-selector-paths');
                });
            }]
        })
        .state('th-selector-paths.edit', {
            parent: 'th-selector-paths',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-selector-paths/th-selector-paths-dialog.html',
                    controller: 'ThSelectorPathsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ThSelectorPaths', function(ThSelectorPaths) {
                            return ThSelectorPaths.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-selector-paths', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('th-selector-paths.delete', {
            parent: 'th-selector-paths',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-selector-paths/th-selector-paths-delete-dialog.html',
                    controller: 'ThSelectorPathsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ThSelectorPaths', function(ThSelectorPaths) {
                            return ThSelectorPaths.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-selector-paths', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
