'use strict';

/**
 * @ngdoc function
 * @name tradeProcessorFrontApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the tradeProcessorFrontApp
 */
angular.module('tradeProcessorFrontApp')
  .controller('MainCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

	var socket;
	var stompClient;

	$scope.isConnected = false;

	$scope.init = function() {
		if (stompClient)
			stompClient.send("/app/trade-summaries", {}, {});
	};

    $scope.connect = function() {
	    socket = new SockJS('http://localhost:8080/trade-processor/trade-summaries');
		stompClient = Stomp.over(socket);

		stompClient.connect({}, function(response) {
			console.log('Connected');

			stompClient.subscribe('/topic/trade-summaries', function(response){
				$scope.loadTradeSummaries(response);
				$scope.$apply();
			});

			$scope.init();

			$scope.isConnected = true;
			$scope.$apply();
		});

    };

	$scope.chartObject = {
		'type': 'ColumnChart',
		'displayed': true,
		'data': {
			'cols': [
				{
					'id': 'countryTo',
					'label': 'Country To',
					'type': 'string',
					'p': {}
				},
				{
					'id': 'amountSell',
					'label': 'Amount Sell',
					'type': 'number',
					'p': {}
				},
				{
					'id': 'amountBuy',
					'label': 'Amount Buy',
					'type': 'number',
					'p': {}
				}
			]
		},
		'options': {
			'isStacked': 'true',
			'fill': 20,
			'displayExactValues': true,
			'vAxis': {
				'title': 'Money',
				'gridlines': {
					'count': 10
				}
			},
			'hAxis': {
				'title': 'Data'
			},
			'tooltip': {
				'isHtml': false
			}
		},
		'formatters': {},
		'view': {}
	};

    $scope.loadTradeSummaries = function(response) {
		var tradeSummaries = JSON.parse(response.body);
		var rows = [];

		for (var currencyFrom in tradeSummaries) {
			var tradeSummaryForCurrencyFrom = tradeSummaries[currencyFrom]; 
			
			for (var i in tradeSummaryForCurrencyFrom) {
				var tradeSummary = tradeSummaryForCurrencyFrom[i];
				var currencyToCaption = currencyFrom + ' - ' + tradeSummary['currencyTo'] +
					'. Rate avg: ' + tradeSummary['rateAvg'].toFixed(3);

				rows.push({
					'c': [
						{
							'v': tradeSummary['currencyTo'],
							'f': currencyToCaption
						},
						{
							'v': tradeSummary['totalAmountSell']
						},
						{
							'v': tradeSummary['totalAmountBuy']
						}
					]
				});
			}
		}

		$scope.chartObject.data.rows = rows;
		$scope.tradeSummaries = tradeSummaries;
    };

    // Connecting and initializing canvas
    $scope.connect();

  });
