'use strict';

describe('Controller Tests', function() {

    describe('ThGUI Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockThGUI;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockThGUI = jasmine.createSpy('MockThGUI');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ThGUI': MockThGUI
            };
            createController = function() {
                $injector.get('$controller')("ThGUIDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'acmeSiteApp:thGUIUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
