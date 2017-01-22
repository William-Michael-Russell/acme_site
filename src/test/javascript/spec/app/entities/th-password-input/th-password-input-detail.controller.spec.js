'use strict';

describe('Controller Tests', function() {

    describe('ThPasswordInput Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockThPasswordInput, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockThPasswordInput = jasmine.createSpy('MockThPasswordInput');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ThPasswordInput': MockThPasswordInput,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("ThPasswordInputDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'acmeSiteApp:thPasswordInputUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
