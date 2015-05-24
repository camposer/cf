'use strict';

/**
 * @ngdoc overview
 * @name tradeProcessorFrontApp
 * @description
 * # tradeProcessorFrontApp
 *
 * Main module of the application.
 */

const BASE_URL = 'http://localhost:8080/trade-processor';
 
angular
  .module('tradeProcessorFrontApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'googlechart'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
