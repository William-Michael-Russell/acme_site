'use strict';

describe('Controller Tests', function() {

    describe('ThPhoneInput Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockThPhoneInput, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockThPhoneInput = jasmine.createSpy('MockThPhoneInput');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ThPhoneInput': MockThPhoneInput,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("ThPhoneInputDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'acmeSiteApp:thPhoneInputUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
