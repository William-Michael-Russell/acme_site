(function() {
    'use strict';

    angular
        .module('acmeSiteApp')
        .controller('ThRadioInputController', ThRadioInputController);

    ThRadioInputController.$inject = ['$scope', '$state', 'ThRadioInput', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function ThRadioInputController ($scope, $state, ThRadioInput, ParseLinks, AlertService, pagingParams, paginationConstants) {
        var vm = this;
        vm.loadAll = loadAll;
        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;

        vm.occurrenceOptions ={};


        vm.presidents = [{
            name: "Trump"
        }, {
            name: "Washington"
        }, {
            name: "George"
        }, {
            name: "Jefferson"
        }];


        vm.loadAll();

        function loadAll () {
            ThRadioInput.query({
                page: pagingParams.page - 1,
                size: paginationConstants.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.thRadioInputs = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage (page) {
            vm.page = page;
            vm.transition();
        }

        function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }

    }
})();
