'use strict';

describe('Controller Tests', function() {

    describe('ThDropDownInput Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockThDropDownInput, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockThDropDownInput = jasmine.createSpy('MockThDropDownInput');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ThDropDownInput': MockThDropDownInput,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("ThDropDownInputDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'acmeSiteApp:thDropDownInputUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
