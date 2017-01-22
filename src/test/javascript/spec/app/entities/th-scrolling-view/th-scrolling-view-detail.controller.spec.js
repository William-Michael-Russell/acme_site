'use strict';

describe('Controller Tests', function() {

    describe('ThScrollingView Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockThScrollingView;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockThScrollingView = jasmine.createSpy('MockThScrollingView');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ThScrollingView': MockThScrollingView
            };
            createController = function() {
                $injector.get('$controller')("ThScrollingViewDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'acmeSiteApp:thScrollingViewUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
