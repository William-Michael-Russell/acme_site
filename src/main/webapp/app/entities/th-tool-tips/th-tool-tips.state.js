(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('th-tool-tips', {
            parent: 'entity',
            url: '/th-tool-tips?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThToolTips'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-tool-tips/th-tool-tips.html',
                    controller: 'ThToolTipsController',
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
        .state('th-tool-tips-detail', {
            parent: 'entity',
            url: '/th-tool-tips/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThToolTips'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-tool-tips/th-tool-tips-detail.html',
                    controller: 'ThToolTipsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ThToolTips', function($stateParams, ThToolTips) {
                    return ThToolTips.get({id : $stateParams.id});
                }]
            }
        })
        .state('th-tool-tips.new', {
            parent: 'th-tool-tips',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-tool-tips/th-tool-tips-dialog.html',
                    controller: 'ThToolTipsDialogController',
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
                    $state.go('th-tool-tips', null, { reload: true });
                }, function() {
                    $state.go('th-tool-tips');
                });
            }]
        })
        .state('th-tool-tips.edit', {
            parent: 'th-tool-tips',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-tool-tips/th-tool-tips-dialog.html',
                    controller: 'ThToolTipsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ThToolTips', function(ThToolTips) {
                            return ThToolTips.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-tool-tips', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('th-tool-tips.delete', {
            parent: 'th-tool-tips',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-tool-tips/th-tool-tips-delete-dialog.html',
                    controller: 'ThToolTipsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ThToolTips', function(ThToolTips) {
                            return ThToolTips.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-tool-tips', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
