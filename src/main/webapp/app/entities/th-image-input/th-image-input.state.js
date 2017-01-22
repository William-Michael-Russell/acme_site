(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('th-image-input', {
            parent: 'entity',
            url: '/th-image-input?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThImageInputs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-image-input/th-image-inputs.html',
                    controller: 'ThImageInputController',
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
        .state('th-image-input-detail', {
            parent: 'entity',
            url: '/th-image-input/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThImageInput'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-image-input/th-image-input-detail.html',
                    controller: 'ThImageInputDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ThImageInput', function($stateParams, ThImageInput) {
                    return ThImageInput.get({id : $stateParams.id});
                }]
            }
        })
        .state('th-image-input.new', {
            parent: 'th-image-input',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-image-input/th-image-input-dialog.html',
                    controller: 'ThImageInputDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                imageInputField: null,
                                imageInputFieldContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('th-image-input', null, { reload: true });
                }, function() {
                    $state.go('th-image-input');
                });
            }]
        })
        .state('th-image-input.edit', {
            parent: 'th-image-input',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-image-input/th-image-input-dialog.html',
                    controller: 'ThImageInputDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ThImageInput', function(ThImageInput) {
                            return ThImageInput.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-image-input', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('th-image-input.delete', {
            parent: 'th-image-input',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-image-input/th-image-input-delete-dialog.html',
                    controller: 'ThImageInputDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ThImageInput', function(ThImageInput) {
                            return ThImageInput.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-image-input', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
