'use strict';

describe('Controller Tests', function() {

    describe('ThEmailInput Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockThEmailInput, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockThEmailInput = jasmine.createSpy('MockThEmailInput');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ThEmailInput': MockThEmailInput,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("ThEmailInputDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'acmeSiteApp:thEmailInputUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
