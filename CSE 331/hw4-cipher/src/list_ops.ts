import { List, cons, nil } from './list';


/** Returns the last element in the given list. */
export const last = <A,>(L: List<A>): A => {
  if (L.kind === "nil") {
      throw new Error("empty list has no last element");
  } else if (L.tl.kind === "nil") {
      return L.hd;
  } else {
      return last(L.tl);
  }
};


/**
 * This function, (prefix(n, L)), takes a natural number n and a list L of length at least n and
 * returns a list containing just the first n elements of L
 */
export const prefix = <A,>(n: bigint, L: List<A>): List<A> => {
  if (n === 0n) {
      return nil;
  } else if (L.kind === "nil") {
      throw new Error("n must be >= to the length of L!!!");
  } else {
      return cons(L.hd, prefix(n - 1n, L.tl));
  }
};


/**
 * This function (suffix(n, L)),takes a natural number n and a list L of length at least n
 * and returns a list containing everything after the first n elements of L
 */
export const suffix = <A,>(n: bigint, L: List<A>): List<A> => {
  if (n === 0n) {
    return L;
} else if (L.kind === "nil") {
    throw new Error("n must be >= to the length of L!!!");
} else {
    return suffix(n - 1n, L.tl);
}
};
