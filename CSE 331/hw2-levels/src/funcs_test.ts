import * as assert from 'assert';
import { fact } from './funcs';
import {r, s, t} from './funcs';

describe('funcs', function() {

  // TODO: uncomment these when you have implemented and imported "r"
  it('r', function() {
    assert.deepStrictEqual(r({n: 0n, m: 0n}), 1n);
    assert.deepStrictEqual(r({n: 0n, m: 5n}), 1n);
    assert.deepStrictEqual(r({n: 1n, m: 0n}), -1n);
    assert.deepStrictEqual(r({n: 2n, m: 0n}), -1n);
    assert.deepStrictEqual(r({n: 2n, m: 2n}), 0n);
    assert.deepStrictEqual(r({n: 19n, m: 13n}), 0n);
  });

  // TODO: uncomment these when you have implemented and imported "s"
  it('s', function() {
    assert.deepStrictEqual(s(true), 0n);
    assert.deepStrictEqual(s(false), 0n);
    assert.deepStrictEqual(s([0n, true]), 0n);
    assert.deepStrictEqual(s([5n, true]), 5n);
    assert.deepStrictEqual(s([9n, true]), 9n);
    assert.deepStrictEqual(s([0n, false]), 1n);
    assert.deepStrictEqual(s([5n, false]), 6n);
    assert.deepStrictEqual(s([11n, false]), 12n);
  });

  // TODO: uncomment these when you have implemented and imported "t"
  it('t', function() {
    assert.deepStrictEqual(t([true, {n: 0, m: 0}]), 0);
    assert.deepStrictEqual(t([true, {n: 1, m: 0}]), 0);
    assert.deepStrictEqual(t([true, {n: 5, m: 8.2}]), 41);
    assert.deepStrictEqual(t([true, {n: -9, m: 2}]), -18);
    assert.deepStrictEqual(t([false, {n: 0, m: 0}]), 0);
    assert.deepStrictEqual(t([false, {n: 1, m: 0}]), 1);
    assert.deepStrictEqual(t([false, {n: -1, m: 3}]), -7);
    assert.deepStrictEqual(t([false, {n: 10, m: 2}]), 6);
  });


  // TODO: write tests according to our heuristics for "fact" here
  //       after you have implemented and imported it. Don't forget
  //       to add comments justifying your cases!
  it('fact', function() {
    // Example test:
    //testing base case (0 recursive calls)
    assert.deepStrictEqual(fact(0n), 1n);
    // 0-1-many heuristic, 1 recursive call (only 1 possible)
    assert.deepStrictEqual(fact(1n), 1n);
    // 0-1-many heuristic, 2 recursive calls (more than 1 recursive call)
    assert.deepStrictEqual(fact(2n), 2n);
    // 0-1-many heuristic, 3 recursive calls (more than 1 recursive call)
    assert.deepStrictEqual(fact(3n), 6n);

    //extra test cases beyond the required minumum:
    // 0-1-many heuristic, 4 recursive calls (more than 1 recursive call)
    assert.deepStrictEqual(fact(4n), 24n);
    // 0-1-many heuristic, 5 recursive calls (more than 1 recursive call)
    assert.deepStrictEqual(fact(5n), 120n);
    // 0-1-many heuristic, 6 recursive calls (more than 1 recursive call)
    assert.deepStrictEqual(fact(6n), 720n);
    // assert.deepStrictEqual takes 2 arguments:
    //  - first an actual value: where we call the function we're testing
    //  - second an expected value: what the function should return for the inputs
    // Then it compares these outputs and the case pases if they are equal.

    // You should have an assert.deepStrictEqual statement for each test
    // case you need (according to our heuristics)
  });
});
