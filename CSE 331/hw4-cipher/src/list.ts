export type List<A> =
    | {readonly kind: "nil"}
    | {readonly kind: "cons", readonly hd: A, readonly tl: List<A>};


/** The empty list. */
export const nil: {readonly kind: "nil"} = {kind: "nil"};

/** Returns a list with hd in front of tl. */
export const cons = <A,>(hd: A, tl: List<A>): List<A> => {
  return {kind: "cons", hd: hd, tl: tl};
};


/** Returns the length of the given list. */
export const len = <A,>(L: List<A>): bigint => {
  if (L.kind === "nil") {
    return 0n;
  } else {
    return 1n + len(L.tl);
  }
};


/** Returns the a list consisting of L followed by R. */
export const concat = <A,>(L: List<A>, R: List<A>): List<A> => {
  if (L.kind === "nil") {
    return R;
  } else {
    return cons(L.hd, concat(L.tl, R));
  }
};


/** Returns the reverse of the given list. */
export const rev = <A,>(L: List<A>): List<A> => {
  if (L.kind === "nil") {
    return nil;
  } else {
    return concat(rev(L.tl), cons(L.hd, nil));
  }
};
