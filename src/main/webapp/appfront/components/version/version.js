'use strict';

angular.module('PaytmAuth.version', [
  'PaytmAuth.version.interpolate-filter',
  'PaytmAuth.version.version-directive'
])

.value('version', '0.1');
