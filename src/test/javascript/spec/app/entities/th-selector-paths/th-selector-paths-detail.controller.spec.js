'use strict';

describe('Controller Tests', function() {

    describe('ThSelectorPaths Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockThSelectorPaths;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockThSelectorPaths = jasmine.createSpy('MockThSelectorPaths');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ThSelectorPaths': MockThSelectorPaths
            };
            createController = function() {
                $injector.get('$controller')("ThSelectorPathsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'acmeSiteApp:thSelectorPathsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
