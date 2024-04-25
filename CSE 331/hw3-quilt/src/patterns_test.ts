import * as assert from 'assert';
import { NE, GREEN, ROUND, Square, qcons, rcons, rnil, qnil, Row, RED, STRAIGHT, SW, NW, SE, Quilt, qconcat} from './quilt';
import { PatternA, PatternB, PatternC, PatternD, PatternE } from './patterns';


describe('patterns', function() {

  const nw_rnd_grn: Square = {shape: ROUND, color: GREEN, corner: NW};
  const nw_rnd_red: Square = {shape: ROUND, color: RED, corner: NW};
  const nw_strt_grn: Square = {shape: STRAIGHT, color: GREEN, corner: NW};
  const nw_strt_red: Square = {shape: STRAIGHT, color: RED, corner: NW};

  const ne_rnd_grn: Square = {shape: ROUND, color: GREEN, corner: NE};
  const ne_rnd_red: Square = {shape: ROUND, color: RED, corner: NE};
  const ne_strt_grn: Square = {shape: STRAIGHT, color: GREEN, corner: NE};
  const ne_strt_red: Square = {shape: STRAIGHT, color: RED, corner: NE};

  const se_rnd_grn: Square = {shape: ROUND, color: GREEN, corner: SE};
  const se_rnd_red: Square = {shape: ROUND, color: RED, corner: SE};
  const se_strt_grn: Square = {shape: STRAIGHT, color: GREEN, corner: SE};
  const se_strt_red: Square = {shape: STRAIGHT, color: RED, corner: SE};

  const sw_rnd_grn: Square = {shape: ROUND, color: GREEN, corner: SW};
  const sw_rnd_red: Square = {shape: ROUND, color: RED, corner: SW};
  const sw_strt_grn: Square = {shape: STRAIGHT, color: GREEN, corner: SW};
  const sw_strt_red: Square = {shape: STRAIGHT, color: RED, corner: SW};

  it('PatternA', function() {
    //testing base case (0 recursive calls) (minimum 2 tests per subdomain)
    assert.deepStrictEqual(PatternA(0n, GREEN), qnil);
    assert.deepStrictEqual(PatternA(0n, RED), qnil);
    assert.deepStrictEqual(PatternA(0n), qnil);

    // 0-1-many heuristic, 1 recursive call (minimum 2 tests per subdomain)
    assert.deepStrictEqual(PatternA(1n, GREEN), qcons(rcons(ne_rnd_grn, rcons(ne_rnd_grn, rnil)), qnil));
    assert.deepStrictEqual(PatternA(1n, RED), qcons(rcons(ne_rnd_red, rcons(ne_rnd_red, rnil)), qnil));
    assert.deepStrictEqual(PatternA(1n), qcons(rcons(ne_rnd_grn, rcons(ne_rnd_grn, rnil)), qnil));

    // 0-1-many heuristic, many recursive calls (minimum 2 tests per subdomain)
    const gr_rnd_row: Row = rcons(ne_rnd_grn, rcons(ne_rnd_grn, rnil));
    assert.deepStrictEqual(PatternA(4n), qcons(gr_rnd_row, qcons(gr_rnd_row, qcons(gr_rnd_row, qcons(gr_rnd_row, qnil)))));
    assert.deepStrictEqual(PatternA(8n, GREEN), qcons(gr_rnd_row, qcons(gr_rnd_row, qcons(gr_rnd_row, qcons(gr_rnd_row, qcons(gr_rnd_row, qcons(gr_rnd_row, qcons(gr_rnd_row, (qcons(gr_rnd_row, qnil))))))))));
    const red_rnd_row: Row = rcons(ne_rnd_red, rcons(ne_rnd_red, rnil));
    assert.deepStrictEqual(PatternA(5n, RED), qcons(red_rnd_row, qcons(red_rnd_row, qcons(red_rnd_row, qcons(red_rnd_row, qcons(red_rnd_row, qnil))))));

    // assert.throw() checks that an error occurs when the first argument (a function) is called.
    //error cases (minimum two tests per subdomain):
    assert.throws(() => PatternA(-1n, GREEN), Error);
    assert.throws(() => PatternA(-7n, RED), Error);
    //extra error case test cases
    assert.throws(() => PatternA(-10n), Error);
    assert.throws(() => PatternA(-17n), Error);
  });

  it('PatternB', function() {
    //testing base case (0 recursive calls) (minimum 2 tests per subdomain)
    assert.deepStrictEqual(PatternB(0n, GREEN), qnil);
    assert.deepStrictEqual(PatternB(0n, RED), qnil);
    assert.deepStrictEqual(PatternB(0n), qnil);

    // 0-1-many heuristic, 1 recursive call (minimum 2 tests per subdomain)
    assert.deepStrictEqual(PatternB(1n, GREEN), qcons(rcons(ne_strt_grn, rcons(sw_strt_grn, rnil)), qnil));
    assert.deepStrictEqual(PatternB(1n, RED), qcons(rcons(ne_strt_red, rcons(sw_strt_red, rnil)), qnil));
    assert.deepStrictEqual(PatternB(1n), qcons(rcons(ne_strt_grn, rcons(sw_strt_grn, rnil)), qnil));

    // 0-1-many heuristic, many recursive calls (minimum 2 tests per subdomain)
    const gr_strt_row: Row = rcons(ne_strt_grn, rcons(sw_strt_grn, rnil));
    assert.deepStrictEqual(PatternB(4n), qcons(gr_strt_row, qcons(gr_strt_row, qcons(gr_strt_row, qcons(gr_strt_row, qnil)))));
    assert.deepStrictEqual(PatternB(7n), qcons(gr_strt_row, qcons(gr_strt_row, qcons(gr_strt_row, qcons(gr_strt_row, qcons(gr_strt_row, qcons(gr_strt_row, qcons(gr_strt_row, qnil))))))));
    const red_strt_row: Row = rcons(ne_strt_red, rcons(sw_strt_red, rnil));
    assert.deepStrictEqual(PatternB(6n, RED), qcons(red_strt_row, qcons(red_strt_row, qcons(red_strt_row, qcons(red_strt_row, qcons(red_strt_row, qcons(red_strt_row, qnil)))))));

    //error cases (minimum two tests per subdomain):
    assert.throws(() => PatternB(-1n, GREEN), Error);
    assert.throws(() => PatternB(-3n, RED), Error);
    //extra error case test cases
    assert.throws(() => PatternB(-5n), Error);
    assert.throws(() => PatternB(-21n), Error);
  });

  it('PatternC', function() {
    //testing base case (0 recursive calls) (minimum 2 tests per subdomain)
    assert.deepStrictEqual(PatternC(0n, GREEN), qnil);
    assert.deepStrictEqual(PatternC(0n, RED), qnil);
    assert.deepStrictEqual(PatternC(0n), qnil);

    // 0-1-many heuristic, 1 recursive call (minimum 2 tests per subdomain)
    assert.deepStrictEqual(PatternC(2n, GREEN), qcons(rcons(se_rnd_grn, rcons(sw_rnd_grn, rnil)), qcons(rcons(ne_rnd_grn, rcons(nw_rnd_grn, rnil)), qnil)));
    assert.deepStrictEqual(PatternC(2n), qcons(rcons(se_rnd_grn, rcons(sw_rnd_grn, rnil)), qcons(rcons(ne_rnd_grn, rcons(nw_rnd_grn, rnil)), qnil)));
    assert.deepStrictEqual(PatternC(2n, RED), qcons(rcons(se_rnd_red, rcons(sw_rnd_red, rnil)), qcons(rcons(ne_rnd_red, rcons(nw_rnd_red, rnil)), qnil)));

    // 0-1-many heuristic, many recursive calls (minimum 2 tests per subdomain)
    const gr_star_quilt: Quilt =  qcons(rcons(se_rnd_grn, rcons(sw_rnd_grn, rnil)), qcons(rcons(ne_rnd_grn, rcons(nw_rnd_grn, rnil)), qnil));
    const red_star_quilt: Quilt =  qcons(rcons(se_rnd_red, rcons(sw_rnd_red, rnil)), qcons(rcons(ne_rnd_red, rcons(nw_rnd_red, rnil)), qnil));
    assert.deepStrictEqual(PatternC(4n), qconcat(gr_star_quilt, gr_star_quilt));
    assert.deepStrictEqual(PatternC(6n, RED), qconcat(red_star_quilt, qconcat(red_star_quilt, red_star_quilt)));
    assert.deepStrictEqual(PatternC(8n), qconcat(gr_star_quilt, qconcat(gr_star_quilt, qconcat(gr_star_quilt, gr_star_quilt))));

    //error cases (minimum two tests per subdomain):
    assert.throws(() => PatternC(-1n, GREEN), Error);
    assert.throws(() => PatternC(3n, RED), Error);
    //extra error case test cases
    assert.throws(() => PatternC(-1n), Error);
    assert.throws(() => PatternC(-15n), Error);
    assert.throws(() => PatternC(-20n), Error);
    assert.throws(() => PatternC(9n, RED), Error);
  });

  it('PatternD', function() {
    //testing base case (0 recursive calls) (minimum 2 tests per subdomain)
    assert.deepStrictEqual(PatternD(0n, GREEN), qnil);
    assert.deepStrictEqual(PatternD(0n, RED), qnil);
    assert.deepStrictEqual(PatternD(0n), qnil);

    const gr_circle_quilt: Quilt =  qcons(rcons(nw_rnd_grn, rcons(ne_rnd_grn, rnil)), qcons(rcons(sw_rnd_grn, rcons(se_rnd_grn, rnil)), qnil));
    const red_circle_quilt: Quilt =  qcons(rcons(nw_rnd_red, rcons(ne_rnd_red, rnil)), qcons(rcons(sw_rnd_red, rcons(se_rnd_red, rnil)), qnil));

    // 0-1-many heuristic, 1 recursive call (minimum 2 tests per subdomain)
    assert.deepStrictEqual(PatternD(2n, GREEN), gr_circle_quilt);
    assert.deepStrictEqual(PatternD(2n), gr_circle_quilt);
    assert.deepStrictEqual(PatternD(2n, RED), red_circle_quilt);

    // 0-1-many heuristic, many recursive calls (minimum 2 tests per subdomain)
    assert.deepStrictEqual(PatternD(4n), qconcat(gr_circle_quilt, gr_circle_quilt));
    assert.deepStrictEqual(PatternD(6n, RED), qconcat(red_circle_quilt, qconcat(red_circle_quilt, red_circle_quilt)));
    assert.deepStrictEqual(PatternD(8n), qconcat(gr_circle_quilt, qconcat(gr_circle_quilt, qconcat(gr_circle_quilt, gr_circle_quilt))));

    //error cases (minimum two tests per subdomain):
    assert.throws(() => PatternD(-1n, GREEN), Error);
    assert.throws(() => PatternD(3n, RED), Error);
    //extra error case test cases
    assert.throws(() => PatternD(-1n), Error);
    assert.throws(() => PatternD(-6n), Error);
    assert.throws(() => PatternD(-19n), Error);
    assert.throws(() => PatternD(13n, RED), Error);
  });

  it('PatternE', function() {
    //testing base case 1 (0 recursive calls) (minimum 2 tests per subdomain)
    assert.deepStrictEqual(PatternE(0n, GREEN), qnil);
    assert.deepStrictEqual(PatternE(0n, RED), qnil);
    assert.deepStrictEqual(PatternE(0n), qnil);

    //testing base case 2 (0 recursive calls) (minimum 2 tests per subdomain)
    assert.deepStrictEqual(PatternE(1n, GREEN), qcons(rcons(ne_strt_grn, rcons(sw_strt_grn, rnil)), qnil));
    assert.deepStrictEqual(PatternE(1n, RED), qcons(rcons(ne_strt_red, rcons(sw_strt_red, rnil)), qnil));
    assert.deepStrictEqual(PatternE(1n), qcons(rcons(ne_strt_grn, rcons(sw_strt_grn, rnil)), qnil));

    const gr_row1: Row = rcons(ne_strt_grn, rcons(sw_strt_grn, rnil));
    const gr_row2: Row = rcons(se_strt_grn, rcons(nw_strt_grn, rnil));
    const red_row1: Row = rcons(ne_strt_red, rcons(sw_strt_red, rnil));
    const red_row2: Row = rcons(se_strt_red, rcons(nw_strt_red, rnil));

    // 0-1-many heuristic, 1 recursive call (minimum 2 tests per subdomain)
    //one recursive call hits base case 1
    assert.deepStrictEqual(PatternE(2n, GREEN), qcons(gr_row1, qcons(gr_row2, qnil)));
    assert.deepStrictEqual(PatternE(2n, RED), qcons(red_row1, qcons(red_row2, qnil)));
    assert.deepStrictEqual(PatternE(2n), qcons(gr_row1, qcons(gr_row2, qnil)));
    //one recursive call hits base case 2
    assert.deepStrictEqual(PatternE(3n, GREEN), qcons(gr_row1, qcons(gr_row2, qcons(gr_row1, qnil))));
    assert.deepStrictEqual(PatternE(3n, RED), qcons(red_row1, qcons(red_row2, qcons(red_row1, qnil))));
    assert.deepStrictEqual(PatternE(3n), qcons(gr_row1, qcons(gr_row2, qcons(gr_row1, qnil))));

    // 0-1-many heuristic, many recursive calls (minimum 2 tests per subdomain)
    assert.deepStrictEqual(PatternE(4n, GREEN), qcons(gr_row1, qcons(gr_row2, qcons(gr_row1, qcons(gr_row2, qnil)))));
    assert.deepStrictEqual(PatternE(5n), qcons(gr_row1, qcons(gr_row2, qcons(gr_row1, qcons(gr_row2, qcons(gr_row1, qnil))))));
    assert.deepStrictEqual(PatternE(6n, RED), qcons(red_row1, qcons(red_row2, qcons(red_row1, qcons(red_row2, qcons(red_row1, qcons(red_row2, qnil)))))));
    assert.deepStrictEqual(PatternE(7n), qcons(gr_row1, qcons(gr_row2, qcons(gr_row1, qcons(gr_row2, qcons(gr_row1, qcons(gr_row2, qcons(gr_row1, qnil))))))));

    //error cases (minimum two tests per subdomain):
    assert.throws(() => PatternE(-1n, GREEN), Error);
    assert.throws(() => PatternE(-3n, RED), Error);
    //extra error case test cases
    assert.throws(() => PatternE(-1n), Error);
    assert.throws(() => PatternE(-6n), Error);
    assert.throws(() => PatternE(-19n), Error);
    assert.throws(() => PatternE(-13n, RED), Error);
  });
});
