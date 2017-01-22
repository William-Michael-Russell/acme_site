'use strict';

describe('Controller Tests', function() {

    describe('ThToolTips Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockThToolTips;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockThToolTips = jasmine.createSpy('MockThToolTips');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ThToolTips': MockThToolTips
            };
            createController = function() {
                $injector.get('$controller')("ThToolTipsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'acmeSiteApp:thToolTipsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
