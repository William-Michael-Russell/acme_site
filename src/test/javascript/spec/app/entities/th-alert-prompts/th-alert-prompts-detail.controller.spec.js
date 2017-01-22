'use strict';

describe('Controller Tests', function() {

    describe('ThAlertPrompts Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockThAlertPrompts;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockThAlertPrompts = jasmine.createSpy('MockThAlertPrompts');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ThAlertPrompts': MockThAlertPrompts
            };
            createController = function() {
                $injector.get('$controller')("ThAlertPromptsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'acmeSiteApp:thAlertPromptsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
