/*-
 * ============LICENSE_START=======================================================
 * ONAP CLAMP
 * ================================================================================
 * Copyright (C) 2017-2018 AT&T Intellectual Property. All rights
 *                             reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 * ============LICENSE_END============================================
 * ===================================================================
 * 
 */
'use strict';
angular.module('clds-app').controller(
'QueryParamsHandlerCtrl',
[ '$scope', '$rootScope', '$resource', '$http', '$location',
function($scope, $rootScope, $resource, $http, $location) {
	console.log("//////////QueryParamsHandlerCtrl");
	if (!jQuery.isEmptyObject($location.search())) {
		$rootScope.urlapp = $location.search().app;
		$rootScope.urlparams = $location.search();
		$location.path('/' + $rootScope.urlapp);
	} else if ($rootScope.urlapp != undefined && $rootScope.urlapp != "") {
		$location.path('/' + $rootScope.urlapp);
	} else {
		$location.path('/dashboard');
	}
} ]);
