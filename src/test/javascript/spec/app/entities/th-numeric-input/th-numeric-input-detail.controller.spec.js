'use strict';

describe('Controller Tests', function() {

    describe('ThNumericInput Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockThNumericInput, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockThNumericInput = jasmine.createSpy('MockThNumericInput');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ThNumericInput': MockThNumericInput,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("ThNumericInputDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'acmeSiteApp:thNumericInputUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
