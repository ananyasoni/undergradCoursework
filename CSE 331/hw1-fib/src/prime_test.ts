import * as assert from 'assert';
import { isPrime, numDivisors, maxNumDivisors, isHighlyComposite } from './prime';


describe('prime', function() {

  it('isPrime', function() {
    assert.deepStrictEqual(isPrime(1n), false);
    assert.deepStrictEqual(isPrime(2n), true);
    assert.deepStrictEqual(isPrime(3n), true);
    assert.deepStrictEqual(isPrime(4n), false);
    assert.deepStrictEqual(isPrime(5n), true);
    assert.deepStrictEqual(isPrime(6n), false);
    assert.deepStrictEqual(isPrime(10n), false);
    assert.deepStrictEqual(isPrime(11n), true);
    assert.deepStrictEqual(isPrime(101n), true);
    assert.deepStrictEqual(isPrime(102n), false);
  });

  it('numDivisors', function() {
    assert.deepStrictEqual(numDivisors(1n), 1n);
    assert.deepStrictEqual(numDivisors(2n), 2n);
    assert.deepStrictEqual(numDivisors(3n), 2n);
    assert.deepStrictEqual(numDivisors(4n), 3n);
    assert.deepStrictEqual(numDivisors(5n), 2n);
    assert.deepStrictEqual(numDivisors(6n), 4n);
    assert.deepStrictEqual(numDivisors(12n), 6n);
    assert.deepStrictEqual(numDivisors(13n), 2n);
    assert.deepStrictEqual(numDivisors(24n), 8n);
    assert.deepStrictEqual(numDivisors(25n), 3n);
    assert.deepStrictEqual(numDivisors(36n), 9n);
    assert.deepStrictEqual(numDivisors(37n), 2n);
    assert.deepStrictEqual(numDivisors(48n), 10n);
    assert.deepStrictEqual(numDivisors(49n), 3n);
  });

  it('maxNumDivisors', function() {
    assert.deepStrictEqual(maxNumDivisors(1n), 1n);
    assert.deepStrictEqual(maxNumDivisors(2n), 2n);
    assert.deepStrictEqual(maxNumDivisors(3n), 2n);
    assert.deepStrictEqual(maxNumDivisors(4n), 3n);
    assert.deepStrictEqual(maxNumDivisors(5n), 3n);
    assert.deepStrictEqual(maxNumDivisors(6n), 4n);
    assert.deepStrictEqual(maxNumDivisors(7n), 4n);
    assert.deepStrictEqual(maxNumDivisors(12n), 6n);
    assert.deepStrictEqual(maxNumDivisors(13n), 6n);
    assert.deepStrictEqual(maxNumDivisors(24n), 8n);
    assert.deepStrictEqual(maxNumDivisors(25n), 8n);
    assert.deepStrictEqual(maxNumDivisors(36n), 9n);
    assert.deepStrictEqual(maxNumDivisors(37n), 9n);
    assert.deepStrictEqual(maxNumDivisors(48n), 10n);
    assert.deepStrictEqual(maxNumDivisors(49n), 10n);
  });

  it('isHighlyComposite', function() {
    assert.deepStrictEqual(isHighlyComposite(1n), true);
    assert.deepStrictEqual(isHighlyComposite(2n), true);
    assert.deepStrictEqual(isHighlyComposite(3n), false);
    assert.deepStrictEqual(isHighlyComposite(4n), true);
    assert.deepStrictEqual(isHighlyComposite(5n), false);
    assert.deepStrictEqual(isHighlyComposite(6n), true);
    assert.deepStrictEqual(isHighlyComposite(7n), false);
    assert.deepStrictEqual(isHighlyComposite(11n), false);
    assert.deepStrictEqual(isHighlyComposite(12n), true);
    assert.deepStrictEqual(isHighlyComposite(13n), false);
    assert.deepStrictEqual(isHighlyComposite(23n), false);
    assert.deepStrictEqual(isHighlyComposite(24n), true);
    assert.deepStrictEqual(isHighlyComposite(25n), false);
    assert.deepStrictEqual(isHighlyComposite(35n), false);
    assert.deepStrictEqual(isHighlyComposite(36n), true);
    assert.deepStrictEqual(isHighlyComposite(37n), false);
    assert.deepStrictEqual(isHighlyComposite(47n), false);
    assert.deepStrictEqual(isHighlyComposite(48n), true);
    assert.deepStrictEqual(isHighlyComposite(49n), false);
  });

});
