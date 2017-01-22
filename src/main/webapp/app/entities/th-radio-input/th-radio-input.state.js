(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('th-radio-input', {
            parent: 'entity',
            url: '/th-radio-input?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThRadioInputs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-radio-input/th-radio-inputs.html',
                    controller: 'ThRadioInputController',
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
        .state('th-radio-input-detail', {
            parent: 'entity',
            url: '/th-radio-input/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThRadioInput'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-radio-input/th-radio-input-detail.html',
                    controller: 'ThRadioInputDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ThRadioInput', function($stateParams, ThRadioInput) {
                    return ThRadioInput.get({id : $stateParams.id});
                }]
            }
        })
        .state('th-radio-input.new', {
            parent: 'th-radio-input',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-radio-input/th-radio-input-dialog.html',
                    controller: 'ThRadioInputDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                radioInputField: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('th-radio-input', null, { reload: true });
                }, function() {
                    $state.go('th-radio-input');
                });
            }]
        })
        .state('th-radio-input.edit', {
            parent: 'th-radio-input',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-radio-input/th-radio-input-dialog.html',
                    controller: 'ThRadioInputDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ThRadioInput', function(ThRadioInput) {
                            return ThRadioInput.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-radio-input', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('th-radio-input.delete', {
            parent: 'th-radio-input',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-radio-input/th-radio-input-delete-dialog.html',
                    controller: 'ThRadioInputDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ThRadioInput', function(ThRadioInput) {
                            return ThRadioInput.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-radio-input', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
