'use strict';

describe('PaytmAuth.version module', function() {
  beforeEach(module('PaytmAuth.version'));

  describe('version service', function() {
    it('should return current version', inject(function(version) {
      expect(version).toEqual('0.1');
    }));
  });
});
