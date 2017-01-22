(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('th-scrolling-view', {
            parent: 'entity',
            url: '/th-scrolling-view?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThScrollingViews'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-scrolling-view/th-scrolling-views.html',
                    controller: 'ThScrollingViewController',
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
        .state('th-scrolling-view-detail', {
            parent: 'entity',
            url: '/th-scrolling-view/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThScrollingView'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-scrolling-view/th-scrolling-view-detail.html',
                    controller: 'ThScrollingViewDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ThScrollingView', function($stateParams, ThScrollingView) {
                    return ThScrollingView.get({id : $stateParams.id});
                }]
            }
        })
        .state('th-scrolling-view.new', {
            parent: 'th-scrolling-view',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-scrolling-view/th-scrolling-view-dialog.html',
                    controller: 'ThScrollingViewDialogController',
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
                    $state.go('th-scrolling-view', null, { reload: true });
                }, function() {
                    $state.go('th-scrolling-view');
                });
            }]
        })
        .state('th-scrolling-view.edit', {
            parent: 'th-scrolling-view',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-scrolling-view/th-scrolling-view-dialog.html',
                    controller: 'ThScrollingViewDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ThScrollingView', function(ThScrollingView) {
                            return ThScrollingView.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-scrolling-view', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('th-scrolling-view.delete', {
            parent: 'th-scrolling-view',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-scrolling-view/th-scrolling-view-delete-dialog.html',
                    controller: 'ThScrollingViewDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ThScrollingView', function(ThScrollingView) {
                            return ThScrollingView.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-scrolling-view', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
