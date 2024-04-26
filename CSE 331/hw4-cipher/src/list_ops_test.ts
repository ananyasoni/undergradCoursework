import * as assert from 'assert';
import { nil } from './list';
import { explode, compact } from './char_list';
import { last, prefix, suffix } from './list_ops';


describe('list_ops', function() {

  it('last', function() {
    // Error case branch
    assert.throws(() => last(nil), Error);

    // 0-1-many: base case
    assert.deepEqual(last(explode("a")), "a".charCodeAt(0));
    assert.deepEqual(last(explode("_")), "_".charCodeAt(0));

    // 0-1-many: one recursive call
    assert.deepEqual(last(explode("hm")), "m".charCodeAt(0));
    assert.deepEqual(last(explode("hu")), "u".charCodeAt(0));

    // 0-1-many: many recursive calls
    assert.deepEqual(last(explode("hub")), "b".charCodeAt(0));
    assert.deepEqual(last(explode("stray")), "y".charCodeAt(0));
    assert.deepEqual(last(explode("shrug")), "g".charCodeAt(0));
  });

  it('prefix', function() {
    // 0-1-many heursitic (base-case) 0 recursive calls (minimum 2 tests per subdomain)
    assert.deepStrictEqual(prefix(0n, nil), nil);
    assert.deepStrictEqual(prefix(0n, explode('')), nil);
    assert.deepStrictEqual(prefix(0n, explode('cat')), nil);
    assert.deepStrictEqual(prefix(0n, explode('bananasareawesome')), nil);

    //error case / base case 2: n is NOT >= to the length of the list L (minimum 2 tests per subdomain)
    assert.throws(() => prefix(6n, explode("hello")), Error);
    assert.throws(() => prefix(1n, explode("")), Error);
    assert.throws(() => prefix(1n, nil), Error);
    assert.throws(() => prefix(19n, nil), Error);
    assert.throws(() => prefix(5n, explode("soni")), Error);

    // 0-1-many heursitic 1 recursive call (minimum 2 tests per subdomain)
    assert.deepStrictEqual(compact(prefix(1n, explode('ilovedogs'))), 'i');
    assert.deepStrictEqual(compact(prefix(1n, explode('O'))), 'O');
    assert.deepStrictEqual(compact(prefix(1n, explode('brother'))), 'b');
    assert.throws(() => prefix(2n, explode("c")), Error);
    assert.throws(() => prefix(5n, explode("a")), Error);

    // 0-1-many heursitic many recursive calls (minimum 2 tests per subdomain)
    assert.deepStrictEqual(compact(prefix(2n, explode('12345'))), '12');
    assert.deepStrictEqual(compact(prefix(5n, explode('ilovedogs'))), 'ilove');
    assert.deepStrictEqual(compact(prefix(3n, explode('bunny'))), 'bun');
    assert.deepStrictEqual(compact(prefix(6n, explode('ananya'))), 'ananya');
    assert.deepStrictEqual(compact(prefix(2n, explode('math'))), 'ma');
  });

  it('suffix', function() {
   // 0-1-many heursitic (base-case) 0 recursive calls (minimum 2 tests per subdomain)
   assert.deepStrictEqual(suffix(0n, nil), nil);
   assert.deepStrictEqual(suffix(0n, explode('')), nil);
   assert.deepStrictEqual(compact(suffix(0n, explode('cat'))), 'cat');
   assert.deepStrictEqual(compact(suffix(0n, explode('bananasareawesome'))), 'bananasareawesome');

   //error case / base case 2: n is NOT >= to the length of the list L (minimum 2 tests per subdomain)
   assert.throws(() => suffix(6n, explode("hello")), Error);
   assert.throws(() => suffix(1n, explode("")), Error);
   assert.throws(() => suffix(1n, nil), Error);
   assert.throws(() => suffix(19n, nil), Error);
   assert.throws(() => suffix(5n, explode("soni")), Error);

   // 0-1-many heursitic 1 recursive call (minimum 2 tests per subdomain)
   assert.deepStrictEqual(compact(suffix(1n, explode('ilovedogs'))), 'lovedogs');
   assert.deepStrictEqual(compact(suffix(1n, explode('O'))), '');
   assert.deepStrictEqual(compact(suffix(1n, explode('brother'))), 'rother');
   assert.deepStrictEqual(compact(suffix(1n, explode('he'))), 'e');
   assert.throws(() => suffix(2n, explode("c")), Error);
   assert.throws(() => suffix(5n, explode("a")), Error);

   // 0-1-many heursitic many recursive calls (minimum 2 tests per subdomain)
   assert.deepStrictEqual(compact(suffix(5n, explode('ilovedogs'))), 'dogs');
   assert.deepStrictEqual(compact(suffix(2n, explode('12345'))), '345');
   assert.deepStrictEqual(compact(suffix(3n, explode('bunny'))), 'ny');
   assert.deepStrictEqual(compact(suffix(6n, explode('ananya'))), '');
   assert.deepStrictEqual(compact(suffix(2n, explode('math'))), 'th');
  });

});
