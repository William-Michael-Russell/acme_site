(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('th-frame-view', {
            parent: 'entity',
            url: '/th-frame-view?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThFrameViews'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-frame-view/th-frame-views.html',
                    controller: 'ThFrameViewController',
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
        .state('th-frame-view-detail', {
            parent: 'entity',
            url: '/th-frame-view/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThFrameView'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-frame-view/th-frame-view-detail.html',
                    controller: 'ThFrameViewDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ThFrameView', function($stateParams, ThFrameView) {
                    return ThFrameView.get({id : $stateParams.id});
                }]
            }
        })
        .state('th-frame-view.new', {
            parent: 'th-frame-view',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-frame-view/th-frame-view-dialog.html',
                    controller: 'ThFrameViewDialogController',
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
                    $state.go('th-frame-view', null, { reload: true });
                }, function() {
                    $state.go('th-frame-view');
                });
            }]
        })
        .state('th-frame-view.edit', {
            parent: 'th-frame-view',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-frame-view/th-frame-view-dialog.html',
                    controller: 'ThFrameViewDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ThFrameView', function(ThFrameView) {
                            return ThFrameView.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-frame-view', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('th-frame-view.delete', {
            parent: 'th-frame-view',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-frame-view/th-frame-view-delete-dialog.html',
                    controller: 'ThFrameViewDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ThFrameView', function(ThFrameView) {
                            return ThFrameView.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-frame-view', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
