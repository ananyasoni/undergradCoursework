import * as assert from 'assert';
import { nil, cons, len, concat, rev } from './list';


describe('list', function() {

  it('len', function() {
    // 0-1-many: base case, 0 recursive calls (only 1 possible input)
    assert.deepEqual(len(nil), 0n);

    // 0-1-many: 1 recursive call
    assert.deepEqual(len(cons(1n, nil)), 1n);
    assert.deepEqual(len(cons(2n, nil)), 1n);

    // 0-1-many: 2+ recursive calls
    assert.deepEqual(len(cons(1n, cons(2n, nil))), 2n);
    assert.deepEqual(len(cons(3n, cons(2n, cons(1n, cons(0n, nil))))), 4n);
  });

  it('concat', function() {
    // 0-1-many: base case, 0 recursive calls
    assert.deepEqual(concat(nil, nil), nil);
    assert.deepEqual(concat(nil, cons(1n, nil)), cons(1n, nil));
    assert.deepEqual(concat(nil, cons(1n, cons(2n, nil))), cons(1n, cons(2n, nil)));

    // 0-1-many: 1 recursive call
    assert.deepEqual(concat(cons(1n, nil), nil), cons(1n, nil));
    assert.deepEqual(concat(cons(1n, nil), cons(2n, nil)), cons(1n, cons(2n, nil)));
    assert.deepEqual(concat(cons(1n, nil), cons(2n, cons(3n, nil))),
        cons(1n, cons(2n, cons(3n, nil))));

    // 0-1-many: 2+ recursive call
    assert.deepEqual(concat(cons(1n, cons(2n, nil)), nil), cons(1n, cons(2n, nil)));
    assert.deepEqual(concat(cons(1n, cons(2n, nil)), cons(3n, nil)),
        cons(1n, cons(2n, cons(3n, nil))));
    assert.deepEqual(concat(cons(1n, cons(2n, nil)), cons(3n, cons(4n, nil))),
        cons(1n, cons(2n, cons(3n, cons(4n, nil)))));
  });

  it('rev', function() {
    // 0-1-many: base case, 0 recursive calls (only 1 possible input)
    assert.deepEqual(rev(nil), nil);

    // 0-1-many: 1 recursive call
    assert.deepEqual(rev(cons(1n, nil)), cons(1n, nil));
    assert.deepEqual(rev(cons(2n, nil)), cons(2n, nil));

    // 0-1-many: 2+ recursive calls
    assert.deepEqual(rev(cons(1n, cons(2n, nil))), cons(2n, cons(1n, nil)));
    assert.deepEqual(rev(cons(1n, cons(2n, cons(3n, nil)))),
        cons(3n, cons(2n, cons(1n, nil))));
  });
});
