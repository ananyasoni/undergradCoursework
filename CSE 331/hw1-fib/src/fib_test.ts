import * as assert from 'assert';
import { fib, fastFib, fastFib2, nextFib } from './fib';


describe('fib', function() {

  it('fib', function() {
    assert.strictEqual(fib(0n), 0n);
    assert.strictEqual(fib(1n), 1n);
    assert.strictEqual(fib(2n), 1n);
    assert.strictEqual(fib(3n), 2n);
    assert.strictEqual(fib(11n), 89n);
  });

  it('fastFib', function() {
    assert.deepStrictEqual(fastFib(1n), {curFib: 1n, prevFib: 0n});
    assert.deepStrictEqual(fastFib(2n), {curFib: 1n, prevFib: 1n});
    assert.deepStrictEqual(fastFib(3n), {curFib: 2n, prevFib: 1n});
    assert.deepStrictEqual(fastFib(11n), {curFib: 89n, prevFib: 55n});
  });

  it('fastFib2', function() {
    assert.deepStrictEqual(fastFib2(1n), [0n, 1n]);
    assert.deepStrictEqual(fastFib2(2n), [1n, 1n]);
    assert.deepStrictEqual(fastFib2(3n), [1n, 2n]);
    assert.deepStrictEqual(fastFib2(11n), [55n, 89n]);
  });

  it('nextFib', function() {
    assert.deepStrictEqual(nextFib(0n), 0n);
    assert.deepStrictEqual(nextFib(1n), 1n);
    assert.deepStrictEqual(nextFib(2n), 2n);
    assert.deepStrictEqual(nextFib(3n), 3n);
    assert.deepStrictEqual(nextFib(4n), 5n);
    assert.deepStrictEqual(nextFib(5n), 5n);
    assert.deepStrictEqual(nextFib(6n), 8n);
    assert.deepStrictEqual(nextFib(7n), 8n);
    assert.deepStrictEqual(nextFib(9n), 13n);
    assert.deepStrictEqual(nextFib(54n), 55n);
    assert.deepStrictEqual(nextFib(55n), 55n);
    assert.deepStrictEqual(nextFib(56n), 89n);
  });

});
