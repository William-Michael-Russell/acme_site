'use strict';

describe('Controller Tests', function() {

    describe('ThRadioInput Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockThRadioInput, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockThRadioInput = jasmine.createSpy('MockThRadioInput');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ThRadioInput': MockThRadioInput,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("ThRadioInputDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'acmeSiteApp:thRadioInputUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
