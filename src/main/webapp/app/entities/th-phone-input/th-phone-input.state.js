(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('th-phone-input', {
            parent: 'entity',
            url: '/th-phone-input?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThPhoneInputs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-phone-input/th-phone-inputs.html',
                    controller: 'ThPhoneInputController',
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
        .state('th-phone-input-detail', {
            parent: 'entity',
            url: '/th-phone-input/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThPhoneInput'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-phone-input/th-phone-input-detail.html',
                    controller: 'ThPhoneInputDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ThPhoneInput', function($stateParams, ThPhoneInput) {
                    return ThPhoneInput.get({id : $stateParams.id});
                }]
            }
        })
        .state('th-phone-input.new', {
            parent: 'th-phone-input',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-phone-input/th-phone-input-dialog.html',
                    controller: 'ThPhoneInputDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                phoneInputField: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('th-phone-input', null, { reload: true });
                }, function() {
                    $state.go('th-phone-input');
                });
            }]
        })
        .state('th-phone-input.edit', {
            parent: 'th-phone-input',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-phone-input/th-phone-input-dialog.html',
                    controller: 'ThPhoneInputDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ThPhoneInput', function(ThPhoneInput) {
                            return ThPhoneInput.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-phone-input', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('th-phone-input.delete', {
            parent: 'th-phone-input',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-phone-input/th-phone-input-delete-dialog.html',
                    controller: 'ThPhoneInputDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ThPhoneInput', function(ThPhoneInput) {
                            return ThPhoneInput.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-phone-input', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
