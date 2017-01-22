(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('th-video-input', {
            parent: 'entity',
            url: '/th-video-input?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThVideoInputs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-video-input/th-video-inputs.html',
                    controller: 'ThVideoInputController',
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
        .state('th-video-input-detail', {
            parent: 'entity',
            url: '/th-video-input/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThVideoInput'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-video-input/th-video-input-detail.html',
                    controller: 'ThVideoInputDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ThVideoInput', function($stateParams, ThVideoInput) {
                    return ThVideoInput.get({id : $stateParams.id});
                }]
            }
        })
        .state('th-video-input.new', {
            parent: 'th-video-input',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-video-input/th-video-input-dialog.html',
                    controller: 'ThVideoInputDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                videoInputField: null,
                                videoInputFieldContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('th-video-input', null, { reload: true });
                }, function() {
                    $state.go('th-video-input');
                });
            }]
        })
        .state('th-video-input.edit', {
            parent: 'th-video-input',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-video-input/th-video-input-dialog.html',
                    controller: 'ThVideoInputDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ThVideoInput', function(ThVideoInput) {
                            return ThVideoInput.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-video-input', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('th-video-input.delete', {
            parent: 'th-video-input',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-video-input/th-video-input-delete-dialog.html',
                    controller: 'ThVideoInputDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ThVideoInput', function(ThVideoInput) {
                            return ThVideoInput.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-video-input', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
