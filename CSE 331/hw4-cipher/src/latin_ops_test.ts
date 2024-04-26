import * as assert from 'assert';
import { nil } from './list';
import { explode, compact } from './char_list';
import { next_latin_char, prev_latin_char, count_consonants, cipher_encode, cipher_decode, crazy_caps_encode, crazy_caps_decode, frog_latin_encode, frog_latin_decode } from './latin_ops';

describe('latin_ops', function() {

  // For the following 2 functions, there are a finite number of cases
  // but the number exceeds our reasonable case limit of 20, so just some
  // were selected.
  it('next_latin_char', function() {
    assert.equal(next_latin_char("a".charCodeAt(0)), "i".charCodeAt(0));
    assert.equal(next_latin_char("e".charCodeAt(0)), "y".charCodeAt(0));
    assert.equal(next_latin_char("i".charCodeAt(0)), "u".charCodeAt(0));
    assert.equal(next_latin_char("o".charCodeAt(0)), "a".charCodeAt(0));
    assert.equal(next_latin_char("u".charCodeAt(0)), "o".charCodeAt(0));
    assert.equal(next_latin_char("j".charCodeAt(0)), "d".charCodeAt(0));
    assert.equal(next_latin_char("g".charCodeAt(0)), "j".charCodeAt(0));
    assert.equal(next_latin_char("d".charCodeAt(0)), "b".charCodeAt(0));
    assert.equal(next_latin_char("t".charCodeAt(0)), "p".charCodeAt(0));
    assert.equal(next_latin_char("c".charCodeAt(0)), "z".charCodeAt(0));
    assert.equal(next_latin_char("k".charCodeAt(0)), "c".charCodeAt(0));
    assert.equal(next_latin_char("f".charCodeAt(0)), "w".charCodeAt(0));
    assert.equal(next_latin_char("v".charCodeAt(0)), "f".charCodeAt(0));
    assert.equal(next_latin_char("w".charCodeAt(0)), "v".charCodeAt(0));
    assert.equal(next_latin_char("h".charCodeAt(0)), "r".charCodeAt(0));
    assert.equal(next_latin_char("l".charCodeAt(0)), "h".charCodeAt(0));
    assert.equal(next_latin_char("r".charCodeAt(0)), "l".charCodeAt(0));
    assert.equal(next_latin_char("m".charCodeAt(0)), "n".charCodeAt(0));
    assert.equal(next_latin_char("n".charCodeAt(0)), "m".charCodeAt(0));
    assert.equal(next_latin_char("x".charCodeAt(0)), "q".charCodeAt(0));
  });

  it('prev_latin_char', function() {
    assert.equal(prev_latin_char("a".charCodeAt(0)), "o".charCodeAt(0));
    assert.equal(prev_latin_char("e".charCodeAt(0)), "y".charCodeAt(0));
    assert.equal(prev_latin_char("i".charCodeAt(0)), "a".charCodeAt(0));
    assert.equal(prev_latin_char("u".charCodeAt(0)), "i".charCodeAt(0));
    assert.equal(prev_latin_char("y".charCodeAt(0)), "e".charCodeAt(0));
    assert.equal(prev_latin_char("b".charCodeAt(0)), "d".charCodeAt(0));
    assert.equal(prev_latin_char("p".charCodeAt(0)), "t".charCodeAt(0));
    assert.equal(prev_latin_char("j".charCodeAt(0)), "g".charCodeAt(0));
    assert.equal(prev_latin_char("g".charCodeAt(0)), "p".charCodeAt(0));
    assert.equal(prev_latin_char("k".charCodeAt(0)), "s".charCodeAt(0));
    assert.equal(prev_latin_char("s".charCodeAt(0)), "z".charCodeAt(0));
    assert.equal(prev_latin_char("z".charCodeAt(0)), "c".charCodeAt(0));
    assert.equal(prev_latin_char("f".charCodeAt(0)), "v".charCodeAt(0));
    assert.equal(prev_latin_char("v".charCodeAt(0)), "w".charCodeAt(0));
    assert.equal(prev_latin_char("w".charCodeAt(0)), "f".charCodeAt(0));
    assert.equal(prev_latin_char("l".charCodeAt(0)), "r".charCodeAt(0));
    assert.equal(prev_latin_char("m".charCodeAt(0)), "n".charCodeAt(0));
    assert.equal(prev_latin_char("n".charCodeAt(0)), "m".charCodeAt(0));
    assert.equal(prev_latin_char("q".charCodeAt(0)), "x".charCodeAt(0));
    assert.equal(prev_latin_char("x".charCodeAt(0)), "q".charCodeAt(0));
  });

  it('cipher_encode', function() {
    // 0-1-many heursitic (base-case) 0 recursive calls (only one possible input nil)
    assert.deepStrictEqual(cipher_encode(nil), nil);
    assert.deepStrictEqual(cipher_encode(explode('')), nil);
    assert.deepStrictEqual(compact(cipher_encode(explode(''))), '');

    // 0-1-many heursitic 1 recursive calls (minimum 2 test cases per subdomain)
    assert.deepStrictEqual(compact(cipher_encode(explode('a'))), 'i');
    assert.deepStrictEqual(compact(cipher_encode(explode('w'))), 'v');
    assert.deepStrictEqual(compact(cipher_encode(explode('r'))), 'l');
    assert.deepStrictEqual(compact(cipher_encode(explode('m'))), 'n');

    // 0-1-many heursitic many recursive calls (minimum 2 test cases per subdomain)
    assert.deepStrictEqual(compact(cipher_encode(explode('cse'))), 'zky');
    assert.deepStrictEqual(compact(cipher_encode(explode('ananya'))), 'imimei');
    assert.deepStrictEqual(compact(cipher_encode(explode('dog'))), 'baj');
  });

  it('cipher_decode', function() {
    // 0-1-many heursitic (base-case) 0 recursive calls (only one possible input nil)
    assert.deepStrictEqual(cipher_decode(nil), nil);
    assert.deepStrictEqual(cipher_decode(explode('')), nil);
    assert.deepStrictEqual(compact(cipher_decode(explode(''))), '');

    // 0-1-many heursitic 1 recursive calls (minimum 2 test cases per subdomain)
    assert.deepStrictEqual(compact(cipher_decode(explode('i'))), 'a');
    assert.deepStrictEqual(compact(cipher_decode(explode('v'))), 'w');
    assert.deepStrictEqual(compact(cipher_decode(explode('l'))), 'r');
    assert.deepStrictEqual(compact(cipher_decode(explode('n'))), 'm');

    // 0-1-many heursitic many recursive calls (minimum 2 test cases per subdomain)
    assert.deepStrictEqual(compact(cipher_decode(explode('zky'))), 'cse');
    assert.deepStrictEqual(compact(cipher_decode(explode('imimei'))), 'ananya');
    assert.deepStrictEqual(compact(cipher_decode(explode('baj'))), 'dog');
  });

  it('crazy_caps_encode', function() {
    // 0-1-many heursitic (base-case 1) 0 recursive calls (only one possible input nil)
    assert.deepStrictEqual(crazy_caps_encode(nil), nil);
    assert.deepStrictEqual(crazy_caps_encode(explode('')), nil);
    assert.deepStrictEqual(compact(crazy_caps_encode(explode(''))), '');

    // 0-1-many heursitic (base-case 2) 0 recursive calls (minimum 2 tests per subdomain)
    assert.deepStrictEqual(compact(crazy_caps_encode(explode('c'))), 'c');
    assert.deepStrictEqual(compact(crazy_caps_encode(explode('a'))), 'a');
    assert.deepStrictEqual(compact(crazy_caps_encode(explode('z'))), 'z');

    // 0-1-many heursitic 1 recursive call (minimum 2 test cases per subdomain)
    assert.deepStrictEqual(compact(crazy_caps_encode(explode('cat'))), 'cAt');
    assert.deepStrictEqual(compact(crazy_caps_encode(explode('dog'))), 'dOg');
    assert.deepStrictEqual(compact(crazy_caps_encode(explode('to'))), 'tO');
    assert.deepStrictEqual(compact(crazy_caps_encode(explode('do'))), 'dO');

    // 0-1-many heursitic many recursive calls (minimum 2 test cases per subdomain)
    assert.deepStrictEqual(compact(crazy_caps_encode(explode('crazy'))), 'cRaZy');
    assert.deepStrictEqual(compact(crazy_caps_encode(explode('ananya'))), 'aNaNyA');
    assert.deepStrictEqual(compact(crazy_caps_encode(explode('shreya'))), 'sHrEyA');
    assert.deepStrictEqual(compact(crazy_caps_encode(explode('soni'))), 'sOnI');
    assert.deepStrictEqual(compact(crazy_caps_encode(explode('ilovecomputerscience'))), 'iLoVeCoMpUtErScIeNcE');
  });

  it('crazy_caps_decode', function() {
    // 0-1-many heursitic (base-case 1) 0 recursive calls (only one possible input nil)
    assert.deepStrictEqual(crazy_caps_decode(nil), nil);
    assert.deepStrictEqual(crazy_caps_decode(explode('')), nil);
    assert.deepStrictEqual(compact(crazy_caps_decode(explode(''))), '');

    // 0-1-many heursitic (base-case 2) 0 recursive calls (minimum 2 tests per subdomain)
    assert.deepStrictEqual(compact(crazy_caps_decode(explode('c'))), 'c');
    assert.deepStrictEqual(compact(crazy_caps_decode(explode('a'))), 'a');
    assert.deepStrictEqual(compact(crazy_caps_decode(explode('z'))), 'z');

    // 0-1-many heursitic 1 recursive call (minimum 2 test cases per subdomain)
    assert.deepStrictEqual(compact(crazy_caps_decode(explode('cAt'))), 'cat');
    assert.deepStrictEqual(compact(crazy_caps_decode(explode('dOg'))), 'dog');
    assert.deepStrictEqual(compact(crazy_caps_decode(explode('tO'))), 'to');
    assert.deepStrictEqual(compact(crazy_caps_decode(explode('dO'))), 'do');

    // 0-1-many heursitic many recursive calls (minimum 2 test cases per subdomain)
    assert.deepStrictEqual(compact(crazy_caps_decode(explode('cRaZy'))), 'crazy');
    assert.deepStrictEqual(compact(crazy_caps_decode(explode('aNaNyA'))), 'ananya');
    assert.deepStrictEqual(compact(crazy_caps_decode(explode('sHrEyA'))), 'shreya');
    assert.deepStrictEqual(compact(crazy_caps_decode(explode('sOnI'))), 'soni');
    assert.deepStrictEqual(compact(crazy_caps_decode(explode('iLoVeCoMpUtErScIeNcE'))), 'ilovecomputerscience');
  });

  it('count_consonants', function() {
    // base case: nil
    assert.strictEqual(count_consonants(nil), -1n);
    // base case: 1st char is vowel, no recursive calls
    assert.strictEqual(count_consonants(explode("e")), 0n);
    assert.strictEqual(count_consonants(explode("astray")), 0n);
    // base case: no vowels or cosonants
    assert.strictEqual(count_consonants(explode("")), -1n);
    assert.strictEqual(count_consonants(explode("_")), -1n);

    // 1 recursive call:
    assert.strictEqual(count_consonants(explode("say")), 1n);
    assert.strictEqual(count_consonants(explode("l_")), -1n);

    // multiple recursive calls:
    assert.strictEqual(count_consonants(explode("stingray")), 2n);
    assert.strictEqual(count_consonants(explode("stray")), 3n);
    assert.strictEqual(count_consonants(explode("str")), -1n);
    assert.strictEqual(count_consonants(explode("st_a")), -1n);
  });

  // TODO: uncomment the following tests when you are ready to test your
  // Frog Latin functions. You'll need to import these functions.

  // Note: these are just a subset of tests to get you started. We will have
  // additional staff tests, some of which will be hidden. Please add tests/
  // reason through your code carefully to be confident it's correct! Though
  // we will not be grading these things.

  it('frog_latin_encode', function() {
    assert.strictEqual(frog_latin_encode(nil), nil);
    assert.strictEqual(compact(frog_latin_encode(explode(""))), "");
    assert.strictEqual(compact(frog_latin_encode(explode("cd"))), "cd");
    assert.strictEqual(compact(frog_latin_encode(explode("elf"))), "felfrog");
    assert.strictEqual(compact(frog_latin_encode(explode("kevin"))), "evinkrog");
    assert.strictEqual(compact(frog_latin_encode(explode("ten"))), "entrog");

    //case 1
    assert.strictEqual(compact(frog_latin_encode(explode("hmm"))), "hmm");
    assert.strictEqual(compact(frog_latin_encode(explode("brrr"))), "brrr");
    assert.strictEqual(compact(frog_latin_encode(explode("hwk"))), "hwk");
    assert.strictEqual(compact(frog_latin_encode(explode("nhhhhhhhh"))), "nhhhhhhhh");
    assert.strictEqual(compact(frog_latin_encode(explode("h"))), "h");

    //case 2
    assert.strictEqual(compact(frog_latin_encode(explode("alt"))), "faltrog");
    assert.strictEqual(compact(frog_latin_encode(explode("ananya"))), "fananyarog");
    assert.strictEqual(compact(frog_latin_encode(explode("eat"))), "featrog");
    assert.strictEqual(compact(frog_latin_encode(explode("enter"))), "fenterrog");
    assert.strictEqual(compact(frog_latin_encode(explode("egg"))), "feggrog");
    assert.strictEqual(compact(frog_latin_encode(explode("a"))), "farog");

    //case 3
    assert.strictEqual(compact(frog_latin_encode(explode("ti"))), "itrog");
    assert.strictEqual(compact(frog_latin_encode(explode("thing"))), "ingthrog");
    assert.strictEqual(compact(frog_latin_encode(explode("cat"))), "atcrog");
    assert.strictEqual(compact(frog_latin_encode(explode("dog"))), "ogdrog");
    assert.strictEqual(compact(frog_latin_encode(explode("stray"))), "aystrrog");
    assert.strictEqual(compact(frog_latin_encode(explode("baby"))), "abybrog");
    assert.strictEqual(compact(frog_latin_encode(explode("canonical"))), "anonicalcrog");
    assert.strictEqual(compact(frog_latin_encode(explode("shroud"))), "oudshrrog");
    assert.strictEqual(compact(frog_latin_encode(explode("shreya"))), "eyashrrog");
  });

  it('frog_latin_decode', function() {
    assert.strictEqual(compact(frog_latin_decode(explode("james"))), "james");
    assert.strictEqual(compact(frog_latin_decode(explode("forangerog"))), "orange");
    assert.strictEqual(compact(frog_latin_decode(explode("featrog"))), "eat");
    assert.strictEqual(compact(frog_latin_decode(explode("ameshrog"))), "shame");
    assert.strictEqual(compact(frog_latin_decode(explode("entrog"))), "nte");

    //case 1
    assert.strictEqual(compact(frog_latin_decode(explode("farog"))), "a");
    assert.strictEqual(compact(frog_latin_decode(explode("fananyarog"))), "ananya");
    assert.strictEqual(compact(frog_latin_decode(explode("ferog"))), "e");
    assert.strictEqual(compact(frog_latin_decode(explode("fenterrog"))), "enter");
    assert.strictEqual(compact(frog_latin_decode(explode("forog"))), "o");
    assert.strictEqual(compact(frog_latin_decode(explode("fyesrog"))), "yes");

    //case 2
    assert.strictEqual(compact(frog_latin_decode(explode("unnybrog"))), "bunny");
    assert.strictEqual(compact(frog_latin_decode(explode("unnsbrog"))), "nnsbu");
    assert.strictEqual(compact(frog_latin_decode(explode("eakbrrog"))), "kbrea");
    assert.strictEqual(compact(frog_latin_decode(explode("abrog"))), "ba");
    assert.strictEqual(compact(frog_latin_decode(explode("itrog"))), "ti");
    assert.strictEqual(compact(frog_latin_decode(explode("anceprrog"))), "prance");
    assert.strictEqual(compact(frog_latin_decode(explode("itbrrog"))), "tbri");
    assert.strictEqual(compact(frog_latin_decode(explode("abybrog"))), "baby");
    assert.strictEqual(compact(frog_latin_decode(explode("anonicalcrog"))), "lcanonica");
    assert.strictEqual(compact(frog_latin_decode(explode("oudshrrog"))), "dshrou");
    assert.strictEqual(compact(frog_latin_decode(explode("eyashrrog"))), "shreya");
    assert.strictEqual(compact(frog_latin_decode(explode("ingthrog"))), "ngthi");
    assert.strictEqual(compact(frog_latin_decode(explode("atcrog"))), "tca");
    assert.strictEqual(compact(frog_latin_decode(explode("ogdrog"))), "gdo");
    assert.strictEqual(compact(frog_latin_decode(explode("aystrrog"))), "stray");

    //case 3
    assert.strictEqual(compact(frog_latin_decode(explode("frog"))), "frog");
    assert.strictEqual(compact(frog_latin_decode(explode("f rog"))), "f rog");
    assert.strictEqual(compact(frog_latin_decode(explode("aterog"))), "aterog");
    assert.strictEqual(compact(frog_latin_decode(explode("strog"))), "strog");
    assert.strictEqual(compact(frog_latin_decode(explode("s"))), "s");
    assert.strictEqual(compact(frog_latin_decode(explode("eats"))), "eats");
    assert.strictEqual(compact(frog_latin_decode(explode("sat"))), "sat");
    assert.strictEqual(compact(frog_latin_decode(explode(""))), "");
    assert.strictEqual(compact(frog_latin_decode(explode("      "))), "      ");
    assert.strictEqual(compact(frog_latin_decode(explode("lollllllz!rog"))), "lollllllz!rog");
    assert.strictEqual(frog_latin_decode(nil), nil);
    assert.strictEqual(compact(frog_latin_decode(explode("y"))), "y");
    assert.strictEqual(compact(frog_latin_decode(explode("fcrog"))), "fcrog");
    assert.strictEqual(compact(frog_latin_decode(explode("grad"))), "grad");
    assert.strictEqual(compact(frog_latin_decode(explode("lrog"))), "lrog");
    assert.strictEqual(compact(frog_latin_decode(explode("yrog"))), "yrog");
  });

});
