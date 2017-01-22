(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('th-gui', {
            parent: 'entity',
            url: '/th-gui',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThGUIS'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-gui/th-guis.html',
                    controller: 'ThGUIController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('th-gui-detail', {
            parent: 'entity',
            url: '/th-gui/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ThGUI'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/th-gui/th-gui-detail.html',
                    controller: 'ThGUIDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ThGUI', function($stateParams, ThGUI) {
                    return ThGUI.get({id : $stateParams.id});
                }]
            }
        })
        .state('th-gui.new', {
            parent: 'th-gui',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-gui/th-gui-dialog.html',
                    controller: 'ThGUIDialogController',
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
                    $state.go('th-gui', null, { reload: true });
                }, function() {
                    $state.go('th-gui');
                });
            }]
        })
        .state('th-gui.edit', {
            parent: 'th-gui',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-gui/th-gui-dialog.html',
                    controller: 'ThGUIDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ThGUI', function(ThGUI) {
                            return ThGUI.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-gui', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('th-gui.delete', {
            parent: 'th-gui',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/th-gui/th-gui-delete-dialog.html',
                    controller: 'ThGUIDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ThGUI', function(ThGUI) {
                            return ThGUI.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('th-gui', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
