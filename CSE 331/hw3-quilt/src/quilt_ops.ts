import { Square, Row, rconcat, Quilt, qnil, qcons, qconcat, NE, SE, SW, NW, rnil, rcons} from './quilt';


/** Returns the same square but flipped vertically. */
export const sflip_vert = (s: Square): Square => {
  if (s.corner === NE) {
    return {shape: s.shape, color: s.color, corner: SE};
  } else if (s.corner === NW) {
    return {shape: s.shape, color: s.color, corner: SW};
  } else if (s.corner === SE) {
    return {shape: s.shape, color: s.color, corner: NE};
  } else {
    return {shape: s.shape, color: s.color, corner: NW};
  }
}

/** Returns the same row but flipped vertically. */
export const rflip_vert = (r: Row): Row => {
  if (r.kind === "rnil") {
    return rnil;
  } else {
    return rcons(sflip_vert(r.hd), rflip_vert(r.tl));
  }
}

/** Returns the same quilt but flipped vertically. */
export const qflip_vert = (q: Quilt): Quilt => {
  if (q.kind === "qnil") {
    return qnil;
  } else {
    return qconcat(qflip_vert(q.tl), qcons(rflip_vert(q.hd), qnil));
  }
}

/** Returns the same square but flipped horizontally. */
export const sflip_horz = (s: Square): Square => {
  if (s.corner === NE) {
    return {shape: s.shape, color: s.color, corner: NW};
  } else if (s.corner === NW) {
    return {shape: s.shape, color: s.color, corner: NE};
  } else if (s.corner === SE) {
    return {shape: s.shape, color: s.color, corner: SW};
  } else {
    return {shape: s.shape, color: s.color, corner: SE};
  }
}

/** Returns the same row but flipped horizontally. */
export const rflip_horz = (r: Row): Row => {
  if (r.kind === "rnil") {
    return rnil;
  } else {
    return rconcat(rflip_horz(r.tl), rcons(sflip_horz(r.hd), rnil));
  }
}

/** Returns the same quilt but flipped horizontally. */
export const qflip_horz = (q: Quilt): Quilt => {
  if (q.kind === "qnil") {
    return qnil;
  } else {
    return qcons(rflip_horz(q.hd), qflip_horz(q.tl));
  }
}

/**
 * Returns the result of sewing together q1 and q2 horizontally, i.e.,
 * concatenating each of their rows. Throws an exception if they are not the
 * same length.
 */
export const sew = (q1: Quilt, q2: Quilt): Quilt => {
  if (q1.kind === "qnil") {
    if (q2.kind === "qnil") {
      return qnil;
    } else {
      throw new Error("bad q2 argument: q1 has none rows but q2 has some");
    }
  } else {
    if (q2.kind === "qnil") {
      throw new Error("bad q1 argument: q2 has none rows but q1 has some");
    } else {
      return qcons(rconcat(q1.hd, q2.hd), sew(q1.tl, q2.tl));
    }
  }
};


/**
 * Returns the result of symmetrizing this quilt first vertically, by sewing it
 * together with its horizontally flipped version, and then horizontally, by
 * concatenating its rows with those of its vertically flipped version.
 */
export const symmetrize = (q: Quilt): Quilt => {
  const r = sew(q, qflip_horz(q));
  return qconcat(r, qflip_vert(r));
};