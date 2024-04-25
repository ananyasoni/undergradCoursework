export type Shape = "STRAIGHT" | "ROUND";

/** Represents a pattern with straight lines. */
export const STRAIGHT: "STRAIGHT" = "STRAIGHT";

/** Represents a pattern with rounded curves. */
export const ROUND: "ROUND" = "ROUND";


export type Color = "GREEN" | "RED";

/** Represents the color green. */
export const GREEN: "GREEN" = "GREEN";

/** Represents the color red. */
export const RED: "RED" = "RED";


export type Corner = "NW" | "NE" | "SW" | "SE";

/** Pattern oriented toward the NW corner. */
export const NW: "NW" = "NW";

/** Pattern oriented toward the NE corner. */
export const NE: "NE" = "NE";

/** Pattern oriented toward the SW corner. */
export const SW: "SW" = "SW";

/** Pattern oriented toward the SE corner. */
export const SE: "SE" = "SE";


export type Square = {
  readonly shape: Shape,
  readonly color: Color,
  readonly corner: Corner
};


export type Row = {readonly kind: "rnil"} | {readonly kind: "rcons", readonly hd: Square, readonly tl: Row};

/** The empty list of squares. */
export const rnil: {readonly kind: "rnil"} = {kind: "rnil"};

/** Returns a list of squares with hd in front of tl. */
export const rcons = (hd: Square, tl: Row): Row => {
  return {kind: "rcons", hd: hd, tl: tl};
};


export type Quilt= {readonly kind: "qnil"} | {readonly kind: "qcons", readonly hd: Row, readonly tl: Quilt};

/** The empty list of rows. */
export const qnil: {readonly kind: "qnil"} = {kind: "qnil"};

/** Returns a list of rows with hd in front of tl. */
export const qcons= (hd: Row, tl: Quilt): Quilt => {
  return {kind: "qcons", hd: hd, tl: tl};
};

/** Returns the length of the given row. */
export const rlen = (row: Row): bigint => {
  if (row.kind === "rnil") {
    return 0n;
  } else {
    return 1n + rlen(row.tl);
  }
};

/** Returns the concatenation of two rows. */
export const rconcat = (row1: Row, row2: Row): Row =>{
  if (row1.kind === "rnil") {
    return row2;
  } else {
    return rcons(row1.hd, rconcat(row1.tl, row2));
  }
};

/** Returns the length of the given quilt. */
export const qlen = (quilt: Quilt): bigint => {
  if (quilt.kind === "qnil") {
    return 0n;
  } else {
    return 1n + qlen(quilt.tl);
  }
};

/** Returns the concatenation of two quilts. */
export const qconcat = (quilt1: Quilt, quilt2: Quilt): Quilt => {
  if (quilt1.kind === "qnil") {
    return quilt2;
  } else {
    return qcons(quilt1.hd, qconcat(quilt1.tl, quilt2));
  }
};