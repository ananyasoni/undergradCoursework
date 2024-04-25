import { Quilt, qnil, rnil, qcons, rcons, ROUND, STRAIGHT, NE, NW, SE, SW, Color, GREEN, Square, Row} from './quilt';

/** Returns a quilt in pattern "A". */
export const PatternA = (numRows: bigint, colorOfSquare?: Color): Quilt => {
  if(numRows < 0n) {
    throw new Error("number of rows must be non-negative!!!");
  }
  const squareSC: Square = {shape: ROUND, color: (colorOfSquare === undefined ? GREEN : colorOfSquare), corner: NE};
  if (numRows === 0n) {
    return qnil;
  } else {
    const row: Row = rcons(squareSC, rcons(squareSC, rnil));
    return qcons(row, PatternA(numRows - 1n, colorOfSquare));
  }
}

/** Returns a quilt in pattern "B". */
export const PatternB = (numRows: bigint, colorOfSquare?: Color): Quilt => {
  if(numRows < 0n) {
    throw new Error("number of rows must be non-negative !!!");
  }
  const squareSC: Square = {shape: STRAIGHT, color: (colorOfSquare === undefined ? GREEN : colorOfSquare), corner: NE};
  const squareTC: Square = {shape: STRAIGHT, color: (colorOfSquare === undefined ? GREEN : colorOfSquare), corner: SW};
  if (numRows === 0n) {
    return qnil;
  } else {
    const row: Row = rcons(squareSC, rcons(squareTC, rnil));
    return qcons(row, PatternB(numRows - 1n, colorOfSquare));
  }
}

/** Returns a quilt in pattern "C". */
export const PatternC = (numRows: bigint, colorOfSquare?: Color): Quilt => {
  if(numRows < 0n || numRows % 2n !== 0n) {
    throw new Error("number of rows must be non-negative and even !!!");
  }
  const squareSC: Square = {shape: ROUND, color: (colorOfSquare === undefined ? GREEN : colorOfSquare), corner: SE};
  const squareTC: Square = {shape: ROUND, color: (colorOfSquare === undefined ? GREEN : colorOfSquare), corner: SW};
  const squareUC: Square = {shape: ROUND, color: (colorOfSquare === undefined ? GREEN : colorOfSquare), corner: NE};
  const squareVC: Square = {shape: ROUND, color: (colorOfSquare === undefined ? GREEN : colorOfSquare), corner: NW};
  if (numRows === 0n) {
    return qnil;
  } else {
    const row1: Row = rcons(squareSC, rcons(squareTC, rnil));
    const row2: Row = rcons(squareUC, rcons(squareVC, rnil));
    return qcons(row1, qcons(row2, PatternC(numRows - 2n, colorOfSquare)));
  }
}

/** Returns a quilt in pattern "D". */
export const PatternD = (numRows: bigint, colorOfSquare?: Color): Quilt => {
  if(numRows < 0n || numRows % 2n !== 0n) {
    throw new Error("number of rows must be non-negative and even !!!");
  }
  const squareSC: Square = {shape: ROUND, color: (colorOfSquare === undefined ? GREEN : colorOfSquare), corner: NW};
  const squareTC: Square = {shape: ROUND, color: (colorOfSquare === undefined ? GREEN : colorOfSquare), corner: NE};
  const squareUC: Square = {shape: ROUND, color: (colorOfSquare === undefined ? GREEN : colorOfSquare), corner: SW};
  const squareVC: Square = {shape: ROUND, color: (colorOfSquare === undefined ? GREEN : colorOfSquare), corner: SE};
  if (numRows === 0n) {
    return qnil;
  } else {
    const row1: Row = rcons(squareSC, rcons(squareTC, rnil));
    const row2: Row = rcons(squareUC, rcons(squareVC, rnil));
    return qcons(row1, qcons(row2, PatternD(numRows - 2n, colorOfSquare)));
  }
}

/** Returns a quilt in pattern "E". */
export const PatternE = (numRows: bigint, colorOfSquare ?: Color): Quilt => {
  if(numRows < 0n) {
    throw new Error("number of rows must be non-negative !!!");
  }
  const squareSC: Square = {shape: STRAIGHT, color: (colorOfSquare === undefined ? GREEN : colorOfSquare), corner: NE};
  const squareTC: Square = {shape: STRAIGHT, color: (colorOfSquare === undefined ? GREEN : colorOfSquare), corner: SW};
  const squareUC: Square = {shape: STRAIGHT, color: (colorOfSquare === undefined ? GREEN : colorOfSquare), corner: SE};
  const squareVC: Square = {shape: STRAIGHT, color: (colorOfSquare === undefined ? GREEN : colorOfSquare), corner: NW};
  if (numRows === 0n) {
    return qnil;
  } else if (numRows === 1n) {
    const row1: Row = rcons(squareSC, rcons(squareTC, rnil));
    return qcons(row1, qnil);
  } else {
    const row1: Row = rcons(squareSC, rcons(squareTC, rnil));
    const row2: Row = rcons(squareUC, rcons(squareVC, rnil));
    return qcons(row1, qcons(row2, PatternE(numRows - 2n, colorOfSquare)));
  }
}